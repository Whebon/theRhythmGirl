package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class CountIn extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(CountIn.class.getSimpleName());
    public static final String IMG = makeCardPath(CountIn.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;

    // /STAT DECLARATION/


    public CountIn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof AbstractRhythmGirlCard && ((AbstractRhythmGirlCard)c).hasOnBeatEffect())
                return true;
        }
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Predicate<AbstractCard> predicate = (AbstractCard c)->{
            if (c instanceof AbstractRhythmGirlCard)
                return ((AbstractRhythmGirlCard)c).hasOnBeatEffect();
            return false;
        };
        //todo: rework [card 'fetchedFromCountIn'] -> [beatUI 'fetchedFromCountIn'] with uuid of the fetched card. clear fetchedFromCountIn on beat gain
        Consumer<List<AbstractCard>> callback = (List<AbstractCard> lst)->{
            if (lst.size()>=1){
                AbstractCard fetchedCard = lst.get(0);
                if (fetchedCard instanceof AbstractRhythmGirlCard){
                    int targetBeat = ((AbstractRhythmGirlCard)fetchedCard).getOnBeatIntegerX();
                    ((AbstractRhythmGirlCard) fetchedCard).setFetchedFromCountIn(true);
                    RhythmGirlMod.beatUI.gainBeatsUntil(targetBeat);
                }
            }
        };
        this.addToBot(new FetchAction(p.drawPile, predicate, callback));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
