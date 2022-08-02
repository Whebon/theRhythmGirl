package theRhythmGirl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.actions.CustomSFXAction;
import theRhythmGirl.cards.PackingPests;
import theRhythmGirl.senddata.CustomMetrics;
import theRhythmGirl.util.TextureLoader;
import static theRhythmGirl.RhythmGirlMod.makePowerPath;

public class PackagePower extends AbstractCountdownPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = RhythmGirlMod.makeID(PackagePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int uniqueID;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(PackagePower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(PackagePower.class.getSimpleName() + "32.png"));

    private static final int AMOUNT_OF_CHARACTERS = 3;

    private final AbstractCard content;

    public PackagePower(final AbstractCreature owner, final AbstractCreature source, int countdown, AbstractCard content, int numberOfCopies) {
        name = NAME;
        ID = POWER_ID + uniqueID;
        uniqueID++;

        this.owner = owner;
        this.source = source;
        this.amount = countdown;
        this.originalCountdown = this.countdown = countdown;
        this.amount2 = numberOfCopies;
        this.greenColor2 = Color.GOLD.cpy();

        this.content = content.makeStatEquivalentCopy();

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        int realAmount2 = this.amount2;
        this.amount2 = 0;
        super.renderAmount(sb, x, y, c);
        this.amount2 = realAmount2;
        if (this.content.name.length() > 0) {
            this.greenColor2.a = c.a;
            c = this.greenColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, this.content.name.substring(0, AMOUNT_OF_CHARACTERS), x, y + 15.0F * Settings.scale, 1.0F, c);
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        if (this.amount2 == 1) {
            this.description += DESCRIPTIONS[3];
        } else {
            this.description += DESCRIPTIONS[4] + this.amount2 + DESCRIPTIONS[5];
        }
        this.description += this.content.name.replaceAll(" ", " #y") + DESCRIPTIONS[6];
    }

    @Override
    public AbstractPower makeCopy() { return new PackagePower(owner, source, amount, content.makeStatEquivalentCopy(), amount2); }

    @Override
    public void onCountdownTrigger() {
        CustomMetrics.increasePowerEffectiveness(this, this.amount2);
        CustomMetrics.addCardSpecificDetails(PackingPests.ID, content.cardID);
        this.addToBot(new CustomSFXAction("PACKING_PESTS_TRIGGER"));
        this.addToBot(new MakeTempCardInHandAction(content, amount2, false));
    }
}