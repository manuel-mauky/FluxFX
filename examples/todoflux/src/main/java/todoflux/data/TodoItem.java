package todoflux.data;

/**
 * @author manuel.mauky
 */
public class TodoItem {
	
	private String text;
	private boolean completed = false;

    public TodoItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
