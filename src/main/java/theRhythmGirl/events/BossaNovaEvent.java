package theRhythmGirl.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.exordium.LivingWall;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cards.BossaNova;

import java.awt.*;
import java.util.Iterator;

import static theRhythmGirl.RhythmGirlMod.makeEventPath;

public class BossaNovaEvent extends AbstractImageEvent {


    public static final String ID = RhythmGirlMod.makeID("BossaNovaEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("BossaNovaEvent1.png");
    public static final String IMG_ACCEPT = makeEventPath("BossaNovaEvent2.png");
    public static final String IMG_REJECT = makeEventPath("BossaNovaEvent3.png");

    private final int price;

    private BossaNovaEvent.Screen screen;
    private BossaNovaEvent.Choice choice;

    public BossaNovaEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.screen = BossaNovaEvent.Screen.INTRO;

        //dialog options in the intro screen
        if (AbstractDungeon.ascensionLevel >= 15) {
            //price for 'worse events' ascension level
            price = 75;
        } else {
            price = 50;
        }

        //transform 2
        if (getBasicCardGroup().size() <= 1)
            imageEventText.setDialogOption(OPTIONS[3], true);
        else if (AbstractDungeon.player.gold < price)
            imageEventText.setDialogOption(OPTIONS[4]+ price +OPTIONS[5], true);
        else
            imageEventText.setDialogOption(OPTIONS[0]+ price +OPTIONS[1], new BossaNova());

        //transform 1
        if (getBasicCardGroup().size() <= 0)
            imageEventText.setDialogOption(OPTIONS[3], true);
        else
            imageEventText.setDialogOption(OPTIONS[2], new BossaNova());

        //leave
        imageEventText.setDialogOption(OPTIONS[7]);
    }

    private CardGroup getBasicCardGroup(){
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).group) {
            if (c.rarity == AbstractCard.CardRarity.BASIC) {
                retVal.addToTop(c);
            }
        }
        return retVal;
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.choice = BossaNovaEvent.Choice.INVEST;
                        CardGroup group2 = getBasicCardGroup();
                        if (group2.size() > 1) {
                            AbstractDungeon.player.loseGold(price);
                            AbstractDungeon.gridSelectScreen.open(group2, 2, OPTIONS[6], false, false, false, true);
                        }
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        this.imageEventText.updateDialogOption(0, OPTIONS[8]);
                        break;
                    case 1:
                        this.choice = BossaNovaEvent.Choice.ACCEPT;
                        CardGroup group = getBasicCardGroup();
                        if (group.size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(group, 1, OPTIONS[6], false, false, false, true);
                        }
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        this.imageEventText.updateDialogOption(0, OPTIONS[8]);
                        break;
                    case 2:
                        this.choice = BossaNovaEvent.Choice.REJECT;
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        imageEventText.loadImage(IMG_REJECT);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        this.imageEventText.updateDialogOption(0, OPTIONS[8]);
                        break;
                }
                this.screen = Screen.RESULT;
                break;
            case RESULT:
                openMap();
                break;
        }
    }

    public void update() {
        super.update();
        if (this.choice == Choice.INVEST || this.choice == Choice.ACCEPT ) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards){
                    AbstractDungeon.player.masterDeck.removeCard(card);
                    AbstractCard c = new BossaNova().makeCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                }
                this.imageEventText.loadImage(IMG_ACCEPT);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }

    }

    private enum Choice {
        INVEST,
        ACCEPT,
        REJECT
    }

    private enum Screen {
        INTRO,
        RESULT
    }
}
