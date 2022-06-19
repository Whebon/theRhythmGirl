package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.relics.TimeSignature44;

//adds a beat and takes time signatures into consideration

public class OnBeatAction extends AbstractGameAction {
    Runnable action;
    int onBeat;
    int secondOnBeat;

    public OnBeatAction(AbstractCreature target, AbstractCreature source, int onBeat, int secondOnBeat, Runnable action) {
        this.actionType = ActionType.USE;
        this.target = target;
        this.source = source;
        this.onBeat = onBeat;
        this.secondOnBeat = secondOnBeat;
        this.action = action;
    }

    public OnBeatAction(AbstractCreature target, AbstractCreature source, int onBeat1, Runnable action) {
        this(target, source, onBeat1, 0, action);
    }

    public boolean onBeatTriggered(int beat){
        if (!AbstractDungeon.player.hasPower(BeatPower.POWER_ID))
            return beat==1;
        if (beat==1 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount <= 1)
            return true;
        if (beat==2 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 2)
            return true;
        if (beat==3 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 3)
            return true;
        return beat==4 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 4;
    }

    public void update() {
        if (this.onBeatTriggered(onBeat) || this.onBeatTriggered(secondOnBeat)){
            action.run();
        }
        this.isDone = true;
    }
}