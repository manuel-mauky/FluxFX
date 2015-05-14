package todoflux.views.controls;

import eu.lestard.fluxfx.View;
import todoflux.actions.ChangeFilterAction;

public class ControlsView implements View {

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
