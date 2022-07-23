package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;


public class OkItsOn extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(OkItsOn.class.getSimpleName());
    public static final String IMG = makeCardPath(OkItsOn.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    private static final int COST = 1;
    private static final int STRENGTH = 6;
    private static final int UPGRADE_STRENGTH = 6;

    private boolean descriptionUpdated = false;

    // /STAT DECLARATION/


    public OkItsOn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = STRENGTH;

        FleetingField.fleeting.set(this, true);
    }

    private void updateDescription(){
        //GOAL: marks the card as "copied" if it is not in the master deck.
        //uuids are copied after initialization, so running this in the constructor doesn't work.
        if (!this.descriptionUpdated){
            if (StSLib.getMasterDeckEquivalent(this) != null)
                this.rawDescription = cardStrings.DESCRIPTION;
            else
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION;
            super.initializeDescription();
            this.descriptionUpdated = true;
        }
    }

    @Override
    public void applyPowers() {
        updateDescription();
        super.applyPowers();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("OK_ITS_ON"));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STRENGTH);
            initializeDescription();
        }
    }
}
