package theRhythmGirl.senddata;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.patches.MetricsPatch;

import static theRhythmGirl.RhythmGirlMod.*;

public class SendDataPopup extends ConfirmPopup {
    public static final String UI_ID = RhythmGirlMod.makeID("SendDataPopup");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    public SendDataPopup() {
        super(uiStrings.TEXT[0], uiStrings.TEXT[1], ConfirmPopup.ConfirmType.EXIT);
    }

    @Override
    protected void noButtonEffect(){
        this.shown = false;
    }

    @Override
    protected void yesButtonEffect(){
        sendRunData = true;
        enableButtonSendData.toggle.enabled = true;
        try {
            SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultRhythmGirlSettings);
            config.setBool(ENABLE_SEND_RUN_DATA_SETTINGS, sendRunData);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RunDetails runDetails = MetricsPatch.generateRunDetails();
        runDetails.win = (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.VictoryRoom || CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheEnding);
        RhythmGirlMod.logger.info("Send Data Prompt accepted");
        RhythmGirlMod.logger.info("Sending gameplay data");
        SendData.sendData(runDetails);
        RhythmGirlMod.logger.info("Gameplay data sent");
        this.shown = false;
    }
}