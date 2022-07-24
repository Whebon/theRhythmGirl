package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: 1-cost, gain 12 additional beats (upgrade: 24)
//old version: 1-cost, gain 8 beats (upgrade: repeat)
//old version: 1-cost, gain 4 beats (upgrade: 6)

public class MochiPounding extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(MochiPounding.class.getSimpleName());
    public static final String IMG = makeCardPath(MochiPounding.class.getSimpleName()+".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int ADDITIONAL_BEATS = 8;
    private static final int UPGRADE_ADDITIONAL_BEATS = 4;

    // /STAT DECLARATION/

    public MochiPounding() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = ADDITIONAL_BEATS;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("MOCHI_POUNDING"));
        this.addToBot(new GainAdditionalBeatsAction(p, p, magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_ADDITIONAL_BEATS);
            initializeDescription();
        }
    }
}