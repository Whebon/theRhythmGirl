package theRhythmGirl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.AbstractRhythmGirlCard;
import theRhythmGirl.powers.*;
import theRhythmGirl.relics.*;
import theRhythmGirl.util.TextureLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static theRhythmGirl.RhythmGirlMod.*;

public class BeatUI
{
    public int currentBeat;
    public int measuresGainedThisTurn = 0;
    private int previousHandSize = 0;

    private static final float MAX_FLOATY_OFFSET = 2.0f;
    private static final float MARSHAL_FEET_Y_OFFSET = 0.0f;
    private static final float PILLAR_SPACING_MARGIN = 6.0f;
    private static final float X_OFFSET = 16;
    private static final float Y_OFFSET = 196;
    private float floatyTimer = 0;
    private final Hitbox hitbox;

    public static final String UI_ID = RhythmGirlMod.makeID("BeatUI");
    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    private static final Texture pillarTex = TextureLoader.getTexture(makeUIPath("Pillar.png"));
    private static final Texture lineTex = TextureLoader.getTexture(makeUIPath("Line.png"));
    private static final Texture lineTexRhythmHeaven = TextureLoader.getTexture(makeUIPath("LineRhythmHeaven.png"));
    private static final Texture marshalTex = TextureLoader.getTexture(makeUIPath("Marshal.png"));
    private final HashMap<Integer, TextureAtlas.AtlasRegion> pillarRegions;
    private final TextureAtlas.AtlasRegion lineRegion;
    private final TextureAtlas.AtlasRegion lineRegionRhythmHeaven;
    private TextureAtlas.AtlasRegion marshalRegion;

    public enum BeatColor {NORMAL, RED, GREEN, BLUE, ON_BEAT, CUED, RHYTHM_HEAVEN}
    private final HashMap<BeatColor, TextureAtlas.AtlasRegion> allPillarRegions;
    private final List<TextureAtlas.AtlasRegion> allMarshalRegions;
    private static final float MARSHAL_ANIMATION_TOTAL_IMAGES = 34;
    private static class Keyframe{
        Integer frame;
        Float until;
        Keyframe(Integer frame, Float until){
            this.frame = frame;
            this.until = until;
        }
    }
    private static final int MARSHAL_ANIMATION_IDLE_NUMBER_OF_FRAMES = 6;
    List<Keyframe> MARSHAL_ANIMATION_IDLE_KEYFRAMES = new ArrayList<Keyframe>() {{
        add(new Keyframe(10,0.1666f));
        add(new Keyframe(11, 0.3333f));
        add(new Keyframe(12, 0.5f));
        add(new Keyframe(13, 0.666f));
        add(new Keyframe(14, 0.8333f));
        add(new Keyframe(15, 1.0f));
    }};
    private static final int MARSHAL_ANIMATION_WALK_NUMBER_OF_FRAMES = 6;
    List<Keyframe> MARSHAL_ANIMATION_WALK_KEYFRAMES = new ArrayList<Keyframe>() {{
        add(new Keyframe(22,0.1666f));
        add(new Keyframe(23, 0.3333f));
        add(new Keyframe(24, 0.5f));
        add(new Keyframe(25, 0.666f));
        add(new Keyframe(26, 0.8333f));
        add(new Keyframe(27, 1.0f));
    }};
    private static final int MARSHAL_ANIMATION_JUMP_NUMBER_OF_FRAMES = 5;
    List<Keyframe> MARSHAL_ANIMATION_JUMP_KEYFRAMES = new ArrayList<Keyframe>() {{
        add(new Keyframe(0, 0.3125f));
        add(new Keyframe(1, 0.375f));
        add(new Keyframe(2, 0.4375f));
        add(new Keyframe(3, 0.5f));
        add(new Keyframe(4, 0.75f));
        add(new Keyframe(3, 0.8125f));
        add(new Keyframe(2, 0.875f));
        add(new Keyframe(1, 0.9375f));
        add(new Keyframe(0, 1.0f));
    }};

    private static final int MINIMUM_NUMBER_OF_ANIMATIONS_TO_ACTIVATE_FAST_MODE = 6;
    private static final float MARSHAL_ANIMATION_IDLE_DURATION = 0.6f;
    private static final float MARSHAL_ANIMATION_IDLE_DURATION_FAST = 0.05f;
    private static final float MARSHAL_ANIMATION_WALK_DURATION = 0.4f;
    private static final float MARSHAL_ANIMATION_WALK_DURATION_FAST = 0.05f;
    private static final float MARSHAL_ANIMATION_JUMP_DURATION = 0.4f;
    private static final float MARSHAL_ANIMATION_JUMP_DURATION_FAST = 0.05f;
    private boolean marshalAnimationIsFast;
    private float marshalAnimationDuration;
    private float marshalAnimationTimeElapsed;

