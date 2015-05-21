package todoflux.views.item;

import eu.lestard.fluxfx.View;
import eu.lestard.fluxfx.utils.ViewCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import todoflux.data.TodoItem;
import todoflux.stores.ItemsStore;

public class ItemOverviewView implements View {

    @FXML
    public ListView<TodoItem> items;

    private final ItemsStore itemStore;

    public ItemOverviewView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }

    private ObservableList<TodoItem> itemList = FXCollections.observableArrayList();

    public void initialize() {

        ViewCellFactory<TodoItem, ItemView> cellFactory = new ViewCellFactory<>(ItemView.class, (todoItem, itemView) -> itemView.update(todoItem));


        items.setCellFactory(cellFactory);

        itemStore.itemIdsToUpdate().subscribe(id ->
                cellFactory.updateViews(item ->
                        item.getId().equals(id)));

        items.setItems(itemStore.getItems());
    }

}
