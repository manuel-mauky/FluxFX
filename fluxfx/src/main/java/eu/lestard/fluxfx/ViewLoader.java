package eu.lestard.fluxfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * A utility to load FXML files via a naming convention.
 *
 * In our terminology a "view" consists of a FXML file and a view class that implements the {@link View}
 * interface. This class has to be defined as "fx:controller" in the FXML file.
 *
 * The naming convention assumes that the view class and the fxml file have the same name (excluding file extension)
 * and are contained in the same directory/package. For example:
 *
 * ```java
 * package com.example.myapp;
 *
 * public class MyView implements View {
 *     ...
 * }
 * ```
 * ```xml
 * <?xml version="1.0" encoding="UTF-8"?>
 *     ...
 * <VBox fx:controller="com.example.myapp.MyView" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
 * </VBox>
 * ```
 *
 * The fxml file should be named "MyView.fxml" in a directory "com/example/myapp".
 *
 * It can then be loaded like this:
 *
 * ```java
 * Parent parent = ViewLoader.load(MyView.class);
 * ```
 *
 * If you need a reference to the created view class see this code:
 * ```java
 * Tuple<MyView> tuple = ViewLoader.loadTuple(MyView.class);
 *
 * Parent parent = tuple.getParent();
 * MyView myView = tuple.getController();
 * ````
 *
 * Optionally you can provide a global ResourceBundle with the {@link #setResourceBundle(ResourceBundle)}.
 *
 *
 * By default new instances of controller/view classes are created by the {@link FXMLLoader}.
 * This can be overwritten by defining a custom Dependency-Injection function {@link #setDependencyInjector(Callback)}.
 * This way you can use the dependency injection library of your choice.
 *
 */
public class ViewLoader {

    public static class Tuple <T extends View> {
        private Parent parent;
        private T controller;

        Tuple(Parent parent, T controller) {
            this.parent = parent;
            this.controller = controller;
        }

        public Parent getParent() {
            return parent;
        }

        public T getController() {
            return controller;
        }
    }


    private static Callback<Class<?>, Object> injector;

    private static ResourceBundle resourceBundle;

    public static void setDependencyInjector(Callback<Class<?>, Object> injector){
        ViewLoader.injector = injector;
    }

    public static void setResourceBundle(ResourceBundle resourceBundle) {
        ViewLoader.resourceBundle = resourceBundle;
    }

    public static <T extends Parent> T load(Class<? extends View> viewClass) {
        final FXMLLoader fxmlLoader = createFxmlLoader(viewClass);

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T extends View> Tuple<T> loadTuple(Class<T> viewClass) {
        final FXMLLoader fxmlLoader = createFxmlLoader(viewClass);

        try {
            return new Tuple<>(fxmlLoader.load(), fxmlLoader.getController());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static FXMLLoader createFxmlLoader(Class<? extends View> viewClass) {
        final URL fxmlPath = ViewLoader.class.getResource(createFxmlPath(viewClass));

        if(fxmlPath == null) {
            throw new IllegalArgumentException("Can't load View " + viewClass);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
        fxmlLoader.setLocation(fxmlPath);
        fxmlLoader.setResources(resourceBundle);

        if(injector != null) {
            fxmlLoader.setControllerFactory(injector);
        }
        return fxmlLoader;
    }

    /**
     * Taken from mvvmFX
     */
    private static String createFxmlPath(Class<?> viewType) {
        final StringBuilder pathBuilder = new StringBuilder();

        pathBuilder.append("/");

        if (viewType.getPackage() != null) {
            pathBuilder.append(viewType.getPackage().getName().replaceAll("\\.", "/"));
            pathBuilder.append("/");
        }

        pathBuilder.append(viewType.getSimpleName());
        pathBuilder.append(".fxml");

        return pathBuilder.toString();
    }
}
