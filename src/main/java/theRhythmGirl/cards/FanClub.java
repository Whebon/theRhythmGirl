package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.PopularityPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: "The first attack you play each turn is played twice."
//old version: "The repeat keyword triggers !M! additional times."
//current version: "The first card you play each turn has 'Repeat'."

public class FanClub extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(FanClub.class.getSimpleName());
    public static final String IMG = makeCardPath(FanClub.class.getSimpleName()+".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 2;

    // /STAT DECLARATION/


    public FanClub() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //important note: card effects related to 'apply popularity' should be applied BEFORE a beat is gained.
        this.addToTop(new CustomSFXAction("FAN_CLUB"));
        this.addToTop(new ApplyPowerAction(p, p, new PopularityPower(p, p, magicNumber), magicNumber));
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
