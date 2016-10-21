package contactsflux;

import eu.lestard.fluxfx.Action;
import eu.lestard.fluxfx.Dispatcher;
import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

/**
 * The sole FluxFX View for the Application
 *
 * @author carl
 */
public class ContactsView implements View {

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

    public void initialize() {

        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    @FXML
    public void add() {

        publishAction(new AddContactAction(tfFirstName.getText(), tfLastName.getText()));

        tfFirstName.clear();
        tfLastName.clear();
    }
}
