package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class RatRacePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(RatRacePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84_rat = TextureLoader.getTexture(makePowerPath(RatRacePower.class.getSimpleName() + "84.png"));
    private static final Texture tex32_rat = TextureLoader.getTexture(makePowerPath(RatRacePower.class.getSimpleName() + "32.png"));

    private static final Texture tex84_cat = TextureLoader.getTexture(makePowerPath(RatRacePower.class.getSimpleName() + "Cat84.png"));
    private static final Texture tex32_cat = TextureLoader.getTexture(makePowerPath(RatRacePower.class.getSimpleName() + "Cat32.png"));

    private final TextureAtlas.AtlasRegion region128_rat;
    private final TextureAtlas.AtlasRegion region48_rat;

    private final TextureAtlas.AtlasRegion region128_cat;
    private final TextureAtlas.AtlasRegion region48_cat;


    public RatRacePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128_rat = new TextureAtlas.AtlasRegion(tex84_rat, 0, 0, 84, 84);
        this.region48_rat = new TextureAtlas.AtlasRegion(tex32_rat, 0, 0, 32, 32);

        this.region128_cat = new TextureAtlas.AtlasRegion(tex84_cat, 0, 0, 84, 84);
        this.region48_cat = new TextureAtlas.AtlasRegion(tex32_cat, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            if (RhythmGirlMod.beatUI.measuresGainedThisTurn == 0) {
                this.flash();
                this.addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (RhythmGirlMod.beatUI.measuresGainedThisTurn == 0){
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
            this.region128 = this.region128_rat;
            this.region48 = this.region48_rat;
        }
        else{
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
            this.region128 = this.region128_cat;
            this.region48 = this.region48_cat;
        }
    }

    @Override
    public void atStartOfTurn() {
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(MeasurePower.POWER_ID) && target == this.owner) {
            updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RatRacePower(owner, source, amount);
    }
}