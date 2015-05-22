import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainView implements View {

    @FXML
    public Label valueLabel;


    private final MainStore store;

    public MainView(MainStore store) {
        this.store = store;
    }


    public void initialize(){
        valueLabel.textProperty().bind(store.value());
    }

}
