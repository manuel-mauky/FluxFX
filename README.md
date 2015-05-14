# FluxFX - Flux architecture with JavaFX

In this repository I'm trying out the [Flux Architecture](https://facebook.github.io/flux/docs/overview.html) with JavaFX.


The flux architecture is an alternative to Model-View-Controller build by Facebook for their JavaScript library [React](https://facebook.github.io/react/).
The key concept of flux is unidirectional data flow:
The View can't directly change the state of the application.
Instead it has to trigger so called *actions*.
These actions are dispatched to *stores* that are containing the application state.
Stores can react to actions and change their state.
Afterwards the store emits a "change" event that triggers a re-rendering of the View.


Another example of flux with JavaFX can be seen [here](https://github.com/lestard/juggr_model-view-star/tree/master/flux).
