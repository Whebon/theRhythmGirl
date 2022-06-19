package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.actions.GainBeatAction;
import theRhythmGirl.characters.TheDefault;

import static theRhythmGirl.DefaultMod.makeCardPath;

public class TallScrew extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TallScrew.class.getSimpleName());
    public static final String IMG = makeCardPath("TallScrew.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    // /STAT DECLARATION/


    public TallScrew() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("TALL_SCREW"));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new GainBeatAction(p, p));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
