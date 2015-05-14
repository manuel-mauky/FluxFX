package todoflux.stores;

import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.StoreBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.reactfx.EventSource;
import org.reactfx.EventStream;
import todoflux.actions.AddItemAction;
import todoflux.actions.ChangeStateAction;
import todoflux.actions.ChangeFilterAction;
import todoflux.actions.DeleteItemAction;
import todoflux.data.TodoItem;

import javax.inject.Singleton;

@Singleton
public class ItemsStore extends StoreBase {

    private ObservableList<TodoItem> items = FXCollections.observableArrayList();
    private FilteredList<TodoItem> filteredData = new FilteredList<TodoItem>(items, s -> true);
    private ChangeFilterAction.VisibilityType filterStatus = null;

    private EventSource<String> inputText = new EventSource<>();
    private EventSource<String> itemIdsToUpdate = new EventSource<>();

    @Override
    public void processAction(Action action) {

        if (action instanceof AddItemAction) {
            processAddItemAction((AddItemAction) action);
            return;
        }

        if (action instanceof DeleteItemAction) {
            processDeleteItemAction((DeleteItemAction) action);
            return;
        }

        if (action instanceof ChangeStateAction) {
            processChangeStateAction((ChangeStateAction) action);
            return;
        }

        if (action instanceof ChangeFilterAction) {
            processChangeFilterAction((ChangeFilterAction) action);
        }
    }

    private void processDeleteItemAction(DeleteItemAction action) {
        items
                .stream()
                .filter(item -> item.getId().equals(action.getId()))
                .findAny()
                .ifPresent(items::remove);
    }

    private void processAddItemAction(AddItemAction action) {
        TodoItem newItem = new TodoItem(action.getText());

        items.add(newItem);

        inputText.push("");

    }

    private void processChangeStateAction(ChangeStateAction action) {
        items
                .stream()
                .filter(item -> item.getId().equals(action.getId()))
                .findAny()
                .ifPresent(item -> {
                    item.setCompleted(action.getNewState());
                    itemIdsToUpdate.push(item.getId());
                });
    }

    private void processChangeFilterAction(ChangeFilterAction action) {
        filterStatus = action.getVisibilityType();
        filteredData.setPredicate(todoItem -> {
            switch (filterStatus) {
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
    }

    public FilteredList<TodoItem> getItems() {
        return filteredData;
    }

    public EventStream<String> inputText() {
        return inputText;
    }

    public EventStream<String> itemIdsToUpdate() {
        return itemIdsToUpdate;
    }
}