    private static final float MARSHAL_ANIMATION_JUMP_HEIGHT = 80;
    private boolean marshalAnimationLeft;
    private float marshalAnimationX;
    private float marshalAnimationY;

    private enum MarshalAnimationTypes {IDLE, WALKING, JUMPING}
    private static class MarshalAnimation{
        public MarshalAnimationTypes type;
        public int target;
        public boolean playCountingSfx;
        MarshalAnimation(MarshalAnimationTypes type, int target){
            this.type = type;
            this.target = target;
        }
        MarshalAnimation(MarshalAnimationTypes type, int target, boolean playCountingSfx){
            this.type = type;
            this.target = target;
            this.playCountingSfx=playCountingSfx;
        }
    }
    private MarshalAnimation marshalAnimationActive;
    private List<MarshalAnimation> marshalAnimationQueue;

    public BeatUI() {
        //actual dimensions will be set in the update function
        hitbox = new Hitbox(32, 32);

        pillarRegions = new HashMap<>();
        allPillarRegions = new HashMap<>();
        TextureAtlas.AtlasRegion normalPillarRegion = new TextureAtlas.AtlasRegion(pillarTex, 0, 0, 18, 67);
        allPillarRegions.put(BeatColor.NORMAL, normalPillarRegion);
        allPillarRegions.put(BeatColor.RED, normalPillarRegion);
        allPillarRegions.put(BeatColor.GREEN, normalPillarRegion);
        allPillarRegions.put(BeatColor.BLUE, normalPillarRegion);
        allPillarRegions.put(BeatColor.ON_BEAT, new TextureAtlas.AtlasRegion(pillarTex, 18, 0, 18, 67));
        allPillarRegions.put(BeatColor.CUED, new TextureAtlas.AtlasRegion(pillarTex, 36, 0, 18, 67));
        allPillarRegions.put(BeatColor.RHYTHM_HEAVEN, new TextureAtlas.AtlasRegion(pillarTex, 54, 0, 18, 67));
        lineRegion = new TextureAtlas.AtlasRegion(lineTex, 0, 0, 63, 9);
        lineRegionRhythmHeaven = new TextureAtlas.AtlasRegion(lineTexRhythmHeaven, 0, 0, 63, 9);
        allMarshalRegions = new ArrayList<>();
        for (int i = 0; i < MARSHAL_ANIMATION_TOTAL_IMAGES; i++) {
            allMarshalRegions.add(new TextureAtlas.AtlasRegion(marshalTex, 55*i, 0, 55,85));
        }

        marshalAnimationLeft = false;
        marshalAnimationActive = new MarshalAnimation(MarshalAnimationTypes.IDLE, 1);
        currentBeat = 1;
        reset();
    }

    public void reset(boolean atTurnStart){
        if (atTurnStart){
            measuresGainedThisTurn = 0;
        }
        reset();
    }

    public void reset(){
        marshalAnimationIsFast = false;
        marshalAnimationQueue = new ArrayList<>();
        for (int i = currentBeat-1; i > 0; i--) {
            marshalAnimationQueue.add(new MarshalAnimation(MarshalAnimationTypes.WALKING, i));
        }
        marshalAnimationTimeElapsed = 0;
        marshalAnimationX = 0;
        marshalAnimationY = 0;
        currentBeat = 1;
        setMarshalAnimationDuration();
        setMarshalRegion();
    }

