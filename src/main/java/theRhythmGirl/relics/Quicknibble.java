package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class Quicknibble extends CustomRelic implements OnGainMeasureSubscriber {

    public static final String ID = RhythmGirlMod.makeID("Quicknibble");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(Quicknibble.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(Quicknibble.class.getSimpleName()+".png"));

    private boolean triggeredThisTurn = false;

    public Quicknibble() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onGainMeasure(int numberOfMeasuresGained) {
        if (!this.triggeredThisTurn) {
            this.triggeredThisTurn = true;
            this.flash();
            this.addToBot(new CustomSFXAction("QUICKNIBBLE"));
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainEnergyAction(1));
        }
    }
}