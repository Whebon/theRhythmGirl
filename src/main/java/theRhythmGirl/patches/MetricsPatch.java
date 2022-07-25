package theRhythmGirl.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.senddata.CardDetails;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.senddata.RunDetails;
import theRhythmGirl.senddata.SendData;

//patch taken from the MysteryDungeon Mod

//note: the run data is lost when the run is abandoned from the main menu

public class MetricsPatch {
    public static Logger logger = LogManager.getLogger(RhythmGirlMod.class);
    public static int SEND_DATA_POPUP_MINIMUM_FLOORS = 17;

    @SpirePatch(clz = DeathScreen.class, method = "<ctor>", paramtypez = {MonsterGroup.class})
    public static class DeathScreenPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (!RhythmGirlMod.sendRunData && AbstractDungeon.floorNum >= SEND_DATA_POPUP_MINIMUM_FLOORS) {
                RhythmGirlMod.sendDataPopup.show();
            }
            if (!RhythmGirlMod.sendRunData)
                return;
            RunDetails runDetails = MetricsPatch.generateRunDetails();
            runDetails.win = (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.VictoryRoom || CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheEnding);
            MetricsPatch.logger.info("Sending gameplay data");
            SendData.sendData(runDetails);
            MetricsPatch.logger.info("Gameplay data sent");
        }
    }

    @SpirePatch(clz = VictoryScreen.class, method = "<ctor>", paramtypez = {MonsterGroup.class})
    public static class VictoryScreenPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (!RhythmGirlMod.sendRunData) {
                RhythmGirlMod.sendDataPopup.show();
            }
            if (!RhythmGirlMod.sendRunData)
                return;
            RunDetails runDetails = MetricsPatch.generateRunDetails();
            runDetails.win = true;
            MetricsPatch.logger.info("Sending gameplay data");
            SendData.sendData(runDetails);
            MetricsPatch.logger.info("Gameplay data sent");
        }
    }

    @SuppressWarnings("unchecked")
    public static RunDetails generateRunDetails() {
        RunDetails runDetails = new RunDetails();
        runDetails.characterName = AbstractDungeon.player.name;
        runDetails.sessionID = RhythmGirlMod.sessionID;
        runDetails.version = "failed";
        for (ModInfo modInfo : Loader.MODINFOS) {
            if (modInfo.ID.toLowerCase().contains("rhythmgirl")) {
                logger.info(modInfo.ModVersion.toString());
                runDetails.version = modInfo.ModVersion.toString();
            }
        }
        runDetails.ascensionLevel = AbstractDungeon.ascensionLevel;
        runDetails.maxFloor = AbstractDungeon.floorNum;
        runDetails.win = true;
        runDetails.elapsedTime = CardCrawlGame.playtime;
        runDetails.score = GameOverScreen.calcScore(runDetails.win);
        runDetails.seed = SeedHelper.getString(Settings.seed.longValue());
        ArrayList<CardDetails> cardDetails = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            CardDetails cardDetail = new CardDetails();
            cardDetail.name = card.cardID;
            cardDetail.upgrade = card.timesUpgraded;
            cardDetails.add(cardDetail);
        }
        runDetails.goldPerFloor = CardCrawlGame.metricData.gold_per_floor;
        runDetails.itemsPurchased = CardCrawlGame.metricData.items_purchased;
        runDetails.itemsPurged = CardCrawlGame.metricData.items_purged;
        runDetails.relicDetails = new ArrayList<>();
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            runDetails.relicDetails.add(relic.relicId);
        }
        for (HashMap cardChoice : CardCrawlGame.metricData.card_choices) {
            CardDetails pickedCardInfo = new CardDetails();
            Object pickedCard = cardChoice.get("picked");
            String cardNameWithUpgrade = (String)pickedCard;
            String[] nameAndUpgrade = cardNameWithUpgrade.split("\\+");
            pickedCardInfo.name = nameAndUpgrade[0];
            if (nameAndUpgrade.length == 2) {
                pickedCardInfo.upgrade = Integer.parseInt(nameAndUpgrade[1]);
            } else {
                pickedCardInfo.upgrade = 0;
            }
            runDetails.chosenCards.add(pickedCardInfo);
            ArrayList<String> notPickedCards = (ArrayList<String>)cardChoice.get("not_picked");
            for (String notPickedCard : notPickedCards) {
                CardDetails notPickedCardInfo = new CardDetails();
                String[] nameAndUpgrade2 = notPickedCard.split("\\+");
                notPickedCardInfo.name = nameAndUpgrade2[0];
                if (nameAndUpgrade2.length == 2) {
                    notPickedCardInfo.upgrade = Integer.parseInt(nameAndUpgrade2[1]);
                } else {
                    notPickedCardInfo.upgrade = 0;
                }
                runDetails.notChosenCards.add(notPickedCardInfo);
            }
        }
        runDetails.cardDetails = cardDetails;
        runDetails.customCardDetails = CustomMetrics.customCardDetails;
        return runDetails;
    }
}
