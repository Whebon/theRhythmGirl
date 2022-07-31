//deprecated: supports the use of jab, which is not the goal of the countdown archetype

/*
  "theRhythmGirl:BowlingPin": {
    "NAME": "Bowling Pin",
    "DESCRIPTION": "therhythmgirl:Countdown !theRhythmGirl:MagicNumber2!. NL Deal !M! damage. Increase the damage by !M! each time you play a card with therhythmgirl:Repeat."
  }

public class BowlingPin extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(BowlingPin.class.getSimpleName());
    public static final String IMG = makeCardPath(BowlingPin.class.getSimpleName()+".png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int REPEAT_BONUS = 8;
    private static final int UPGRADE_REPEAT_BONUS = 2;
    private static final int COUNTDOWN = 4;

    // /STAT DECLARATION/

    public BowlingPin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = REPEAT_BONUS;
        baseMagicNumber2 = magicNumber2 = COUNTDOWN;
    }

    @Override
    public int getEffectiveness(){
        return 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("BOWLING_PIN_APPLY"));
        this.addToBot(new ApplyPowerAction(m, p, new BowlingPinPower(m, p, magicNumber2, magicNumber, magicNumber), magicNumber2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REPEAT_BONUS);
            initializeDescription();
        }
    }
}
 */