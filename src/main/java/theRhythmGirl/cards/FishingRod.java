package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class FishingRod extends AbstractRhythmGirlCard {
    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(FishingRod.class.getSimpleName());
    public static final String IMG = makeCardPath("FishingRod.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public FishingRod() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        onBeatColor.put(1, BeatUI.BeatColor.ON_BEAT);
        mustBePlayedOnBeat = true;
        this.cardsToPreview = new Pausegill();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("PAUSEGILL_CUE"));
        this.addToBot(new GainAdditionalBeatsAction(p, p));
        Pausegill pausegill = new Pausegill();
        if (upgraded)
            pausegill.upgrade();
        this.addToBot(new MakeTempCardInHandAction(pausegill, 1));
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
