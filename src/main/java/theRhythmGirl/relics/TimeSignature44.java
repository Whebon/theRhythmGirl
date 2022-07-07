package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.BossaNova;
import theRhythmGirl.util.TextureLoader;

import java.sql.Time;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class TimeSignature44 extends AbstractTimeSignatureRelic {

    public static final String ID = RhythmGirlMod.makeID("TimeSignature44");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(TimeSignature44.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(TimeSignature44.class.getSimpleName()+".png"));

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

    @Override
    public int getNumberOfBeatsPerMeasure() {
        return 4;
    }
}