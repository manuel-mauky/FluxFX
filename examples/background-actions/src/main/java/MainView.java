import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class MainView implements View {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @FXML
    public Label valueLabel;


    private final MainStore store;

    public MainView(MainStore store) {
        this.store = store;
    }


    public void initialize(){
        store.dateTime().subscribe(time -> valueLabel.setText(time.format(formatter)));
    }

}
