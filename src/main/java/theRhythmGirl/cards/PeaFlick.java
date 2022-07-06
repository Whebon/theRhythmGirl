package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class PeaFlick extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(PeaFlick.class.getSimpleName());
    public static final String IMG = makeCardPath(PeaFlick.class.getSimpleName()+".png");
    public static final String IMG_REPEAT = makeCardPath(PeaFlick.class.getSimpleName()+"_Repeat.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public PeaFlick() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.tags.add(CardTags.HEALING);
        this.exhaust = true;

        onBeatColor.put(1, BeatUI.BeatColor.ON_BEAT);
        onBeatColor.put(2, BeatUI.BeatColor.ON_BEAT);
        mustBePlayedOnBeat = true;
        this.cardsToPreview = new PeaFork();
    }

    @Override
    public void loadAlternativeCardImage(){
        this.loadCardImage(IMG_REPEAT);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("PEA_FLICK"));
        PeaFork peaFork = new PeaFork(RhythmGirlMod.beatUI.currentBeat);
        this.addToBot(new MakeTempCardInHandAction(peaFork, 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            CardModifierManager.addModifier(this, new RepeatModifier(false, false));
            initializeDescription();
        }
    }
}
