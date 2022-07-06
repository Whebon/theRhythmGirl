package theRhythmGirl.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExhaustAndEtherealModifier extends AbstractCardModifier {
    public static String ID = "therhythmgirl:ExhaustAndEtherealModifier";

    private boolean appliedExhaust;
    private boolean appliedEthereal;

    public ExhaustAndEtherealModifier() {
    }

    public ExhaustAndEtherealModifier(boolean appliedExhaust, boolean appliedEthereal) {
        this.appliedExhaust = appliedExhaust;
        this.appliedEthereal = appliedEthereal;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (appliedExhaust && appliedEthereal)
            return rawDescription + " NL Exhaust. Ethereal.";
        if (appliedExhaust)
            return rawDescription + " Exhaust.";
        if (appliedEthereal)
            return rawDescription + " Ethereal.";
        return rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return (!card.exhaust || !card.isEthereal);
    }

    public void onInitialApplication(AbstractCard card) {
        this.appliedExhaust = !card.exhaust;
        this.appliedEthereal = !card.isEthereal;
        card.exhaust = true;
        card.isEthereal = true;
    }

    public void onRemove(AbstractCard card) {
        if (appliedExhaust)
            card.exhaust = false;
        if (appliedEthereal)
            card.isEthereal = false;
    }

    public AbstractCardModifier makeCopy() {
        return new ExhaustAndEtherealModifier(appliedExhaust, appliedEthereal);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
