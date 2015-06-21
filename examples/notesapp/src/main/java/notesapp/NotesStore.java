package notesapp;

import eu.lestard.fluxfx.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotesStore extends Store {

    private ObservableList<String> notes = FXCollections.observableArrayList();

    public NotesStore() {
        subscribe(AddNoteAction.class, this::processAddNoteAction);
    }

    protected void processAddNoteAction(AddNoteAction action) {
        final String noteText = action.getNoteText();

        if(noteText != null && !noteText.trim().isEmpty()) {
            notes.add(noteText);
        }
    }


    public ObservableList<String> getNotes() {
        return FXCollections.unmodifiableObservableList(notes);
    }
}
