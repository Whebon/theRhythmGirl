package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class ForceWaitAction extends AbstractGameAction {
    public ForceWaitAction(float setDur) {
        this.setValues(null, null, 0);
        this.duration = setDur;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}