package theRhythmGirl.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.powers.RhythmHeavenPower;

public class HeavenPotion extends CustomPotion {

    public static final String POTION_ID = RhythmGirlMod.makeID("HeavenPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    // Potion Colors in RGB
    public static final Color LIQUID = CardHelper.getColor(255.0f, 255.0f, 255.0f); // White
    public static final Color HYBRID = CardHelper.getColor(231.0f, 231.0f, 0.0f); // Yellow
    public static final Color SPOTS = null;

    //Gain 1 Rhythm Heaven Power
    public static final int POTENCY = 1;
    public static final int UPGRADE_POTENCY = 1;

    public HeavenPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main RhythmGirlMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.MOON, PotionColor.WHITE);
        isThrown = false;
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("POWER_FLIGHT"));
        // If you are in combat, enter rhythm heaven
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ApplyPowerAction(target, target, new RhythmHeavenPower(target, target, potency), potency));
        }
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = potionStrings.DESCRIPTIONS[1] + potionStrings.DESCRIPTIONS[2];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0] + potionStrings.DESCRIPTIONS[2];
        }

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HeavenPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int ascensionLevel) {
        return POTENCY;
    }

    public void upgradePotion()
    {
      potency += UPGRADE_POTENCY;
      this.description = potionStrings.DESCRIPTIONS[1] + potionStrings.DESCRIPTIONS[2];
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}