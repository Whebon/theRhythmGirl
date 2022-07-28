package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class FastMakeTempCardInDrawPileAction extends MakeTempCardInDrawPileAction {
    public FastMakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition) {
        super(card, amount, randomSpot, autoPosition, false, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        this.startDuration = this.duration = 0.05F;
    }
}