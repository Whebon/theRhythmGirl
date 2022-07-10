package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class HomeRun extends AbstractRhythmGirlCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     */

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(HomeRun.class.getSimpleName());
    public static final String IMG = makeCardPath("HomeRun.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 35;
    private static final int UPGRADE_PLUS_DMG = 15;

    // /STAT DECLARATION/

    public HomeRun() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        onBeatColor.put(1, BeatUI.BeatColor.CUED);
        mustBePlayedOnBeat = true;
        CardModifierManager.addModifier(this, new ExhaustAndEtherealModifier());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("HOME_RUN"));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY, false, enableCustomSoundEffects));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}