# FluxFX - Flux architecture with JavaFX

This is an experimental implementation of the [Flux Architecture](https://facebook.github.io/flux/docs/overview.html) with JavaFX.
"Experimental" means that the API is likely to be changed in the future and it's not production ready (yet) in terms of tests, documentation and feature set.
If you are looking for a production ready application framework for JavaFX have look at [mvvmFX](https://github.com/sialcasa/mvvmFX).

[![Build Status](https://travis-ci.org/lestard/FluxFX.svg?branch=master)](https://travis-ci.org/lestard/FluxFX)


### Flux architecture
The flux architecture is an alternative to Model-View-Controller build by Facebook for their JavaScript library [React](https://facebook.github.io/react/).


The key concept of flux is unidirectional data flow:

The application state is located in so called **Stores**.
**Views** are displaying the data of a the stores but they can't change the state pf the stores.
Instead the View has to publish **Actions**.
These actions are managed by a **Dispatcher** that dispatches *all* actions to *all* stores.
Each store can decide if it likes to handle an incoming action and can change it's internal state accordingly.
After a state change the store will emit a "change" event to the views so that the view can update itself.


### FluxFX

FluxFX follows this idea with some small exceptions to make it more suitable for the usage with JavaFX:

- Stores can "subscribe" to specific action types. Other actions aren't dispatched to the store.
- There is no explicit mechanism for "change events" from the Stores to trigger the re-rendering of views.
Instead it's recommended to use the Properties and unidirectional Data-Binding provided by JavaFX. Another great tool for this purpose are
the `EventStream`/`EventSource` classes from [ReactFX](https://github.com/TomasMikula/ReactFX).


Internally FluxFX uses the great library [ReactFX](https://github.com/TomasMikula/ReactFX) which is the only dependency.

To use FluxFX in your own project you can us the following config:

Gradle:

```groovy
dependencies {
    compile 'eu.lestard:fluxfx:0.1'
}
```

Maven:

```xml
<dependency>
    <groupId>eu.lestard</groupId>
    <artifactId>fluxfx</artifactId>
    <version>0.1</version>
</dependency>
```



Another example of the flux architecture with JavaFX (but without fluxfx) can be seen [here](https://github.com/lestard/juggr_model-view-star/tree/master/flux).



### Tutorial

In this short tutorial we are creating a simple notes app.

##### Create a basic Store

We start with the store class that will hold our notes data. In our case we will use a simple list of
strings:

```java
package notesapp;

import eu.lestard.fluxfx.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotesStore extends Store {

    private ObservableList<String> notes = FXCollections.observableArrayList();

    public ObservableList<String> getNotes() {
        return FXCollections.unmodifiableObservableList(notes);
    }
}
```

The store class extends from `eu.lestard.fluxfx.Store`.
Internally we use an observable array list.
But the public getter for the list returns an unmodifiable wrapper of our list.
This way we can be sure that the state can only be modified by the Store itself and not from
other classes.

##### Create a FXML file for the View (with [SceneBuilder](http://gluonhq.com/products/scene-builder/))

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" spacing="5.0" xmlns="http://javafx.com/javafx/8.0.40" xmlns:fx="http://javafx.com/fxml/1"
    fx:controller="notesapp.NotesView">
   <children>
      <HBox spacing="5.0">
         <children>
            <TextField fx:id="input" HBox.hgrow="ALWAYS" />
            <Button defaultButton="true" mnemonicParsing="false" onAction="#add" text="Add" />
         </children>
      </HBox>
      <ListView fx:id="list" />
   </children>
   <padding>
      <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
   </padding>
</VBox>
```
This FXML file produces this GUI:

![NotesApp](/examples/notesapp/notesapp.png)

Note that we have set the attribute `fx:controller` to `notesapp.NotesView`. We will create this class in the next step.

##### Create a View class

The view class acts as a connector between the Java logic and the FXML file.
We can inject references to the UI elements declared in the FXML file and fill them with data.

In the first step the view class looks like this:

```java
package notesapp;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class NotesView implements View {

    @FXML
    private ListView<String> list;

    @FXML
    private TextField input;

    private NotesStore store = new NotesStore();

    public void initialize() {
        list.setItems(store.getNotes());
    }

    public void add() {
        // todo
    }
}
```

The intersting part is the `initialize` method. This method will be called automatically by JavaFX when the FXML file
is loaded. With the statement `list.setItems(store.getNotes());` the `ListView` will show the notes from the Store.
As the notes list is observable the ListView will update itself automatically when the state in the store is changed.

Another thing to note is that we implement the `eu.lestard.fluxfx.View` interface. In the next steps we will see why.

##### Define an Action to add notes

With flux all changes to the applications state are triggered by *actions*.
In our case we like to add new notes so we create a class `AddNoteAction`.
An action implements the `eu.lestard.fluxfx.Action` interface:

```java
package notesapp;

import eu.lestard.fluxfx.Action;

public class AddNoteAction implements Action {

    private final String noteText;

    public AddNoteAction(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }
}
```

Actions can contain data but they should be immutable (i.e. data can't be changed after creation).

##### Trigger action in the View

We can now implement the `add` method in our view class.
When the user clicks the `add` button we will take the text from the input field and publish a new `AddNoteAction`.

```java
...
public class NotesView implements View {

    ...

    public void add() {
        publishAction(new AddNoteAction(input.getText()));
    }
}
```

The `View` interface has a method `publishAction` that we are using to bring our action into the system.

##### Process the action in the store

In our store implementation we can subscribe to our action and make the needed changed to the applications state.

```java
public class NotesStore extends Store {
    ...
    public NotesStore() {
        subscribe(AddNoteAction.class, new Consumer<AddNoteAction>() {
            @Override
            public void accept(AddNoteAction addNoteAction) {
                final String noteText = addNoteAction.getNoteText();

                if(noteText != null && !noteText.trim().isEmpty()) {
                    notes.add(noteText);
                }
            }
        });
    }
    ...
}
```

In the constructor we use the `subscribe` method from the `Store` class.
The first param is the class type of the action we are interested in.
The second param is a `Consumer` function that is processing the incoming action.

With Java8 of cause we can use a lambda instead of the anonymous inner class for the consumer.
Even better is to create a method in the store and use a method reference in
the subscribe statement.
The method shouldn't be `public` to prevent a direct invokation from outside of the store.
Instead I recomment to use the default scope (no access modifier) which makes unit-testing
realy easy:

```java
public NotesStore() {
    subscribe(AddNoteAction.class, this::processAddNoteAction);
}

protected void processAddNoteAction(AddNoteAction action) {
    final String noteText = action.getNoteText();

    if(noteText != null && !noteText.trim().isEmpty()) {
        notes.add(noteText);
    }
}
```

##### Create an application class and load the View

The last step is to create an application class with a `main` method and load the FXML file.
This can be done with the `ViewLoader` class from FluxFX like this:

```java
package notesapp;

import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Parent root = ViewLoader.load(NotesView.class);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
```

The complete code for this example can be seen in [the repository](/examples/notesapp).
