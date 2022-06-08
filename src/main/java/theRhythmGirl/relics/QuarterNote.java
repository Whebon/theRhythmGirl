package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.powers.BeatPower;
import theRhythmGirl.util.TextureLoader;

import static theRhythmGirl.DefaultMod.makeRelicOutlinePath;
import static theRhythmGirl.DefaultMod.makeRelicPath;

public class QuarterNote extends CustomRelic {

    public static final String ID = DefaultMod.makeID("QuarterNote");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public QuarterNote() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new BeatPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        ++this.counter;
        if (this.counter == 5) {
            this.counter = 1;
        }
    }

    public void onVictory(){
        this.counter = 1;
    }
}