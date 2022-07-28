package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.cards.Tweezers;

public class UpdateAllTweezersImagesAction extends AbstractGameAction {
    public UpdateAllTweezersImagesAction() {
    }

    public int countAllTweezers(){
        int count = 0;
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof Tweezers) {
                    count++;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof Tweezers) {
                    count++;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof Tweezers) {
                    count++;
                }
            }
        }
        return count;
    }

    public void update() {
        int index = countAllTweezers()-1;
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof Tweezers) {
                    ((Tweezers)c).setTweezersImageIndex(index);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof Tweezers) {
                    ((Tweezers)c).setTweezersImageIndex(index);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof Tweezers) {
                    ((Tweezers)c).setTweezersImageIndex(index);
                }
            }
        }
        this.isDone = true;
    }
}
