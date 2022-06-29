package theRhythmGirl.cards;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.ui.BeatUI;

import java.util.HashMap;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractRhythmGirlCard extends CustomCard {

    public int defaultSecondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultBaseSecondMagicNumber;    // And our base stat - the number in its base state. It will reset to that by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public boolean mustBePlayedOnBeat = false;
    public HashMap<Integer, BeatUI.BeatColor> onBeatColor;
    public static HashMap<BeatUI.BeatColor, com.badlogic.gdx.graphics.Color> beatColorToGlow = new HashMap<BeatUI.BeatColor, com.badlogic.gdx.graphics.Color>(){{
        put(BeatUI.BeatColor.NORMAL, AbstractCard.BLUE_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.RED, Color.RED.cpy());
        put(BeatUI.BeatColor.WHITE, Color.GRAY.cpy());
        put(BeatUI.BeatColor.ON_BEAT, AbstractCard.GOLD_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.CUED, AbstractCard.BLUE_BORDER_GLOW_COLOR);
    }};

    public static String originalImg;

    public AbstractRhythmGirlCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        this(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }

    public AbstractRhythmGirlCard(final String id,
                                  final String name,
                                  final String img,
                                  final int cost,
                                  final String rawDescription,
                                  final CardType type,
                                  final CardColor color,
                                  final CardRarity rarity,
                                  final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
        originalImg = img;

        //Any non-NORMAL BeatColor will trigger all "On Beat"-related mechanics for that beat
        onBeatColor = new HashMap<>();
        onBeatColor.put(1, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(2, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(3, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(4, BeatUI.BeatColor.NORMAL);
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
        return onBeatColor.get(RhythmGirlMod.beatUI.currentBeat) != BeatUI.BeatColor.NORMAL;
    }

    public boolean onBeatTriggered(int beat){
        return RhythmGirlMod.beatUI.currentBeat == beat;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = beatColorToGlow.get(onBeatColor.get(RhythmGirlMod.beatUI.currentBeat)).cpy();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)){
            return false;
        }

        //todo: be more transparent about how 'mustBePlayedOnBeat' works to the player. maybe:
        //      * "cued cards must be played on beat"
        //      * "card that have no effect without their on beat effects cannot be played"
        //      * [BEST OPTION] Let the player be able to play the card and say: "This card must be played on beat to have effect"

        if (mustBePlayedOnBeat && !onBeatTriggered()) {
            this.cantUseMessage = "This card must be played #rOn #rBeat.";
            return false;
        }
        return true;
    }

    public void loadAlternativeCardImage(){
        this.loadCardImage(originalImg);
    }

    public void loadOriginalCardImage(){
        this.loadCardImage(originalImg);
    }
}