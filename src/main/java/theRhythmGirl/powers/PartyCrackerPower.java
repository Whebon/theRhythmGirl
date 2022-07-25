package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makePowerPath;

//this used to be hp loss instead of damage, but I prefer damage.
//and since I'm using damage, I also won't be using the 'HealthBarRenderPower' interface anymore

public class PartyCrackerPower extends AbstractCountdownPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(PartyCrackerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int uniqueID;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(PartyCrackerPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(PartyCrackerPower.class.getSimpleName()+"32.png"));

    public PartyCrackerPower(final AbstractCreature owner, final AbstractCreature source, int countdown, int damage) {
        name = NAME;
        ID = POWER_ID + uniqueID;
        uniqueID++;

        this.owner = owner;
        this.source = source;
        this.amount = countdown;
        this.countdown = countdown;
        this.amount2 = damage;
        this.greenColor2 = Color.GOLD.cpy();

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        this.description += DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PartyCrackerPower(owner, source, amount, amount2);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.amount2 *= 2;
        this.fontScale = 8.0F;
        updateDescription();
    }

    @Override
    public void onCountdownTrigger() {
        CustomMetrics.increasePowerEffectiveness(this, this.amount2);
        this.addToBot(new CustomSFXAction("PARTY_CRACKER_BLAST"));
        this.addToBot(new DamageAction(owner, new DamageInfo(this.source, amount2, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE, false, enableCustomSoundEffects));
    }
}
