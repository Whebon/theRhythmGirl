package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DamageAllEnemiesExceptOneAction extends AbstractGameAction {
    public int[] damage;
    private int baseDamage;
    private boolean firstFrame;
    private boolean utilizeBaseDamage;
    private final AbstractMonster targetMonster;

    public DamageAllEnemiesExceptOneAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AttackEffect effect, AbstractMonster targetMonster, boolean isFast) {
        this.firstFrame = true;
        this.utilizeBaseDamage = false;
        this.source = source;
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
        this.targetMonster = targetMonster;
    }

    public DamageAllEnemiesExceptOneAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AttackEffect effect, AbstractMonster targetMonster) {
        this(source, amount, type, effect, targetMonster, false);
    }

    public DamageAllEnemiesExceptOneAction(AbstractPlayer player, int baseDamage, DamageInfo.DamageType type, AttackEffect effect, AbstractMonster targetMonster) {
        this(player, null, type, effect, targetMonster, false);
        this.baseDamage = baseDamage;
        this.utilizeBaseDamage = true;
    }

    public void update() {
        int monsterSize;
        if (this.firstFrame) {
            boolean playedMusic = false;
            monsterSize = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            if (this.utilizeBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            }

            for(int i = 0; i < monsterSize; ++i) {
                AbstractMonster currentMonster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!currentMonster.isDying && currentMonster.currentHealth > 0 && !currentMonster.isEscaping && !currentMonster.equals(this.targetMonster)) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect((AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect((AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect));
                    }
                }
            }

            this.firstFrame = false;
        }

        this.tickDuration();
        if (this.isDone) {

            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }

            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for(int i = 0; i < temp; ++i) {
                AbstractMonster currentMonster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!currentMonster.isDeadOrEscaped() && !currentMonster.equals(this.targetMonster)) {
                    currentMonster.damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}
