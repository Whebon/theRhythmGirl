package theRhythmGirl.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;

public class ExhaustAndEtherealModifier extends AbstractCardModifier {
    public static String ID = "therhythmgirl:ExhaustAndEtherealModifier";

    public ExhaustAndEtherealModifier() {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Exhaust. Ethereal.";
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.exhaust;
    }

    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
        card.isEthereal = true;
    }

    public void onRemove(AbstractCard card) {
        card.exhaust = false;
        card.isEthereal = false;
    }

    public AbstractCardModifier makeCopy() {
        return new ExhaustAndEtherealModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
