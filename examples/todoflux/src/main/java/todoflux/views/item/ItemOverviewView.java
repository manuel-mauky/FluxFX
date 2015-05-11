package todoflux.views.item;

import eu.lestard.fluxfx.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<TodoItem> itemList = FXCollections.observableArrayList();

    public void initialize() {
        items.setCellFactory(new ItemViewFactory());
        items.setItems(itemList);

        itemStore.onChange(() -> {
            itemList.clear();

            itemList.addAll(itemStore.getItems());
        });
    }

}
