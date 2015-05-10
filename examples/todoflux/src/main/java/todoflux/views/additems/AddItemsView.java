package todoflux.views.additems;

import eu.lestard.fluxfx.Dispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import todoflux.actions.AddItemAction;
import todoflux.stores.ItemStore;

public class AddItemsView {
    @FXML
    public TextField addInput;

    private final Dispatcher dispatcher;


    public AddItemsView(Dispatcher dispatcher, ItemStore itemStore) {
        this.dispatcher = dispatcher;

        itemStore.onChange(() -> addInput.setText(""));
    }

    public void initialize() {
        addInput.setOnAction(event ->
            dispatcher.dispatch(new AddItemAction(addInput.getText())));
    }

}
