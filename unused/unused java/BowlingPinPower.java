//deprecated: supports the use of jab, which is not the goal of the countdown archetype

/*
"theRhythmGirl:BowlingPinPower": {
        "NAME": "Bowling Pin",
        "DESCRIPTIONS": ["After #b", " #yBeat, ", " #yBeats, ", "this enemy takes #b", " damage. Increase the damage by #b", " each time you play a card with #yRepeat."]
}

public class BowlingPinPower extends AbstractCountdownPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(BowlingPinPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int uniqueID;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(BowlingPinPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(BowlingPinPower.class.getSimpleName()+"32.png"));

    private final int repeatBonusDamage;

    public BowlingPinPower(final AbstractCreature owner, final AbstractCreature source, int countdown, int damage, int repeatBonusDamage) {
        name = NAME;
        ID = POWER_ID + uniqueID;
        uniqueID++;

        this.owner = owner;
        this.source = source;
        this.amount = countdown;
        this.countdown = countdown;
        this.amount2 = damage;
        this.greenColor2 = Color.GOLD.cpy();

        this.repeatBonusDamage = repeatBonusDamage;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (CardModifierManager.hasModifier(card, RepeatModifier.ID)){
            this.amount2 += repeatBonusDamage;
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        this.description += DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[4] + this.repeatBonusDamage;
    }

    @Override
    public AbstractPower makeCopy() {
        return new BowlingPinPower(owner, source, amount, amount2, repeatBonusDamage);
    }

    @Override
    public void onCountdownTrigger() {
        CustomMetrics.increasePowerEffectiveness(this, this.amount2);
        this.addToBot(new CustomSFXAction("BOWLING_PIN_BLAST"));
        this.addToBot(new DamageAction(owner, new DamageInfo(this.source, amount2, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE, false, enableCustomSoundEffects));
    }
}
*/