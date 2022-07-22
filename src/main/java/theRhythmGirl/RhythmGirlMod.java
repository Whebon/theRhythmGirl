package theRhythmGirl;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.cards.*;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.events.BossaNovaEvent;
import theRhythmGirl.potions.BeatPotion;
import theRhythmGirl.potions.HeavenPotion;
import theRhythmGirl.relics.*;
import theRhythmGirl.senddata.SendDataPopup;
import theRhythmGirl.ui.BeatUI;
import theRhythmGirl.util.IDCheckDontTouchPls;
import theRhythmGirl.util.TextureLoader;
import theRhythmGirl.variables.MagicNumber2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//todo: more cards for the coffee archetype
//todo: an attack on 1, for coffee and karateka form

@SpireInitializer
public class RhythmGirlMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnCardUseSubscriber,
        AddAudioSubscriber,
        PostPlayerUpdateSubscriber,
        OnPlayerTurnStartSubscriber,
        PostBattleSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultRhythmGirlSettings = new Properties();
    public static final String ENABLE_CUSTOM_SOUND_EFFECTS_SETTINGS = "enableCustomSoundEffects";
    public static boolean enableCustomSoundEffects = true; // The boolean we'll be setting on/off (true/false)
    public static final String ENABLE_SEND_RUN_DATA_SETTINGS = "sendRunData";
    public static boolean sendRunData = false;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Rhythm Girl";
    private static final String AUTHOR = "Whebon";
    private static final String DESCRIPTION = "An in-game description for my own Slay the Spire mod";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    // gray: CardHelper.getColor(64.0f, 70.0f, 70.0f);
    // skin: CardHelper.getColor(252.0f, 218.0f, 159.0f);
    // redwood: CardHelper.getColor(164.0f, 90.0f, 82.0f); (#a45a52)
    // brown (orb): CardHelper.getColor(127.0f, 83.0f, 40.0f) (#7f5328)
    public static final Color RHYTHM_GIRL_CHARACTER_COLOR = CardHelper.getColor(164.0f, 90.0f, 82.0f);

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_RHYTHM_GIRL = "theRhythmGirlResources/images/cardFrames/512/bg_attack_rhythm_girl_color.png";
    private static final String SKILL_RHYTHM_GIRL = "theRhythmGirlResources/images/cardFrames/512/bg_skill_rhythm_girl_color.png";
    private static final String POWER_RHYTHM_GIRL = "theRhythmGirlResources/images/cardFrames/512/bg_power_rhythm_girl_color.png";

    private static final String ENERGY_ORB_RHYTHM_GIRL = "theRhythmGirlResources/images/cardFrames/512/card_rhythm_girl_orb.png";
    private static final String CARD_ENERGY_ORB = "theRhythmGirlResources/images/cardFrames/512/card_small_orb.png";
    
    private static final String ATTACK_RHYTHM_GIRL_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/bg_attack_rhythm_girl_color.png";
    private static final String SKILL_RHYTHM_GIRL_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/bg_skill_rhythm_girl_color.png";
    private static final String POWER_RHYTHM_GIRL_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/bg_power_rhythm_girl_color.png";
    private static final String ENERGY_ORB_RHYTHM_GIRL_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/card_rhythm_girl_orb.png";
    
    // Character assets
    private static final String THE_RHYTHM_GIRL_BUTTON = "theRhythmGirlResources/images/charSelect/RhythmGirlCharacterButton.png";
    private static final String THE_RHYTHM_GIRL_PORTRAIT = "theRhythmGirlResources/images/charSelect/RhythmGirlCharacterPortraitBG.png";
    public static final String THE_RHYTHM_GIRL_SHOULDER_1 = "theRhythmGirlResources/images/char/rhythmGirlCharacter/shoulder.png";
    public static final String THE_RHYTHM_GIRL_SHOULDER_2 = "theRhythmGirlResources/images/char/rhythmGirlCharacter/shoulder2.png";
    public static final String THE_RHYTHM_GIRL_CORPSE = "theRhythmGirlResources/images/char/rhythmGirlCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theRhythmGirlResources/images/Badge.png";

    //singleton beatUI
    public static BeatUI beatUI;

    public static ConfirmPopup sendDataPopup;
    public static ModLabeledToggleButton enableButtonSendData;
    
    // =============== MAKE IMAGE PATHS =================

    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/sfx/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeScenePath(String resourcePath) {
        return getModID() + "Resources/images/scenes/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_RHYTHM_GIRL, INITIALIZE =================
    
    public RhythmGirlMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
      
        setModID("theRhythmGirl");
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL.toString());
        
        BaseMod.addColor(TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL, RHYTHM_GIRL_CHARACTER_COLOR, RHYTHM_GIRL_CHARACTER_COLOR, RHYTHM_GIRL_CHARACTER_COLOR,
                RHYTHM_GIRL_CHARACTER_COLOR, RHYTHM_GIRL_CHARACTER_COLOR, RHYTHM_GIRL_CHARACTER_COLOR, RHYTHM_GIRL_CHARACTER_COLOR,
                ATTACK_RHYTHM_GIRL, SKILL_RHYTHM_GIRL, POWER_RHYTHM_GIRL, ENERGY_ORB_RHYTHM_GIRL,
                ATTACK_RHYTHM_GIRL_PORTRAIT, SKILL_RHYTHM_GIRL_PORTRAIT, POWER_RHYTHM_GIRL_PORTRAIT,
                ENERGY_ORB_RHYTHM_GIRL_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultRhythmGirlSettings.setProperty(ENABLE_CUSTOM_SOUND_EFFECTS_SETTINGS, "TRUE"); // This is the default setting. It's actually set...
        theDefaultRhythmGirlSettings.setProperty(ENABLE_SEND_RUN_DATA_SETTINGS, "TRUE");
        try {
            SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultRhythmGirlSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enableCustomSoundEffects = config.getBool(ENABLE_CUSTOM_SOUND_EFFECTS_SETTINGS);
            sendRunData = config.getBool(ENABLE_SEND_RUN_DATA_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    //it's really tempting to edit this area, but I feel pretty threatened :O
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = RhythmGirlMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        assert in != null;
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = RhythmGirlMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        assert in != null;
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = RhythmGirlMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    public static void initialize() {
        logger.info("======================== Initializing The Rhythm Girl Mod. ========================");
        //this is important
        RhythmGirlMod rhythmGirlMod = new RhythmGirlMod();
        logger.info("======================== /The Rhythm Girl Mod Initialized./ ========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheRhythmGirl.Enums.THE_RHYTHM_GIRL.toString());
        
        BaseMod.addCharacter(new TheRhythmGirl("the Rhythm Girl", TheRhythmGirl.Enums.THE_RHYTHM_GIRL),
                THE_RHYTHM_GIRL_BUTTON, THE_RHYTHM_GIRL_PORTRAIT, TheRhythmGirl.Enums.THE_RHYTHM_GIRL);
        
        receiveEditPotions();
        logger.info("Added " + TheRhythmGirl.Enums.THE_RHYTHM_GIRL.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create on/off button for enableCustomSoundEffects
        ModLabeledToggleButton enableButtonSoundEffects = new ModLabeledToggleButton("Enable Custom Sound Effects from Rhythm Heaven",
                359.0f, 708.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enableCustomSoundEffects, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enableCustomSoundEffects = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultRhythmGirlSettings);
                config.setBool(ENABLE_CUSTOM_SOUND_EFFECTS_SETTINGS, enableCustomSoundEffects);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableButtonSoundEffects); // Add the button to the settings panel. Button is a go.

        // Create on/off button for sendRunData
        // sendDataPopup and enableButtonSendData are public static fields, because they need to be accessed in the SendDataPopup
        sendDataPopup = new SendDataPopup();
        enableButtonSendData = new ModLabeledToggleButton("Contribute to balancing this mod by sending anonymous deck data",
                359.0f, 749.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                sendRunData,
                settingsPanel,
                (label) -> {},
                (button) -> {
                    sendRunData = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultRhythmGirlSettings);
                        config.setBool(ENABLE_SEND_RUN_DATA_SETTINGS, sendRunData);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        settingsPanel.addUIElement(enableButtonSendData);
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================

        //adding the bossa nova event
        AddEventParams bossaNovaEventParams = new AddEventParams.Builder(BossaNovaEvent.ID, BossaNovaEvent.class)
            .dungeonID(Exordium.ID)
            .playerClass(TheRhythmGirl.Enums.THE_RHYTHM_GIRL)
            .create();
        BaseMod.addEvent(bossaNovaEventParams);

        // Add the beat ui
        beatUI = new BeatUI();

        // =============== /EVENTS/ =================

        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(BeatPotion.class, BeatPotion.LIQUID, BeatPotion.HYBRID, BeatPotion.SPOTS, BeatPotion.POTION_ID, TheRhythmGirl.Enums.THE_RHYTHM_GIRL);
        BaseMod.addPotion(HeavenPotion.class, HeavenPotion.LIQUID, HeavenPotion.HYBRID, HeavenPotion.SPOTS, HeavenPotion.POTION_ID, TheRhythmGirl.Enums.THE_RHYTHM_GIRL);
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractRhythmGirlCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new TimeSignature24(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new TimeSignature34(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new TimeSignature44(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new TimeSignature54(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new Cowbell(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new Widget(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new Quicknibble(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new Freepeat(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new GoForAPerfect(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new TimeSignature44(), RelicType.SHARED);
        
        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        UnlockTracker.markRelicAsSeen(TimeSignature24.ID);
        UnlockTracker.markRelicAsSeen(TimeSignature34.ID);
        UnlockTracker.markRelicAsSeen(TimeSignature44.ID);
        UnlockTracker.markRelicAsSeen(TimeSignature54.ID);
        UnlockTracker.markRelicAsSeen(Cowbell.ID);
        UnlockTracker.markRelicAsSeen(Widget.ID);
        UnlockTracker.markRelicAsSeen(Quicknibble.ID);
        UnlockTracker.markRelicAsSeen(Freepeat.ID);
        UnlockTracker.markRelicAsSeen(GoForAPerfect.ID);

        logger.info("Done adding relics");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new MagicNumber2());
        
        logger.info("Adding cards");
        // Add the cards
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        new AutoAdd("RhythmGirl") // ${project.artifactId}
            .packageFilter(AbstractRhythmGirlCard.class) // filters to any class in the same package as AbstractRhythmGirlCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-Character-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/RhythmGirlMod-UI-Strings.json");
        
        logger.info("Done editing strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/RhythmGirlMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)

                // note to self: added keywords do not get parsed correctly in relic strings.
                // expected behavior: therhythmgirl:beat -> `Beat` [Beat, description]
                // actual behavior: therhythmgirl:beat -> therhythmgirl:beat [Beat, description]
                // workaround: #yBeat -> `Beat` []
                // downside of the workaround: the keyword doesn't get parsed so the description of the keyword is not displayed
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveAddAudio() {
        // please automate this somehow
        BaseMod.addAudio("STRIKE", makeAudioPath("SFX_Strike.wav"));
        BaseMod.addAudio("MANDRILL_STRIKE_SOUR", makeAudioPath("SFX_MandrillStrikeSour.wav"));
        BaseMod.addAudio("MANDRILL_STRIKE_SWEET", makeAudioPath("SFX_MandrillStrikeSweet.wav"));
        BaseMod.addAudio("TALL_SCREW", makeAudioPath("SFX_TallScrew.wav"));
        BaseMod.addAudio("TALL_SCREW_UPGRADED", makeAudioPath("SFX_TallScrewUpgraded.wav"));
        BaseMod.addAudio("METRONOME", makeAudioPath("SFX_Metronome.wav"));
        BaseMod.addAudio("AIR_RALLY_REPEAT", makeAudioPath("SFX_AirRallyRepeat.wav"));
        BaseMod.addAudio("AIR_RALLY_EXHAUST", makeAudioPath("SFX_AirRallyExhaust.wav"));
        BaseMod.addAudio("MICRO_ROW_SWIM", makeAudioPath("SFX_MicroRowSwim.wav"));
        BaseMod.addAudio("JAB_REPEAT", makeAudioPath("SFX_JabRepeat.wav"));
        BaseMod.addAudio("JAB_EXHAUST", makeAudioPath("SFX_JabExhaust.wav"));
        BaseMod.addAudio("INTO_YOU_REPEAT", makeAudioPath("SFX_IntoYouRepeat.wav"));
        BaseMod.addAudio("INTO_YOU_EXHAUST", makeAudioPath("SFX_IntoYouExhaust.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_1", makeAudioPath("SFX_FlipperRoll1.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_2", makeAudioPath("SFX_FlipperRoll2.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_3", makeAudioPath("SFX_FlipperRoll3.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_4", makeAudioPath("SFX_FlipperRoll4.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_5", makeAudioPath("SFX_FlipperRoll5.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_6", makeAudioPath("SFX_FlipperRoll6.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_7", makeAudioPath("SFX_FlipperRoll7.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_8", makeAudioPath("SFX_FlipperRoll8.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_9", makeAudioPath("SFX_FlipperRoll9.wav"));
        BaseMod.addAudio("FLIPPER_ROLL_10", makeAudioPath("SFX_FlipperRoll10.wav"));
        BaseMod.addAudio("DOUBLE_UP", makeAudioPath("SFX_DoubleUp.wav"));
        BaseMod.addAudio("DOUBLE_UP_TRIGGER", makeAudioPath("SFX_DoubleUpTrigger.wav"));
        BaseMod.addAudio("SEESAW_REPEAT", makeAudioPath("SFX_SeeSawRepeat.wav"));
        BaseMod.addAudio("SEESAW_EXHAUST", makeAudioPath("SFX_SeeSawExhaust.wav"));
        BaseMod.addAudio("PITCH", makeAudioPath("SFX_Pitch.wav"));
        BaseMod.addAudio("HOME_RUN", makeAudioPath("SFX_HomeRun.wav"));
        BaseMod.addAudio("READY", makeAudioPath("SFX_Ready.wav"));
        BaseMod.addAudio("NEVER_GIVE_UP", makeAudioPath("SFX_NeverGiveUp.wav"));
        BaseMod.addAudio("MOCHI_POUNDING", makeAudioPath("SFX_MochiPounding.wav"));
        BaseMod.addAudio("PAUSEGILL_CUE", makeAudioPath("SFX_PausegillCue.wav"));
        BaseMod.addAudio("PAUSEGILL_CATCH", makeAudioPath("SFX_PausegillCatch.wav"));
        BaseMod.addAudio("BIG_FLEX", makeAudioPath("SFX_BigFlex.wav"));
        BaseMod.addAudio("LAUNCH_PARTY_APPLY", makeAudioPath("SFX_LaunchPartyApply.wav"));
        BaseMod.addAudio("LAUNCH_PARTY_BLAST", makeAudioPath("SFX_LaunchPartyBlast.wav"));
        BaseMod.addAudio("PARTY_CRACKER_APPLY", makeAudioPath("SFX_PartyCrackerApply.wav"));
        BaseMod.addAudio("PARTY_CRACKER_BLAST", makeAudioPath("SFX_PartyCrackerBlast.wav"));
        BaseMod.addAudio("SPACE_BELL_APPLY", makeAudioPath("SFX_SpaceBellApply.wav"));
        BaseMod.addAudio("SPACE_BELL_BLAST", makeAudioPath("SFX_SpaceBellBlast.wav"));
        BaseMod.addAudio("BEAT_POTION", makeAudioPath("SFX_BeatPotion.wav"));
        BaseMod.addAudio("COWBELL", makeAudioPath("SFX_Cowbell.wav"));
        BaseMod.addAudio("COUNT_1", makeAudioPath("SFX_Count1.wav"));
        BaseMod.addAudio("COUNT_2", makeAudioPath("SFX_Count2.wav"));
        BaseMod.addAudio("COUNT_3", makeAudioPath("SFX_Count3.wav"));
        BaseMod.addAudio("COUNT_4", makeAudioPath("SFX_Count4.wav"));
        BaseMod.addAudio("COUNT_GO", makeAudioPath("SFX_CountGo.wav"));
        BaseMod.addAudio("BOSSA_NOVA_1", makeAudioPath("SFX_BossaNova1.wav"));
        BaseMod.addAudio("BOSSA_NOVA_2", makeAudioPath("SFX_BossaNova2.wav"));
        BaseMod.addAudio("BOSSA_NOVA_3", makeAudioPath("SFX_BossaNova3.wav"));
        BaseMod.addAudio("BOSSA_NOVA_4", makeAudioPath("SFX_BossaNova4.wav"));
        BaseMod.addAudio("CLICK_BUTTON", makeAudioPath("SFX_ClickButton.wav"));
        BaseMod.addAudio("MARSHAL_JUMP_1", makeAudioPath("SFX_MarshalJump1.wav"));
        BaseMod.addAudio("MARSHAL_JUMP_2", makeAudioPath("SFX_MarshalJump2.wav"));
        BaseMod.addAudio("SCREWBOT_13", makeAudioPath("SFX_Screwbot13.wav"));
        BaseMod.addAudio("SCREWBOT_24", makeAudioPath("SFX_Screwbot24.wav"));
        BaseMod.addAudio("PEA_FLICK", makeAudioPath("SFX_PeaFlick.wav"));
        BaseMod.addAudio("PEA_FORK", makeAudioPath("SFX_PeaFork.wav"));
        BaseMod.addAudio("KARATEKA_FORM", makeAudioPath("SFX_KaratekaForm.wav"));
        BaseMod.addAudio("QUICKNIBBLE", makeAudioPath("SFX_Quicknibble.wav"));
        BaseMod.addAudio("ROLL_CALL", makeAudioPath("SFX_RollCall.wav"));
        BaseMod.addAudio("BOARD_MEETING", makeAudioPath("SFX_BoardMeeting.wav"));
        BaseMod.addAudio("COMBO", makeAudioPath("SFX_Combo.wav"));
        BaseMod.addAudio("COMBO_UPGRADED", makeAudioPath("SFX_ComboUpgraded.wav"));
        BaseMod.addAudio("BARREL", makeAudioPath("SFX_Barrel.wav"));
        BaseMod.addAudio("BARREL_OBTAIN", makeAudioPath("SFX_BarrelObtain.wav"));
        BaseMod.addAudio("FAN_CLUB", makeAudioPath("SFX_FanClub.wav"));
        BaseMod.addAudio("POPULARITY", makeAudioPath("SFX_Popularity.wav"));
        BaseMod.addAudio("POSE_FOR_THE_FANS", makeAudioPath("SFX_PoseForTheFans.wav"));
        BaseMod.addAudio("GROWING_FANBASE", makeAudioPath("SFX_GrowingFanbase.wav"));
        BaseMod.addAudio("CROP_STOMP_13", makeAudioPath("SFX_CropStomp13.wav"));
        BaseMod.addAudio("CROP_STOMP_24", makeAudioPath("SFX_CropStomp24.wav"));
        BaseMod.addAudio("SNEAKY_SPIRIT_SOUR", makeAudioPath("SFX_SneakySpiritSour.wav"));
        BaseMod.addAudio("SNEAKY_SPIRIT_SWEET", makeAudioPath("SFX_SneakySpiritSweet.wav"));
        BaseMod.addAudio("EARLY_BIRD", makeAudioPath("SFX_EarlyBird.wav"));
        BaseMod.addAudio("EARLY_BIRD_UPGRADED", makeAudioPath("SFX_EarlyBirdUpgraded.wav"));
        BaseMod.addAudio("TRY_AGAIN", makeAudioPath("SFX_TryAgain.wav"));
        BaseMod.addAudio("PERFECT_BREAKS", makeAudioPath("SFX_PerfectBreaks.wav"));
        BaseMod.addAudio("RAT_RACE", makeAudioPath("SFX_RatRace.wav"));
        BaseMod.addAudio("FILLBOTS_BEAT", makeAudioPath("SFX_FillbotsBeat.wav"));
        BaseMod.addAudio("FILLBOTS_FINAL_BEAT", makeAudioPath("SFX_FillbotsFinalBeat.wav"));
        BaseMod.addAudio("DONK_DONK", makeAudioPath("SFX_DonkDonk.wav"));
        BaseMod.addAudio("POWER_CALLIGRAPHY", makeAudioPath("SFX_PowerCalligraphy.wav"));
        BaseMod.addAudio("SFX_WANDERING_SAMURAI_13", makeAudioPath("SFX_WanderingSamurai13.wav"));
        BaseMod.addAudio("SFX_WANDERING_SAMURAI_24", makeAudioPath("SFX_WanderingSamurai24.wav"));

    }

    @Override
    public void receivePostPlayerUpdate() {
        beatUI.update(AbstractDungeon.player);
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        //usually, a beat is gained whenever a card is played
        //the description of beat tells that a beat is gained after playing a card, but this is what actually happens behind the scenes:
        //card checks current beat and applies actions accordingly --> gain 1 beat --> execute actions
        if (!(abstractCard instanceof CoffeeBreak) && !(abstractCard instanceof TryAgain) && !(abstractCard instanceof CountIn)){
            logger.info("Gain 1 Beat (played a card)");
            AbstractDungeon.actionManager.addToBottom(new GainAdditionalBeatsAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        beatUI.reset(true);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {beatUI.reset(false);}
}
