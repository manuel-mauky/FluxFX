package counterapp.actions;

import eu.lestard.fluxfx.Action;

public class IncreaseAction implements Action {

    private final int amount;

    public IncreaseAction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
