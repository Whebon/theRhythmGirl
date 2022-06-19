package theRhythmGirl.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.actions.OnBeatAction;
import theRhythmGirl.characters.TheDefault;

import java.util.function.Consumer;
import java.util.function.Function;

import static theRhythmGirl.DefaultMod.makeCardPath;

public class AirRally extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(AirRally.class.getSimpleName());

    public static final String IMG_13 = makeCardPath("AirRally_13.png");
    public static final String IMG_24 = makeCardPath("AirRally_24.png");

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

    // /STAT DECLARATION/

    public AirRally() {
        super(ID, IMG_24, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = STACKS;

        isOnBeat1 = true;
        isOnBeat2 = true;
        isOnBeat3 = true;
        isOnBeat4 = true;
    }
    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(1) || this.onBeatTriggered(3)){
            this.loadCardImage(IMG_13);
            this.glowColor = Color.GRAY.cpy();
        }
        else {
            this.loadCardImage(IMG_24);
            this.glowColor = Color.RED.cpy();
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new OnBeatAction(p, m, 1, 3, ()->{
            AbstractDungeon.actionManager.addToBottom(new SFXAction("AIR_RALLY_13"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new WeakPower(m, magicNumber, false), magicNumber));
        }));
        AbstractDungeon.actionManager.addToBottom(new OnBeatAction(p, m, 2, 4, ()->{
            AbstractDungeon.actionManager.addToBottom(new SFXAction("AIR_RALLY_24"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new VulnerablePower(m, magicNumber, false), magicNumber));
        }));

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