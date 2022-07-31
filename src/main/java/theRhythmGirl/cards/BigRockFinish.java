package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//note: added to support the karateka archetype
//note: maybe remove the yellow pillar On Beat 1 if the player doesn't have a measure

public class BigRockFinish extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(BigRockFinish.class.getSimpleName());
    public static final String IMG_SOUR = makeCardPath(BigRockFinish.class.getSimpleName()+"Sour.png");
    public static final String IMG_SWEET = makeCardPath(BigRockFinish.class.getSimpleName()+"Sweet.png");

    public static final String UI_ID = RhythmGirlMod.makeID("WarnNoMeasure");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int MAGIC = 18;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int UPGRADE_MAGIC = 6;

    // /STAT DECLARATION/

    public BigRockFinish() {
        super(ID, IMG_SOUR, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;

        onBeatColor.put(1, BeatUI.BeatColor.ON_BEAT);
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

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (!AbstractDungeon.player.hasPower(MeasurePower.POWER_ID))
            this.glowColor = beatColorToGlow.get(BeatUI.BeatColor.NORMAL);
        if (this.glowColor == beatColorToGlow.get(BeatUI.BeatColor.NORMAL))
            loadCardImage(IMG_SOUR);
        else
            loadCardImage(IMG_SWEET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalDamage = damage;
        if (onBeatTriggered() && !p.hasPower(MeasurePower.POWER_ID)){
            AbstractDungeon.effectList.add(new ThoughtBubble(
                    p.dialogX,
                    p.dialogY,
                    3.0f,
                    uiStrings.TEXT[0],
                    true));
        }
        if (onBeatTriggered() && p.hasPower(MeasurePower.POWER_ID)){
            totalDamage = magicNumber;
            this.addToBot(new ReducePowerAction(p, p, MeasurePower.POWER_ID, 1));
            this.addToBot(new CustomSFXAction("BIG_ROCK_FINISH_SWEET"));
        }
        else{
            this.addToBot(new CustomSFXAction("BIG_ROCK_FINISH_SOUR"));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.LIGHTNING, false, enableCustomSoundEffects));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}