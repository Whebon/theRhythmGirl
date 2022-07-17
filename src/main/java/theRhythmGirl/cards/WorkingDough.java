//todo: this card is too funky. rework it to: "0, put a copy of the previous card you played into your hand. Exhaust"
/*
package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//todo: check how this card work in combination with the Repeat keyword!

public class WorkingDough extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(WorkingDough.class.getSimpleName());
    public static final String IMG = makeCardPath(WorkingDough.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = -2;

    private AbstractCard previousCard = null;
    private boolean shouldDowngrade = false;

    HashMap<Integer, BeatUI.BeatColor> ON_BEAT_COLOR = new HashMap<Integer, BeatUI.BeatColor>() {{
        put(1, BeatUI.BeatColor.NORMAL);
        put(2, BeatUI.BeatColor.NORMAL);
        put(3, BeatUI.BeatColor.NORMAL);
        put(4, BeatUI.BeatColor.NORMAL);
        put(5, BeatUI.BeatColor.NORMAL);
        put(6, BeatUI.BeatColor.NORMAL);
    }};


    // /STAT DECLARATION/

    public WorkingDough() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        resetCard();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        setPreviousCard(m);
        if (this.previousCard != null){
            this.previousCard.use(p, m);
        }
    }

    private void setCardImage(){
        //todo: set card images based on this.type (attack, power or skill)
    }

    private void resetCard(){
        this.previousCard = null;
        if (shouldDowngrade)
            this.upgraded = false;
        this.name = cardStrings.NAME;
        if (upgraded)
            upgradeName();
        this.rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.cost = COST;
        this.costForTurn = COST;
        this.target = TARGET;
        this.type = TYPE;
        this.exhaust = false;
        this.onBeatColor = ON_BEAT_COLOR;
        CardModifierManager.removeAllModifiers(this, true);
        setCardImage();
        initializeTitle();
        initializeDescription();
    }

    private void setPreviousCard(AbstractMonster targetMonster) {
        List<AbstractCard> list = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        for (int i = 0; i < list.size(); i++) {
            AbstractCard c = list.get(list.size()-1-i);
            if (!c.cardID.equals(this.cardID)) {
                //todo: reintroduce uuids
                this.previousCard = c.makeStatEquivalentCopy();
                logger.info(String.format("'WorkingDough' transforms into a new copy of '%s'", this.previousCard.name));
                if (shouldDowngrade)
                    this.upgraded = false;
                if (upgraded)
                    this.previousCard.upgrade();
                else
                    shouldDowngrade = true;
                CardModifierManager.removeAllModifiers(this, true);
                CardModifierManager.copyModifiers(c, this, true, false, false);
                //todo: add more stats
                //todo: fix interaction with repeat
                //todo: fix infinite loops? maybe this is already fixed?
                this.returnToHand = this.previousCard.returnToHand;
                this.exhaust = this.previousCard.exhaust;
                this.retain = this.previousCard.retain;
                this.name = this.previousCard.name;
                this.cost = this.previousCard.cost;
                this.target = this.previousCard.target;
                this.type = this.previousCard.type;
                this.baseBlock = this.previousCard.baseBlock;
                this.baseDamage = this.previousCard.baseDamage;
                this.baseMagicNumber = this.previousCard.baseMagicNumber;
                if (this.previousCard instanceof AbstractRhythmGirlCard){
                    this.baseMagicNumber2 = ((AbstractRhythmGirlCard)this.previousCard).baseMagicNumber2;
                    this.onBeatColor = ((AbstractRhythmGirlCard)this.previousCard).onBeatColor;
                }
                this.upgraded = this.previousCard.upgraded;
                this.timesUpgraded = this.previousCard.timesUpgraded;
                this.isLocked = this.previousCard.isLocked;
                this.misc = this.previousCard.misc;
                this.freeToPlayOnce = this.previousCard.freeToPlayOnce;
                this.previousCard.applyPowers();
                if (targetMonster!=null)
                    this.previousCard.calculateCardDamage(targetMonster);
                this.damage = this.previousCard.damage;
                this.block = this.previousCard.block;
                this.magicNumber = this.previousCard.magicNumber;
                if (this.previousCard instanceof AbstractRhythmGirlCard){
                    this.magicNumber2 = ((AbstractRhythmGirlCard)this.previousCard).magicNumber2;
                }
                this.isDamageModified = this.previousCard.isDamageModified;
                this.isBlockModified = this.previousCard.isBlockModified;
                this.rawDescription = this.previousCard.rawDescription;
                if (ExhaustiveField.ExhaustiveFields.baseExhaustive.get(this.previousCard) > 0){
                    ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 1);
                    ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 1);
                    this.exhaust = true;
                }
                this.costForTurn = this.previousCard.costForTurn;
                this.isCostModified = this.previousCard.isCostModified;
                this.isCostModifiedForTurn = this.previousCard.isCostModifiedForTurn;
                //todo: this.useSmallTitleFont = false;
                setCardImage();
                initializeTitle();
                initializeDescription();
                return;
            }
        }
        resetCard();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        setPreviousCard(null);
    }

    @Override
    public void triggerWhenDrawn() {
        setPreviousCard(null);
    }

    @Override
    public void applyPowers() {
        setPreviousCard(null);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        setPreviousCard(mo);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.previousCard == null) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        boolean canUsePreviousCard = this.previousCard.canUse(p, m);
        this.cantUseMessage = this.previousCard.cantUseMessage;
        return canUsePreviousCard;
    }

    @Override
    public void triggerOnGlowCheck(){
        if (this.previousCard != null){
            previousCard.triggerOnGlowCheck();
            this.glowColor = previousCard.glowColor;
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> toolTipList = new ArrayList<>();
        toolTipList.add(new TooltipInfo(cardStrings.NAME, cardStrings.DESCRIPTION));
        return toolTipList;
    }

    @Override
    public void onMoveToDiscard() {
        resetCard();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            resetCard();
            initializeDescription();
        }
    }
}
*/