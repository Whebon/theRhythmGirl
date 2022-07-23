package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.MarchingOrdersPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//Accuracy, but better

public class MarchingOrders extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(MarchingOrders.class.getSimpleName());
    public static final String IMG = makeCardPath(MarchingOrders.class.getSimpleName() + ".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;

    // /STAT DECLARATION/


    public MarchingOrders() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("MARCHING_ORDERS"));
        this.addToBot(new ApplyPowerAction(p, p, new MarchingOrdersPower(p, p, magicNumber), magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
