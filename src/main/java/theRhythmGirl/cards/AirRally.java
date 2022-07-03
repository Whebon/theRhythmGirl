package theRhythmGirl.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class AirRally extends AbstractRhythmGirlCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     */

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(AirRally.class.getSimpleName());

    public static final String IMG_13 = makeCardPath("AirRally_13.png");
    public static final String IMG_24 = makeCardPath("AirRally_24.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int STACKS = 1;
    private static final int UPGRADE_STACKS = 1;
    private static final int DAMAGE = 6;

    // /STAT DECLARATION/

    public AirRally() {
        super(ID, IMG_24, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = STACKS;

        onBeatColor.put(1, BeatUI.BeatColor.BLUE);
        onBeatColor.put(2, BeatUI.BeatColor.RED);
        onBeatColor.put(3, BeatUI.BeatColor.BLUE);
        onBeatColor.put(4, BeatUI.BeatColor.RED);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(1) || this.onBeatTriggered(3)){
            this.loadCardImage(IMG_13);
        }
        else {
            this.loadCardImage(IMG_24);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (onBeatTriggered(1) || onBeatTriggered(3)){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("AIR_RALLY_13"));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true, true));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new WeakPower(m, magicNumber, false), magicNumber));
        }
        if (onBeatTriggered(2) || onBeatTriggered(4)){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("AIR_RALLY_24"));
            if (!(onBeatTriggered(1) || onBeatTriggered(3)))
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true, true));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new VulnerablePower(m, magicNumber, false), magicNumber));
        }
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