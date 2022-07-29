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
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.characters.TheRhythmGirl;

import static theRhythmGirl.RhythmGirlMod.enableCustomSoundEffects;
import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//old version: 3 damage repeat. upgrade +2
//old version: 5 damage. upgrade repeat. (but this was confusing with the switching artwork on repeat, so I reverted it to the old version)

//idea: nerf this?
//idea: give this exhaust(ive)
//idea: this doesn't give a beat

public class Jab extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(Jab.class.getSimpleName());
    public static final String IMG = makeCardPath("Jab_Exhaust.png");
    public static final String IMG_REPEAT = makeCardPath("Jab_Repeat.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 0;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 2;

    // /STAT DECLARATION/

    public Jab() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        CardModifierManager.addModifier(this, new RepeatModifier());
    }

    @Override
    public void loadAlternativeCardImage(){
        this.loadCardImage(IMG_REPEAT);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CardModifierManager.hasModifier(this, RepeatModifier.ID))
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("JAB_REPEAT"));
        else
            AbstractDungeon.actionManager.addToBottom(new CustomSFXAction("JAB_EXHAUST"));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false, enableCustomSoundEffects));
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