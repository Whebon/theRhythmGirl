package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class TimeSignature34 extends CustomRelic implements TimeSignature {

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
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip){
        // This method is called by StoreRelic.purchaseRelic
        // Try to overwrite TimeSignature44 instead of taking up a new slot
        for (int i=0; i<AbstractDungeon.player.relics.size(); i++) {
            if (AbstractDungeon.player.relics.get(i).relicId.equals(TimeSignature44.ID)) {
                slot = i;
                break;
            }
        }
        super.instantObtain(p, slot, callOnEquip);
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