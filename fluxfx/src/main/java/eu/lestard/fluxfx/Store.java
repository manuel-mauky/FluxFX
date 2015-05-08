package eu.lestard.fluxfx;

public interface Store {

    void processAction(Action action);

    void onChange(Runnable runnable);

}
