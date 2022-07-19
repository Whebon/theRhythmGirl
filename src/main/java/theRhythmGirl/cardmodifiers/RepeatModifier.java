package theRhythmGirl.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.AbstractRhythmGirlCard;
import theRhythmGirl.cards.WorkingDough;
import theRhythmGirl.relics.Freepeat;

public class RepeatModifier extends AbstractCardModifier {
    public static String ID = "therhythmgirl:RepeatCardModifier";
    private final boolean isTemp;
    private final boolean hasLineBreak;

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    public RepeatModifier(boolean isTemp, boolean hasLineBreak) {
        this.isTemp = isTemp;
        this.hasLineBreak = hasLineBreak;
    }

    public RepeatModifier(boolean isTemp) {
        this.isTemp = isTemp;
        this.hasLineBreak = true;
    }

    public RepeatModifier() {
        this.isTemp = false;
        this.hasLineBreak = true;
    }

    @Override
    public boolean shouldApply(AbstractCard card){
        //a card can have 'Repeat' multiple times
        return true;
        //a card cannot have 'Repeat' twice
        //return !CardModifierManager.hasModifier(card, RepeatModifier.ID);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof AbstractRhythmGirlCard)
            ((AbstractRhythmGirlCard)card).loadAlternativeCardImage();
        String linebreak = hasLineBreak ? " NL " : " ";
        return rawDescription + linebreak + "therhythmgirl:Repeat.";
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
        logger.info("Repeat Modifier triggers for "+card.name);
        AbstractCard newCard;
        if (card instanceof WorkingDough && card.cardsToPreview != null)
            newCard = card.cardsToPreview.makeStatEquivalentCopy();
        else
            newCard = card.makeStatEquivalentCopy();
        CardModifierManager.removeModifiersById(newCard, RepeatModifier.ID, false);
        CardModifierManager.addModifier(newCard, new ExhaustAndEtherealModifier());
        if (AbstractDungeon.player.hasRelic(Freepeat.ID) && newCard.cost!=0){
            AbstractDungeon.player.getRelic(Freepeat.ID).flash();
            newCard.setCostForTurn(0);
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(newCard, 1));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RepeatModifier(isTemp, hasLineBreak);
    }
}