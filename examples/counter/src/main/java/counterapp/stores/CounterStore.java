package counterapp.stores;

import counterapp.actions.DecreaseCounterAction;
import counterapp.actions.IncreaseCounterAction;
import eu.lestard.fluxfx.Store;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CounterStore extends Store {

    private IntegerProperty counter = new SimpleIntegerProperty();


    public CounterStore() {
        subscribe(IncreaseCounterAction.class, this::increase);
        subscribe(DecreaseCounterAction.class, this::decrease);
    }


    private void decrease(DecreaseCounterAction action) {
        counter.setValue(counter.get() - action.getAmount());
    }

    private void increase(IncreaseCounterAction action) {
        counter.setValue(counter.get() + action.getAmount());
    }

    public ReadOnlyIntegerProperty counter() {
        return counter;
    }

}
