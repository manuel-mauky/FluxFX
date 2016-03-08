package counterapp;

import counterapp.views.CounterView;
import eu.lestard.easydi.EasyDI;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CounterApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        EasyDI context = new EasyDI();

        ViewLoader.setDependencyInjector(context::getInstance);


        final Parent root = ViewLoader.load(CounterView.class);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
