import eu.lestard.fluxfx.Store;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;

public class MainStore implements Store {

    private ReadOnlyStringWrapper value = new ReadOnlyStringWrapper("value");


    public MainStore() {
        subscribe(UpdateAction.class, this::processUpdateAction);
    }

    void processUpdateAction(UpdateAction action) {
        value.setValue(action.getValue());
    }

    public ObservableStringValue value() {
        return value.getReadOnlyProperty();
    }

}
