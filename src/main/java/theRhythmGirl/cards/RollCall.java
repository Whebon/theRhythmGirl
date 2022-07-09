package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class RollCall extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(RollCall.class.getSimpleName());
    public static final String IMG = makeCardPath(RollCall.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public RollCall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        onBeatColor.put(3, BeatUI.BeatColor.ON_BEAT);
        mustBePlayedOnBeat = true;
        this.cardsToPreview = new BoardMeeting();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ROLL_CALL"));
        BoardMeeting boardMeeting = new BoardMeeting();
        if (upgraded)
            boardMeeting.upgrade();
        this.addToBot(new MakeTempCardInHandAction(boardMeeting, 1));
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
