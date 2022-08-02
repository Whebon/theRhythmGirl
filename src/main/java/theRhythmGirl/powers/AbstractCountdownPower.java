package theRhythmGirl.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;

//originally named "OnGainBeatSubscriber"
//listens to beatUI::publishOnGainBeat
abstract public class AbstractCountdownPower extends TwoAmountPower {
    public int originalCountdown;
    public int countdown;

    public void onGainBeat(int numberOfBeatsGained){
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.countdown>0) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, numberOfBeatsGained));
            this.countdown -= numberOfBeatsGained;
            if (this.countdown <= 0) {
                onCountdownTrigger();
            }
        }
    }

    public void resetCountdown(){
        RhythmGirlMod.logger.info(String.format("%s is Resetting its Countdown from %d to %d.", this.ID, this.countdown, this.originalCountdown));
        if (this.countdown < this.originalCountdown){
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, this, this.originalCountdown-this.countdown));
            this.countdown = this.originalCountdown;
        }
    }

    abstract public void onCountdownTrigger();
}