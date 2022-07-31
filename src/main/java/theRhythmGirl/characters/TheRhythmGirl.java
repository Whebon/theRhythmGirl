package theRhythmGirl.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.*;
import theRhythmGirl.relics.*;

import java.util.ArrayList;
import java.util.List;

import static theRhythmGirl.RhythmGirlMod.*;
import static theRhythmGirl.RhythmGirlMod.makeScenePath;
import static theRhythmGirl.characters.TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

public class TheRhythmGirl extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_RHYTHM_GIRL;
        @SpireEnum(name = "COLOR_RHYTHM_GIRL") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_RHYTHM_GIRL;
        @SpireEnum(name = "COLOR_RHYTHM_GIRL") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("RhythmGirlCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================

    public static final String[] orbTextures = {
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer1.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer2.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer3.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer4.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer5.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer6.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer1d.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer2d.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer3d.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer4d.png",
            "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/layer5d.png",};


    public TheRhythmGirl(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theRhythmGirlResources/images/char/rhythmGirlCharacter/energyOrb/vfx.png", null,
                new SpriterAnimation(
                        "theRhythmGirlResources/images/char/rhythmGirlCharacter/Spriter/theRhythmGirlAnimation.scml"));

        initializeClass(null,
                THE_RHYTHM_GIRL_SHOULDER_2,
                THE_RHYTHM_GIRL_SHOULDER_1,
                THE_RHYTHM_GIRL_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(Strike_RhythmGirl.ID);
        retVal.add(Strike_RhythmGirl.ID);
        retVal.add(Strike_RhythmGirl.ID);
        retVal.add(Strike_RhythmGirl.ID);
        retVal.add(Defend_RhythmGirl.ID);
        retVal.add(Defend_RhythmGirl.ID);
        retVal.add(Defend_RhythmGirl.ID);
        retVal.add(Defend_RhythmGirl.ID);
        retVal.add(MandrillStrike.ID);
        retVal.add(IntoYou.ID);

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(TimeSignature44.ID);
        UnlockTracker.markRelicAsSeen(TimeSignature44.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.play(getCustomModeCharacterButtonSoundKey());
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "CLICK_BUTTON";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at Ascension 14 or higher
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_RHYTHM_GIRL;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return RhythmGirlMod.RHYTHM_GIRL_CHARACTER_COLOR;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new IntoYou();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheRhythmGirl(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return RhythmGirlMod.RHYTHM_GIRL_CHARACTER_COLOR;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return RhythmGirlMod.RHYTHM_GIRL_CHARACTER_COLOR;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage(makeScenePath("rhythmGirlBg.jpg"));
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(makeScenePath("rhythmGirl1.png"), "MARSHAL_JUMP_1"));
        panels.add(new CutscenePanel(makeScenePath("rhythmGirl2.png"), "MARSHAL_JUMP_1"));
        panels.add(new CutscenePanel(makeScenePath("rhythmGirl3.png"), "MARSHAL_JUMP_2"));
        return panels;
    }
}