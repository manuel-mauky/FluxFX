package todoflux.views.item;

import eu.lestard.fluxfx.ViewLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import todoflux.data.TodoItem;

import java.util.HashMap;
import java.util.Map;

public class ItemViewFactory implements Callback<ListView<TodoItem>, ListCell<TodoItem>> {

    private Map<TodoItem, ViewLoader.Tuple<ItemView>> cache = new HashMap<>();

    @Override
    public ListCell<TodoItem> call(ListView<TodoItem> param) {
        return new ListCell<TodoItem>() {
            @Override
            protected void updateItem(TodoItem item, boolean empty) {
                super.updateItem(item, empty);

                if(empty) {
                    setText(null);
                    setGraphic(null);
                }else {
                    setText(null);

                    if(item != null) {
                        if(!cache.containsKey(item)){
                            final ViewLoader.Tuple<ItemView> tuple = ViewLoader.loadTuple(ItemView.class);
                            cache.put(item, tuple);
                        }

                        final ViewLoader.Tuple<ItemView> tuple = cache.get(item);

                        tuple.getController().update(item);
                        setGraphic(tuple.getParent());
                    }
                }
            }
        };
    }

}
