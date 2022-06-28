package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.UUID;

//removes a card with a specific uuid from the master deck

public class RemoveFromMasterDeckAction extends AbstractGameAction {
    private final UUID uuidToRemove;

    public RemoveFromMasterDeckAction(UUID uuidToRemove)
    {
        this.uuidToRemove = uuidToRemove;
    }

    public void update() {
        AbstractDungeon.player.masterDeck.group.removeIf(card -> card.uuid == uuidToRemove);
        this.isDone = true;
    }
}