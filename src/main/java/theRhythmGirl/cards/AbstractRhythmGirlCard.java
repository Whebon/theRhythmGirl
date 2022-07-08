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

    //variables for the second magic number
    public int magicNumber2;
    public int baseMagicNumber2;
    public boolean upgradedMagicNumber2;
    public boolean isMagicNumber2Modified;

    //variables for 'On Beat' keyword
    public boolean mustBePlayedOnBeat = false;
    public HashMap<Integer, BeatUI.BeatColor> onBeatColor;
    public static HashMap<BeatUI.BeatColor, com.badlogic.gdx.graphics.Color> beatColorToGlow = new HashMap<BeatUI.BeatColor, com.badlogic.gdx.graphics.Color>(){{
        put(BeatUI.BeatColor.NORMAL, AbstractCard.BLUE_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.RED, Color.RED.cpy());
        put(BeatUI.BeatColor.BLUE, AbstractCard.BLUE_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.ON_BEAT, AbstractCard.GOLD_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.CUED, AbstractCard.BLUE_BORDER_GLOW_COLOR);
        put(BeatUI.BeatColor.RHYTHM_HEAVEN, Color.WHITE.cpy());
    }};

    public String originalImg;

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
        isMagicNumber2Modified = false;
        originalImg = img;

        //Any non-NORMAL BeatColor will trigger all "On Beat"-related mechanics for that beat
        onBeatColor = new HashMap<>();
        onBeatColor.put(1, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(2, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(3, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(4, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(5, BeatUI.BeatColor.NORMAL);
        onBeatColor.put(6, BeatUI.BeatColor.NORMAL);
}

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedMagicNumber2) {
            magicNumber2 = baseMagicNumber2;
            isMagicNumber2Modified = true;
        }
    }

    public void upgradeMagicNumber2(int amount) {
        baseMagicNumber2 += amount;
        magicNumber2 = baseMagicNumber2;
        upgradedMagicNumber2 = true;
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
        //get the closest on beat effect, with respect to the current beat
        int n = RhythmGirlMod.beatUI.getNumberOfPillars();
        for (int i = 0; i < n; i++) {
            int beat = (RhythmGirlMod.beatUI.currentBeat+i)%n+1;
            if (onBeatColor.get(beat) != BeatUI.BeatColor.NORMAL){
                return beat;
            }
        }
        /*
        //get the lowest on beat effect
        for (Integer i : onBeatColor.keySet()){
            if (onBeatColor.get(i) != BeatUI.BeatColor.NORMAL){
                return i;
            }
        }
         */
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
}