package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class TimeSignature34 extends AbstractTimeSignatureRelic {

    public static final String ID = RhythmGirlMod.makeID("TimeSignature34");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(TimeSignature34.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(TimeSignature34.class.getSimpleName()+".png"));

    public TimeSignature34() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(TimeSignature44.ID);}

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public int getNumberOfBeatsPerMeasure() {
        return 3;
    }
}