    private void updateMarshalAnimation(){
        //animation timing
        setMarshalAnimationDuration();
        marshalAnimationTimeElapsed += Gdx.graphics.getDeltaTime();
        if (marshalAnimationTimeElapsed > marshalAnimationDuration || (marshalAnimationQueue.size() > 0 && marshalAnimationActive.type == MarshalAnimationTypes.IDLE)){
            marshalAnimationLeft = !marshalAnimationLeft;
            marshalAnimationTimeElapsed = Math.max(0, marshalAnimationTimeElapsed-marshalAnimationDuration);
            if (marshalAnimationQueue.size() > 0){
                //next animation in queue
                marshalAnimationActive = marshalAnimationQueue.get(0);
                if (marshalAnimationActive.playCountingSfx){
                    CardCrawlGame.sound.play("COWBELL");
                    /*
                    //play sound based on current beat
                    //while cool on paper, shouting out beat numbers doesn't work with the vibe of the game
                    //the girl shouting '3, 4, 1' for a card to be played 'on 2' is disorientating
                    int n = getNumberOfPillars();
                    int sfxIndex = (marshalAnimationActive.target+n-2)%n+1;
                    if (sfxIndex >= 1 && sfxIndex<=4)
                        CardCrawlGame.sound.play("COUNT_" + sfxIndex);
                    else
                        CardCrawlGame.sound.play("COWBELL");
                     */
                }
                marshalAnimationQueue.remove(0);
            }
            else{
                //back to idle
                marshalAnimationIsFast = false;
                marshalAnimationActive.type = MarshalAnimationTypes.IDLE;
            }
        }
        setMarshalRegion();

        //set target X, Y
        int target = marshalAnimationActive.target;
        if (target == 1 && marshalAnimationActive.type == MarshalAnimationTypes.JUMPING &&  marshalAnimationTimeElapsed <= marshalAnimationDuration/2)
            target = getNumberOfPillars()+1;
        marshalAnimationX = getX()+(target-1)*getPillarSpacing()*Settings.scale
                -marshalRegion.getRegionWidth()/2.0f*Settings.scale;
        marshalAnimationY = getY()+MARSHAL_FEET_Y_OFFSET*Settings.scale;

        //walking animation (to the left)
        if(marshalAnimationActive.type == MarshalAnimationTypes.WALKING){
            float t = marshalAnimationTimeElapsed/marshalAnimationDuration;
            marshalAnimationX += getPillarSpacing()*Settings.scale*(1-t);
        }

        //jumping animation (to the right)
        if(marshalAnimationActive.type == MarshalAnimationTypes.JUMPING){
            float t = marshalAnimationTimeElapsed/marshalAnimationDuration;
            marshalAnimationX -= getPillarSpacing()*Settings.scale*(1-t);
            marshalAnimationY += 4*t*(1-t)*MARSHAL_ANIMATION_JUMP_HEIGHT*Settings.scale;
        }
    }

    private void setMarshalRegion(){
        float t = marshalAnimationTimeElapsed/marshalAnimationDuration;
        List<Keyframe> keyframes = null;
        int leftStartsAtFrame = 0;
        switch (marshalAnimationActive.type) {
            case IDLE:
                leftStartsAtFrame = MARSHAL_ANIMATION_IDLE_NUMBER_OF_FRAMES;
                keyframes = MARSHAL_ANIMATION_IDLE_KEYFRAMES;
                break;
            case WALKING:
                leftStartsAtFrame = MARSHAL_ANIMATION_WALK_NUMBER_OF_FRAMES;
                keyframes = MARSHAL_ANIMATION_WALK_KEYFRAMES;
                break;
            case JUMPING:
                leftStartsAtFrame = MARSHAL_ANIMATION_JUMP_NUMBER_OF_FRAMES;
                keyframes = MARSHAL_ANIMATION_JUMP_KEYFRAMES;
                break;
        }
        for (Keyframe keyframe : keyframes) {
            if (t < keyframe.until) {
                int frame = keyframe.frame;
                if (marshalAnimationLeft)
                    frame += leftStartsAtFrame;
                marshalRegion = allMarshalRegions.get(frame);
                return;
            }
        }
    }

    private void setMarshalAnimationDuration(){
        switch (marshalAnimationActive.type) {
            case IDLE:
                marshalAnimationDuration = marshalAnimationIsFast ? MARSHAL_ANIMATION_IDLE_DURATION_FAST : MARSHAL_ANIMATION_IDLE_DURATION;
                break;
            case WALKING:
                marshalAnimationDuration = marshalAnimationIsFast ? MARSHAL_ANIMATION_WALK_DURATION_FAST : MARSHAL_ANIMATION_WALK_DURATION;
                break;
            case JUMPING:
                marshalAnimationDuration = marshalAnimationIsFast ? MARSHAL_ANIMATION_JUMP_DURATION_FAST : MARSHAL_ANIMATION_JUMP_DURATION;
                break;
        }
    }

    public boolean isAnimationPlaying(){
        return (marshalAnimationQueue.size() > 0 || !(marshalAnimationActive.type == MarshalAnimationTypes.IDLE));
    }

