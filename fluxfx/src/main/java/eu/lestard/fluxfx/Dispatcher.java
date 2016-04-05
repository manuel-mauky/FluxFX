package eu.lestard.fluxfx;

import javafx.application.Platform;

import org.reactfx.EventSource;
import org.reactfx.EventStream;

/**
 * The central dispatcher that manages incoming {@link Action}s and provides them as EventStream so that
 * {@link Store}s can subscribe them.
 *
 * Views don't need to care about the dispatcher because there is a {@link View#publishAction(Action)} method in the {@link View}
 * interface that should be used to publish actions.
 *
 * Only external systems (f.e. backend services) that like to publish actions have to use this dispatcher class.
 *
 */
public class Dispatcher {

    private static final Dispatcher SINGLETON = new Dispatcher();

    private final EventSource<Action> actionStream = new EventSource<>();

    private Dispatcher() {
    }

    /**
     * @return a singleton instance of the Dispatcher.
     */
    public static Dispatcher getInstance() {
        return SINGLETON;
    }

    /**
     * Dispatch the given action. Dispatching is always done on the JavaFX application
     * thread, even if this method is called from another thread.
     *
     * @param action the action that will be dispatched to the stores.
     */
    public void dispatchOnFxThread(Action action) {
        if(Platform.isFxApplicationThread()) {
            actionStream.push(action);
        } else {
            Platform.runLater(() -> actionStream.push(action));
        }
    }

    /**
     * Dispatch the given action.
     *
     * @param action the action that will be dispatched to the stores.
     */
    public void dispatch(Action action) {
        actionStream.push(action);
    }

    /**
     * @return an event-stream of all published actions.
     */
    public EventStream<Action> getActionStream(){
        return actionStream;
    }

    /**
     * A filtered event-stream of actions of the given type.
     *
     * @param actionType the class type of the action.
     * @param <T> the generic type of the action.
     * @return an event-stream of all actions of the given type.
     */
    @SuppressWarnings("unchecked")
    <T extends Action> EventStream<T> getActionStream(Class<T> actionType) {
        return Dispatcher.getInstance()
                .getActionStream()
                .filter(action -> action.getClass().equals(actionType))
                .map(action -> (T) action);
    }

}
