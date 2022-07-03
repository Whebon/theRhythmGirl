package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.DoubleUpPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class DoubleUp extends AbstractRhythmGirlCard {

    //todo: rework this card's mechanics. think about it: it massively anti-synergises with half of the the rhythm girl's attacks (CUED/ON_BEAT)
    // idea for new mechanic: "The repeat keyword triggers !M! additional time, times"

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(DoubleUp.class.getSimpleName());
    public static final String IMG = makeCardPath("DoubleUp.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    // /STAT DECLARATION/


    public DoubleUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("DOUBLE_UP"));
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
