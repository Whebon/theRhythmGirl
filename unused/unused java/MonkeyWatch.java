//deprecated: boring card

/*
  "theRhythmGirl:MonkeyWatch": {
    "NAME": "Monkey Watch",
    "DESCRIPTION": "Gain !B! Block. NL therhythmgirl:On_Beat 2: Gain !M! Block instead."
  }

package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class MonkeyWatch extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(MonkeyWatch.class.getSimpleName());
    public static final String IMG = makeCardPath(MonkeyWatch.class.getSimpleName()+".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int MAGIC = 6;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int UPGRADE_MAGIC = 4;

    // /STAT DECLARATION/


    public MonkeyWatch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;

        onBeatColor.put(2, BeatUI.BeatColor.ON_BEAT);
    }

    @Override
    public void applyPowers() {
        int realBaseBlock = this.baseBlock;
        this.baseBlock = this.baseMagicNumber;
        super.applyPowers();
        this.baseBlock = realBaseBlock;
        this.magicNumber = this.block;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        super.applyPowers();
        this.isBlockModified = this.block != this.baseBlock;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalBlock = block;
        if (onBeatTriggered()){
            totalBlock = magicNumber;
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("MONKEY_WATCH_SWEET"));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("MONKEY_WATCH_SOUR"));
        }
        this.addToBot(new GainBlockAction(p, p, totalBlock));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
*/