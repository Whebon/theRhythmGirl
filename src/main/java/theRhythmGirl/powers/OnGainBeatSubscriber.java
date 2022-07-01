package theRhythmGirl.powers;

//only powers should implement this interface (e.g. LaunchPartyPower)
//listens to beatUI::publishOnGainBeat
public interface OnGainBeatSubscriber {
    void onGainBeat(int numberOfBeatsGained);
}