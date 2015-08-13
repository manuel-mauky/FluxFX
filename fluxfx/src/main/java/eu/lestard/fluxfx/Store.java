package eu.lestard.fluxfx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A base class for all Stores.
 */
public abstract class Store {

    private List<Runnable> updateListener = new ArrayList<>();

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


    protected void onChange() {
        updateListener.forEach(Runnable::run);
    }

    public void addOnChangeListener(Runnable listener) {
        this.updateListener.add(listener);
    }

    public void removeOnChangeListener(Runnable listener) {
        this.updateListener.remove(listener);
    }

}
