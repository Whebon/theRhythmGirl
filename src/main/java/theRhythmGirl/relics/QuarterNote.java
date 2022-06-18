package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.actions.AddBeatAction;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.DefaultMod.makeRelicOutlinePath;
import static theRhythmGirl.DefaultMod.makeRelicPath;

public class QuarterNote extends CustomRelic {

    public static final String ID = DefaultMod.makeID("QuarterNote");

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
        AbstractDungeon.actionManager.addToBottom(new AddBeatAction(AbstractDungeon.player, AbstractDungeon.player));
    }

    public void onVictory(){
        this.counter = -1;
    }
}