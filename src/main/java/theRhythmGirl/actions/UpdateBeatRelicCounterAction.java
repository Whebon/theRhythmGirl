package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.relics.QuarterNote;
import theRhythmGirl.relics.TimeSignature44;

//adds a beat and takes time signatures into consideration

public class UpdateBeatRelicCounterAction extends AbstractGameAction {
    public UpdateBeatRelicCounterAction() {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (AbstractDungeon.player.hasRelic(QuarterNote.ID) && AbstractDungeon.player.hasPower(BeatPower.POWER_ID))
            AbstractDungeon.player.getRelic(QuarterNote.ID).counter = AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasRelic(TimeSignature44.ID))
            AbstractDungeon.player.getRelic(TimeSignature44.ID).counter = AbstractDungeon.player.hasPower(MeasurePower.POWER_ID) ?
                    AbstractDungeon.player.getPower(MeasurePower.POWER_ID).amount : 0;
        this.isDone = true;
    }
}