//deprecated, spawning multiple rockets from 1 card is confusing with the Countdown keyword

/*
  "theRhythmGirl:LaunchFestival": {
    "NAME": "Launch Festival",
    "DESCRIPTION": "therhythmgirl:Countdown !theRhythmGirl:MagicNumber2!. NL Deal !M! damage to a random enemy 3 times."
  }


public class LaunchFestival extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(LaunchFestival.class.getSimpleName());
    public static final String IMG = makeCardPath(LaunchFestival.class.getSimpleName() + ".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int COUNTDOWN = 3;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int TIMES = 3;

    // /STAT DECLARATION/

    public LaunchFestival() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DAMAGE;
        baseMagicNumber2 = magicNumber2 = COUNTDOWN;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < TIMES; i++) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (randomMonster.currentHealth > 0){
                    this.addToBot(new CustomSFXAction("LAUNCH_PARTY_APPLY"));
                    this.addToBot(new ApplyPowerAction(randomMonster, p, new LaunchPartyPower(randomMonster, p, magicNumber2, magicNumber), magicNumber, true));
                    this.addToBot(new WaitAction(0.1F));
                }
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
*/