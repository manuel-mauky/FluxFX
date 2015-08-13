package todoflux.views.controls;

import eu.lestard.fluxfx.react.ReactView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todoflux.actions.ChangeFilterAction;
import todoflux.stores.ItemsStore;

public class ControlsView implements ReactView {

    @FXML
    public Label itemsLeftLabel;


    private final ItemsStore itemsStore;

    public ControlsView(ItemsStore itemsStore) {
        this.itemsStore = itemsStore;
    }


    private int itemsLeft;

    @Override
    public Runnable getInitialState() {
        return () -> itemsLeft = 0;
    }

    @Override
    public void componentDidMount() {
        itemsStore.addOnChangeListener(() -> {
            setState(() -> itemsLeft = itemsStore.getNumberOfItemsLeft());
        });
    }

    @Override
    public void render() {
        itemsLeftLabel.setText(Integer.toString(itemsLeft));
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
