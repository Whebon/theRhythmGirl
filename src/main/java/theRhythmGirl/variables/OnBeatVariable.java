package theRhythmGirl.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theRhythmGirl.cards.AbstractDefaultCard;

import static theRhythmGirl.DefaultMod.makeID;

public class OnBeatVariable extends DynamicVariable {


    @Override
    public String key() {
        return makeID("OnBeat");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDefaultCard) card).isOnBeatModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDefaultCard) card).onBeat;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseOnBeat;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedOnBeat;
    }
}