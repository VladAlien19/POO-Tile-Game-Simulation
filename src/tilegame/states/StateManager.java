package tilegame.states;

public final class StateManager {

    private static State currentState = null;

    private StateManager() {
    }

    public static void setState(final State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

}
