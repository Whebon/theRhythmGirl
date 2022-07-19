//deprecated, too self oriented

/*
  "theRhythmGirl:DarkPortal": {
    "NAME": "Dark Portal",
    "DESCRIPTION": "therhythmgirl:On_Beat 1 or 2: NL Add a [#e3be4e]Samurai [#e3be4e]Slice NL to your hand.",
    "UPGRADE_DESCRIPTION": "therhythmgirl:On_Beat 1 or 2: NL Add a [#e3be4e]Samurai [#e3be4e]Slice+ NL to your hand."
  }

package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class DarkPortal extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(DarkPortal.class.getSimpleName());
    public static final String IMG = makeCardPath(DarkPortal.class.getSimpleName()+"Exhaust.png");
    public static final String IMG_REPEAT = makeCardPath(DarkPortal.class.getSimpleName()+"Repeat.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public DarkPortal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        onBeatColor.put(1, BeatUI.BeatColor.ON_BEAT);
        onBeatColor.put(2, BeatUI.BeatColor.ON_BEAT);
        mustBePlayedOnBeat = true;
        this.cardsToPreview = new SamuraiSlice();

        CardModifierManager.addModifier(this, new RepeatModifier());
    }

    @Override
    public void loadAlternativeCardImage(){
        this.loadCardImage(IMG_REPEAT);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (RhythmGirlMod.beatUI.currentBeat == 1)
            this.addToBot(new CustomSFXAction("DARK_PORTAL_1"));
        else
            this.addToBot(new CustomSFXAction("DARK_PORTAL_2"));
        SamuraiSlice samuraiSlice = new SamuraiSlice();
        if (upgraded)
            samuraiSlice.upgrade();
        this.addToBot(new MakeTempCardInHandAction(samuraiSlice, 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            cardsToPreview.upgrade();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
*/