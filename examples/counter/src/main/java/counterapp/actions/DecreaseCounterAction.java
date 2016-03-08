package counterapp.actions;

import eu.lestard.fluxfx.Action;

public class DecreaseCounterAction implements Action {

    private final int amount;

    public DecreaseCounterAction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
