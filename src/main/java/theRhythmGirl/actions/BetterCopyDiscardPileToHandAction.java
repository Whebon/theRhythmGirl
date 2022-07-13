package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;

import java.util.ArrayList;
import java.util.Iterator;

public class BetterCopyDiscardPileToHandAction extends AbstractGameAction {
    public static final String[] TEXT;
    private final AbstractPlayer player;
    private final int numberOfCards;
    private final boolean optional;
    private int newCost;
    private final boolean setCost;

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    public BetterCopyDiscardPileToHandAction(int numberOfCards, boolean optional) {
        this.newCost = 0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
        this.setCost = false;
    }

    public BetterCopyDiscardPileToHandAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public BetterCopyDiscardPileToHandAction(int numberOfCards, int newCost) {
        this.newCost = 0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = false;
        this.setCost = true;
        this.newCost = newCost;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            //whebon edit:
            CardGroup nonHealingDiscardPile = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
            for (AbstractCard discardedCard : this.player.discardPile.group){
                if (!discardedCard.hasTag(AbstractCard.CardTags.HEALING))
                    nonHealingDiscardPile.addToTop(discardedCard);
            }

            if (!nonHealingDiscardPile.isEmpty() && this.numberOfCards > 0) {
                if (nonHealingDiscardPile.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>();
                    Iterator<AbstractCard> var5 = nonHealingDiscardPile.group.iterator();

                    AbstractCard c;
                    while(var5.hasNext()) {
                        c = var5.next();
                        cardsToMove.add(c);
                    }

                    var5 = cardsToMove.iterator();

                    while(var5.hasNext()) {
                        c = var5.next();
                        if (this.player.hand.size() < 10) {
                            this.player.hand.addToHand(c);
                            if (this.setCost) {
                                c.setCostForTurn(this.newCost);
                            }

                            //whebon edit:
                            this.player.discardPile.addToTop(c.makeStatEquivalentCopy());
                            this.player.discardPile.removeCard(c);

                        }

                        c.lighten(false);
                        c.applyPowers();
                    }

                    this.isDone = true;
                } else {
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(nonHealingDiscardPile, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(nonHealingDiscardPile, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(nonHealingDiscardPile, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(nonHealingDiscardPile, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            Iterator<AbstractCard> var1;
            AbstractCard c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    c = var1.next();
                    if (this.player.hand.size() < 10) {
                        this.player.hand.addToHand(c);
                        if (this.setCost) {
                            c.setCostForTurn(this.newCost);
                        }

                        //whebon edit:
                        this.player.discardPile.addToTop(c.makeStatEquivalentCopy());
                        this.player.discardPile.removeCard(c);
                    }

                    c.lighten(false);
                    c.unhover();
                    c.applyPowers();
                }

                for(var1 = this.player.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float)CardGroup.DISCARD_PILE_X;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
            if (this.isDone) {
                var1 = this.player.hand.group.iterator();

                while(var1.hasNext()) {
                    c = var1.next();
                    c.applyPowers();
                }
            }

        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
