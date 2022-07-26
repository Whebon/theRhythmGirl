package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRhythmGirl.actions.BigFlexAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.PoseForTheFansAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.MeasurePower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class BigFlex extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(BigFlex.class.getSimpleName());
    public static final String IMG = makeCardPath(BigFlex.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int MULTIPLIER = 4;

    // /STAT DECLARATION/


    public BigFlex() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MULTIPLIER;
    }

    @Override
    public int getEffectiveness(){
        return AbstractDungeon.player.hasPower(MeasurePower.POWER_ID) ?
                AbstractDungeon.player.getPower(MeasurePower.POWER_ID).amount*magicNumber : 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //important note: card effects related to 'apply popularity' should be applied BEFORE a beat is gained.
        //this card is very indirectly related to a 'apply popularity', namely by having the same kind of behavior as "PoseForTheFans"
        this.addToTop(new CustomSFXAction("BIG_FLEX"));
        this.addToTop(new BigFlexAction(magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            GraveField.grave.set(this, true);
            initializeDescription();
        }
    }
}
