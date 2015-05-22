package eu.lestard.fluxfx;

import java.util.function.Consumer;

public abstract class Store {

    protected <T extends Action> void subscribe(Class<T> actionType, Consumer<T> actionConsumer) {
        Dispatcher.getInstance().getActionStream(actionType).subscribe(actionConsumer);
    }

}
