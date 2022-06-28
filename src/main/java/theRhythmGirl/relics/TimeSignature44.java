package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class TimeSignature44 extends CustomRelic {

    public static final String ID = RhythmGirlMod.makeID("TimeSignature44");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("timesignature44.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("timesignature44.png"));

    public TimeSignature44() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }
}