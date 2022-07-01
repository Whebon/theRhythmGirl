package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class DoubleUpPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(DoubleUpPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(DoubleUpPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(DoubleUpPower.class.getSimpleName()+"32.png"));

    private int cardsDoubledThisTurn = 0;
    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    public DoubleUpPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.purgeOnUse || this.amount <= this.cardsDoubledThisTurn || !card.type.equals(AbstractCard.CardType.ATTACK)){
            logger.info(String.format("'DoubleUpPower' does not consider copying '%s'", card.name));
            return;
        }
        int attacksPlayedThisTurn = 0;
        for(AbstractCard playedCard : AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if (playedCard.type.equals(AbstractCard.CardType.ATTACK)){
                ++attacksPlayedThisTurn;
                logger.info(String.format("'DoubleUpPower' recognized attack #%d: '%s'", attacksPlayedThisTurn, playedCard.name));
                if (attacksPlayedThisTurn > this.amount + cardsDoubledThisTurn){
                    logger.info(String.format("'DoubleUpPower' concludes that '%s' may not be copied because that would be attack #%d.",
                            card.name, attacksPlayedThisTurn-cardsDoubledThisTurn));
                    return;
                }
            }
        }

        ++cardsDoubledThisTurn;
        logger.info(String.format("'DoubleUpPower' will copy '%s' as copied attack %d out of %d",
                card.name, cardsDoubledThisTurn, this.amount));
        this.flash();
        AbstractMonster m = null;
        if (action.target != null) {
            m = (AbstractMonster)action.target;
        }

        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new DoubleUpPower(owner, source, amount);
    }
}
