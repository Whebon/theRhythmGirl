package theRhythmGirl.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

//old version: replaced the 44 time signature relic instead of the 43 time signature relic.

//idea: add an act 3 event that replaces 34 time signature into 24 for a high price (curse, 30% ,max hp, 300 gold)

public class TimeSignature24 extends AbstractTimeSignatureRelic {

    public static final String ID = RhythmGirlMod.makeID("TimeSignature24");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(TimeSignature24.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(TimeSignature24.class.getSimpleName()+".png"));

    public TimeSignature24() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[1] + DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(TimeSignature34.ID);}

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public int getNumberOfBeatsPerMeasure() {
        return 2;
    }
}