package theRhythmGirl.senddata;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRhythmGirl.RhythmGirlMod;
import theRhythmGirl.cardmodifiers.RepeatModifier;
import theRhythmGirl.cards.*;
import theRhythmGirl.powers.*;

import java.util.HashMap;

public class CustomMetrics {

    public static HashMap<String, CustomCardDetails> customCardDetails = new HashMap<>();

    public static final HashMap<String, String> powerToCardTable = new HashMap<String, String>() {{
        put(ComboPower.POWER_ID, Combo.ID);
        put(DoubleUpPower.POWER_ID, DoubleUp.ID);
        put(FillbotsPower.POWER_ID, Fillbots.ID);
        put(GrowingFanbasePower.POWER_ID, GrowingFanbase.ID);
        put(KarateBoostPower.POWER_ID, KaratekaForm.ID);
        put(LaunchPartyPower.POWER_ID, LaunchParty.ID);
        put(MarchingOrdersPower.POWER_ID, MarchingOrders.ID);
        put(PartyCrackerPower.POWER_ID, PartyCracker.ID);
        put(PopularityPower.POWER_ID, FanClub.ID);
        put(RatRacePower.POWER_ID, RatRace.ID);
        put(SpaceBellPower.POWER_ID, SpaceBell.ID);
        put(SeeingHeavenPower.POWER_ID, SeeingHeaven.ID);
        put(RhythmHeavenPower.POWER_ID, RhythmHeaven.ID);
    }};

    public static void receiveCardUsed(AbstractCard abstractCard){
        if (abstractCard instanceof AbstractRhythmGirlCard){
            AbstractRhythmGirlCard card = ((AbstractRhythmGirlCard)abstractCard);
            if (!customCardDetails.containsKey(card.cardID)){
                customCardDetails.put(card.cardID, new CustomCardDetails(card.cardID));
            }
            CustomCardDetails cardDetails = customCardDetails.get(card.cardID);
            cardDetails.timesPlayed += 1;
            switch(RhythmGirlMod.beatUI.currentBeat){
                case 1:
                    cardDetails.onBeat1 += 1;
                    break;
                case 2:
                    cardDetails.onBeat2 += 1;
                    break;
                case 3:
                    cardDetails.onBeat3 += 1;
                    break;
                case 4:
                    cardDetails.onBeat4 += 1;
                    break;
            }
            if (CardModifierManager.hasModifier((card), RepeatModifier.ID))
                cardDetails.withRepeat += 1;
            increaseEffectiveness(card.cardID, card.getEffectiveness());

            if (card.cardID.equals(WorkingDough.ID)){
                if (card.cardsToPreview != null){
                    String details = card.cardsToPreview.cardID;
                    if (card.cardsToPreview instanceof AbstractRhythmGirlCard){
                        details += ":"+((AbstractRhythmGirlCard)card.cardsToPreview).getEffectiveness();
                    }
                    addCardSpecificDetails(WorkingDough.ID, details);
                }
            }
        }
    }

    public static void receiveStartGame(){
        if (AbstractDungeon.floorNum == 0)
            customCardDetails = new HashMap<>();
    }

    public static void increasePowerEffectiveness(AbstractPower power, int amount){
        if (power instanceof AbstractCountdownPower){
            if (((AbstractCountdownPower) power).countdown > 0){
                increaseEffectiveness(PreParty.ID, amount);
                addCardSpecificDetails(PreParty.ID, power.ID+":"+amount);
            }
        }
        else if (powerToCardTable.containsKey(power.ID)){
            increaseEffectiveness(powerToCardTable.get(power.ID), amount);
        }
    }

    public static void increaseEffectiveness(String cardID, int amount){
        if (!customCardDetails.containsKey(cardID)){
            customCardDetails.put(cardID, new CustomCardDetails(cardID));
        }
        customCardDetails.get(cardID).effectiveness += amount;
    }

    public static void addCardSpecificDetails(String cardID, String details){
        if (!customCardDetails.containsKey(cardID)){
            customCardDetails.put(cardID, new CustomCardDetails(cardID));
        }
        customCardDetails.get(cardID).cardSpecificDetails.add(details);
    }
}