package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.RemoveFromMasterDeckAction;
import theRhythmGirl.characters.TheRhythmGirl;

import java.util.UUID;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: uncommon

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
    private UUID uuidToRemove;
    private static final int COST = 3;
    private static final int PERCENTAGE_DISPLAYED_ON_CARD = 25;
    private static final int UPGRADE_PERCENTAGE_DISPLAYED_ON_CARD = 50;

    // /STAT DECLARATION/


    public NeverGiveUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = PERCENTAGE_DISPLAYED_ON_CARD;
        this.purgeOnUse = true;
        this.tags.add(CardTags.HEALING);
        this.uuidToRemove = uuid;
        //logger.info("NeverGiveUp own uuid: "+uuid.toString());
    }

    public NeverGiveUp(UUID uuidReference) {
        this();
        this.uuidToRemove = uuidReference;
        //logger.info("NeverGiveUp reference uuid: "+uuidToRemove.toString());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("NEVER_GIVE_UP"));
        if (upgraded)
            this.addToBot(new HealAction(p, p, p.maxHealth*3/4));
        else
            this.addToBot(new HealAction(p, p, p.maxHealth/4));
        this.addToBot(new RemoveFromMasterDeckAction(uuidToRemove));
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

    @Override
    public AbstractCard makeCopy() {
        //logger.info("NeverGiveUp gets copied");
        if (AbstractDungeon.player != null && AbstractDungeon.player.masterDeck != null  && AbstractDungeon.player.masterDeck.group.contains(this))
            return new NeverGiveUp(this.uuidToRemove);
        else
            return new NeverGiveUp();
    }
}
