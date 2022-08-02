package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.actions.ResetBeatsAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.AbstractCountdownPower;
import theRhythmGirl.powers.MeasurePower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old text: "Instead of gaining a beat, reset your beat to 1" was too convoluted.

public class TryAgain extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(TryAgain.class.getSimpleName());
    public static final String IMG = makeCardPath(TryAgain.class.getSimpleName()+".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;
    private static final int DRAW = 1;
    private static final int UPGRADE_DRAW = 1;

    // /STAT DECLARATION/

    public TryAgain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("TRY_AGAIN"));
        this.addToBot(new ResetBeatsAction());
        this.addToBot(new RemoveSpecificPowerAction(p, p, MeasurePower.POWER_ID));
        this.addToBot(new DrawCardAction(p, magicNumber));
        for (AbstractCreature monster : AbstractDungeon.getMonsters().monsters){
            for (AbstractPower power : monster.powers){
                if (power instanceof AbstractCountdownPower){
                    ((AbstractCountdownPower) power).resetCountdown();
                }
            }
        }
        for (AbstractPower power : AbstractDungeon.player.powers){
            if (power instanceof AbstractCountdownPower){
                ((AbstractCountdownPower) power).resetCountdown();
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
