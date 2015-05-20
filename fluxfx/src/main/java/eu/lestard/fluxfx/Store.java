package eu.lestard.fluxfx;

import java.util.function.Consumer;

public interface Store {

    default <T extends Action> void subscribe(Class<T> actionType, Consumer<T> actionConsumer) {
        Dispatcher.getInstance().getActionStream(actionType).subscribe(actionConsumer);
    }

}
