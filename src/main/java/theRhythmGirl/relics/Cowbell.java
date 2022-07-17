package theRhythmGirl.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.cards.AbstractRhythmGirlCard;
import theRhythmGirl.cards.PartyCracker;
import theRhythmGirl.cards.Ready;
import theRhythmGirl.util.TextureLoader;

import java.util.List;

import static theRhythmGirl.RhythmGirlMod.makeRelicOutlinePath;
import static theRhythmGirl.RhythmGirlMod.makeRelicPath;

public class Cowbell extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib

    // ID, images, text.
    public static final String ID = RhythmGirlMod.makeID(Cowbell.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(Cowbell.class.getSimpleName()+".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(Cowbell.class.getSimpleName()+".png"));

    private boolean usedThisCombat = false;
    private boolean isPlayerTurn = false;

    public Cowbell() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onRightClick() {// On right click
        if (!isObtained || usedThisCombat || !isPlayerTurn) {
            return;
        }
        
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            usedThisCombat = true;
            flash();
            stopPulse();
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("COWBELL"));
            AbstractDungeon.actionManager.addToBottom(new GainAdditionalBeatsAction(AbstractDungeon.player, AbstractDungeon.player));
        }
    }
    
    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        beginLongPulse();
    }

    public void atTurnStart() {
        isPlayerTurn = true;
    }
    
    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false;
        stopPulse();
    }
    

    @Override
    public void onVictory() {
        stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
