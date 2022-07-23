package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: uncommon
//old version: "Permanently remove this card from your deck." instead of "Fleeting"
//old version: 3 cost

public class NeverGiveUp extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(NeverGiveUp.class.getSimpleName());
    public static final String IMG = makeCardPath("NeverGiveUp.png");
    public static final String IMG_UPGRADED = makeCardPath("NeverGiveUpUpgraded.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    public static final Logger logger = LogManager.getLogger(RhythmGirlMod.class.getName());

    private static final int COST = 2;
    private static final int PERCENTAGE_DISPLAYED_ON_CARD = 25;
    private static final int UPGRADE_PERCENTAGE_DISPLAYED_ON_CARD = 50;

    private boolean descriptionUpdated = false;

    // /STAT DECLARATION/


    public NeverGiveUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = PERCENTAGE_DISPLAYED_ON_CARD;
        this.tags.add(CardTags.HEALING);

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
        this.addToBot(new CustomSFXAction("NEVER_GIVE_UP"));
        if (upgraded)
            this.addToBot(new HealAction(p, p, p.maxHealth*3/4));
        else
            this.addToBot(new HealAction(p, p, p.maxHealth/4));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PERCENTAGE_DISPLAYED_ON_CARD);
            loadCardImage(IMG_UPGRADED); //patched in CardPortraitUpgradeChange
            initializeDescription();
        }
    }
}
