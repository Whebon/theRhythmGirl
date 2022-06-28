package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.relics.TimeSignature44;

//adds a beat and takes time signatures into consideration

//todo: reword cards with '15 additional beats', to something like 'gain 16 beats instead of 1'

public class GainAdditionalBeatsAction extends AbstractGameAction {
    int cap;
    int stacksToAdd;

    public GainAdditionalBeatsAction(AbstractCreature target, AbstractCreature source) {
        this(target, source, 1);
    }

    public GainAdditionalBeatsAction(AbstractCreature target, AbstractCreature source, int stacksToAdd) {
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.source = source;
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
            this.cap = 4;
        else
            this.cap = Integer.MAX_VALUE;
        this.stacksToAdd = stacksToAdd;
    }

    @Deprecated
    public void update() {
        RhythmGirlMod.beatUI.gainBeats(stacksToAdd);
        this.isDone = true;
    }
}