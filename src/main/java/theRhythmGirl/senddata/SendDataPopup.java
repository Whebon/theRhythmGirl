package theRhythmGirl.senddata;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import theRhythmGirl.RhythmGirlMod;

import static theRhythmGirl.RhythmGirlMod.*;

public class SendDataPopup extends ConfirmPopup {
    public static final String UI_ID = RhythmGirlMod.makeID("SendDataPopup");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    public SendDataPopup() {
        super(uiStrings.TEXT[0], uiStrings.TEXT[1], ConfirmPopup.ConfirmType.EXIT);
        System.out.println("Initialized the SendDataPopup");
    }

    @Override
    protected void noButtonEffect(){
        System.out.println("No");
        this.shown = false;
    }

    @Override
    protected void yesButtonEffect(){
        System.out.println("Yes");
        sendRunData = true;
        enableButtonSendData.toggle.enabled = true;
        try {
            SpireConfig config = new SpireConfig("rhythmGirlMod", "theRhythmGirlConfig", theDefaultRhythmGirlSettings);
            config.setBool(ENABLE_SEND_RUN_DATA_SETTINGS, sendRunData);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.shown = false;
    }
}