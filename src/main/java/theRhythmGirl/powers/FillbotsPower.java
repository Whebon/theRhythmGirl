package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.actions.WaitForMarshallAnimationAction;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.beatUI;
import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class FillbotsPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(FillbotsPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(FillbotsPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(FillbotsPower.class.getSimpleName() + "32.png"));

    public FillbotsPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            //this line is much cleaner, but unfortunately, this line instantly gives the player the measure
            //beatUI.gainBeatsUntil(1);
            if (AbstractDungeon.player.hasPower(CoffeeBreakPower.POWER_ID)) {
                AbstractDungeon.player.getPower(CoffeeBreakPower.POWER_ID).flashWithoutSound();
                return;
            }
            int n = (RhythmGirlMod.beatUI.getNumberOfPillars() + 1 - RhythmGirlMod.beatUI.currentBeat);
            CustomMetrics.increasePowerEffectiveness(this, n);
            for (int i = 0; i < n; i++) {
                if (i == n-1)
                    this.addToBot(new CustomSFXAction("FILLBOTS_FINAL_BEAT"));
                else
                    this.addToBot(new CustomSFXAction("FILLBOTS_BEAT"));
                this.addToBot(new GainAdditionalBeatsAction(AbstractDungeon.player, AbstractDungeon.player, 1));
                if (this.amount > 0)
                    this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount));
                this.addToBot(new WaitForMarshallAnimationAction());
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if (amount > 0)
            description += DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FillbotsPower(owner, source, amount);
    }
}