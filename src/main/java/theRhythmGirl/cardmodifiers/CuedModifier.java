package theRhythmGirl.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;

public class CuedModifier extends AbstractCardModifier {
    public static String ID = "therhythmgirl:Cued";

    public CuedModifier() {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "therhythmgirl:Cued. NL " + rawDescription;
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

    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        if (otherCard.type == AbstractCard.CardType.ATTACK){
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
    }

    public AbstractCardModifier makeCopy() {
        return new CuedModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
