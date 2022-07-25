package theRhythmGirl.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import theRhythmGirl.RhythmGirlMod;

public class SendDataPopupPatch{
    @SpirePatch(
            clz= DeathScreen.class,
            method="render"
    )
    public static class RenderSendDataPopup
    {
        public static SpireReturn<Void> Prefix(GameOverScreen __instance, SpriteBatch sb) {
            if (RhythmGirlMod.sendDataPopup.shown){
                RhythmGirlMod.sendDataPopup.render(sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= DeathScreen.class,
            method="update"
    )
    public static class UpdateSendDataPopup
    {
        public static SpireReturn<Void> Prefix(GameOverScreen __instance) {
            if (RhythmGirlMod.sendDataPopup.shown){
                RhythmGirlMod.sendDataPopup.update();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= VictoryScreen.class,
            method="render"
    )
    public static class RenderVictorySendDataPopup
    {
        public static SpireReturn<Void> Prefix(GameOverScreen __instance, SpriteBatch sb) {
            if (RhythmGirlMod.sendDataPopup.shown){
                RhythmGirlMod.sendDataPopup.render(sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= VictoryScreen.class,
            method="update"
    )
    public static class UpdateVictorySendDataPopup
    {
        public static SpireReturn<Void> Prefix(GameOverScreen __instance) {
            if (RhythmGirlMod.sendDataPopup.shown){
                RhythmGirlMod.sendDataPopup.update();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}