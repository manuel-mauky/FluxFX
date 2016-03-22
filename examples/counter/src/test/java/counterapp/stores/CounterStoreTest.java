package counterapp.stores;

import counterapp.actions.IncreaseAction;
import eu.lestard.fluxfx.Action;
import org.junit.Before;
import org.junit.Test;
import org.reactfx.EventSource;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

public class CounterStoreTest {

    private CounterStore store;

    private EventSource<Action> actionStream;

    @Before
    public void setup() {
        actionStream = new EventSource<>();
        store = new CounterStore(actionStream);
    }

    @Test
    public void test() {
        // given
        assertThat(store.counter()).hasValue(0);

        // when
        actionStream.push(new IncreaseAction(1));

        // then
        assertThat(store.counter()).hasValue(1);
    }



}
