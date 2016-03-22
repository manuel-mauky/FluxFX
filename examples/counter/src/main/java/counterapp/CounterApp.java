package counterapp;

import counterapp.views.CounterView;
import eu.lestard.easydi.EasyDI;
import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.reactfx.EventSource;
import org.reactfx.EventStream;

public class CounterApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        EasyDI context = new EasyDI();

        EventSource<Action> actionStream = new EventSource<>();

        context.bindInstance(EventStream.class, actionStream);
        context.bindInstance(EventSource.class, actionStream);

        ViewLoader.setDependencyInjector(context::getInstance);


        final Parent root = ViewLoader.load(CounterView.class);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
