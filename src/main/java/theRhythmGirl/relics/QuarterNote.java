package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.GainBeatAction;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class QuarterNote extends CustomRelic {

    public static final String ID = RhythmGirlMod.makeID("QuarterNote");

    //todo: redraw the time signature relic icon such that the lines match up with the time signature relic
    //todo: remove the QuarterNote relic and make it a mechanic of the 'beat' keyword itself. by the means of one of these methods:
    //      * im 'OnCardUseSubscriber' in the main mod (downside: the subscriber is a pre-use event)
    //      * in the beat power (downside: must have beat already)
    //      * in 'onUseCard' (downside: only works with rhythm girl cards)
    //todo: update the description of beat/measure to describe what the quarterNote used to do
    //todo: visualize beats and measures better using custom batches

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("quarter_note.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("quarter_note.png"));

    public QuarterNote() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new GainBeatAction(AbstractDungeon.player, AbstractDungeon.player));
    }

    public void onVictory(){
        this.counter = -1;
    }
}