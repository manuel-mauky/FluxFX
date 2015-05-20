package todoflux.stores;

import eu.lestard.fluxfx.Store;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.reactfx.EventSource;
import org.reactfx.EventStream;
import todoflux.actions.*;
import todoflux.data.TodoItem;

import javax.inject.Singleton;

@Singleton
public class ItemsStore implements Store {

    private ObservableList<TodoItem> items = FXCollections.observableArrayList();
    private FilteredList<TodoItem> filteredData = new FilteredList<TodoItem>(items, s -> true);
    private ChangeFilterAction.VisibilityType filterStatus = ChangeFilterAction.VisibilityType.ALL;

    private EventSource<String> inputText = new EventSource<>();
    private EventSource<String> itemIdsToUpdate = new EventSource<>();
    private EventSource<Boolean> selectAllCheckbox = new EventSource<>();

    private ReadOnlyIntegerWrapper numberOfItemsLeft = new ReadOnlyIntegerWrapper();

    public ItemsStore() {
        subscribe(AddItemAction.class, this::processAddItemAction);
        subscribe(DeleteItemAction.class, this::processDeleteItemAction);
        subscribe(ChangeStateForSingleItemAction.class, this::processChangeStateSingleItemAction);
        subscribe(ChangeFilterAction.class, this::processChangeFilterAction);
        subscribe(ChangeStateForAllItemsAction.class, this::processChangeStateAllItemsAction);
        subscribe(SwitchEditModeAction.class, this::processSwitchEditModeAction);
        subscribe(EditAction.class, this::processEditAction);
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
        updateNumberOfItemsLeft();
    }

    private void processDeleteItemAction(DeleteItemAction action) {
        items
                .stream()
                .filter(item -> item.getId().equals(action.getId()))
                .findAny()
                .ifPresent(items::remove);

        updateNumberOfItemsLeft();
    }

    private void processAddItemAction(AddItemAction action) {
        TodoItem newItem = new TodoItem(action.getText());

        items.add(newItem);

        inputText.push("");

        selectAllCheckbox.push(false);

        updateNumberOfItemsLeft();
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

        updateNumberOfItemsLeft();
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

    private void updateNumberOfItemsLeft() {
        numberOfItemsLeft.setValue(items
                .stream()
                .filter(item -> !item.isCompleted())
                .count());
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

    public EventStream<Boolean> selectAllCheckbox() {
        return selectAllCheckbox;
    }

    public ReadOnlyIntegerProperty numberOfItemsLeft() {
        return numberOfItemsLeft.getReadOnlyProperty();
    }
}
