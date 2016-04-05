package todoflux.views.additems;

import eu.lestard.fluxfx.View;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import todoflux.actions.AddItemAction;
import todoflux.actions.ChangeCompletedForAllItemsAction;
import todoflux.stores.ItemsStore;

public class AddItemsView implements View {

    @FXML
    public TextField addInput;
    @FXML
    public CheckBox selectAll;

    private ItemsStore itemStore;

    public AddItemsView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }

    public void initialize() {
        itemStore.inputText().subscribe(addInput::setText);
        addInput.setOnAction(event -> publishAction(new AddItemAction(addInput.getText())));

        itemStore.selectAllCheckbox().subscribe(selectAll::setSelected);
        selectAll.setOnAction(event -> publishAction(new ChangeCompletedForAllItemsAction(selectAll.isSelected())));

        selectAll.visibleProperty().bind(Bindings.isNotEmpty(itemStore.getItems()));
    }

}
