package notesapp;

import eu.lestard.fluxfx.Action;

public class AddNoteAction implements Action {

    private final String noteText;

    public AddNoteAction(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }
}
