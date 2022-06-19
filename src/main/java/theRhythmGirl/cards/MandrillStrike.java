package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.characters.TheDefault;

import static theRhythmGirl.DefaultMod.makeCardPath;

public class MandrillStrike extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(MandrillStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("MandrillStrike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int MAGIC = 12;
    private static final int UPGRADE_MAGIC = 6;

    // /STAT DECLARATION/

    public MandrillStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;

        isOnBeat3 = true;

        this.tags.add(CardTags.STRIKE);
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.magicNumber =this.damage;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        super.applyPowers();
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.isDamageModified = this.damage != this.baseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalDamage = damage;
        if (onBeatTriggered()){
            totalDamage += magicNumber;
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MANDRAKE_STRIKE_SWEET"));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MANDRAKE_STRIKE_SOUR"));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY, false, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}