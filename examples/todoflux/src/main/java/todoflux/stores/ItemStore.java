package todoflux.stores;

import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.StoreBase;
import todoflux.actions.AddItemAction;
import todoflux.actions.DeleteItemAction;
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
		
		if (action instanceof AddItemAction) {
			AddItemAction addItemAction = (AddItemAction) action;
			processAddItemAction(addItemAction);
		}
		
		if (action instanceof DeleteItemAction) {
			DeleteItemAction deleteItemAction = (DeleteItemAction) action;
			processDeleteItemAction(deleteItemAction);
		}
		
	}
	
	private void processDeleteItemAction(DeleteItemAction action) {
        items
            .stream()
            .filter(item -> item.getId().equals(action.getId()))
            .findAny()
            .ifPresent(items::remove);

        publishOnChange();
	}
	
	private void processAddItemAction(AddItemAction action) {
		TodoItem newItem = new TodoItem(action.getText());
		
		items.add(newItem);
		
		publishOnChange();
	}
	
	public List<TodoItem> getItems() {
		return Collections.unmodifiableList(items);
	}
}
