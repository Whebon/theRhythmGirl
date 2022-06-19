package theRhythmGirl.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class RepeatModifier extends AbstractCardModifier {
    public RepeatModifier() {
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard newCard = card.makeCopy();
        CardModifierManager.removeModifiersById(newCard, "Repeat", true);
        newCard.exhaust = true;
        newCard.rawDescription = newCard.rawDescription.replace("Repeat", "Exhaust");
        newCard.initializeDescription();
        if (card.upgraded) {
            newCard.upgrade();
        }
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(newCard, 1));
    }

    @Override
    public String identifier(AbstractCard card) {
        return "Repeat";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RepeatModifier();
    }
}
