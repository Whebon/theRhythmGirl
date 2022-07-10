package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.Barrel;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class ComboPower extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(ComboPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ComboPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ComboPower.class.getSimpleName() + "32.png"));

    private final int totalNumberOfAttacksToPlay;
    private boolean triggeredThisTurn;

    public ComboPower(final AbstractCreature owner, final AbstractCreature source, final int totalNumberOfAttacksToPlay, final int amount) {
        name = NAME;
        ID = POWER_ID+totalNumberOfAttacksToPlay;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.totalNumberOfAttacksToPlay = totalNumberOfAttacksToPlay;
        this.amount2 = totalNumberOfAttacksToPlay;
        this.greenColor2 = Color.GOLD.cpy();
        this.triggeredThisTurn = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.triggeredThisTurn = false;
        this.amount2 = totalNumberOfAttacksToPlay;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!triggeredThisTurn){
            if (card.type == AbstractCard.CardType.ATTACK)
                amount2 -= 1;
            else
                amount2 = totalNumberOfAttacksToPlay;
            if (amount2 <= 0){
                triggeredThisTurn = true;
                this.addToBot(new CustomSFXAction("BARREL_OBTAIN"));
                this.addToBot(new MakeTempCardInHandAction(new Barrel(), this.amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        name = NAME + totalNumberOfAttacksToPlay + DESCRIPTIONS[0];
        if (this.amount == 1)
            description = DESCRIPTIONS[1]+this.totalNumberOfAttacksToPlay+DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
        else
            description = DESCRIPTIONS[1]+this.totalNumberOfAttacksToPlay+DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ComboPower(owner, source, totalNumberOfAttacksToPlay, amount);
    }
}