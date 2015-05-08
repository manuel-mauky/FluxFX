package todoflux;

import eu.lestard.easydi.EasyDI;
import eu.lestard.fluxfx.Dispatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todoflux.stores.ItemStore;

import java.net.URL;


public class App extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EasyDI context = new EasyDI();

        context.markAsSingleton(Dispatcher.class);

        final Dispatcher dispatcher = context.getInstance(Dispatcher.class);

        final ItemStore itemStore = context.getInstance(ItemStore.class);
        dispatcher.register(itemStore);


        final URL fxml = this.getClass().getResource("/todoflux/views/MainView.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setControllerFactory(context::getInstance);

        final Parent root = fxmlLoader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }
}
