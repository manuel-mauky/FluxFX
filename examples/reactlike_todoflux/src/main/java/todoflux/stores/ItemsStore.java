package todoflux.stores;

import eu.lestard.fluxfx.Store;
import todoflux.actions.*;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ItemsStore extends Store {

    private List<TodoItem> items = new ArrayList<>();

    private List<TodoItem> filteredItems = new ArrayList<>();

    private ChangeFilterAction.VisibilityType filterStatus = ChangeFilterAction.VisibilityType.ALL;

    private int numberOfItemsLeft = 0;

    private boolean allSelected = false;

    public ItemsStore() {
        subscribe(AddItemAction.class, this::processAddItemAction);
        subscribe(DeleteItemAction.class, this::processDeleteItemAction);
        subscribe(ChangeCompletedForSingleItemAction.class, this::processChangeCompletedSingleItemAction);
        subscribe(ChangeFilterAction.class, this::processChangeFilterAction);
        subscribe(ChangeCompletedForAllItemsAction.class, this::processChangeCompletedAllItemsAction);
        subscribe(EditAction.class, this::processEditAction);
    }


    void processEditAction(EditAction action){
        items.stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> item.setText(action.getNewText()));

        onChange();
    }


    void processChangeCompletedAllItemsAction(ChangeCompletedForAllItemsAction action) {
        items
                .stream()
                .filter(item -> item.isCompleted() != action.isNewState())
                .forEach(item -> item.setCompleted(action.isNewState()));
        updateFilterPredicate();
        updateNumberOfItemsLeft();

        onChange();
    }

    void processDeleteItemAction(DeleteItemAction action) {
        items
                .stream()
                .filter(item -> item.getId().equals(action.getId()))
                .findAny()
                .ifPresent(items::remove);

        updateNumberOfItemsLeft();
        updateFilterPredicate();

        onChange();
    }

    void processAddItemAction(AddItemAction action) {
        TodoItem newItem = new TodoItem(action.getText());

        items.add(newItem);

        updateFilterPredicate();
        updateNumberOfItemsLeft();

        onChange();
    }

    void processChangeCompletedSingleItemAction(ChangeCompletedForSingleItemAction action) {
        items
                .stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> item.setCompleted(action.getNewState()));

        updateNumberOfItemsLeft();
        updateFilterPredicate();

        onChange();
    }

    void processChangeFilterAction(ChangeFilterAction action) {
        filterStatus = action.getVisibilityType();
        updateFilterPredicate();
        onChange();
    }

    private void updateFilterPredicate() {
        filteredItems = items.stream()
                .filter(todoItem -> {
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
                        })
                .collect(Collectors.toList());
    }

    private void updateNumberOfItemsLeft() {
        numberOfItemsLeft = (int)items
                .stream()
                .filter(item -> !item.isCompleted())
                .count();
    }

    public int getNumberOfItemsLeft() {
        return numberOfItemsLeft;
    }

    public List<TodoItem> getItems() {
        return Collections.unmodifiableList(filteredItems);
    }

    public boolean isAllSelected() {
        return allSelected;
    }
}
