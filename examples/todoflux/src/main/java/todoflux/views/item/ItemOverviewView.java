package todoflux.views.item;

import eu.lestard.fluxfx.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
        final ItemViewFactory itemViewFactory = new ItemViewFactory();
        items.setCellFactory(itemViewFactory);

        itemStore.itemIdsToUpdate().subscribe(itemViewFactory::update);

        items.setItems(itemStore.getItems());
    }

}
