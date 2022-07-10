package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class FlipperRolls extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(FlipperRolls.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("FlipperRolls.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int BLOCK = 5;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/

    public FlipperRolls() {
        this(0);
    }

    public FlipperRolls(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.timesUpgraded = upgrades;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("FLIPPER_ROLL_"+Math.min(Math.max(magicNumber, 1), 10)));
        for(int i = 0; i < magicNumber; ++i) {
            this.addToBot(new GainBlockAction(p, p, block));
        }
    }

    @Override
    public void upgrade() {
        this.upgradeMagicNumber(UPGRADE_MAGIC);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = (this.timesUpgraded+1)+" "+cardStrings.NAME+"s";
        this.initializeTitle();
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public boolean canUpgrade() {
        return true;
    }

    public AbstractCard makeCopy() {
        return new FlipperRolls(this.timesUpgraded);
    }
}
