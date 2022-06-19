package theRhythmGirl.cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.powers.BeatPower;

public abstract class AbstractDefaultCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int defaultSecondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultBaseSecondMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public boolean isOnBeat1 = false;
    public boolean isOnBeat2 = false;
    public boolean isOnBeat3 = false;
    public boolean isOnBeat4 = false;

    public AbstractDefaultCard(final String id,
                               final String name,
                               final String img,
                               final int cost,
                               final String rawDescription,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isDefaultSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedDefaultSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public boolean onBeatTriggered(){
        if (!AbstractDungeon.player.hasPower(BeatPower.POWER_ID))
            return isOnBeat1;
        if (isOnBeat1 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount <= 1)
            return true;
        if (isOnBeat2 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 2)
            return true;
        if (isOnBeat3 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 3)
            return true;
        return isOnBeat4 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 4;
    }

    public boolean onBeatTriggered(int beat){
        if (!AbstractDungeon.player.hasPower(BeatPower.POWER_ID))
            return beat==1 && isOnBeat1;
        if (beat==1 && isOnBeat1 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount <= 1)
            return true;
        if (beat==2 && isOnBeat2 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 2)
            return true;
        if (beat==3 && isOnBeat3 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 3)
            return true;
        return beat==4 && isOnBeat4 && AbstractDungeon.player.getPower(BeatPower.POWER_ID).amount == 4;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}