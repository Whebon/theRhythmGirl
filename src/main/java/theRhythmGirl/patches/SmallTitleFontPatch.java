package theRhythmGirl.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

//This patch enables Working Dough to toggle useSmallTitleFont.
//Normally, it can't be turned off again once you turn it on.

public class SmallTitleFontPatch{
    @SpirePatch(
            clz= AbstractCard.class,
            method="initializeTitle"
    )
    public static class ResetSmallTitleFont
    {
        public static void Prefix(AbstractCard __instance, @ByRef boolean[] ___useSmallTitleFont)
        {
            ___useSmallTitleFont[0] = false;
        }
    }
}