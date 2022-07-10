package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import theRhythmGirl.RhythmGirlMod;

//same as SFXAction, but won't play a sound if "enableCustomSoundEffects=false"

public class CustomSFXAction extends AbstractGameAction {
    private final String key;
    private final float pitchVar;
    private final boolean adjust;

    public CustomSFXAction(String key) {
        this(key, 0.0F, false);
    }

    public CustomSFXAction(String key, float pitchVar) {
        this(key, pitchVar, false);
    }

    public CustomSFXAction(String key, float pitchVar, boolean pitchAdjust) {
        this.key = key;
        this.pitchVar = pitchVar;
        this.adjust = pitchAdjust;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (RhythmGirlMod.enableCustomSoundEffects){
            if (!this.adjust) {
                CardCrawlGame.sound.play(this.key, this.pitchVar);
            } else {
                CardCrawlGame.sound.playA(this.key, this.pitchVar);
            }
        }
        this.isDone = true;
    }
}