package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class WanderingSamurai extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(WanderingSamurai.class.getSimpleName());
    public static final String IMG_13 = makeCardPath(WanderingSamurai.class.getSimpleName()+"_13.png");
    public static final String IMG_24 = makeCardPath(WanderingSamurai.class.getSimpleName()+"_24.png");
    public static final String IMG_1234 = makeCardPath(WanderingSamurai.class.getSimpleName()+"_1234.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int STRENGTH = 2;
    private static final int DEXTERITY = 2;
    private static final int UPGRADE_STRENGTH = 1;
    private static final int UPGRADE_DEXTERITY = 1;

    // /STAT DECLARATION/


    public WanderingSamurai() {
        super(ID, IMG_1234, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = STRENGTH;
        baseMagicNumber2 = magicNumber2 = DEXTERITY;

        onBeatColor.put(1, BeatUI.BeatColor.RED);
        onBeatColor.put(2, BeatUI.BeatColor.GREEN);
        onBeatColor.put(3, BeatUI.BeatColor.RED);
        onBeatColor.put(4, BeatUI.BeatColor.GREEN);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(1) || this.onBeatTriggered(3)){
            if (this.onBeatTriggered(2) || this.onBeatTriggered(4)){
                this.loadCardImage(IMG_1234);
            }
            else{
                this.loadCardImage(IMG_13);
            }
        }
        else {
            this.loadCardImage(IMG_24);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (onBeatTriggered(1) || onBeatTriggered(3)){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SFX_WANDERING_SAMURAI_13"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new StrengthPower(p, magicNumber), magicNumber));
        }
        if (onBeatTriggered(2) || onBeatTriggered(4)){
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SFX_WANDERING_SAMURAI_24"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new DexterityPower(p, magicNumber2), magicNumber2));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STRENGTH);
            upgradeMagicNumber2(UPGRADE_DEXTERITY);
            initializeDescription();
        }
    }
}