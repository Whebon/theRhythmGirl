package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.actions.ResetBeatAction;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.DefaultMod.makeRelicOutlinePath;
import static theRhythmGirl.DefaultMod.makeRelicPath;

public class TimeSignature44 extends CustomRelic {

    public static final String ID = DefaultMod.makeID("TimeSignature44");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("timesignature44.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("timesignature44.png"));

    public TimeSignature44() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.addToBot(new ResetBeatAction(AbstractDungeon.player, AbstractDungeon.player));
    }

    public void onVictory(){
        this.counter = -1;
    }
}