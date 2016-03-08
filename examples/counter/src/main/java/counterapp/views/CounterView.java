package counterapp.views;

import counterapp.actions.DecreaseCounterAction;
import counterapp.actions.IncreaseCounterAction;
import counterapp.stores.CounterStore;
import eu.lestard.fluxfx.View;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterView implements View {

    @FXML
    public Label valueLabel;

    private final CounterStore store;

    public CounterView(CounterStore store) {
        this.store = store;
    }

    public void initialize() {
        valueLabel.textProperty().bind(Bindings.concat("Value:", store.counter()));
    }

    public void decrease() {
        publishAction(new DecreaseCounterAction(1));
    }

    public void increase() {
        publishAction(new IncreaseCounterAction(1));
    }
}
