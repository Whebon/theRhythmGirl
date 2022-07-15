package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theRhythmGirl.RhythmGirlMod;

public class ResetBeatsAction extends AbstractGameAction {

    public ResetBeatsAction() {
    }

    public void update() {
        RhythmGirlMod.beatUI.reset();
        this.isDone = true;
    }
}