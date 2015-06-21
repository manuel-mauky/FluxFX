package notesapp;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class NotesView implements View {

    @FXML
    private ListView<String> list;

    @FXML
    private TextField input;

    private NotesStore store = new NotesStore();

    public void initialize() {
        list.setItems(store.getNotes());
    }

    public void add() {
        publishAction(new AddNoteAction(input.getText()));
    }
}
