package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRhythmGirl.RhythmGirlMod;

//Deprecated (alternative for mustBePlayedOnBeat)
//This was created in an attempt to fix the problem of being more transparent about how 'mustBePlayedOnBeat' works to the player.
//This action allowed a player to play 'mustBePlayedOnBeat' cards without executing their effects
//However, although more restrictive gameplay-wise, preventing the player to play 'mustBePlayedOnBeat' cards will prevent a lot of blunders

public class WarnNotOnBeatAction extends AbstractGameAction {

    public static final String UI_ID = RhythmGirlMod.makeID("WarnNotOnBeat");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    int beat;

    public WarnNotOnBeatAction(int beat) {
        this.beat = beat;
    }

    public void update() {
        AbstractDungeon.effectList.add(new ThoughtBubble(
                AbstractDungeon.player.dialogX,
                AbstractDungeon.player.dialogY,
                3.0f,
                uiStrings.TEXT[0]+beat+uiStrings.TEXT[1],
                true));
        this.isDone = true;
    }
}