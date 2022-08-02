package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.SpaceBellPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: EVERYONE gains 40 (60) block.

public class SpaceBell extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(SpaceBell.class.getSimpleName());
    public static final String IMG = makeCardPath(SpaceBell.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int COUNTDOWN = 7;
    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = 5;

    // /STAT DECLARATION/

    public SpaceBell() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber2 = magicNumber2 = COUNTDOWN;
    }

    @Override
    public int getEffectiveness(){
        return 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("SPACE_BELL_APPLY"));
        this.addToBot(new ApplyPowerAction(p, p, new SpaceBellPower(p, p, magicNumber2, block), magicNumber2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}