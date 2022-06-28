package theRhythmGirl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.relics.TimeSignature44;
import theRhythmGirl.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

import static theRhythmGirl.RhythmGirlMod.makeUIPath;

public class BeatUI
{
    public int currentBeat;

    private static final float MARSHAL_FEET_Y_OFFSET = 6.0f;
    private static final float PILLAR_SPACING_MARGIN = 6.0f;
    private static final float X_OFFSET = 16;
    private static final float Y_OFFSET = 196;
    private static final int DEFAULT_NUMBER_OF_PILLARS = 4;
    private float floatyTimer = 0;
    private final Hitbox hitbox;

    public static final String UI_ID = RhythmGirlMod.makeID("BeatUI");
    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    private static final Texture pillarTex = TextureLoader.getTexture(makeUIPath("Pillar.png"));
    private static final Texture lineTex = TextureLoader.getTexture(makeUIPath("Line.png"));
    private static final Texture marshalTex = TextureLoader.getTexture(makeUIPath("Marshal.png"));
    private final TextureAtlas.AtlasRegion pillarRegion;
    private final TextureAtlas.AtlasRegion lineRegion;
    private final TextureAtlas.AtlasRegion marshalRegion;

    private static final float MARSHAL_ANIMATION_JUMP_HEIGHT = 80;
    private static final float MARSHAL_ANIMATION_DURATION = 0.4f;
    private static final float MARSHAL_ANIMATION_DURATION_FAST = 0.05f;
    private float marshalAnimationDuration;
    private float marshalAnimationTimeElapsed;
    private float marshalAnimationX;
    private float marshalAnimationY;

    private enum MarshalAnimationTypes {NONE, WALKING, JUMPING}
    private static class MarshalAnimation{
        public MarshalAnimationTypes type;
        public int target;
        MarshalAnimation(MarshalAnimationTypes type, int target){
            this.type = type;
            this.target = target;
        }
    }
    private MarshalAnimation marshalAnimationActive;
    private List<MarshalAnimation> marshalAnimationQueue;

    public BeatUI() {
        //actual dimensions will be set in the update function
        hitbox = new Hitbox(32, 32);

        pillarRegion = new TextureAtlas.AtlasRegion(pillarTex, 0, 0, 18, 67);
        lineRegion = new TextureAtlas.AtlasRegion(lineTex, 0, 0, 63, 9);
        marshalRegion = new TextureAtlas.AtlasRegion(marshalTex, 0, 0, 48, 73);

        marshalAnimationActive = new MarshalAnimation(MarshalAnimationTypes.NONE, 1);
        currentBeat = 1;
        reset();
    }

    public void reset(){
        setMarshalAnimationDuration(false);
        marshalAnimationQueue = new ArrayList<>();
        for (int i = currentBeat-1; i > 0; i--) {
            marshalAnimationQueue.add(new MarshalAnimation(MarshalAnimationTypes.WALKING, i));
        }
        marshalAnimationTimeElapsed = 0;
        marshalAnimationX = 0;
        marshalAnimationY = 0;
        currentBeat = 1;
        updateTimeSignatureRelicCounters();
    }

    private void updateMarshalAnimation(){
        if (marshalAnimationActive.type == MarshalAnimationTypes.NONE){
            if (marshalAnimationQueue.size() > 0){
                marshalAnimationTimeElapsed = 0;
                marshalAnimationActive = marshalAnimationQueue.get(0);
                marshalAnimationQueue.remove(0);
                }
            else{
                setMarshalAnimationDuration(false);
            }
        }

        //set target X, Y
        int target = marshalAnimationActive.target;
        if (target == 1 && marshalAnimationActive.type == MarshalAnimationTypes.JUMPING &&  marshalAnimationTimeElapsed <= marshalAnimationDuration/2)
            target = 5;
        marshalAnimationX = getX()+(target-1)*getPillarSpacing()*Settings.scale
                -marshalRegion.getRegionWidth()/2.0f*Settings.scale;
        marshalAnimationY = getY()+MARSHAL_FEET_Y_OFFSET*Settings.scale;
        marshalAnimationTimeElapsed += Gdx.graphics.getDeltaTime();

        //walking animation (to the left)
        if(marshalAnimationActive.type == MarshalAnimationTypes.WALKING){
            //todo: add animation frames between [marshalAnimationTimeElapsed/marshalAnimationDuration] for walking
            if (marshalAnimationTimeElapsed > marshalAnimationDuration){
                marshalAnimationTimeElapsed = marshalAnimationDuration;
                marshalAnimationActive.type = MarshalAnimationTypes.NONE;
            }
            float t = marshalAnimationTimeElapsed/marshalAnimationDuration;
            marshalAnimationX += getPillarSpacing()*Settings.scale*(1-t);
        }

        //jumping animation (to the right)
        if(marshalAnimationActive.type == MarshalAnimationTypes.JUMPING){
            //todo: add animation frames between [marshalAnimationTimeElapsed/marshalAnimationDuration] for jumping
            if (marshalAnimationTimeElapsed > marshalAnimationDuration){
                marshalAnimationTimeElapsed = marshalAnimationDuration;
                marshalAnimationActive.type = MarshalAnimationTypes.NONE;
            }
            float t = marshalAnimationTimeElapsed/marshalAnimationDuration;
            marshalAnimationX -= getPillarSpacing()*Settings.scale*(1-t);
            marshalAnimationY += 4*t*(1-t)*MARSHAL_ANIMATION_JUMP_HEIGHT;
        }
    }

