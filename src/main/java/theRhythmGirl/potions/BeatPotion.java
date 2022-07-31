package theRhythmGirl.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
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

    // Potion Colors in RGB
    public static final Color LIQUID = CardHelper.getColor(164.0f, 90.0f, 82.0f); // Redwood
    public static final Color HYBRID = CardHelper.getColor(164.0f, 90.0f, 82.0f); // Redwood
    public static final Color SPOTS = CardHelper.getColor(10.0f, 10.0f, 10.0f); // Blackish

    //possible time signatures: {2, 3, 4, 6}
    //drinking the beat potion gives 13 beats
    //13%2 = 13%3 = 13%4 = 13%6 = 1
    //so you will always end up 1 beat further than before drinking the potion
    public static final int POTENCY = 13;
    public static final int UPGRADE_POTENCY = 13;

    public BeatPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.SMOKE);
        isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
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
    public int getPotency(final int ascensionLevel) {
        return POTENCY;
    }

    public void upgradePotion()
    {
      potency += UPGRADE_POTENCY;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}