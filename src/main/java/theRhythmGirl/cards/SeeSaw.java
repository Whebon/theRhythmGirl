package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheDefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static theRhythmGirl.DefaultMod.makeCardPath;

public class SeeSaw extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SeeSaw.class.getSimpleName());
    public static final String IMG = makeCardPath("SeeSaw_Exhaust.png");
    public static final String IMG_REPEAT = makeCardPath("SeeSaw_Repeat.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 4;

    private HashMap<AbstractMonster, Integer> monsterHealthAtTurnStart;

    // /STAT DECLARATION/

    public SeeSaw() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.monsterHealthAtTurnStart = new HashMap<>();

        CardModifierManager.addModifier(this, new RepeatModifier());
    }

    @Override
    public void loadAlternativeCardImage(){
        this.loadCardImage(IMG_REPEAT);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CardModifierManager.hasModifier(this, RepeatModifier.ID))
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SEESAW_REPEAT"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SEESAW_EXHAUST"));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false, true));
    }

    @Override
    public void atTurnStart(){
        super.atTurnStart();
        monsterHealthAtTurnStart = new HashMap<>();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            monsterHealthAtTurnStart.put(m, m.currentHealth);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)){
            return false;
        }
        if (m != null){
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return monsterHealthAtTurnStart.containsKey(m) && monsterHealthAtTurnStart.get(m) == m.currentHealth;
        }
        for (AbstractMonster mm : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (canUse(p, mm)){
                return true;
            }
        }
        return false;
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

    @Override
    public AbstractCard makeCopy() {
        SeeSaw that = new SeeSaw();
        that.monsterHealthAtTurnStart = this.monsterHealthAtTurnStart;
        return that;
    }
}