    private void updatePillarRegions() {
        for (int iPillar = 0; iPillar < getNumberOfPillars(); iPillar++) {
            pillarRegions.put(iPillar, allPillarRegions.get(BeatColor.NORMAL));
        }
        AbstractPlayer player = AbstractDungeon.player;
        for (int iPillar = 0; iPillar < getNumberOfPillars(); iPillar++) {
            BeatColor highestPriorityBeatColor = BeatColor.NORMAL;
            for (AbstractCard handAbstractCard : player.hand.group) {
                if (handAbstractCard instanceof AbstractRhythmGirlCard) {
                    AbstractRhythmGirlCard handCard = ((AbstractRhythmGirlCard) handAbstractCard);
                    BeatColor beatColor = handCard.onBeatColor.get(iPillar+1);
                    if (beatColor.ordinal() > highestPriorityBeatColor.ordinal()){
                        highestPriorityBeatColor = beatColor;
                        pillarRegions.put(iPillar, allPillarRegions.get(beatColor));
                    }
                }
            }
        }
    }

    public void publishOnGainBeat(int numberOfBeatsGained){
        logger.info("Publishing OnBeat (for AbstractCountdownPower)");
        for (AbstractCreature m : AbstractDungeon.getMonsters().monsters){
            for (AbstractPower p : m.powers){
                if (p instanceof AbstractCountdownPower){
                    ((AbstractCountdownPower) p).onGainBeat(numberOfBeatsGained);
                }
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof AbstractCountdownPower){
                ((AbstractCountdownPower) p).onGainBeat(numberOfBeatsGained);
            }
        }
        logger.info("Published OnBeat (for AbstractCountdownPower)");
    }

    public void publishOnGainMeasure(int numberOfMeasuresGained){
        //note: this can be refactored into 'onPowerGain' instead publishing 'onGainMeasure' specifically
        logger.info("Publishing OnMeasure (for relics)");
        for (AbstractRelic r : AbstractDungeon.player.relics)
            if (r instanceof OnGainMeasureSubscriber)
                ((OnGainMeasureSubscriber)r).onGainMeasure(numberOfMeasuresGained);
        logger.info("Published OnMeasure (for relics)");
    }

    public void gainBeatsUntil(int targetBeat){
        while (targetBeat<=currentBeat){
            targetBeat += getNumberOfPillars();
        }
        gainBeats(targetBeat-currentBeat, true);
    }

    public void gainBeats(int amount){
        this.gainBeats(amount, false);
    }

    public void gainBeats(int amount, boolean playCountingSfx){
        if (amount <= 0){
            return;
        }
        if (AbstractDungeon.player.hasPower(CoffeeBreakPower.POWER_ID)){
            AbstractDungeon.player.getPower(CoffeeBreakPower.POWER_ID).flashWithoutSound();
            return;
        }
        publishOnGainBeat(amount);
        if (amount >= MINIMUM_NUMBER_OF_ANIMATIONS_TO_ACTIVATE_FAST_MODE)
            marshalAnimationIsFast = true;
        int n = getNumberOfPillars();
        if (n <= 0)
            throw new AssertionError("getNumberOfPillars must be positive to prevent an infinite loop");
        for (int i = currentBeat+1; i <= currentBeat+amount; i++) {
            marshalAnimationQueue.add(new MarshalAnimation(MarshalAnimationTypes.JUMPING, (i-1)%n+1, playCountingSfx));
        }
        currentBeat += amount;
        while (currentBeat>n){
            currentBeat -= n;
            publishOnGainMeasure(1);
            measuresGainedThisTurn += 1;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new MeasurePower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        }
        logger.info(String.format("BeatUI added %d beats", amount));
    }

    public float getPillarSpacing(){
        return (lineRegion.getRegionWidth()+allPillarRegions.get(BeatColor.NORMAL).getRegionWidth()+PILLAR_SPACING_MARGIN);
    }

