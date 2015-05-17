package eu.lestard.fluxfx;

import org.reactfx.EventSource;
import org.reactfx.EventStream;

public class Dispatcher {

    private static final Dispatcher SINGLETON = new Dispatcher();

    private final EventSource<Action> actionStream = new EventSource<>();

    private Dispatcher() {
    }

    static Dispatcher getInstance() {
        return SINGLETON;
    }

    public void dispatch(Action action) {
        actionStream.push(action);
    }

    EventStream<Action> getActionStream(){
        return actionStream;
    }

    @SuppressWarnings("unchecked")
    <T extends Action> EventStream<T> getActionStream(Class<T> actionType) {
        return Dispatcher.getInstance()
                .getActionStream()
                .filter(action -> action.getClass().equals(actionType))
                .map(action -> (T) action);
    }

}
