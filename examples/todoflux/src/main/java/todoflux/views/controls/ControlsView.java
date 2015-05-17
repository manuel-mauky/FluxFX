package todoflux.views.controls;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todoflux.actions.ChangeFilterAction;
import todoflux.stores.ItemsStore;

public class ControlsView implements View {

    @FXML
    public Label itemsLeftLabel;


    private final ItemsStore itemsStore;

    public ControlsView(ItemsStore itemsStore) {
        this.itemsStore = itemsStore;
    }

    public void initialize(){
        itemsLeftLabel.textProperty().bind(itemsStore.numberOfItemsLeft().asString());
    }

    public void all() {
        publishAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ALL));
    }

    public void active() {
        publishAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ACTIVE));
    }

    public void completed() {
        publishAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.COMPLETED));
    }
}
