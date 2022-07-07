package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class TimeSignature24 extends CustomRelic implements TimeSignature {

    public static final String ID = RhythmGirlMod.makeID("TimeSignature24");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(TimeSignature24.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(TimeSignature24.class.getSimpleName()+".png"));

    public TimeSignature24() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void obtain() {
        // Overwrite TimeSignature44, or just give the relic if no time signature is found
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(TimeSignature44.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(TimeSignature44.ID);}

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public int getNumberOfBeatsPerMeasure() {
        return 2;
    }
}