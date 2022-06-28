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

@Deprecated
public class GainBeatAction extends AbstractGameAction {
    int cap;
    int stacksToAdd;

    public GainBeatAction(AbstractCreature target, AbstractCreature source) {
        this(target, source, 1);
    }

    public GainBeatAction(AbstractCreature target, AbstractCreature source, int stacksToAdd) {
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.source = source;
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
            this.cap = 4;
        else
            this.cap = Integer.MAX_VALUE;
        this.stacksToAdd = stacksToAdd;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(BeatPower.POWER_ID)) {
            int currentAmount = AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount;
            int beatsToAdd = (currentAmount+stacksToAdd-1)%cap+1-currentAmount;
            int measuresToAdd = (currentAmount+stacksToAdd-1)/cap;
            this.addToTop(new ReducePowerAction(target, source, BeatPower.POWER_ID, -beatsToAdd));
            if (measuresToAdd>0)
                this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new MeasurePower(AbstractDungeon.player, AbstractDungeon.player, measuresToAdd), measuresToAdd));
        }
        else{
            this.addToTop(new SilentApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new BeatPower(AbstractDungeon.player, AbstractDungeon.player, stacksToAdd), stacksToAdd));
        }
        this.isDone = true;
    }
}