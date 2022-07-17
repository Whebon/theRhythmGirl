package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: Lose 1 hp. Add a copy of this card to your hand. Exhaust.
//old version: Exhaustive 4

public class Metronome extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Metronome.class.getSimpleName());
    public static final String IMG = makeCardPath("Metronome.png");
    public static final String IMG_MIRRORED = makeCardPath("MetronomeMirrored.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;
    private static final int EXHAUSTIVE_USES = 4;

    // /STAT DECLARATION/

    private boolean isMirrored;

    public Metronome() {
        this(EXHAUSTIVE_USES);
    }

    public Metronome(int usesLeft) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //known bug: returnToHand ignores the maximum hand size
        this.returnToHand = true;
        this.isMirrored = false;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, EXHAUSTIVE_USES);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, usesLeft);
        ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, false);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        loadCardImage(isMirrored ? IMG : IMG_MIRRORED);
        this.isMirrored = !this.isMirrored;
        this.addToBot(new CustomSFXAction("METRONOME"));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            AlwaysRetainField.alwaysRetain.set(this, true);
        }
    }

    public AbstractCard makeCopy() {
        return new Metronome(ExhaustiveField.ExhaustiveFields.exhaustive.get(this));
    }
}
