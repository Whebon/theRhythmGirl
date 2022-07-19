package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.GainAdditionalBeatsAction;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: upgraded block by 3

//idea: fix the art of this card, it looks too similar to screwbot, which does something completely different
//maybe make this uncommon?

public class TallScrew extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(TallScrew.class.getSimpleName());
    public static final String IMG = makeCardPath(TallScrew.class.getSimpleName()+".png");
    public static final String IMG_UPGRADED = makeCardPath(TallScrew.class.getSimpleName()+"Upgraded.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int STACKS = 1;
    private static final int UPGRADE_STACKS = 1;

    // /STAT DECLARATION/


    public TallScrew() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = STACKS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("TALL_SCREW_UPGRADED"));
        else
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("TALL_SCREW"));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new GainAdditionalBeatsAction(p, p, magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STACKS);
            loadCardImage(IMG_UPGRADED); //patched in CardPortraitUpgradeChange
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
