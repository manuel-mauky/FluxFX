import eu.lestard.easydi.EasyDI;
import eu.lestard.fluxfx.Dispatcher;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;


public class App extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EasyDI context = new EasyDI();
        ViewLoader.setDependencyInjector(context::getInstance);

        final Parent root = ViewLoader.load(MainView.class);

        stage.setScene(new Scene(root, 400, 200));
        stage.show();


        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final LocalDateTime now = LocalDateTime.now();

                Dispatcher.getInstance().dispatch(new UpdateAction("" + now.getSecond()));
            }
        }, 1000l);

    }
}
