package theRhythmGirl.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theRhythmGirl.cards.AbstractDefaultCard;

import static theRhythmGirl.DefaultMod.makeID;

public class SecondOnBeatVariable extends DynamicVariable {


    @Override
    public String key() {
        return makeID("SecondOnBeat");
        // note to self: if the formatting on keywords doesn't work: use [#efc851]keyword[] to fake it.
        // e.g. therhythmgirl:On_Beat !theRhythmGirl:OnBeat! [#efc851]or[] !theRhythmGirl:SecondOnBeat!:
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDefaultCard) card).isSecondOnBeatModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDefaultCard) card).secondOnBeat;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseSecondOnBeat;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedSecondOnBeat;
    }
}