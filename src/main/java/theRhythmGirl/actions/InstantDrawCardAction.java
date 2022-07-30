package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class InstantDrawCardAction extends DrawCardAction {
    public InstantDrawCardAction(int amount) {
        super(amount);
        this.startDuration = this.duration = 0.0F;
    }
}
