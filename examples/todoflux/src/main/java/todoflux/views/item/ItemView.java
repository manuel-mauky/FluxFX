package todoflux.views.item;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import todoflux.data.TodoItem;

import java.io.IOException;

public class ItemView extends HBox{


    @FXML
    public Label contentLabel;

    @FXML
    public CheckBox completed;

    @FXML
    public TextField contentInput;


    public ItemView() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ItemView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(TodoItem item) {
        contentLabel.setText(item.getText());
        completed.setSelected(item.isCompleted());
    }

    public void delete() {
    }
}
