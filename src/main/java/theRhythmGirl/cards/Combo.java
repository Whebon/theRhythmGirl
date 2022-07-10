package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.ComboPower;
import theRhythmGirl.powers.DoubleUpPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class Combo extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Combo.class.getSimpleName());
    public static final String IMG = makeCardPath(Combo.class.getSimpleName()+".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = -1;

    // /STAT DECLARATION/


    public Combo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;

        this.cardsToPreview = new Barrel();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded){
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("COMBO_UPGRADED"));
        }
       else{
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("COMBO"));
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ComboPower(p, p, magicNumber, 1), 1));
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
