package theRhythmGirl.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theRhythmGirl.cards.AbstractRhythmGirlCard;

import static theRhythmGirl.RhythmGirlMod.makeID;

//in this case all methods depend on custom 'magicNumber2' fields
//theoretically, they can depend on existing fields, like 'baseDamage'
//when creating new custom variables, don't forget to add them in the main mod class using 'BaseMod.addDynamicVariable'
public class MagicNumber2 extends DynamicVariable {
    @Override
    public String key() {
        // This is what you type in your card string to make the variable show up. Remember to encase it in "!"'s in the json!
        return makeID("MagicNumber2");
    }

    @Override
    public boolean isModified(AbstractCard card) { return ((AbstractRhythmGirlCard) card).isMagicNumber2Modified;}

    @Override
    public int value(AbstractCard card) {return ((AbstractRhythmGirlCard) card).magicNumber2;}

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractRhythmGirlCard) card).baseMagicNumber2;
    }

    @Override
    public boolean upgraded(AbstractCard card) {return ((AbstractRhythmGirlCard) card).upgradedMagicNumber2;}
}