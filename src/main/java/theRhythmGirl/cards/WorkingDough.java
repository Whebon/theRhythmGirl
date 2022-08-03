package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
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

public class WorkingDough extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(WorkingDough.class.getSimpleName());
    public static final String IMG_ATTACK = makeCardPath(WorkingDough.class.getSimpleName()+"Attack.png");
    public static final String IMG_SKILL = makeCardPath(WorkingDough.class.getSimpleName()+"Skill.png");
    public static final String IMG_POWER = makeCardPath(WorkingDough.class.getSimpleName()+"Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = -2;

    //private AbstractCard cardsToPreview = null;
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
        super(ID, IMG_SKILL, COST, TYPE, COLOR, RARITY, TARGET);
        resetCard();
    }

    @Override
    public int getEffectiveness(){
        if (this.cardsToPreview instanceof AbstractRhythmGirlCard)
            return ((AbstractRhythmGirlCard) this.cardsToPreview).getEffectiveness();
        return 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.cardsToPreview != null){
            this.cardsToPreview.use(p, m);
            this.exhaust = cardsToPreview.exhaust;
        }
        else{
            logger.error("WorkingDough has not copied any card");
        }
    }

    private void setCardImage(){
        switch(this.type){
            case ATTACK:
                loadCardImage(IMG_ATTACK);
                break;
            case SKILL:
                loadCardImage(IMG_SKILL);
                break;
            case POWER:
                loadCardImage(IMG_POWER);
                break;
        }
    }

    private void resetCard(){
        this.cardsToPreview = null;
        this.purgeOnUse = false;
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
        FleetingField.fleeting.set(this, false);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, -1);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, -1);
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
            AbstractCard c = list.get(list.size() - 1 - i);
            if (!c.cardID.equals(this.cardID)) {
                logger.info("WorkingDough transforms into a copy of "+c.name);
                this.cardsToPreview = c.makeStatEquivalentCopy();
                CardModifierManager.removeAllModifiers(this, true);
                CardModifierManager.copyModifiers(c, this, true, false, false);
                if (shouldDowngrade)
                    this.upgraded = false;
                if (upgraded)
                    this.cardsToPreview.upgrade();
                else
                    shouldDowngrade = true;
                this.returnToHand = this.cardsToPreview.returnToHand;
                this.exhaust = this.cardsToPreview.exhaust;
                this.retain = this.cardsToPreview.retain;
                this.isEthereal = this.cardsToPreview.isEthereal;
                this.selfRetain = this.cardsToPreview.selfRetain;
                this.shuffleBackIntoDrawPile = this.cardsToPreview.shuffleBackIntoDrawPile;
                this.name = this.cardsToPreview.name;
                this.cost = this.cardsToPreview.cost;
                this.target = this.cardsToPreview.target;
                this.type = this.cardsToPreview.type;
                this.baseHeal = this.cardsToPreview.baseHeal;
                this.baseDiscard = this.cardsToPreview.baseDiscard;
                this.baseBlock = this.cardsToPreview.baseBlock;
                this.baseDamage = this.cardsToPreview.baseDamage;
                this.baseMagicNumber = this.cardsToPreview.baseMagicNumber;
                this.purgeOnUse = this.cardsToPreview.purgeOnUse;
                FleetingField.fleeting.set(this, FleetingField.fleeting.get(this.cardsToPreview));
                if (this.cardsToPreview instanceof AbstractRhythmGirlCard){
                    this.baseMagicNumber2 = ((AbstractRhythmGirlCard)this.cardsToPreview).baseMagicNumber2;
                    this.onBeatColor = ((AbstractRhythmGirlCard)this.cardsToPreview).onBeatColor;
                }
                this.upgraded = this.cardsToPreview.upgraded;
                this.timesUpgraded = this.cardsToPreview.timesUpgraded;
                this.isLocked = this.cardsToPreview.isLocked;
                this.misc = this.cardsToPreview.misc;
                this.freeToPlayOnce = this.cardsToPreview.freeToPlayOnce;
                this.rawDescription = this.cardsToPreview.rawDescription;
                if (ExhaustiveField.ExhaustiveFields.baseExhaustive.get(this.cardsToPreview) > 0){
                    //copying a card with exhaustive sets the exhaustive field to 1 to prevent funky interactions
                    ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 1);
                    ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 1);
                    this.exhaust = true;
                }
                else{
                    ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, -1);
                    ExhaustiveField.ExhaustiveFields.exhaustive.set(this, -1);
                }
                this.costForTurn = this.cardsToPreview.costForTurn;
                this.isCostModified = this.cardsToPreview.isCostModified;
                this.isCostModifiedForTurn = this.cardsToPreview.isCostModifiedForTurn;
                setDynamicVariables(targetMonster);
                setCardImage();
                initializeTitle();
                this.cardsToPreview.initializeDescription();
                this.description = this.cardsToPreview.description;
                return;
            }
        }
        resetCard();
    }

    private void setDynamicVariables(AbstractMonster targetMonster){
        this.cardsToPreview.applyPowers();
        if (targetMonster != null)
            this.cardsToPreview.calculateCardDamage(targetMonster);
        this.damage = this.cardsToPreview.damage;
        this.block = this.cardsToPreview.block;
        this.heal = this.cardsToPreview.heal;
        this.discard = this.cardsToPreview.discard;
        this.magicNumber = this.cardsToPreview.magicNumber;
        if (this.cardsToPreview instanceof AbstractRhythmGirlCard) {
            this.magicNumber2 = ((AbstractRhythmGirlCard) this.cardsToPreview).magicNumber2;
        }
        this.isDamageModified = this.cardsToPreview.isDamageModified;
        this.isBlockModified = this.cardsToPreview.isBlockModified;
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
        if (this.cardsToPreview != null)
            setDynamicVariables(null);
        else
            setPreviousCard(null);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (this.cardsToPreview != null)
            setDynamicVariables(mo);
        else
            setPreviousCard(mo);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.cardsToPreview == null) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        boolean canUsePreviousCard = this.cardsToPreview.canUse(p, m);
        this.cantUseMessage = this.cardsToPreview.cantUseMessage;
        return canUsePreviousCard;
    }

    @Override
    public void triggerOnGlowCheck(){
        if (this.cardsToPreview != null){
            cardsToPreview.triggerOnGlowCheck();
            this.target = cardsToPreview.target;
            this.type = cardsToPreview.type;
            this.exhaust = cardsToPreview.exhaust;
            this.glowColor = cardsToPreview.glowColor;
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> toolTipList = new ArrayList<>();
        toolTipList.add(new TooltipInfo(cardStrings.NAME, cardStrings.UPGRADE_DESCRIPTION));
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

    @Override
    public void render(SpriteBatch sb) {
        try {
            super.render(sb);
        }
        catch (Exception e1){
            try {
                logger.info("Rendering of WorkingDough failed, using a description without custom variables instead");
                this.rawDescription = this.cardsToPreview.rawDescription.replaceAll("![^!]*!", "?");
                initializeDescription();
                super.render(sb);
            }
            catch (Exception e2) {
                try {
                    logger.info("Rendering of WorkingDough failed, using the title as description instead.");
                    this.rawDescription = this.cardsToPreview.name;
                    initializeDescription();
                    super.render(sb);
                }
                catch (Exception e3){
                    logger.info("Rendering of WorkingDough failed, using no description instead.");
                    this.rawDescription = "";
                    initializeDescription();
                    super.render(sb);
                }
            }
        }
    }

    public AbstractCard makeCopy() {
        return new WorkingDough();
    }
}