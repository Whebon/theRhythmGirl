package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.AbstractRhythmGirlCard;
import theRhythmGirl.cards.RhythmHeaven;
import theRhythmGirl.cards.SeeingHeaven;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class RhythmHeavenPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(RhythmHeavenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(RhythmHeavenPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(RhythmHeavenPower.class.getSimpleName() + "32.png"));

    public RhythmHeavenPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //for metrics only
        if (card instanceof AbstractRhythmGirlCard){
            if (((AbstractRhythmGirlCard)card).hasOnBeatEffect()){
                CustomMetrics.increasePowerEffectiveness(this, 1);
                CustomMetrics.addCardSpecificDetails(RhythmHeaven.ID, card.cardID + ":" + this.amount);
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount <= 1){
            description = DESCRIPTIONS[0]+DESCRIPTIONS[3];
        }
        else{
            description = DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2]+DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RhythmHeavenPower(owner, source, amount);
    }

    //reduce stacks at the end of round
    public void atEndOfRound() {
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }
}