package todoflux.stores;

import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.StoreBase;
import todoflux.actions.AddItemAction;
import todoflux.data.TodoItem;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class ItemStore extends StoreBase {

    private List<TodoItem> items = new ArrayList<>();

    @Override
    public void processAction(Action action) {

        if(action instanceof AddItemAction){
            AddItemAction addItemAction = (AddItemAction) action;
            processAddItemAction(addItemAction);
        }
    }

    private void processAddItemAction(AddItemAction action) {
        TodoItem newItem = new TodoItem(action.getText());

        items.add(newItem);

        publishOnChange();
    }

    public List<TodoItem> getItems(){
        return Collections.unmodifiableList(items);
    }
}
