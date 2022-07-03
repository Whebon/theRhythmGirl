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
    private final boolean isTemp;

    public RepeatModifier(boolean isTemp) {
        this.isTemp = isTemp;
    }

    public RepeatModifier() {
        this.isTemp = false;
    }

    @Override
    public boolean shouldApply(AbstractCard card){
        //a card can have 'Repeat' twice
        return true;
        //a card cannot have 'Repeat' twice
        //return !CardModifierManager.hasModifier(card, RepeatModifier.ID);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof AbstractRhythmGirlCard)
            ((AbstractRhythmGirlCard)card).loadAlternativeCardImage();
        return rawDescription + " NL therhythmgirl:Repeat.";
    }

    @Override
    public void onRemove(AbstractCard card) {
        if (card instanceof AbstractRhythmGirlCard)
            ((AbstractRhythmGirlCard)card).loadOriginalCardImage();
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card){
        return isTemp;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard newCard = card.makeStatEquivalentCopy();
        CardModifierManager.removeModifiersById(newCard, RepeatModifier.ID, false);
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