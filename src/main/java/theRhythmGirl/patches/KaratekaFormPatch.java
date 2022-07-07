package theRhythmGirl.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import javassist.CtBehavior;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.powers.KaratekaFormPower;

public class KaratekaFormPatch {

    public static final String UI_ID = RhythmGirlMod.makeID("WarnKaratekaForm");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    @SpirePatch(
            clz= AbstractCard.class,
            method="canUse"
    )
    public static class RestrictUseCardWithKaratekaForm
    {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance, AbstractPlayer p, AbstractMonster m)
        {
            if (p.hasPower(KaratekaFormPower.POWER_ID) && RhythmGirlMod.beatUI.currentBeat!=1 && __instance.type == AbstractCard.CardType.ATTACK) {
                __instance.cantUseMessage = uiStrings.TEXT[0];
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="updateSingleTargetInput"
    )
    public static class FlashKaratekaFormPowerSingleTarget{
        @SpireInsertPatch(
                locator = KaratekaFormPatch.Locator.class,
                localvars = {}
        )
        public static void PatchMethod(AbstractPlayer __instance)
        {
            if (AbstractDungeon.player.hasPower(KaratekaFormPower.POWER_ID) && uiStrings.TEXT[0].equals(__instance.hoveredCard.cantUseMessage)){
                AbstractDungeon.player.getPower(KaratekaFormPower.POWER_ID).flashWithoutSound();
            }
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="clickAndDragCards"
    )
    public static class FlashKaratekaFormPowerMultiTarget{
        @SpireInsertPatch(
                locator = KaratekaFormPatch.Locator.class,
                localvars = {}
        )
        public static void PatchMethod(AbstractPlayer __instance)
        {
            if (AbstractDungeon.player.hasPower(KaratekaFormPower.POWER_ID) && uiStrings.TEXT[0].equals(__instance.hoveredCard.cantUseMessage)){
                AbstractDungeon.player.getPower(KaratekaFormPower.POWER_ID).flashWithoutSound();
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(ThoughtBubble.class);
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}