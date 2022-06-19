package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.relics.TimeSignature44;

//applies a power without any effects

public class SilentApplyPowerAction extends AbstractGameAction {
    private final AbstractPower powerToApply;
    private final int stackAmount;

    public SilentApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this.actionType = ActionType.POWER;
        this.target = target;
        this.source = source;
        this.powerToApply = powerToApply;
        this.stackAmount = stackAmount;
    }

    public void update() {
        if (AbstractDungeon.player.hasPower(powerToApply.ID)) {
            this.addToTop(new ReducePowerAction(target, source, powerToApply.ID, -stackAmount));
            //AbstractDungeon.player.getPower(powerToApply.ID).amount += stackAmount;
        }
        else {
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, powerToApply, stackAmount));
        }
        this.isDone = true;
    }
}