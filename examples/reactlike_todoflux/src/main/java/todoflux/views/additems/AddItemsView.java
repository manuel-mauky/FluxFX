package todoflux.views.additems;

import eu.lestard.fluxfx.react.ReactView;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import todoflux.actions.AddItemAction;
import todoflux.actions.ChangeCompletedForAllItemsAction;
import todoflux.stores.ItemsStore;

public class AddItemsView implements ReactView {

    @FXML
    public TextField addInput;
    @FXML
    public CheckBox selectAll;

    private ItemsStore itemStore;

    public AddItemsView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }


    private String inputText;
    private boolean allSelected;


    @Override
    public Runnable getInitialState() {
        return () -> {
            inputText = "";
            allSelected = false;
        };
    }


    @Override
    public void componentDidMount() {

        addInput.setOnAction(event -> {
            publishAction(new AddItemAction(addInput.getText()));
            setState(() -> inputText = "");
        });
        selectAll.setOnAction(event -> publishAction(new ChangeCompletedForAllItemsAction(selectAll.isSelected())));

        itemStore.addOnChangeListener(() -> {
            if(itemStore.isAllSelected() != allSelected) {
                setState(() -> allSelected = itemStore.isAllSelected());
            }
        });
    }

    @Override
    public void render() {
        addInput.setText(inputText);
        selectAll.setSelected(allSelected);
    }
}
