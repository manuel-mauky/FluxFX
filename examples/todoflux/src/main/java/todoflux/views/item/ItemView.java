package todoflux.views.item;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import todoflux.actions.ChangeStateForSingleItemAction;
import todoflux.actions.DeleteItemAction;
import todoflux.data.TodoItem;

public class ItemView implements View {

    public static final String STRIKETHROUGH_CSS_CLASS = "strikethrough";

    @FXML
    public Label contentLabel;

    @FXML
    public CheckBox completed;

    @FXML
    public TextField contentInput;

    @FXML
    public HBox root;
    @FXML
    public Button deleteButton;

    private String id;


    public void initialize() {
        deleteButton.setVisible(false);
        root.setOnMouseEntered(event -> deleteButton.setVisible(true));
        root.setOnMouseExited(event -> deleteButton.setVisible(false));


        completed.setOnAction(event -> publishAction(new ChangeStateForSingleItemAction(id, completed.isSelected())));
    }

    public void update(TodoItem item) {
        id = item.getId();
        contentLabel.setText(item.getText());
        completed.setSelected(item.isCompleted());
        if(item.isCompleted()) {
            contentLabel.getStyleClass().add(STRIKETHROUGH_CSS_CLASS);
        } else {
            contentLabel.getStyleClass().remove(STRIKETHROUGH_CSS_CLASS);
        }
    }

    public void delete() {
        publishAction(new DeleteItemAction(id));
    }
}
