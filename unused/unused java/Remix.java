//deprecated, too ambiguous

/*
public class Remix {
    Queue<Class<? extends AbstractCard>> remixPlayQueue = new LinkedList<>();
    Queue<Class<? extends AbstractCard>> remixPrepareQueue = new LinkedList<>();

    public Remix(){
        play(null);
        play(null);
        play(null);
        play(null);

        play(null);
        play(null);
        play(null);
        prepare(Strike_RhythmGirl.class);

        play(null);
        play(null);
        play(null);
        play(Strike_RhythmGirl.class);

        play(null);
        play(null);
        play(null);
        prepare(Strike_RhythmGirl.class);

        play(null);
        play(null);
        play(null);
        play(Strike_RhythmGirl.class);

        play(null);
        play(null);
        play(null);
        play(null);
    }

    private void play(Class<? extends AbstractCard> play) {
        playAndPrepare(play, null);
    }

    private void prepare(Class<? extends AbstractCard> prepare) {
        playAndPrepare(null, prepare);
    }

    private void playAndPrepare(Class<? extends AbstractCard> play, Class<? extends AbstractCard> prepare) {
        remixPlayQueue.add(play);
        remixPrepareQueue.add(prepare);
    }

    public void executeNext() {
        if (remixPlayQueue.size() > 0) {
            Class<? extends AbstractCard> playClass = remixPlayQueue.remove();
            if (playClass != null) {
                for (AbstractCard handCard : AbstractDungeon.player.hand.group) {
                    if (playClass.equals(handCard.getClass())) {
                        AbstractDungeon.actionManager.addToBottom(new NewQueueCardAction(handCard, true, false, true));
                        break;
                    }
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new GainAdditionalBeatsAction(AbstractDungeon.player, AbstractDungeon.player));
            }
            Class<? extends AbstractCard> prepareClass = remixPrepareQueue.remove();
            if (prepareClass != null) {
                try {
                    AbstractCard prepareCard = prepareClass.getConstructor().newInstance();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(prepareCard, 1));
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    System.out.printf("Unable to instantiate '%s' from Class<? extends AbstractCard>%n", prepareClass.getName());
                }
            }
        }
    }
}
*/


//keyboard in lwjgl

/*
if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !spaceKeyPressed) {
    spaceKeyPressed = true;
}
else {
    spaceKeyPressed = false;
}
 */