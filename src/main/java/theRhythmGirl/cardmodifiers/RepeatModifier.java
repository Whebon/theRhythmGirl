package theRhythmGirl.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.cards.AbstractRhythmGirlCard;


public class RepeatModifier extends AbstractCardModifier {
    public static String ID = "therhythmgirl:RepeatCardModifier";

    public RepeatModifier() {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //todo: check if loadCardImage also loads the correct "_p" image (for the third Repeat card).
        ((AbstractRhythmGirlCard)card).loadAlternativeCardImage();
        return rawDescription + " NL therhythmgirl:Repeat.";
    }

    @Override
    public void onRemove(AbstractCard card) {
        ((AbstractRhythmGirlCard)card).loadOriginalCardImage();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard newCard = card.makeStatEquivalentCopy();
        CardModifierManager.removeModifiersById(newCard, RepeatModifier.ID, true);
        CardModifierManager.addModifier(newCard, new ExhaustAndEtherealModifier());
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(newCard, 1));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RepeatModifier();
    }
}