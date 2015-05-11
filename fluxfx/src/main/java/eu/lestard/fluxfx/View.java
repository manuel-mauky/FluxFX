package eu.lestard.fluxfx;

public interface View {

    default void publishAction(Action action) {
        Dispatcher.getInstance().dispatch(action);
    }

}
