package contactsflux;

import eu.lestard.fluxfx.Action;
import javafx.collections.ObservableList;

public class InitContactsViewAction implements Action {

    private final ObservableList<Contact> contacts;

    public InitContactsViewAction(ObservableList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }
}
