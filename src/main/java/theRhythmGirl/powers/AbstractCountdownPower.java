package theRhythmGirl.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//originally named "OnGainBeatSubscriber"
//listens to beatUI::publishOnGainBeat
abstract public class AbstractCountdownPower extends TwoAmountPower {
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

    abstract public void onCountdownTrigger();
}