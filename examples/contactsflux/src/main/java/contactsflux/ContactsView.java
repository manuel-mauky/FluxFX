package contactsflux;

import eu.lestard.fluxfx.react.ReactView;
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

    /**
     * The Controller-View state, currently trivial because of the one-to-one correspondance with TableView.items but
     * "state" could be a more complex data structure, say a Task status in addition to the ObservableList for a
     * ProgressBar
     */
    private ObservableList<Contact> state = FXCollections.observableArrayList();

    /**
     * A reference to the @Singleton Store for retrieving information (when notified)
     */
    private final ContactsStore store = new ContactsStore();  // singleton

    /**
     * A listener for updating the state of the Controller-View
     */
    private final Runnable changeListener = () -> setState(() -> state = store.getContacts());

    @FXML
    public void add() {

        publishAction(new AddContactAction(tfFirstName.getText(), tfLastName.getText()));

        tfFirstName.clear();
        tfLastName.clear();
    }

    @Override
    public void render() {
        tblContacts.setItems(state);
    }

    @Override
    public Runnable getInitialState() { return () -> store.getContacts(); }

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

        getInitialState().run();

        componentDidMount();

        render();
    }
}
