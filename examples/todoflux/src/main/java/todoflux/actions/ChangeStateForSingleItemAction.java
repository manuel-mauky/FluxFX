package todoflux.actions;

import eu.lestard.fluxfx.Action;

public class ChangeStateForSingleItemAction implements Action {

    private final String itemId;

    private final boolean newState;

    public ChangeStateForSingleItemAction(String itemId, boolean newState) {
        this.itemId = itemId;
        this.newState = newState;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean getNewState() {
        return newState;
    }
}
