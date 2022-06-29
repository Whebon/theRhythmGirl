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
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.cards.*;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.events.IdentityCrisisEvent;
import theRhythmGirl.potions.PlaceholderPotion;
import theRhythmGirl.relics.*;
import theRhythmGirl.ui.BeatUI;
import theRhythmGirl.util.IDCheckDontTouchPls;
import theRhythmGirl.util.TextureLoader;
import theRhythmGirl.variables.DefaultCustomVariable;
import theRhythmGirl.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

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
        OnPlayerTurnStartSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Rhythm Girl";
    private static final String AUTHOR = "Whebon";
    private static final String DESCRIPTION = "An in-game description for my own Slay the Spire mod";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    // gray: CardHelper.getColor(64.0f, 70.0f, 70.0f);
    // skin: CardHelper.getColor(252.0f, 218.0f, 159.0f);
    // redwood: CardHelper.getColor(164.0f, 90.0f, 82.0f);
    public static final Color RHYTHM_GIRL_CHARACTER_COLOR = CardHelper.getColor(164.0f, 90.0f, 82.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theRhythmGirlResources/images/cardFrames/512/bg_attack_rhythm_girl_color.png";
    private static final String SKILL_DEFAULT_GRAY = "theRhythmGirlResources/images/cardFrames/512/bg_skill_rhythm_girl_color.png";
    private static final String POWER_DEFAULT_GRAY = "theRhythmGirlResources/images/cardFrames/512/bg_power_rhythm_girl_color.png";

    //todo: create custom energy orb
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theRhythmGirlResources/images/cardFrames/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theRhythmGirlResources/images/cardFrames/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/bg_attack_rhythm_girl_color.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/bg_skill_rhythm_girl_color.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/bg_power_rhythm_girl_color.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theRhythmGirlResources/images/cardFrames/1024/card_default_gray_orb.png";
    
    // Character assets
    //todo: slightly reduce the size of the corpse asset
    private static final String THE_RHYTHM_GIRL_BUTTON = "theRhythmGirlResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_RHYTHM_GIRL_PORTRAIT = "theRhythmGirlResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_RHYTHM_GIRL_SHOULDER_1 = "theRhythmGirlResources/images/char/rhythmGirlCharacter/shoulder.png";
    public static final String THE_RHYTHM_GIRL_SHOULDER_2 = "theRhythmGirlResources/images/char/rhythmGirlCharacter/shoulder2.png";
    public static final String THE_RHYTHM_GIRL_CORPSE = "theRhythmGirlResources/images/char/rhythmGirlCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theRhythmGirlResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    //whebon edit (atlas):
    //public static final String THE_DEFAULT_SKELETON_ATLAS = "theRhythmGirlResources/images/char/defaultCharacter/skeleton.atlas";
    //public static final String THE_DEFAULT_SKELETON_JSON = "theRhythmGirlResources/images/char/defaultCharacter/skeleton.json";

    //Whebon edit
    public static BeatUI beatUI;
    
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
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    //todo: clean up the mess in the 'no edit area'
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = RhythmGirlMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
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
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class) // for this specific event
            .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
            .playerClass(TheRhythmGirl.Enums.THE_RHYTHM_GIRL) // Character specific event
            .create();

        // Add the event
        BaseMod.addEvent(eventParams);

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
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheRhythmGirl.Enums.THE_RHYTHM_GIRL);
        
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
        BaseMod.addRelicToCustomPool(new TimeSignature44(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);
        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new TimeSignature44(), RelicType.SHARED);
        
        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
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
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
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

                // whebon edit:
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
        // whebon edit
        // please automate somehow
        BaseMod.addAudio("STRIKE", makeAudioPath("SFX_Strike.wav"));
        BaseMod.addAudio("MANDRILL_STRIKE_SOUR", makeAudioPath("SFX_MandrillStrikeSour.wav"));
        BaseMod.addAudio("MANDRILL_STRIKE_SWEET", makeAudioPath("SFX_MandrillStrikeSweet.wav"));
        BaseMod.addAudio("TALL_SCREW", makeAudioPath("SFX_TallScrew.wav"));
        BaseMod.addAudio("METRONOME", makeAudioPath("SFX_Metronome.wav"));
        BaseMod.addAudio("AIR_RALLY_13", makeAudioPath("SFX_AirRally13.wav"));
        BaseMod.addAudio("AIR_RALLY_24", makeAudioPath("SFX_AirRally24.wav"));
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
        BaseMod.addAudio("ODD_FLAMINGO", makeAudioPath("SFX_OddFlamingo.wav"));
        BaseMod.addAudio("SEESAW_REPEAT", makeAudioPath("SFX_SeeSawRepeat.wav"));
        BaseMod.addAudio("SEESAW_EXHAUST", makeAudioPath("SFX_SeeSawExhaust.wav"));
        BaseMod.addAudio("PITCH", makeAudioPath("SFX_Pitch.wav"));
        BaseMod.addAudio("HOME_RUN", makeAudioPath("SFX_HomeRun.wav"));
        BaseMod.addAudio("READY", makeAudioPath("SFX_Ready.wav"));
        BaseMod.addAudio("NEVER_GIVE_UP", makeAudioPath("SFX_NeverGiveUp.wav"));
        BaseMod.addAudio("MOCHI_POUNDING_1", makeAudioPath("SFX_MochiPounding1.wav"));
        BaseMod.addAudio("MOCHI_POUNDING_2", makeAudioPath("SFX_MochiPounding2.wav"));
        BaseMod.addAudio("MOCHI_POUNDING_3", makeAudioPath("SFX_MochiPounding3.wav"));
        BaseMod.addAudio("MOCHI_POUNDING_4", makeAudioPath("SFX_MochiPounding4.wav"));
        BaseMod.addAudio("PAUSEGILL_CUE", makeAudioPath("SFX_PausegillCue.wav"));
        BaseMod.addAudio("PAUSEGILL_CATCH", makeAudioPath("SFX_PausegillCatch.wav"));
    }

    @Override
    public void receivePostPlayerUpdate() {
        beatUI.update(AbstractDungeon.player);
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        AbstractDungeon.actionManager.addToBottom(new GainAdditionalBeatsAction(AbstractDungeon.player, AbstractDungeon.player, 1));
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        beatUI.reset();
    }
}
