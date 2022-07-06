package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class PeaFork extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(PeaFork.class.getSimpleName());
    public static final String IMG_1 = makeCardPath(PeaFork.class.getSimpleName()+"_1.png");
    public static final String IMG_2 = makeCardPath(PeaFork.class.getSimpleName()+"_2.png");
    private final int peas;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int HEAL = 6;
    private static final int HEAL_UPGRADE = 2;

    // /STAT DECLARATION/

    public PeaFork(int peas){
        super(ID, IMG_1, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = HEAL;

        this.peas = peas;
        if (peas >= 2){
            loadCardImage(IMG_2);
        }

        this.tags.add(CardTags.HEALING);

        onBeatColor.put(3, BeatUI.BeatColor.CUED);
        onBeatColor.put(4, BeatUI.BeatColor.CUED);
        mustBePlayedOnBeat = true;
        CardModifierManager.addModifier(this, new ExhaustAndEtherealModifier());
    }

    public PeaFork() {
        this(1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("PEA_FORK"));
        this.addToBot(new HealAction(p, p, magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(HEAL_UPGRADE);
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new PeaFork(peas);
    }
}