    private void setMarshalAnimationDuration(boolean fast){
        marshalAnimationDuration = fast ? MARSHAL_ANIMATION_DURATION_FAST : MARSHAL_ANIMATION_DURATION;
    }

    public void gainBeats(int amount){
        if (amount > 4){
            setMarshalAnimationDuration(true);
        }
        int n = getNumberOfPillars();
        if (n <= 0)
            throw new AssertionError("getNumberOfPillars must be positive to prevent an infinite loop");
        for (int i = currentBeat+1; i <= currentBeat+amount; i++) {
            marshalAnimationQueue.add(new MarshalAnimation(MarshalAnimationTypes.JUMPING, (i-1)%n+1));
        }
        currentBeat += amount;
        while (currentBeat>n){
            currentBeat -= n;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new MeasurePower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        }
        updateTimeSignatureRelicCounters();
    }

    public void updateTimeSignatureRelicCounters(){
        if (AbstractDungeon.player != null){
            if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
                AbstractDungeon.player.getRelic(TimeSignature44.ID).counter = currentBeat;
        }
    }

    public float getPillarSpacing(){
        return (lineRegion.getRegionWidth()+pillarRegion.getRegionWidth()+PILLAR_SPACING_MARGIN);
    }

    public int getNumberOfPillars() {
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
            return 4;
        logger.error("Player has no time signature relic");
        return DEFAULT_NUMBER_OF_PILLARS;
    }

    public float getX() {
        AbstractPlayer player = AbstractDungeon.player;
        return X_OFFSET * Settings.scale + player.drawX - (getPillarSpacing() * (getNumberOfPillars()-1)/2.0f * Settings.scale);
    }

    public float getY() {
        AbstractPlayer player = AbstractDungeon.player;
        float yPos = Y_OFFSET * Settings.scale + player.drawY + player.hb_h / 2.0f;
        if (!player.orbs.isEmpty()) {
            for (AbstractOrb orb : player.orbs) {
                if (orb.cY + orb.hb.height / 2f + pillarRegion.getRegionHeight() > yPos) {
                    yPos = orb.cY + orb.hb.height / 2f + pillarRegion.getRegionHeight();
                }
            }
        }
        return yPos;
    }

    public void update(AbstractPlayer player) {
        hitbox.resize(
                ((getNumberOfPillars()-1)*getPillarSpacing()+pillarRegion.getRegionWidth())*Settings.scale,
                (pillarRegion.getRegionHeight()+marshalRegion.getRegionHeight()*2.0f)*Settings.scale
        );
        hitbox.translate(
                getX()-pillarRegion.getRegionWidth()/2.0f*Settings.scale,
                getY()-pillarRegion.getRegionHeight()*Settings.scale);
        hitbox.update();
    }

    public void render(SpriteBatch sb, AbstractPlayer player) {
        if (AbstractDungeon.getCurrMapNode() != null
                && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                || AbstractDungeon.getCurrRoom() instanceof MonsterRoom)
                && !player.isDead
        ) {
            //draw marshal
            updateMarshalAnimation();
            sb.setColor(Color.WHITE);
            sb.draw(
                    marshalRegion,
                    marshalAnimationX,
                    marshalAnimationY,
                    0,
                    0,
                    marshalRegion.getRegionWidth(),
                    marshalRegion.getRegionHeight(),
                    Settings.scale,
                    Settings.scale,
                    0
            );

            //set the offset for the floaty effect
            floatyTimer += Gdx.graphics.getDeltaTime() * 2;
            float floatyOffset = 1.5f * (float) Math.sin(floatyTimer - 1.2) * Settings.scale;

            for (int iPillar = 0; iPillar < getNumberOfPillars(); iPillar++) {
                //draw the pillars
                sb.setColor(Color.WHITE);
                sb.draw(
                        pillarRegion,
                        getX()+iPillar*getPillarSpacing()*Settings.scale
                                - pillarRegion.getRegionWidth()/2.0f*Settings.scale,
                        getY()+floatyOffset
                                - pillarRegion.getRegionHeight()*Settings.scale,
                        0,
                        0,
                        pillarRegion.getRegionWidth(),
                        pillarRegion.getRegionHeight(),
                        Settings.scale,
                        Settings.scale,
                        0
                );
                if (iPillar != 0) {
                    //draw lines between pillars
                    sb.setColor(Color.WHITE);
                    sb.draw(
                            lineRegion,
                            getX() + (iPillar - 0.5f) * getPillarSpacing() * Settings.scale
                                    - lineRegion.getRegionWidth() / 2.0f * Settings.scale,
                            getY() + floatyOffset
                                    - lineRegion.getRegionHeight() * Settings.scale,
                            0,
                            0,
                            lineRegion.getRegionWidth(),
                            lineRegion.getRegionHeight(),
                            Settings.scale,
                            Settings.scale,
                            0
                    );
                }
            }

            // draw a tooltip
            if (hitbox.hovered && !AbstractDungeon.isScreenUp) {
                String body = UIStrings.TEXT[1]+currentBeat+UIStrings.TEXT[2];

                float height = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, body, 280.0F * Settings.scale, 26.0F * Settings.scale);
                TipHelper.renderGenericTip(
                        hitbox.x + hitbox.width + 4*Settings.scale,
                        hitbox.y + hitbox.height/2 + height/2 + 74 * Settings.scale,
                        UIStrings.TEXT[0],
                        body
                );
            }

            hitbox.render(sb);
        }
    }
}
