package todoflux.views.item;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import todoflux.data.TodoItem;
import todoflux.stores.ItemStore;

import java.util.stream.Collectors;

public class ItemOverviewView {
    @FXML
    public ListView<String> items;


    private final ItemStore itemStore;

    public ItemOverviewView(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public void initialize() {
        itemStore.onChange(() -> {
            items.getItems().clear();
            items.getItems().addAll(
                    itemStore.getItems()
                            .stream()
                            .map(TodoItem::getText)
                            .collect(Collectors.toList()));
        });

    }


}
