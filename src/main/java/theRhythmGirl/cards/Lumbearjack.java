package theRhythmGirl.cards;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.DoubleUpPower;
import theRhythmGirl.relics.Freepeat;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class Lumbearjack extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Lumbearjack.class.getSimpleName());
    public static final String IMG_EXHAUST = makeCardPath(Lumbearjack.class.getSimpleName()+"_Exhaust.png");
    public static final String IMG_REPEAT = makeCardPath(Lumbearjack.class.getSimpleName()+"_Repeat.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 0;
    private static final int MAGIC = 12;
    private static final int UPGRADE_MAGIC = 4;

    // /STAT DECLARATION/

    public Lumbearjack() {
        super(ID, IMG_EXHAUST, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        onBeatColor.put(3, BeatUI.BeatColor.ON_BEAT);
    }

    @Override
    public void loadAlternativeCardImage(){
        this.loadCardImage(IMG_REPEAT);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (onBeatTriggered())
            loadAlternativeCardImage();
        else
            loadOriginalCardImage();
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
        if (onBeatTriggered(3)) {
            CardModifierManager.addModifier(this, new RepeatModifier(true));
        }
        if (onBeatTriggered(4)){
            totalDamage = magicNumber;
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("LUMBEARJACK_EXHAUST"));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("LUMBEARJACK_REPEAT"));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY, false, enableCustomSoundEffects));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}