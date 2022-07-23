package theRhythmGirl.senddata;

import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

public class RunDetails {
    public String version;

    public String characterName;

    public int ascensionLevel;

    public int maxFloor;

    public float elapsedTime;

    public int score;

    public boolean win;

    public String seed;

    public ArrayList<Integer> goldPerFloor;

    public ArrayList<String> itemsPurchased;

    public ArrayList<String> itemsPurged;

    public ArrayList<String> relicDetails;

    public ArrayList<CardDetails> cardDetails;

    public ArrayList<CardDetails> chosenCards = new ArrayList<>();

    public ArrayList<CardDetails> notChosenCards = new ArrayList<>();
}
