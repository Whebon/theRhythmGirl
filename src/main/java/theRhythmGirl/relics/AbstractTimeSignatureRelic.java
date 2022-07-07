package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class AbstractTimeSignatureRelic extends CustomRelic {
    public AbstractTimeSignatureRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    private boolean replaceOldTimeSignature(){
        // time signatures will replace less restrictive time signatures
        //
        // e.g. suppose you already have a 5/4 time signature, and you obtain a 3/4 time signature
        //      then the 5/4 effectively does nothing. therefore, the 3/4 relic will replace the 5/4 relic
        //
        if (AbstractDungeon.player!=null) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                AbstractRelic otherTimeSignature = AbstractDungeon.player.relics.get(i);
                if (otherTimeSignature instanceof AbstractTimeSignatureRelic) {
                    if (((AbstractTimeSignatureRelic) otherTimeSignature).getNumberOfBeatsPerMeasure() >= getNumberOfBeatsPerMeasure()) {
                        super.instantObtain(AbstractDungeon.player, i, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void obtain(){
        if (!replaceOldTimeSignature())
            super.obtain();
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip){
        if (!replaceOldTimeSignature())
            super.instantObtain(p, slot, callOnEquip);
    }

    public abstract int getNumberOfBeatsPerMeasure();
}