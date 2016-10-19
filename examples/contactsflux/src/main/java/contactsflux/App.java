package contactsflux;

import eu.lestard.fluxfx.Dispatcher;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application subclass; main entry point
 *
 * This is a demonstration of the FluxFX framework supplemented with JavaFX Tasks to keep the application lively and
 * give the user feedback on long-running operations.  There are a few HTTP calls that are added to give the
 * application a real-world feel.
 *
 * @author carl
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        final Parent p = ViewLoader.load(ContactsView.class );

        final ContactsStore contactsStore = new ContactsStore();

        Scene scene = new Scene(p );

        primaryStage.setScene( scene );
        primaryStage.setTitle("Contacts FluxFX App");
        primaryStage.setWidth( 667 );
        primaryStage.setHeight( 376 );
        primaryStage.setOnShown(

                (evt) -> {

                    //
                    // Start a Task to retrieve the Contact object
                    //
                    // Prior to starting the Task, send a reference to interested parties who can bind JavaFX
                    // controls to the Task.  When finished, send a notification to listeners with the data.
                    //

                    Task<ObservableList<Contact>> task = new Task<ObservableList<Contact>>() {
                        @Override
                        protected ObservableList<Contact> call() throws Exception {
                            updateMessage("Loading contacts");
                            updateProgress(0.5d, 1.0d);
                            return contactsStore.getContacts();
                        }

                        @Override
                        protected void succeeded() {
                            Dispatcher.getInstance().dispatch(
                                    new InitContactsViewAction(getValue())
                            );
                        }
                    };
                    Dispatcher.getInstance().dispatch(
                            new BindTaskToProgressAction<>(task)
                    );

                    new Thread(task).start();
                 }
        );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
