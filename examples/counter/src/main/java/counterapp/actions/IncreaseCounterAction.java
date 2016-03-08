package counterapp.actions;

import eu.lestard.fluxfx.Action;

public class IncreaseCounterAction implements Action {

    private final int amount;

    public IncreaseCounterAction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
