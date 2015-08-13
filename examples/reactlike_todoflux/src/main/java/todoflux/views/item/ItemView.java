package todoflux.views.item;

import eu.lestard.fluxfx.react.ReactView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import todoflux.actions.ChangeCompletedForSingleItemAction;
import todoflux.actions.DeleteItemAction;
import todoflux.actions.EditAction;
import todoflux.stores.TodoItem;

public class ItemView implements ReactView {

    public static final String STRIKETHROUGH_CSS_CLASS = "strikethrough";

    @FXML
    public Label contentLabel;

    @FXML
    public CheckBox completedCheckbox;

    @FXML
    public TextField contentInput;

    @FXML
    public HBox root;
    @FXML
    public Button deleteButton;

    @FXML
    public HBox contentBox;

    private String id;


    private String content;
    private boolean completed;
    private boolean deleteButtonVisible;
    private boolean editMode;


    @Override
    public Runnable getInitialState() {
        return () -> {
            content = "";
            completed = false;
            deleteButtonVisible = false;
            editMode = false;
        };
    }

    @Override
    public void componentDidMount() {
        root.setOnMouseEntered(event -> setState(() -> deleteButtonVisible = true));
        root.setOnMouseExited(event -> setState(() -> deleteButtonVisible = false));

        completedCheckbox.setOnAction(event -> publishAction(new ChangeCompletedForSingleItemAction(id, completedCheckbox.isSelected())));

        contentLabel.setOnMouseClicked(event -> {
            if(event.getClickCount() > 1) {
                setState(() -> editMode = true);
            }
        });

        contentInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                setState(() -> editMode = false);
            }
        });

        contentInput.setOnAction(event -> {
            publishAction(new EditAction(id, contentInput.getText()));
            setState(() -> editMode = false);
        });


    }

    @Override
    public void render() {
        completedCheckbox.setSelected(completed);
        contentLabel.setText(content);
        contentInput.setText(content);

        deleteButton.setVisible(deleteButtonVisible);

        contentInput.setVisible(editMode);
        if(editMode) {
            contentInput.requestFocus();
        }
        contentBox.setVisible(!editMode);
        completedCheckbox.setVisible(!editMode);

        if(completed) {
            contentLabel.getStyleClass().add(STRIKETHROUGH_CSS_CLASS);
        } else {
            contentLabel.getStyleClass().removeAll(STRIKETHROUGH_CSS_CLASS);
        }

    }


    public void componentWillReceiveProps (TodoItem item) {
        setState(() -> {
            id = item.getId();
            content = item.getText();
            completed = item.isCompleted();
            editMode = false;
        });
    }


    public void delete() {
        publishAction(new DeleteItemAction(id));
    }


}
