package theRhythmGirl.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.red.Anger;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz = Anger.class,
        method = SpirePatch.CONSTRUCTOR
)
public class AngerPatch {
    
    private static final Logger logger = LogManager.getLogger(AngerPatch.class.getName());
    
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {}
    )
    public static void PatchMethod(Anger angerInstance) {
        logger.info("AngerPatch triggered");
        logger.info("Found: baseDamage = "+angerInstance.baseDamage);
        angerInstance.baseDamage = 7;
        logger.info("Modified: baseDamage = "+angerInstance.baseDamage);
        logger.info("Hopefully, I won't forget to delete this patch");
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(Anger.class,"baseDamage");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]+1};
        }
    }
}