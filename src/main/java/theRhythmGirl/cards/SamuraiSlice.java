package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class SamuraiSlice extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(SamuraiSlice.class.getSimpleName());
    public static final String IMG = makeCardPath(SamuraiSlice.class.getSimpleName()+"_3.png");
    public static final String IMG_ALTERNATIVE = makeCardPath(SamuraiSlice.class.getSimpleName()+"_4.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 12;

    // /STAT DECLARATION/

    public SamuraiSlice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        onBeatColor.put(3, BeatUI.BeatColor.CUED);
        onBeatColor.put(4, BeatUI.BeatColor.CUED);
        mustBePlayedOnBeat = true;
        CardModifierManager.addModifier(this, new ExhaustAndEtherealModifier());
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(4)) {
            this.loadCardImage(IMG_ALTERNATIVE);
        }
        else{
            this.loadCardImage(IMG);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (RhythmGirlMod.beatUI.currentBeat == 3)
            this.addToBot(new CustomSFXAction("SAMURAI_SLICE_3"));
        else
            this.addToBot(new CustomSFXAction("SAMURAI_SLICE_4"));
        this.addToBot(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY, false, enableCustomSoundEffects));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}