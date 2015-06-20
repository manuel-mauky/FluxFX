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
