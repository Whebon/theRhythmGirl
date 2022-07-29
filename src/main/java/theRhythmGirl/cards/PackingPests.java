package theRhythmGirl.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.PackagePower;

import java.util.List;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//todo: test this card
//todo: buff this card

public class PackingPests extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(PackingPests.class.getSimpleName());
    public static final String IMG = makeCardPath(PackingPests.class.getSimpleName() + ".png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;
    private static final int COPIES = 1;
    private static final int COUNTDOWN = 4;

    // /STAT DECLARATION/


    public PackingPests() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = COPIES;
        baseMagicNumber2 = magicNumber2 = COUNTDOWN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0],
                (AbstractCard c) -> !c.hasTag(AbstractCard.CardTags.HEALING),
                (List<AbstractCard> cardList) -> {
                    if (cardList.size() > 0) {
                        AbstractCard c = cardList.get(0);
                        this.addToTop(new SFXAction("PACKING_PESTS_APPLY"));
                        this.addToTop(new ApplyPowerAction(p, p, new PackagePower(p, p, magicNumber2, c, magicNumber), magicNumber2));
                        this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    }
                }
        ));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            AlwaysRetainField.alwaysRetain.set(this, true);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
