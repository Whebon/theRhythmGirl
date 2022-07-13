package theRhythmGirl.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.powers.MeasurePower;
import theRhythmGirl.powers.PopularityPower;

public class PoseForTheFansAction extends AbstractGameAction {

    public static final String UI_ID = RhythmGirlMod.makeID("WarnNoMeasure");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);

    private final int multiplier;

    public PoseForTheFansAction(int multiplier) {
        this.multiplier = multiplier;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(MeasurePower.POWER_ID)){
            int amount = p.getPower(MeasurePower.POWER_ID).amount * this.multiplier;
            this.addToTop(new ApplyPowerAction(p, p, new PopularityPower(p, p, amount), amount));
            this.addToTop(new RemoveSpecificPowerAction(p, p, MeasurePower.POWER_ID));
        }
        else{
            AbstractDungeon.effectList.add(new ThoughtBubble(
                    p.dialogX,
                    p.dialogY,
                    3.0f,
                    uiStrings.TEXT[0],
                    true));
        }
        this.isDone = true;
    }
}