import eu.lestard.fluxfx.Store;
import org.reactfx.EventSource;
import org.reactfx.EventStream;

import java.time.LocalDateTime;

public class MainStore extends Store {

    private EventSource<LocalDateTime> dateTimeEventStream = new EventSource<>();


    public MainStore() {
        subscribe(UpdateAction.class, this::processUpdateAction);
    }

    void processUpdateAction(UpdateAction action) {
        dateTimeEventStream.push(action.getDateTime());
    }

    public EventStream<LocalDateTime> dateTime() {
        return dateTimeEventStream;
    }

}