    public AbstractTimeSignatureRelic getTimeSignatureRelic(){
        if (AbstractDungeon.player.hasRelic(TimeSignature24.ID))
            return (AbstractTimeSignatureRelic)AbstractDungeon.player.getRelic(TimeSignature24.ID);
        if (AbstractDungeon.player.hasRelic(TimeSignature34.ID))
            return (AbstractTimeSignatureRelic)AbstractDungeon.player.getRelic(TimeSignature34.ID);
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
            return (AbstractTimeSignatureRelic)AbstractDungeon.player.getRelic(TimeSignature44.ID);
        if (AbstractDungeon.player.hasRelic(TimeSignature54.ID))
            return (AbstractTimeSignatureRelic)AbstractDungeon.player.getRelic(TimeSignature54.ID);
        logger.info("No TimeSignature Relic found. The player will now obtain a 'TimeSignature54'");
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new TimeSignature54());
        return getTimeSignatureRelic();
    }

    public int getNumberOfPillars() {
        return getTimeSignatureRelic().getNumberOfBeatsPerMeasure();
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
                if (orb.cY + orb.hb.height / 2f + allPillarRegions.get(BeatColor.NORMAL).getRegionHeight() > yPos) {
                    yPos = orb.cY + orb.hb.height / 2f + allPillarRegions.get(BeatColor.NORMAL).getRegionHeight();
                }
            }
        }
        return yPos;
    }

    public void update(AbstractPlayer player) {
        //update the hitbox
        //if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {}
        TextureAtlas.AtlasRegion pillarRegion = allPillarRegions.get(BeatColor.NORMAL);
        hitbox.resize(
                ((getNumberOfPillars() - 1) * getPillarSpacing() + pillarRegion.getRegionWidth()) * Settings.scale,
                (pillarRegion.getRegionHeight() + marshalRegion.getRegionHeight() * 2.0f) * Settings.scale
        );
        hitbox.translate(
                getX() - pillarRegion.getRegionWidth() / 2.0f * Settings.scale,
                getY() - pillarRegion.getRegionHeight() * Settings.scale);
        hitbox.update();

        //update the pillar regions whenever the hand size is modified (the colors on the beat UI indicating if you have ON_BEAT cards for that beat)
        if (AbstractDungeon.player.hand.size() != previousHandSize) {
            updatePillarRegions();
            previousHandSize = AbstractDungeon.player.hand.size();
        }
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
            float floatyOffset = MAX_FLOATY_OFFSET * (float) Math.sin(floatyTimer - 1.2) * Settings.scale;

            for (int iPillar = 0; iPillar < getNumberOfPillars(); iPillar++) {
                //draw the pillars
                TextureAtlas.AtlasRegion pillarRegion = (AbstractDungeon.player.hasPower(RhythmHeavenPower.POWER_ID) || AbstractDungeon.player.hasPower(SeeingHeavenPower.POWER_ID)) ?
                        allPillarRegions.get(BeatColor.RHYTHM_HEAVEN) : pillarRegions.getOrDefault(iPillar, allPillarRegions.get(BeatColor.NORMAL));
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
                    sb.draw((AbstractDungeon.player.hasPower(RhythmHeavenPower.POWER_ID) || AbstractDungeon.player.hasPower(SeeingHeavenPower.POWER_ID)) ?
                                    lineRegionRhythmHeaven : lineRegion,
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

            //tooltips
            if (hitbox.hovered && !AbstractDungeon.isScreenUp) {
                String body = UIStrings.TEXT[1]+currentBeat+UIStrings.TEXT[2];
                float height = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, body, 280.0F * Settings.scale, 26.0F * Settings.scale);

                ArrayList<PowerTip> tips = new ArrayList<>();
                tips.add(new PowerTip(UIStrings.TEXT[0], body));
                if (AbstractDungeon.player.hasPower(RhythmHeavenPower.POWER_ID)){
                    AbstractPower power = AbstractDungeon.player.getPower(RhythmHeavenPower.POWER_ID);
                    tips.add(new PowerTip(power.name, power.description, power.region48));
                }
                else if (AbstractDungeon.player.hasPower(SeeingHeavenPower.POWER_ID)){
                    AbstractPower power = AbstractDungeon.player.getPower(SeeingHeavenPower.POWER_ID);
                    tips.add(new PowerTip(power.name, power.description, power.region48));
                }

                AbstractTimeSignatureRelic timeSignatureRelic = getTimeSignatureRelic();
                tips.add(new PowerTip(timeSignatureRelic.name, timeSignatureRelic.getTooltip(), timeSignatureRelic.largeImg));

                TipHelper.queuePowerTips(
                        hitbox.x + hitbox.width + 16*Settings.scale,
                        hitbox.y + hitbox.height/2 + height/2 + 74 * Settings.scale,
                        tips
                );

                /*
                TipHelper.renderGenericTip(
                        hitbox.x + hitbox.width + 12*Settings.scale,
                        hitbox.y + hitbox.height/2 + height/2 + 74 * Settings.scale,
                        UIStrings.TEXT[0],
                        body
                );
                */
            }

            hitbox.render(sb);
        }
    }
}
