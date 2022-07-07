package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

//this relic is held by the BeatUI and used whenever the player does not have an actual 'Time Signature' relic.

public class TimeSignature54 extends AbstractTimeSignatureRelic {

    public static final String ID = RhythmGirlMod.makeID("TimeSignature54");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(TimeSignature54.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(TimeSignature54.class.getSimpleName()+".png"));

    public TimeSignature54() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public int getNumberOfBeatsPerMeasure() {
        return 5;
    }
}