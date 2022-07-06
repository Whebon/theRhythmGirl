package theRhythmGirl.cards;

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

public class Screwbot extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Screwbot.class.getSimpleName());

    public static final String IMG_13 = makeCardPath("Screwbot_13.png");
    public static final String IMG_24 = makeCardPath("Screwbot_24.png");
    public static final String IMG_1234 = makeCardPath("Screwbot_1234.png");

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

    public Screwbot() {
        super(ID, IMG_1234, COST, TYPE, COLOR, RARITY, TARGET);
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
            if (this.onBeatTriggered(2) || this.onBeatTriggered(4)){
                this.loadCardImage(IMG_1234);
            }
            else{
                this.loadCardImage(IMG_13);
            }
        }
        else {
            this.loadCardImage(IMG_24);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (onBeatTriggered(1) || onBeatTriggered(3)){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SCREWBOT_13"));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true, true));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new WeakPower(m, magicNumber, false), magicNumber));
        }
        if (onBeatTriggered(2) || onBeatTriggered(4)){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SCREWBOT_24"));
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