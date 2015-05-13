package todoflux.views.additems;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import todoflux.actions.AddItemAction;
import todoflux.stores.ItemsStore;

public class AddItemsView implements View {

    @FXML
    public TextField addInput;
    private ItemsStore itemStore;

    public AddItemsView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }

    public void initialize() {
        itemStore.inputText().subscribe(addInput::setText);
        addInput.setOnAction(event -> publishAction(new AddItemAction(addInput.getText())));
    }

}
