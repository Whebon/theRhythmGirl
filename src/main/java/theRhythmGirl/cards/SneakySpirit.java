package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.SneakyStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//fixed bug: apply powers doesn't work

public class SneakySpirit extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(SneakySpirit.class.getSimpleName());
    public static final String IMG_1 = makeCardPath(SneakySpirit.class.getSimpleName()+"_1.png");
    public static final String IMG_2 = makeCardPath(SneakySpirit.class.getSimpleName()+"_2.png");
    public static final String IMG_3 = makeCardPath(SneakySpirit.class.getSimpleName()+"_3.png");
    public static final String IMG_4 = makeCardPath(SneakySpirit.class.getSimpleName()+"_4.png");
    public static final String IMG_5 = makeCardPath(SneakySpirit.class.getSimpleName()+"_5.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 2;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 5;

    // /STAT DECLARATION/

    public SneakySpirit() {
        super(ID, IMG_4, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage =  DAMAGE;

        onBeatColor.put(4, BeatUI.BeatColor.ON_BEAT);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.onBeatTriggered(4)) {
            this.loadCardImage(IMG_4);
        }
        else if (this.onBeatTriggered(1)){
            this.loadCardImage(IMG_1);
        }
        else if (this.onBeatTriggered(2)) {
            this.loadCardImage(IMG_2);
        }
        else if (this.onBeatTriggered(3)) {
            this.loadCardImage(IMG_3);
        }
        else if (this.onBeatTriggered(5)) {
            this.loadCardImage(IMG_5);
        }
        else{
            this.loadCardImage(IMG_4);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalDamage = damage;
        if (onBeatTriggered()){
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("SNEAKY_SPIRIT_SWEET"));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("SNEAKY_SPIRIT_SOUR"));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY, false, enableCustomSoundEffects));
        if (onBeatTriggered())
            this.addToBot(new GainEnergyAction(2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            initializeDescription();
        }
    }
}