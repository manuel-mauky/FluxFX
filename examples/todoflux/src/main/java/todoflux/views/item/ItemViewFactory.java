package todoflux.views.item;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import todoflux.data.TodoItem;

import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;

public class ItemViewFactory implements Callback<ListView<TodoItem>, ListCell<TodoItem>> {

    private Map<TodoItem, ItemView> cache = new HashMap<>();
    private final Provider<ItemView> itemViewProvider;

    public ItemViewFactory(Provider<ItemView> itemViewProvider) {
        this.itemViewProvider = itemViewProvider;
    }

    @Override
    public ListCell<TodoItem> call(ListView<TodoItem> param) {
        return new ListCell<TodoItem>() {
            @Override
            protected void updateItem(TodoItem item, boolean empty) {
                super.updateItem(item, empty);

                if(item != null) {
                    if(!cache.containsKey(item)){
                        cache.put(item, itemViewProvider.get());
                    }

                    final ItemView itemView = cache.get(item);
                    itemView.update(item);
                    setGraphic(itemView);
                }
            }
        };
    }

}
