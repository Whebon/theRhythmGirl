package theRhythmGirl.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.characters.TheDefault;

import static theRhythmGirl.DefaultMod.makeCardPath;

public class AirRally extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(AirRally.class.getSimpleName());
    public static final String IMG = makeCardPath("AirRally.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int STACKS = 1;
    private static final int UPGRADE_STACKS = 1;
    private static final int DAMAGE = 6;

    private static final int ON_BEAT = 1;
    private static final int SECOND_ON_BEAT = 3;

    // /STAT DECLARATION/

    public AirRally() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = STACKS;

        onBeat = baseOnBeat = ON_BEAT;
        secondOnBeat = baseSecondOnBeat = SECOND_ON_BEAT;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered() || this.secondOnBeatTriggered())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = Color.RED.cpy();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (onBeatTriggered() || secondOnBeatTriggered()){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("AIR_RALLY_13"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new WeakPower(m, magicNumber, false), magicNumber));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new SFXAction("AIR_RALLY_24"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new VulnerablePower(m, magicNumber, false), magicNumber));
        }

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STACKS);
            initializeDescription();
        }
    }
}