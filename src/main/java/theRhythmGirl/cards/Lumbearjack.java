package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: this card GAINS repeat. (Overpowered with RhythmHeaven + Freepeat)

public class Lumbearjack extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Lumbearjack.class.getSimpleName());
    public static final String IMG_12 = makeCardPath(Lumbearjack.class.getSimpleName()+"_12.png");
    public static final String IMG_3 = makeCardPath(Lumbearjack.class.getSimpleName()+"_3.png");
    public static final String IMG_4 = makeCardPath(Lumbearjack.class.getSimpleName()+"_4.png");
    public static final String IMG_EXHAUST_123 = makeCardPath(Lumbearjack.class.getSimpleName()+"_Exhaust_123.png");
    public static final String IMG_EXHAUST_4 = makeCardPath(Lumbearjack.class.getSimpleName()+"_Exhaust_4.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int MAGIC = 12;
    private static final int UPGRADE_MAGIC = 4;

    // /STAT DECLARATION/

    public Lumbearjack() {
        super(ID, IMG_12, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        onBeatColor.put(3, BeatUI.BeatColor.ON_BEAT);
        onBeatColor.put(4, BeatUI.BeatColor.ON_BEAT);
    }

    @Override
    public void initializeDescription(){
        if (CardModifierManager.hasModifier(this, ExhaustAndEtherealModifier.ID)){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            onBeatColor.put(3, BeatUI.BeatColor.NORMAL);
        }
        else{
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        super.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (CardModifierManager.hasModifier(this, ExhaustAndEtherealModifier.ID)) {
            if (onBeatTriggered(4)) {
                this.loadCardImage(IMG_EXHAUST_4);
            } else {
                this.loadCardImage(IMG_EXHAUST_123);
            }
        }
        else{
            if (onBeatTriggered(4)) {
                this.loadCardImage(IMG_4);
            } else if (onBeatTriggered(3)) {
                this.loadCardImage(IMG_3);
            } else {
                this.loadCardImage(IMG_12);
            }
        }
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.magicNumber = this.damage;
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
        if (onBeatTriggered(3) && !CardModifierManager.hasModifier(this, ExhaustAndEtherealModifier.ID)) {
            //the clean way breaks the interaction with WorkingDough)
            //CardModifierManager.addModifier(this, new RepeatModifier(true));
            (new RepeatModifier(true)).onUse(this, p, null);
        }
        if (onBeatTriggered(4)){
            totalDamage = magicNumber;
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("LUMBEARJACK_4"));
        }
        else if (onBeatTriggered(3)){
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("LUMBEARJACK_3"));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("LUMBEARJACK_12"));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY, false, enableCustomSoundEffects));
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