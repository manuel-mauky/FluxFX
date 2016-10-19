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
