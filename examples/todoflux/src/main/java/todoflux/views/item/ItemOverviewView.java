package todoflux.views.item;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import todoflux.data.TodoItem;
import todoflux.stores.ItemStore;

public class ItemOverviewView implements View {

    @FXML
    public ListView<TodoItem> items;

    private final ItemStore itemStore;

    public ItemOverviewView(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public void initialize() {
        items.setCellFactory(new ItemViewFactory());

        itemStore.onChange(() -> {
            items.getItems().clear();

            items.getItems().addAll(itemStore.getItems());
        });
    }

}
