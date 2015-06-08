package eu.lestard.fluxfx;

/**
 * An interface that should be implemented by controllers of Views.
 *
 * This interface has two purposes:
 * - load a FXML file with a matching name via the {@link ViewLoader}. See there for more information.
 * - publish actions that can be handled by stores (see {@link #publishAction(Action)} method).
 *
 */
public interface View {

    /**
     * Publish an action that can be handled by stores.
     *
     * @param action the action implementation that will be published.
     */
    default void publishAction(Action action) {
        Dispatcher.getInstance().dispatch(action);
    }

}
