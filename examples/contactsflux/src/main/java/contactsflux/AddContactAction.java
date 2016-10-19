package contactsflux;

import eu.lestard.fluxfx.Action;

public class AddContactAction implements Action {

    private final String firstName;
    private final String lastName;

    public AddContactAction(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName =  lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
