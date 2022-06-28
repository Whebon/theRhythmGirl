package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.relics.TimeSignature44;

//resets the beat to 1

@Deprecated
public class ResetBeatAction extends AbstractGameAction {
    public ResetBeatAction(AbstractCreature target, AbstractCreature source) {
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.source = source;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(BeatPower.POWER_ID)) {
            int currentAmount = AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount;
            this.addToTop(new ReducePowerAction(target, source, BeatPower.POWER_ID, currentAmount-1));
        }
        else {
            this.addToTop(new SilentApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new BeatPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        }
        this.isDone = true;
    }
}