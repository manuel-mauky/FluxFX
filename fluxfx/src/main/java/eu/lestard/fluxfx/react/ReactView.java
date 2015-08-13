package eu.lestard.fluxfx.react;

import eu.lestard.fluxfx.View;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public interface ReactView extends View, Initializable {


    void render();

    default void setState(Runnable f) {
        f.run();

        render();
    }

    default Runnable getInitialState() {
        return () -> {};
    }

    @Override
    default void initialize(URL location, ResourceBundle resources) {
        getInitialState().run();
        render();
    }
}
