package theRhythmGirl.cards;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.powers.RhythmHeavenPower;
import theRhythmGirl.ui.BeatUI;

import java.util.HashMap;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractRhythmGirlCard extends CustomCard {

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    public int defaultSecondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultBaseSecondMagicNumber;    // And our base stat - the number in its base state. It will reset to that by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public boolean mustBePlayedOnBeat = false;
    public HashMap<Integer, BeatUI.BeatColor> onBeatColor;
    public static HashMap<BeatUI.BeatColor, com.badlogic.gdx.graphics.Color> beatColorToGlow = new HashMap<BeatUI.BeatColor, com.badlogic.gdx.graphics.Color>(){{
        put(BeatUI.BeatColor.NORMAL, AbstractCard.BLUE_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.RED, Color.RED.cpy());
        put(BeatUI.BeatColor.BLUE, Color.TEAL.cpy());
        put(BeatUI.BeatColor.ON_BEAT, AbstractCard.GOLD_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.CUED, AbstractCard.BLUE_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.RHYTHM_HEAVEN, Color.WHITE.cpy());
    }};
    public boolean fetchedFromCountIn = false;

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
        if (AbstractDungeon.player.hasPower(RhythmHeavenPower.POWER_ID))
            return true;
        return onBeatColor.get(RhythmGirlMod.beatUI.currentBeat) != BeatUI.BeatColor.NORMAL;
    }

    public boolean onBeatTriggered(int beat){
        if (AbstractDungeon.player.hasPower(RhythmHeavenPower.POWER_ID))
            return true;
        return RhythmGirlMod.beatUI.currentBeat == beat;
    }

    public boolean hasOnBeatEffect(){
        for (BeatUI.BeatColor color : onBeatColor.values()){
            if (color != BeatUI.BeatColor.NORMAL)
                return true;
        }
        return false;
    }

    public int getOnBeatIntegerX(){
        for (Integer i : onBeatColor.keySet()){
            if (onBeatColor.get(i) != BeatUI.BeatColor.NORMAL){
                return i;
            }
        }
        logger.warn("Expected this card to have an 'On Beat X' effect, but it hasn't.");
        return -1;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (hasOnBeatEffect() && (AbstractDungeon.player.hasPower(RhythmHeavenPower.POWER_ID))){
            this.glowColor = beatColorToGlow.get(BeatUI.BeatColor.RHYTHM_HEAVEN).cpy();
        }
        else {
            this.glowColor = beatColorToGlow.get(onBeatColor.get(RhythmGirlMod.beatUI.currentBeat)).cpy();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)){
            return false;
        }
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

    public void loadCardImage(String img){
        this.textureImg = img;
        if (img != null) {
            super.loadCardImage(img);
        }
    }

    public void setFetchedFromCountIn(boolean state){
        fetchedFromCountIn = state;
    }

    public boolean getFetchedFromCountIn(){
        return fetchedFromCountIn;
    }
}