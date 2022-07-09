package theRhythmGirl.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.ExhaustAndEtherealModifier;
import theRhythmGirl.ui.BeatUI;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

public class BoardMeeting extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(BoardMeeting.class.getSimpleName());
    public static final String IMG = makeCardPath(BoardMeeting.class.getSimpleName()+".png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public BoardMeeting() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;

        onBeatColor.put(1, BeatUI.BeatColor.CUED);
        mustBePlayedOnBeat = true;
        CardModifierManager.addModifier(this, new ExhaustAndEtherealModifier());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("BOARD_MEETING"));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
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