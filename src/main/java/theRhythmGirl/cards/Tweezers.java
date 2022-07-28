package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.actions.UpdateAllTweezersImagesAction;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class Tweezers extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Tweezers.class.getSimpleName());
    public static final String[] IMG = new String[] {
            makeCardPath(Tweezers.class.getSimpleName()+"_1.png"),
            makeCardPath(Tweezers.class.getSimpleName()+"_2.png"),
            makeCardPath(Tweezers.class.getSimpleName()+"_3.png"),
            makeCardPath(Tweezers.class.getSimpleName()+"_4.png"),
            makeCardPath(Tweezers.class.getSimpleName()+"_5.png"),
            makeCardPath(Tweezers.class.getSimpleName()+"_6.png"),
            makeCardPath(Tweezers.class.getSimpleName()+"_7.png")
    };

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;

    private int imageIndex;

    // /STAT DECLARATION/

    public Tweezers() {
        this(0);
    }

    public Tweezers(int imageIndex) {
        super(ID, IMG[0], COST, TYPE, COLOR, RARITY, TARGET);
        setTweezersImageIndex(imageIndex);
        this.exhaust = true;
    }

    public void setTweezersImageIndex(int index){
        this.imageIndex = Math.max(0, Math.min(index, 6));
        this.loadCardImage(IMG[this.imageIndex]);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("TWEEZERS"));
        this.addToBot(new DrawCardAction(p, 1));
        this.addToBot(new UpdateAllTweezersImagesAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            AlwaysRetainField.alwaysRetain.set(this, true);
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Tweezers(imageIndex);
    }
}
