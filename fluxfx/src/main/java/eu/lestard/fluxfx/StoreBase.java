package eu.lestard.fluxfx;

import java.util.HashSet;
import java.util.Set;

public abstract class StoreBase implements Store {

    private Set<Runnable> onChangeListeners = new HashSet<>();

    @Override
    public void onChange(Runnable runnable) {
        onChangeListeners.add(runnable);
    }


    protected void publishOnChange() {
        onChangeListeners.forEach(Runnable::run);
    }
}
