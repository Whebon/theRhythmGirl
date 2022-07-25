package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.AbstractCountdownPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class PreParty extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(PreParty.class.getSimpleName());
    public static final String IMG = makeCardPath(PreParty.class.getSimpleName() + ".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;

    // /STAT DECLARATION/


    public PreParty() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public int getEffectiveness(){
        return 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean triggeredSomething = false;
        for (AbstractCreature monster : AbstractDungeon.getMonsters().monsters){
            for (AbstractPower power : monster.powers){
                if (power instanceof AbstractCountdownPower){
                    ((AbstractCountdownPower) power).onCountdownTrigger();
                    triggeredSomething = true;
                }
            }
        }
        for (AbstractPower power : AbstractDungeon.player.powers){
            if (power instanceof AbstractCountdownPower){
                ((AbstractCountdownPower) power).onCountdownTrigger();
                triggeredSomething = true;
            }
        }
        if (upgraded || !triggeredSomething)
            this.addToBot(new DrawCardAction(p, 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
