package theRhythmGirl.senddata;

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

    public ArrayList<String> relicDetails;

    public ArrayList<CardDetails> cardDetails;

    public ArrayList<CardDetails> chosenCards = new ArrayList<>();

    public ArrayList<CardDetails> notChosenCards = new ArrayList<>();
}
