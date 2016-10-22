package contactsflux;

import eu.lestard.fluxfx.react.ReactView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The sole FluxFX View for the Application
 *
 * @author carl
 */
public class ContactsView extends VBox implements ReactView {

    @FXML
    private TextField tfFirstName, tfLastName;

    @FXML
    private TableView<Contact> tblContacts;

    @FXML
    private TableColumn<Contact, String> tcFirstName, tcLastName;

    @FXML
    private HBox statusHBox;

    @FXML
    private ProgressBar pb;

    @FXML
    private Label statusLabel;

    @FXML
    private Button btnAdd;

    private ContactsViewState state = new ContactsViewState();

    /**
     * A reference to the @Singleton Store for retrieving information (when notified)
     */
    private final ContactsStore store = new ContactsStore();  // singleton

    /**
     * A listener for updating the state of the Controller-View
     *
     * Needs FX thread safety b/c could be called from long-running Thread off the FX Thread
     */
    private final Runnable changeListener = () ->
            Platform.runLater(() ->
            setState(
            () -> {
                state.running = store.isRunning();
                state.progress = store.getProgress();
                state.message = store.getMessage();
                state.contacts = store.getContacts();
            }
    ));

    @FXML
    public void add() {

        publishAction(new AddContactAction(tfFirstName.getText(), tfLastName.getText()));

        tfFirstName.clear();
        tfLastName.clear();
    }

    @Override
    public void render() {

        if( state.running ) {
            statusHBox.setVisible(true);
            pb.setProgress( state.progress );
            statusLabel.setText( state.message );
        } else {
            statusHBox.setVisible(false);
            pb.setProgress( 0.0d );
            statusLabel.setText( "" );
        }

        tblContacts.setItems(state.contacts);
    }

    /**
     * Runs from FXMLLoader initialize, so doesn't need Platform.runLater() b/c on FX Thread already
     *
     * @return
     */
    @Override
    public Runnable getInitialState() { return () -> {
        state.running = store.isRunning();
        state.progress = store.getProgress();
        state.message = store.getMessage();
        state.contacts = store.getContacts();
    };
    }

    @Override
    public void componentDidMount() {
        store.addOnChangeListener( changeListener );
    }

    @Override
    public void componentWillUnmount() {
        store.removeOnChangeListener( changeListener );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //
        // Note that because this is a default interface method, there is no overriding (just complete replacement)
        //

        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        btnAdd.disableProperty().bind( tfFirstName.textProperty().isEmpty().and( tfLastName.textProperty().isEmpty() ));

        getInitialState().run();

        componentDidMount();

        render();
    }

    static class ContactsViewState {

        boolean running = false;
        String message = "";
        Double progress = 0.0d;
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

    }
}
