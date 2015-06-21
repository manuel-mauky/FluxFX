package notesapp;

import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        final Parent root = ViewLoader.load(NotesView.class);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
