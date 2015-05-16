package todoflux.actions;

import eu.lestard.fluxfx.Action;

public class ChangeStateForAllItemsAction implements Action {

    private final boolean newState;

    public ChangeStateForAllItemsAction(boolean newState) {
        this.newState = newState;
    }

    public boolean isNewState() {
        return newState;
    }
}
