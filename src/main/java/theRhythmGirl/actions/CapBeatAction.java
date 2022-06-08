package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;

//caps the beat to the given cap

public class CapBeatAction extends AbstractGameAction {
    int cap;

    public CapBeatAction(AbstractCreature target, AbstractCreature source, int cap) {
        //whebon edit:
        this.actionType = ActionType.DEBUFF; //not sure, I just picked one that sounded right
        this.target = target;
        this.source = source;
        this.cap = cap;
    }

    public void update() {
        //whebon edit:
        if (this.target != null && this.target.hasPower(BeatPower.POWER_ID)) {
            int currentAmount = AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount;
            if (currentAmount > cap)
                this.addToTop(new ReducePowerAction(target, source, BeatPower.POWER_ID, currentAmount-cap));
        }
        this.isDone = true;
    }
}