package theRhythmGirl.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;
import theRhythmGirl.actions.CustomSFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.characters.TheRhythmGirl;
import theRhythmGirl.powers.PartyRocketPower;

import static theRhythmGirl.RhythmGirlMod.makeCardPath;

//the card says: 'damage', but it is actually magic damage
//I had to change damage to magic because the card was broken with attack relics like Akabeko

//I turned "This cards effects are applied on a target and trigger after you've gained X beats." into a keyword "Countdown X"
//however, I don't feel like the rhythm girl needs Countdown synergy and converting it into a keyword may lead to disappointing builds

//idea: make more countdown based cards.

//old version: 12 (16) damage

public class PartyRocket extends AbstractRhythmGirlCard {

    // TEXT DECLARATION

    public static final String ID = RhythmGirlMod.makeID(PartyRocket.class.getSimpleName());
    public static final String IMG = makeCardPath(PartyRocket.class.getSimpleName()+".png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRhythmGirl.Enums.COLOR_RHYTHM_GIRL;

    private static final int COST = 1;
    private static final int COUNTDOWN = 3;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public PartyRocket() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DAMAGE;
        baseMagicNumber2 = magicNumber2 = COUNTDOWN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CustomSFXAction("LAUNCH_PARTY_APPLY"));
        this.addToBot(new ApplyPowerAction(m, p, new PartyRocketPower(m, p, magicNumber2, magicNumber), magicNumber2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}