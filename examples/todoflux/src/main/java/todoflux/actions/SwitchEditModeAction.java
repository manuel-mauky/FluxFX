package todoflux.actions;

import eu.lestard.fluxfx.Action;

public class SwitchEditModeAction implements Action{

    private final String itemId;

    private final boolean editMode;

    public SwitchEditModeAction(String itemId, boolean editMode) {
        this.itemId = itemId;
        this.editMode = editMode;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isEditMode() {
        return editMode;
    }
}
