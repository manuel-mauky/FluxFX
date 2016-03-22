package counterapp.actions;

import eu.lestard.fluxfx.Action;

public class DecreaseAction implements Action {

    private final int amount;

    public DecreaseAction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
