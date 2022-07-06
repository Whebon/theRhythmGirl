/*package theRhythmGirl.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.random.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.events.BossaNovaEvent;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "generateEvent"
)
public class ForceEventPatch {
    
    private static final Logger logger = LogManager.getLogger(ForceEventPatch.class.getName());

    public static SpireReturn<AbstractEvent> Prefix(Random rng) {
        logger.info("Forcing an Event");
        return SpireReturn.Return(new BossaNovaEvent());
    }
}*/