package todoflux.views.item;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ItemView{


    @FXML
    public Label contentLabel;

    @FXML
    public TextField contentInput;

    public void update(String text){
        contentLabel.setText(text);
    }


    public void delete() {
    }
}
