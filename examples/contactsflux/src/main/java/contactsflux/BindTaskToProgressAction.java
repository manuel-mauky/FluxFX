package contactsflux;

import eu.lestard.fluxfx.Action;
import javafx.concurrent.Task;

/**
 * A FluxFX Action to bind a Task to JavaFX controls like a ProgressBar
 *
 * @author carl
 *
 * @param <T>
 */
public class BindTaskToProgressAction<T> implements Action {

    private final Task<T> task;

    public BindTaskToProgressAction(Task<T> task) {
        this.task = task;
    }

    public Task<T> getTask() {
        return task;
    }
}
