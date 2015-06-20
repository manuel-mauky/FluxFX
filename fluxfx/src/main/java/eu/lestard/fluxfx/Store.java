package eu.lestard.fluxfx;

import java.util.function.Consumer;

/**
 * A base class for all Stores.
 */
public abstract class Store {

    /**
     * This method can be used to subscribe to actions of a given type. The subscription is managed by the {@link Dispatcher}.
     *
     * @param actionType the class type of the action.
     * @param actionConsumer a consumer function that is invoked when an action is dispatched.
     * @param <T> the generic type of the action.
     */
    protected <T extends Action> void subscribe(Class<T> actionType, Consumer<T> actionConsumer) {
        Dispatcher.getInstance().getActionStream(actionType).subscribe(actionConsumer);
    }

}
