package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theRhythmGirl.RhythmGirlMod;

//adds a beat and takes time signatures into consideration
//idea: reword cards with '15 additional beats', to something like 'gain 16 beats instead of 1'

public class GainAdditionalBeatsAction extends AbstractGameAction {
    int stacksToAdd;

    public GainAdditionalBeatsAction(AbstractCreature target, AbstractCreature source) {
        this(target, source, 1);
    }

    public GainAdditionalBeatsAction(AbstractCreature target, AbstractCreature source, int stacksToAdd) {
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.source = source;
        this.stacksToAdd = stacksToAdd;
    }

    public void update() {
        RhythmGirlMod.beatUI.gainBeats(stacksToAdd);
        this.isDone = true;
    }
}