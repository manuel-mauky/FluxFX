package contactsflux;

import eu.lestard.fluxfx.Store;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A FluxFX Store; simulates data retrieval and persistence
 *
 * @author carl
 */
public class ContactsStore extends Store {

    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final BooleanProperty runningProperty = new SimpleBooleanProperty();
    private final DoubleProperty progressProperty = new SimpleDoubleProperty();
    private final StringProperty messageProperty = new SimpleStringProperty();

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public boolean isRunning() {
        return runningProperty.get();
    }

    public double getProgress() {
        return progressProperty.get();
    }

    public String getMessage() {
        return messageProperty.get();
    }

    public ContactsStore() {

        subscribe(FetchContactsAction.class,

                  (fetchContactsAction) -> {

                      Task<List<Contact>> task = new Task<List<Contact>>() {

                          @Override
                          protected List<Contact> call() throws Exception {

                              updateMessage("Fetching contacts");
                              updateProgress( 0.5d, 1.0d );
                              onChange();

                              simulateTimeConsumingOp();
                              List<Contact> fromDB = new ArrayList<>();
                              fromDB.add( new Contact("Initial", "Contact"));  // test data

                              updateMessage("Contacts fetched");
                              updateProgress( 1.0d, 1.0d );
                              onChange();

                              return fromDB;
                          }
                          @Override
                          protected void succeeded() {
                              super.succeeded();
                              contacts.addAll( getValue() );
                              onChange();
                          }
                      };

                      runningProperty.bind( task.runningProperty() );
                      progressProperty.bind( task.progressProperty() );
                      messageProperty.bind( task.messageProperty());

                      new Thread(task).start();
                  });

        subscribe(AddContactAction.class,

                  (addContactAction) -> {

                      /**
                       * Start a Task to save the data.  Upon successful completion, add the Contact to the model.
                       * Prior to starting the Task, send a reference to allow interested parties to bind JavaFX
                       * controls to the Task.
                       */

                        final String fn = addContactAction.getFirstName();
                        final String ln = addContactAction.getLastName();

                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {

                                updateMessage("Saving new contact");
                                updateProgress(0.5d, 1.0d);
                                onChange();

                                simulateTimeConsumingOp();  // actually do the backend saving

                                updateMessage("Contact saved");
                                updateProgress( 1.0d, 1.0d );
                                onChange();

                                return null;
                            }

                            @Override
                            protected void succeeded() {
                                contacts.add( new Contact(fn, ln) );  // updates the UI on fx thread
                                onChange();
                            }
                        };

                      runningProperty.bind( task.runningProperty() );
                      progressProperty.bind( task.progressProperty() );
                      messageProperty.bind( task.messageProperty());

                      new Thread(task).start();
                    }
        );
    }

    private void simulateTimeConsumingOp() {

        //
        // A time consuming operation to simulate data retrieval and persistence behavior
        //

        try {
            for( int i=0; i<3; i++ ) {
                HttpURLConnection c = (HttpURLConnection) new URL("http://w3c.org").openConnection();
                try (InputStream is = c.getInputStream()) {
                    int ch;
                    while ((ch = is.read()) != -1) {
                    }
                }
                c.disconnect();
            }
        } catch(Exception exc) {
            exc.printStackTrace();
        }
    }
}
