package todoflux.views.item;

import eu.lestard.fluxfx.react.ReactView;
import eu.lestard.fluxfx.utils.ViewCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import todoflux.stores.ItemsStore;
import todoflux.stores.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class ItemOverviewView implements ReactView {

    @FXML
    public ListView<TodoItem> itemListView;

    private final ItemsStore itemStore;

    public ItemOverviewView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }

    private List<TodoItem> items;

    @Override
    public Runnable getInitialState() {
        return () -> {
            items = new ArrayList<>();
        };
    }


    @Override
    public void componentDidMount() {
        ViewCellFactory<TodoItem, ItemView> cellFactory = new ViewCellFactory<>(ItemView.class,
                (todoItem, itemView) -> itemView.componentWillReceiveProps(todoItem));


        itemListView.setCellFactory(cellFactory);

        itemStore.addOnChangeListener(() -> setState(() -> {
            items.clear();
            items.addAll(itemStore.getItems());
        }));
    }

    @Override
    public void render() {
        itemListView.getItems().setAll(items);
    }
}
