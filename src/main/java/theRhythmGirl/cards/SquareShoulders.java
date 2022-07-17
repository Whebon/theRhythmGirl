package theRhythmGirl.cards;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.MeasurePower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//Measure-based decks can be frustrating as you need to play 100s of cards to gain a reasonable amount of measure.
//That's why this card ramps up instead of growing linearly

//idea: scale linearly, because it scales too fast

//old version: 2 (1) cost

public class SquareShoulders extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(SquareShoulders.class.getSimpleName());
    public static final String IMG = makeCardPath(SquareShoulders.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 3;

    // /STAT DECLARATION/

    public SquareShoulders() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        CardModifierManager.addModifier(this, new ExhaustMod());
        baseDamage = 0;
    }

    private int getBaseDamage(AbstractCreature owner){
        int measure = owner.hasPower(MeasurePower.POWER_ID) ? owner.getPower(MeasurePower.POWER_ID).amount : 0;
        return measure*measure;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = getBaseDamage(AbstractDungeon.player);
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = getBaseDamage(p);
        this.calculateCardDamage(m);
        this.addToBot(new SFXAction("STRIKE", -0.25F, true));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            CardModifierManager.removeModifiersById(this, ExhaustMod.ID, true);
            initializeDescription();
        }
    }
}