package counterapp.stores;

import counterapp.actions.DecreaseAction;
import counterapp.actions.IncreaseAction;
import eu.lestard.fluxfx.Action;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import org.reactfx.EventStream;

public class CounterStore {

    private ReadOnlyIntegerWrapper counter = new ReadOnlyIntegerWrapper();

    public CounterStore(EventStream<Action> eventStream) {
        eventStream.filter(a -> a instanceof IncreaseAction)
                .cast(IncreaseAction.class)
                .subscribe(this::increase);

        eventStream.filter(a -> a instanceof DecreaseAction)
                .cast(DecreaseAction.class)
                .subscribe(this::decrease);
    }


    private void decrease(DecreaseAction action) {
        counter.setValue(counter.get() - action.getAmount());
    }

    private void increase(IncreaseAction action) {
        counter.setValue(counter.get() + action.getAmount());
    }

    public ReadOnlyIntegerProperty counter() {
        return counter.getReadOnlyProperty();
    }

}
