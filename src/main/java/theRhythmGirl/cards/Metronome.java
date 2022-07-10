package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//Lose 1 hp. Add a copy of this card to your hand. Exhaust.
//idea: rework metronome to retain itself in hand after play instead of creating a copy (to prevent overflowing the exhaust pile)
//idea: rework metronome to be limited to 8 uses per turn (to prevent tedious infinite combos)
//todo: maximum number of uses (tungsten rod)
//todo: no self damage

public class Metronome extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Metronome.class.getSimpleName());
    public static final String IMG = makeCardPath("Metronome.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;

    // /STAT DECLARATION/


    public Metronome() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("METRONOME"));
        this.addToBot(new LoseHPAction(p, p, 1));
        AbstractCard s = (new Metronome()).makeCopy();
        if (upgraded)
            s.upgrade();
        this.addToBot(new MakeTempCardInHandAction(s, 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.retain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
