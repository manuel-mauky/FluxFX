import eu.lestard.fluxfx.Action;

public class UpdateAction implements Action {

    private final String value;

    public UpdateAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
