package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class LaunchPartyPower extends TwoAmountPower implements CloneablePowerInterface, OnGainBeatSubscriber {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(LaunchPartyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int uniqueID;
    private int countdown;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(LaunchPartyPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(LaunchPartyPower.class.getSimpleName()+"32.png"));

    public LaunchPartyPower(final AbstractCreature owner, final AbstractCreature source, int countdown, int damage) {
        name = NAME;
        ID = POWER_ID + uniqueID;
        uniqueID++;

        this.owner = owner;
        this.source = source;
        this.amount = countdown;
        this.countdown = countdown;
        this.amount2 = damage;
        this.greenColor2 = Color.YELLOW.cpy();

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
        return new LaunchPartyPower(owner, source, amount, amount2);
    }

    @Override
    public void onGainBeat(int numberOfBeatsGained) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.countdown>0) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, numberOfBeatsGained));
            this.countdown -= numberOfBeatsGained;
            if (this.countdown <= 0) {
                this.addToBot(new SFXAction("LAUNCH_PARTY_BLAST"));
                this.addToBot(new DamageAction(owner, new DamageInfo(this.source, amount2, DamageInfo.DamageType.THORNS),
                        AbstractGameAction.AttackEffect.FIRE, false, true));
            }
        }
    }
}
