package todoflux.views.additems;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import todoflux.actions.AddItemAction;
import todoflux.stores.ItemStore;

public class AddItemsView implements View{
    @FXML
    public TextField addInput;

    public AddItemsView(ItemStore itemStore) {
        itemStore.onChange(() -> addInput.setText(""));
    }

    public void initialize() {
        addInput.setOnAction(event -> publishAction(new AddItemAction(addInput.getText())));
    }

}
