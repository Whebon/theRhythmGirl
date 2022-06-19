package theRhythmGirl.cards;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.DefaultMod;
import theRhythmGirl.actions.GainBeatAction;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheDefault;

import static theRhythmGirl.DefaultMod.makeCardPath;

public class IntoYou extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IntoYou.class.getSimpleName());
    public static final String IMG = makeCardPath("IntoYou.png");
    public static final String IMG_2 = makeCardPath("IntoYou_2.png");

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


    public IntoYou() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;

        CardModifierManager.addModifier(this, new RepeatModifier());
    }

    @Override
    public void triggerWhenCopied(){
        this.loadCardImage(IMG_2);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CardModifierManager.hasModifier(this, RepeatModifier.ID))
            AbstractDungeon.actionManager.addToBottom(new SFXAction("INTO_YOU_1"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("INTO_YOU_2"));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
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
