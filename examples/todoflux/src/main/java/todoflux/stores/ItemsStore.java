package todoflux.stores;

import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.StoreBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.reactfx.EventSource;
import org.reactfx.EventStream;
import todoflux.actions.*;
import todoflux.data.TodoItem;

import javax.inject.Singleton;

@Singleton
public class ItemsStore extends StoreBase {

    private ObservableList<TodoItem> items = FXCollections.observableArrayList();
    private FilteredList<TodoItem> filteredData = new FilteredList<TodoItem>(items, s -> true);
    private ChangeFilterAction.VisibilityType filterStatus = ChangeFilterAction.VisibilityType.ALL;

    private EventSource<String> inputText = new EventSource<>();
    private EventSource<String> itemIdsToUpdate = new EventSource<>();
    private EventSource<Boolean> selectAllCheckbox = new EventSource<>();


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

        if (action instanceof ChangeStateForSingleItemAction) {
            processChangeStateSingleItemAction((ChangeStateForSingleItemAction) action);
            return;
        }

        if (action instanceof ChangeFilterAction) {
            processChangeFilterAction((ChangeFilterAction) action);
            return;
        }

        if (action instanceof ChangeStateForAllItemsAction) {
            processChangeStateAllItemsAction((ChangeStateForAllItemsAction) action);
            return;
        }

        if(action instanceof SwitchEditModeAction){
            processSwitchEditModeAction((SwitchEditModeAction) action);
            return;
        }

        if(action instanceof EditAction){
            processEditAction((EditAction) action);
        }
    }

    private void processEditAction(EditAction action){
        items.stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> {
                    item.setText(action.getNewText());
                    item.setEditMode(false);
                    itemIdsToUpdate.push(item.getId());
                });
    }


    private void processSwitchEditModeAction(SwitchEditModeAction action) {
        items.stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> {
                    item.setEditMode(action.isEditMode());
                    itemIdsToUpdate.push(item.getId());
                });
    }

    private void processChangeStateAllItemsAction(ChangeStateForAllItemsAction action) {
        selectAllCheckbox.push(action.isNewState());
        items
                .stream()
                .filter(item -> item.isCompleted() != action.isNewState())
                .forEach(item -> {
                    item.setCompleted(action.isNewState());
                    itemIdsToUpdate.push(item.getId());
                });
        updateFilterPredicate();
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

        selectAllCheckbox.push(false);
    }

    private void processChangeStateSingleItemAction(ChangeStateForSingleItemAction action) {
        items
                .stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> {
                    item.setCompleted(action.getNewState());
                    itemIdsToUpdate.push(item.getId());
                });

        selectAllCheckbox.push(false);
    }

    private void processChangeFilterAction(ChangeFilterAction action) {
        filterStatus = action.getVisibilityType();
        updateFilterPredicate();
    }

    private void updateFilterPredicate() {
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

    public EventSource<Boolean> selectAllCheckbox() {
        return selectAllCheckbox;
    }
}
