package theRhythmGirl.senddata;

import java.util.ArrayList;

public class CustomCardDetails {
    public String cardID;
    public Integer timesPlayed = 0;
    public Integer withRepeat = 0;
    public Integer onBeat1 = 0;
    public Integer onBeat2 = 0;
    public Integer onBeat3 = 0;
    public Integer onBeat4 = 0;
    public Integer effectiveness = 0;
    public ArrayList<String> cardSpecificDetails = new ArrayList<>();

    public CustomCardDetails(String cardID){
        this.cardID = cardID;
    }
}