package theRhythmGirl.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theRhythmGirl.RhythmGirlMod;

public class BeatUIPatch{
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="render"
    )
    public static class RenderBeatUI
    {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb)
        {
            RhythmGirlMod.beatUI.render(sb, __instance);
        }
    }
}
