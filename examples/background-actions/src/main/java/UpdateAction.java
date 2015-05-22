import eu.lestard.fluxfx.Action;

import java.time.LocalDateTime;

public class UpdateAction implements Action {

    private final LocalDateTime dateTime;

    public UpdateAction(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
