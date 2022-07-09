package theRhythmGirl.relics;

//only relics should implement this interface (e.g. Widget)
//listens to beatUI::publishOnGainMeasure
public interface OnGainMeasureSubscriber {
    void onGainMeasure(int numberOfMeasuresGained);
}