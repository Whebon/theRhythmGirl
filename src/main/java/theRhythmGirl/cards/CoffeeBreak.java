package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.CoffeeBreakPower;
import theRhythmGirl.powers.FillbotsPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//intended to combine with PartyCracker or Pitch
//idea: for the upgrade, 2 stacks instead of draw a card

//idea: make coffee beat based instead of turn based
//"Ignore the next #b incoming #yBeats, wears off at the end of your turn."
//downside: playing this card would already consume 1 stack.

//

//

public class CoffeeBreak extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(CoffeeBreak.class.getSimpleName());
    public static final String IMG = makeCardPath(CoffeeBreak.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;
    private static final int DRAW = 1;
    private static final int BLOCK = 0;
    private static final int UPGRADE_BLOCK = 3;

    // /STAT DECLARATION/


    public CoffeeBreak() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DRAW;
        block = baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasPower(CoffeeBreakPower.POWER_ID))
            this.addToBot(new ApplyPowerAction(p, p, new CoffeeBreakPower(p, p, 0), 0));
        if (block > 0){
            this.addToBot((new GainBlockAction(p, p, block)));
        }
        this.addToBot(new DrawCardAction(p, magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
