package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theRhythmGirl.RhythmGirlMod;

public class WaitForMarshallAnimationAction extends AbstractGameAction {
    public WaitForMarshallAnimationAction() {
        this.setValues(null, null, 0);
        this.duration = 0.1F;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
        if (this.isDone)
            this.isDone = !RhythmGirlMod.beatUI.isAnimationPlaying();
    }
}
