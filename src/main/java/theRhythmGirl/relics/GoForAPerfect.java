package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.util.TextureLoader;

import java.util.Iterator;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class GoForAPerfect extends CustomRelic {

    public static final String ID = RhythmGirlMod.makeID(GoForAPerfect.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(GoForAPerfect.class.getSimpleName()+".png"));
    private static final Texture IMG_BROKE = TextureLoader.getTexture(makeRelicPath(GoForAPerfect.class.getSimpleName()+"Broke.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(GoForAPerfect.class.getSimpleName()+".png"));

    private static final int GOLD = 35;

    private boolean tookDamageThisTurn;

    /*
    //I ended up using the STOLEN_GOLD enum to prevent further patching
    //known bug: save&quit on combat rewards with stolen gold and perfect gold. After reloading, both rewards are marked with '(Stolen Back)'
    public static class Enums {
        @SpireEnum
        public static RewardItem.RewardType PERFECT_GOLD;
    }
     */

    public GoForAPerfect() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
        this.tookDamageThisTurn = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void wasHPLost(int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !tookDamageThisTurn) {
            this.tookDamageThisTurn = true;
            this.setTexture(IMG_BROKE);
            this.flash();
            this.addToTop(new CustomSFXAction("PERFECT_BREAKS"));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

    }

    public void atPreBattle() {
        tookDamageThisTurn = false;
        this.setTexture(IMG);
    }

    public void justEnteredRoom(AbstractRoom room) {
        tookDamageThisTurn = false;
        this.setTexture(IMG);
    }

    public void onVictory() {
        if (!tookDamageThisTurn){
            this.flash();
            RewardItem rewardItem = new RewardItem(GOLD, true);
            rewardItem.text = rewardItem.goldAmt + DESCRIPTIONS[1];
            AbstractDungeon.getCurrRoom().rewards.add(rewardItem);
        }
        tookDamageThisTurn = false;
        this.setTexture(IMG);
    }
}