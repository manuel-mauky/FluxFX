package counterapp.views;

import counterapp.actions.DecreaseAction;
import counterapp.actions.IncreaseAction;
import counterapp.stores.CounterStore;
import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.View;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.reactfx.EventSource;

public class CounterView implements View {

    @FXML
    public Label valueLabel;

    private final CounterStore store;

    private final EventSource<Action> actionStream;

    public CounterView(CounterStore store, EventSource<Action> actionStream) {
        this.store = store;
        this.actionStream = actionStream;
    }

    public void initialize() {
        valueLabel.textProperty().bind(Bindings.concat("Value:", store.counter()));
    }

    public void decrease() {
        actionStream.push(new DecreaseAction(1));
    }

    public void increase() {
        actionStream.push(new IncreaseAction(1));
    }
}
