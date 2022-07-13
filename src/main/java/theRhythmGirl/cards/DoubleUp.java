package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.DoubleUpPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: "The first attack you play each turn is played twice."
//old version: "The repeat keyword triggers !M! additional times."
//current version: "The first card you play each turn has 'Repeat'."

//idea: buff to standard 1-cost

public class DoubleUp extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(DoubleUp.class.getSimpleName());
    public static final String IMG = makeCardPath(DoubleUp.class.getSimpleName()+".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    // /STAT DECLARATION/


    public DoubleUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("DOUBLE_UP"));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new DoubleUpPower(p, p, 1), 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
