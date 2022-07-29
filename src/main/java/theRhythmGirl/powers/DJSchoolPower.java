package theRhythmGirl.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.cards.DJSchool;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class DJSchoolPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(DJSchoolPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(DJSchoolPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(DJSchoolPower.class.getSimpleName() + "32.png"));

    public DJSchoolPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.isEthereal) {
            addToBot(new CustomSFXAction("DJ_SCHOOL_TRIGGER"));
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            CardModifierManager.addModifier(card, new RepeatModifier(true));
            CustomMetrics.addCardSpecificDetails(DJSchool.ID, card.cardID);
            CustomMetrics.increasePowerEffectiveness(this, 1);
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DJSchoolPower(owner, source, amount);
    }
}