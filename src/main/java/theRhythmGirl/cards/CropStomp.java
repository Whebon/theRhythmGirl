package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class CropStomp extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(CropStomp.class.getSimpleName());

    public static final String IMG_13 = makeCardPath("CropStomp_13.png");
    public static final String IMG_24 = makeCardPath("CropStomp_24.png");
    public static final String IMG_1234 = makeCardPath("CropStomp_1234.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;

    private static final int DRAW = 2;
    private static final int UPGRADE_DRAW = 1;
    private static final int ENERGY = 2;
    private static final int UPGRADE_ENERGY = 1;

    // /STAT DECLARATION/

    public CropStomp() {
        super(ID, IMG_1234, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DRAW;
        baseMagicNumber2 = magicNumber2 = ENERGY;

        mustBePlayedOnBeat = true;
        onBeatColor.put(1, BeatUI.BeatColor.BLUE);
        onBeatColor.put(2, BeatUI.BeatColor.ON_BEAT);
        onBeatColor.put(3, BeatUI.BeatColor.BLUE);
        onBeatColor.put(4, BeatUI.BeatColor.ON_BEAT);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(1) || this.onBeatTriggered(3)){
            if (this.onBeatTriggered(2) || this.onBeatTriggered(4)){
                this.exhaust = true;
                this.loadCardImage(IMG_1234);
            }
            else{
                this.exhaust = false;
                this.loadCardImage(IMG_13);
            }
        }
        else {
            this.exhaust = true;
            this.loadCardImage(IMG_24);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (onBeatTriggered(1) || onBeatTriggered(3)){
            this.addToBot(new CustomSFXAction("CROP_STOMP_13"));
            this.addToBot(new DrawCardAction(p, this.magicNumber));
        }
        if (onBeatTriggered(2) || onBeatTriggered(4)){
            this.addToBot(new CustomSFXAction("CROP_STOMP_24"));
            this.addToBot(new GainEnergyAction(this.magicNumber2));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            upgradeMagicNumber2(UPGRADE_ENERGY);
            initializeDescription();
        }
    }
}