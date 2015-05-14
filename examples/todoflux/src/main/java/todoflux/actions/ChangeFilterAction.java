package todoflux.actions;

import eu.lestard.fluxfx.Action;

public class ChangeFilterAction implements Action {

    public enum VisibilityType {
        ALL,
        ACTIVE,
        COMPLETED

    }

    private VisibilityType visibilityType = VisibilityType.ACTIVE;

    public ChangeFilterAction(VisibilityType visibilityType) {
        this.visibilityType = visibilityType;
    }

    public VisibilityType getVisibilityType() {
        return visibilityType;
    }
}
