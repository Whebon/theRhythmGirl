package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.DamageAllEnemiesExceptOneAction;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//idea: rework this card. it's too situational.
//idea: remove this card.

public class OddFlamingo extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(OddFlamingo.class.getSimpleName());
    public static final String IMG = makeCardPath("OddFlamingo.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int DAMAGE = 25;
    private static final int UPGRADE_PLUS_DMG = 10;

    // /STAT DECLARATION/

    public OddFlamingo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("ODD_FLAMINGO"));
        this.addToBot(new DamageAllEnemiesExceptOneAction(p, baseDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.LIGHTNING, m));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}