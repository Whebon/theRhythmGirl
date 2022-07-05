package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class BossaNova extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(BossaNova.class.getSimpleName());

    public static final String IMG_13 = makeCardPath("BossaNova_13.png");
    public static final String IMG_24 = makeCardPath("BossaNova_24.png");
    public static final String IMG_1234 = makeCardPath("BossaNova_1234.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 3;

    // /STAT DECLARATION/

    public BossaNova() {
        super(ID, IMG_1234, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseBlock = block = BLOCK;

        onBeatColor.put(1, BeatUI.BeatColor.ON_BEAT);
        onBeatColor.put(2, BeatUI.BeatColor.BLUE);
        onBeatColor.put(3, BeatUI.BeatColor.ON_BEAT);
        onBeatColor.put(4, BeatUI.BeatColor.BLUE);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(1) || this.onBeatTriggered(3)){
            this.target = CardTarget.ENEMY;
            if (this.onBeatTriggered(2) || this.onBeatTriggered(4)){
                this.loadCardImage(IMG_1234);
            }
            else{
                this.loadCardImage(IMG_13);
            }
        }
        else {
            this.target = CardTarget.SELF;
            this.loadCardImage(IMG_24);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int sfxIndex = Math.max(1, Math.min(4, RhythmGirlMod.beatUI.currentBeat));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("BOSSA_NOVA_"+sfxIndex));
        if (onBeatTriggered(1) || onBeatTriggered(3)){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true, true));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true, true));
        }
        if (onBeatTriggered(2) || onBeatTriggered(4)){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}