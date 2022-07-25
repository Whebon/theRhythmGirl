package theRhythmGirl.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.patches.FleetingPatch;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.util.TextureLoader;

//note: 2x DoubleUpPower + <AnyCard> + <WorkingDough> gives 3 copies of <AnyCard>
//this is funky, yet intended, behavior.

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class DoubleUpPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(DoubleUpPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(DoubleUpPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(DoubleUpPower.class.getSimpleName()+"32.png"));

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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        /*
        //repeating a purgeOnUse card is fine I guess
        if (card.purgeOnUse){
            logger.info(String.format("'DoubleUpPower' does not consider applying Repeat on '%s'", card.name));
            return;
        }
         */

        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > this.amount){
            logger.info(String.format("'DoubleUpPower' does not apply Repeat on '%s' because that would be card %d/%d.",
                    card.name, AbstractDungeon.actionManager.cardsPlayedThisTurn.size(), this.amount));
            return;
        }

        logger.info(String.format("'DoubleUpPower' will apply Repeat on '%s' (card %d/%d)",
                card.name, AbstractDungeon.actionManager.cardsPlayedThisTurn.size(), this.amount));
        this.flash();
        addToBot(new CustomSFXAction("DOUBLE_UP_TRIGGER"));
        CardModifierManager.addModifier(card, new RepeatModifier(true));
        CustomMetrics.increasePowerEffectiveness(this, 1);
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
