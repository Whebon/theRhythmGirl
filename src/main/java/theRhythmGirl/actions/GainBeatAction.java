package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.relics.TimeSignature44;

//adds a beat and takes time signatures into consideration

public class GainBeatAction extends AbstractGameAction {
    int cap;

    public GainBeatAction(AbstractCreature target, AbstractCreature source) {
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.source = source;
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
            this.cap = 4;
        else
            this.cap = Integer.MAX_VALUE;
    }

    public void update() {
        this.addToTop(new UpdateBeatRelicCounterAction());
        boolean addOne = true;
        if (this.target != null && this.target.hasPower(BeatPower.POWER_ID)) {
            int currentAmount = AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount;
            if (currentAmount >= cap){
                //reduce beats instead and add a measure
                this.addToTop(new ReducePowerAction(target, source, BeatPower.POWER_ID, cap-1));
                this.addToTop(new SilentApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new MeasurePower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                addOne = false;
            }
        }
        if (addOne) {
            this.addToTop(new SilentApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new BeatPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        }
        this.isDone = true;
    }
}