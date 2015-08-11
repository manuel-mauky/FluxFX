package todoflux.stores;

import org.junit.Before;
import org.junit.Test;
import todoflux.actions.*;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsStoreTest {

    private ItemsStore store;

    // To test the emitted values of the ReactFX EventStreams we use
    // stacks that will contain the values.
    private Stack<String> inputText = new Stack<>();
    private Stack<Boolean> selectAllCheckbox = new Stack<>();

    @Before
    public void setup() {
        store = new ItemsStore();

        clearEventStreams();

        store.inputText().subscribe(inputText::add);
        store.selectAllCheckbox().subscribe(selectAllCheckbox::add);
    }

    @Test
    public void testAddItem() {
        assertThat(store.getItems()).isEmpty();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(0);

        store.processAddItemAction(new AddItemAction("hello"));

        assertThat(store.getItems()).hasSize(1);
        assertThat(store.getItems().get(0).getText()).isEqualTo("hello");
        assertThat(last(inputText)).isEmpty();
        assertThat(last(selectAllCheckbox)).isFalse();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(1);


        store.processAddItemAction(new AddItemAction("hello world"));

        assertThat(store.getItems()).hasSize(2);
        assertThat(store.getItems().get(1).getText()).isEqualTo("hello world");
        assertThat(last(inputText)).isEmpty();
        assertThat(last(selectAllCheckbox)).isFalse();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(2);


        store.processAddItemAction(new AddItemAction("hello"));
        assertThat(store.getItems()).hasSize(3);
        assertThat(store.getItems().get(2).getText()).isEqualTo("hello");
        assertThat(last(inputText)).isEmpty();
        assertThat(last(selectAllCheckbox)).isFalse();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(3);
    }

    @Test
    public void testDeleteItem() {
        store.processAddItemAction(new AddItemAction("hello"));
        clearEventStreams();
        final String id = store.getItems().get(0).getId();


        assertThat(store.numberOfItemsLeft().get()).isEqualTo(1);

        store.processDeleteItemAction(new DeleteItemAction(id));

        assertThat(store.getItems()).hasSize(0);
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(0);
    }

    @Test
    public void testEditItem() {
        store.processAddItemAction(new AddItemAction("hello"));
        clearEventStreams();
        final String id = store.getItems().get(0).getId();

        store.processEditAction(new EditAction(id, "hello world"));
        assertThat(store.getItems().get(0).getText()).isEqualTo("hello world");
    }

    @Test
    public void testSwitchEditMode() {
        store.processAddItemAction(new AddItemAction("hello"));
        clearEventStreams();
        final String id = store.getItems().get(0).getId();

        assertThat(store.getItems().get(0).isEditMode()).isFalse();


        store.processSwitchEditModeAction(new SwitchEditModeAction(id, true));

        assertThat(store.getItems().get(0).isEditMode()).isTrue();

    }

    @Test
    public void testChangeCompletedSingleItem() {
        store.processAddItemAction(new AddItemAction("hello"));
        store.processAddItemAction(new AddItemAction("hello world"));
        clearEventStreams();
        assertThat(store.getItems().get(0).isCompleted()).isFalse();
        assertThat(store.getItems().get(1).isCompleted()).isFalse();


        assertThat(store.numberOfItemsLeft().get()).isEqualTo(2);

        String id1 = store.getItems().get(0).getId();
        String id2 = store.getItems().get(1).getId();


        store.processChangeCompletedSingleItemAction(new ChangeCompletedForSingleItemAction(id1, true));

        assertThat(store.getItems().get(0).isCompleted()).isTrue();
        assertThat(store.getItems().get(1).isCompleted()).isFalse();

        assertThat(last(selectAllCheckbox)).isFalse();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(1);


        store.processChangeFilterAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ACTIVE));
        clearEventStreams();

        assertThat(store.getItems()).hasSize(1);
        assertThat(store.getItems().get(0).getId()).isEqualTo(id2);



        store.processChangeCompletedSingleItemAction(new ChangeCompletedForSingleItemAction(id1, false));

        assertThat(store.getItems()).hasSize(2);

        assertThat(store.getItems().get(0).isCompleted()).isFalse();
        assertThat(store.getItems().get(1).isCompleted()).isFalse();

        assertThat(last(selectAllCheckbox)).isFalse();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(2);
    }


    @Test
    public void testChangeCompletedAllItems() {
        store.processAddItemAction(new AddItemAction("hello"));
        store.processAddItemAction(new AddItemAction("hello world"));
        clearEventStreams();
        assertThat(store.getItems().get(0).isCompleted()).isFalse();
        assertThat(store.getItems().get(1).isCompleted()).isFalse();


        assertThat(store.numberOfItemsLeft().get()).isEqualTo(2);

        String id1 = store.getItems().get(0).getId();
        String id2 = store.getItems().get(1).getId();


        store.processChangeCompletedAllItemsAction(new ChangeCompletedForAllItemsAction(true));

        assertThat(store.getItems().get(0).isCompleted()).isTrue();
        assertThat(store.getItems().get(1).isCompleted()).isTrue();

        assertThat(last(selectAllCheckbox)).isTrue();
        assertThat(store.numberOfItemsLeft().get()).isEqualTo(0);


        store.processChangeFilterAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ACTIVE));
        clearEventStreams();

        assertThat(store.getItems()).isEmpty();

        store.processChangeCompletedAllItemsAction(new ChangeCompletedForAllItemsAction(false));

        assertThat(store.getItems()).hasSize(2);
        assertThat(store.getItems().get(0).isCompleted()).isFalse();
        assertThat(store.getItems().get(1).isCompleted()).isFalse();
    }

    @Test
    public void testChangeFilter() {
        store.processAddItemAction(new AddItemAction("hello"));
        store.processAddItemAction(new AddItemAction("hello world"));

        String id1 = store.getItems().get(0).getId();
        String id2 = store.getItems().get(1).getId();

        store.processChangeCompletedSingleItemAction(new ChangeCompletedForSingleItemAction(id1, true));
        clearEventStreams();

        assertThat(store.getItems()).hasSize(2);




        store.processChangeFilterAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ACTIVE));
        assertThat(store.getItems()).hasSize(1);
        assertThat(store.getItems().get(0).getId()).isEqualTo(id2);

        store.processChangeFilterAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.COMPLETED));
        assertThat(store.getItems()).hasSize(1);
        assertThat(store.getItems().get(0).getId()).isEqualTo(id1);

        store.processChangeFilterAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ALL));
        assertThat(store.getItems()).hasSize(2);

    }

    private void clearEventStreams() {
        selectAllCheckbox.clear();
        inputText.clear();
    }

    private <T> T last(Stack<T> list){
        if(list.isEmpty()) {
            return null;
        }else {
            return list.pop();
        }
    }

}
