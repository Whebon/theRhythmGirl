package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.PartyCrackerPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class PartyCracker extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(PartyCracker.class.getSimpleName());
    public static final String IMG = makeCardPath(PartyCracker.class.getSimpleName()+"5.png");
    public static final String IMG_UPGRADED = makeCardPath(PartyCracker.class.getSimpleName()+"10.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    //todo: nerf this card
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int COUNTDOWN = 5;
    private static final int COUNTDOWN_UPGRADE = 5;

    // /STAT DECLARATION/

    public PartyCracker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //todo: maybe use a magic number instead of damage
        baseDamage = damage = DAMAGE;
        baseMagicNumber2 = magicNumber2 = COUNTDOWN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("PARTY_CRACKER_APPLY"));
        this.addToBot(new ApplyPowerAction(m, p, new PartyCrackerPower(m, p, magicNumber2, damage), magicNumber2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            loadCardImage(IMG_UPGRADED); //patched in CardPortraitUpgradeChange
            upgradeMagicNumber2(COUNTDOWN_UPGRADE);
            initializeDescription();
        }
    }
}