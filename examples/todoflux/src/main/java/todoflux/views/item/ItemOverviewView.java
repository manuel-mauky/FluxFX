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

    private FilteredList<TodoItem> filteredData;

    private final ItemsStore itemStore;

    public ItemOverviewView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }

    private ObservableList<TodoItem> itemList = FXCollections.observableArrayList();

    public void initialize() {
        final ItemViewFactory itemViewFactory = new ItemViewFactory();
        items.setCellFactory(itemViewFactory);

        itemStore.itemIdsToUpdate().subscribe(itemViewFactory::update);
        itemStore.filterStatusEventSource().subscribe(visibilityType -> {
            filteredData.setPredicate(todoItem -> {
                switch (visibilityType) {
                    case ALL:
                        return true;
                    case ACTIVE:
                        return !todoItem.isCompleted();
                    case COMPLETED:
                        return todoItem.isCompleted();
                    default:
                        return true;
                }
            });
        });

        filteredData = new FilteredList<TodoItem>(itemStore.getItems(), s -> true);
        items.setItems(filteredData);
    }

}
