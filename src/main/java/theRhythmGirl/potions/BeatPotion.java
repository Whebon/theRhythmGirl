package theRhythmGirl.potions;

import basemod.abstracts.CustomPotion;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.GainAdditionalBeatsAction;

public class BeatPotion extends CustomPotion {

    public static final String POTION_ID = RhythmGirlMod.makeID("BeatPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    //possible time signatures: {2, 3, 4, 6}
    //drinking the beat potion gives 13 beats
    //13%2 = 13%3 = 13%4 = 13%6 = 1
    //so you will always end up 1 beat further than before drinking the potion
    public static final int POTENCY = 13;
    public static final int UPGRADE_POTENCY = 13;

    public BeatPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main RhythmGirlMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.SMOKE);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("BEAT_POTION"));
        // If you are in combat, gain beats equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new GainAdditionalBeatsAction(target, target, potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BeatPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }

    public void upgradePotion()
    {
      potency += UPGRADE_POTENCY;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}