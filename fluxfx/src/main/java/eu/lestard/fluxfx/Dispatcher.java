package eu.lestard.fluxfx;

import java.util.HashSet;
import java.util.Set;

public final class Dispatcher {

    private static final Dispatcher SINGLETON = new Dispatcher();

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        return SINGLETON;
    }

    private Set<Store> stores = new HashSet<>();

    public void dispatch(Action action) {
        stores.forEach(store -> store.processAction(action));
    }

    public void register(Store store) {
        stores.add(store);
    }

}
