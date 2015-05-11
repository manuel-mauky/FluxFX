package todoflux.actions;

import eu.lestard.fluxfx.Action;

public class ChangeStateAction implements Action {

    private final String id;

    private final boolean newState;

    public ChangeStateAction(String id, boolean newState) {
        this.id = id;
        this.newState = newState;
    }

    public String getId() {
        return id;
    }

    public boolean getNewState() {
        return newState;
    }
}
