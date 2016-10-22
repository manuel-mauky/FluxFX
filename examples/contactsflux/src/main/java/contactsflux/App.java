package contactsflux;

import eu.lestard.easydi.EasyDI;
import eu.lestard.fluxfx.Dispatcher;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
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

        EasyDI context = new EasyDI();
        ViewLoader.setDependencyInjector(context::getInstance);

        final Parent p = ViewLoader.load(ContactsView.class );

        Scene scene = new Scene(p );

        primaryStage.setScene( scene );
        primaryStage.setTitle("Contacts FluxFX App");
        primaryStage.setWidth( 667 );
        primaryStage.setHeight( 376 );
        primaryStage.setOnShown(
                (evt) -> Dispatcher.getInstance().dispatch(new FetchContactsAction())
        );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
