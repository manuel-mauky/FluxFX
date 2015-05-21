package eu.lestard.fluxfx.utils;

import eu.lestard.fluxfx.View;
import eu.lestard.fluxfx.ViewLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class ViewCellFactory <T, V extends View> implements Callback<ListView<T>, ListCell<T>> {

    private Map<T, ViewLoader.Tuple<V>> cache = new HashMap<>();
    private final Class<V> viewType;

    private BiConsumer<T, V> updateFunction;


    public ViewCellFactory(Class<V> viewType, BiConsumer<T, V> updateFunction) {
        this.viewType = viewType;
        this.updateFunction = updateFunction;
    }

    @Override
    public ListCell<T> call(ListView<T> param) {
        return new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if(empty) {
                    setText(null);
                    setGraphic(null);
                }else {
                    setText(null);

                    if(item != null) {
                        if(!cache.containsKey(item)){
                            final ViewLoader.Tuple<V> tuple = ViewLoader.loadTuple(viewType);
                            cache.put(item, tuple);
                        }

                        final ViewLoader.Tuple<V> tuple = cache.get(item);

                        updateFunction.accept(item, tuple.getController());
                        setGraphic(tuple.getParent());
                    }
                }
            }
        };
    }


    /**
     * Update all views by invoking the update function ({@link #getUpdateFunction()}).
     */
    public void updateViews() {
        cache.entrySet()
                .forEach(entry -> updateFunction.accept(entry.getKey(), entry.getValue().getController()));
    }

    /**
     * Update all views that match the given predicate by invoking the update function ({@link #getUpdateFunction()}).
     */
    public void updateViews(Predicate<T> predicate) {
        cache.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getKey()))
                .forEach(entry -> updateFunction.accept(entry.getKey(), entry.getValue().getController()));
    }

    public BiConsumer<T, V> getUpdateFunction() {
        return updateFunction;
    }

    public void setUpdateFunction(BiConsumer<T, V> updateFunction) {
        this.updateFunction = updateFunction;
    }

}
