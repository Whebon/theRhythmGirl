package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.relics.TimeSignature44;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//removes a card with a specific uuid from the master deck

public class RemoveFromMasterDeckAction extends AbstractGameAction {
    private final UUID uuidToRemove;

    public RemoveFromMasterDeckAction(UUID uuidToRemove)
    {
        this.uuidToRemove = uuidToRemove;
    }

    public void update() {
        System.out.printf("uuidToRemove = %s%n", uuidToRemove);
        List<String> uuidList = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            String s = c.uuid.toString();
            uuidList.add(s);
        }
        System.out.printf("uuidToRemove = %s%n", "a");
        System.out.println(uuidList);
        AbstractDungeon.player.masterDeck.group.removeIf(card -> card.uuid == uuidToRemove);
        this.isDone = true;
    }
}