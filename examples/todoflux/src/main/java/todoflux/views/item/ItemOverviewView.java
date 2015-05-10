package todoflux.views.item;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import todoflux.data.TodoItem;
import todoflux.stores.ItemStore;

public class ItemOverviewView {
    @FXML
    public ListView<TodoItem> items;


    private final ItemStore itemStore;

    private final ItemViewFactory itemViewFactory;

    public ItemOverviewView(ItemStore itemStore, ItemViewFactory itemViewFactory) {
        this.itemStore = itemStore;
        this.itemViewFactory = itemViewFactory;
    }

    public void initialize() {
        itemStore.onChange(() -> {
            items.getItems().clear();

            items.setCellFactory(itemViewFactory);
            items.getItems().addAll(
                    itemStore.getItems());


        });

    }


}
