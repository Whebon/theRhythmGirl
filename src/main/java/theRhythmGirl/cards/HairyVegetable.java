package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.FastMakeTempCardInDrawPileAction;
import theRhythmGirl.actions.UpdateAllTweezersImagesAction;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//idea: update the card of HairyVegetable for better distinction between HairyVegetable and Tweezers

public class HairyVegetable extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(HairyVegetable.class.getSimpleName());
    public static final String IMG = makeCardPath(HairyVegetable.class.getSimpleName()+".png");
    public static final String IMG_UPGRADED = makeCardPath(HairyVegetable.class.getSimpleName()+"Upgraded.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int TWEEZERS = 5;
    private static final int UPGRADE_TWEEZERS = 2;

    // /STAT DECLARATION/


    public HairyVegetable() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = TWEEZERS;
        this.cardsToPreview = new Tweezers(magicNumber-1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new SFXAction("HAIRY_VEGETABLE"));
            this.addToBot(new FastMakeTempCardInDrawPileAction(new Tweezers(), 1, true, false));
        }
        this.addToBot(new UpdateAllTweezersImagesAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_TWEEZERS);
            loadCardImage(IMG_UPGRADED); //patched in CardPortraitUpgradeChange
            this.cardsToPreview = new Tweezers(magicNumber-1);
            initializeDescription();
        }
    }
}
