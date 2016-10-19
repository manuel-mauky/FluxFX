package contactsflux;

import eu.lestard.fluxfx.Action;
import javafx.concurrent.Task;

public class BindTaskToProgressAction<T> implements Action {

    private final Task<T> task;

    public BindTaskToProgressAction(Task<T> task) {
        this.task = task;
    }

    public Task<T> getTask() {
        return task;
    }
}
