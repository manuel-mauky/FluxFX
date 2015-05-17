package eu.lestard.fluxfx;

import org.reactfx.EventStream;

public interface Store {

    default <T extends Action> EventStream<T> getActionStream(Class<T> actionType) {
        return Dispatcher.getInstance().getActionStream(actionType);
    }
}
