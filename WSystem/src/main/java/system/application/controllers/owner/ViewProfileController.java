package system.application.controllers.owner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import system.application.controllers.AbstractController;
import system.application.controllers.Controller;
import system.application.controllers.FxmlLoader;
import system.application.others.DataRetriever;
import system.application.others.MessageService;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;
import system.backend.profiles.ProfileManager;

import javax.validation.ConstraintViolation;
import java.net.URL;
import java.util.*;

public class ViewProfileController extends AbstractController implements Initializable, Controller {
    @FXML
    private AnchorPane editPane;  // Container for edit. Loads whenever edit button is pressed
    @FXML
    private VBox profileVbox;   // Container for user info
    @FXML
    private AnchorPane anchorPane = null;   // Parent Container
    @FXML
    private Label firstNameLabel = null;
    @FXML
    private Hyperlink why1;
    @FXML
    private Hyperlink why2;
    @FXML
    private TextField firstNameField;
    @FXML
    private Label lastNameLabel = null;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label usernameLabel = null;
    @FXML
    private TextField usernameField;
    @FXML
    private Label emailLabel = null;
    @FXML
    private TextField emailField;
    @FXML
    private Label phoneLabel = null;
    @FXML
    private TextField phoneField;
    @FXML
    private Button editButton = null;
    @FXML
    private Hyperlink changePassword = null;
    @FXML
    private VBox consVbox1;  // Container which shows violations if there are
    @FXML
    private VBox consVbox2;  // Container which shows violations if there are
    @FXML
    private Label violationsLabel;

    private Owner owner;
    private OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();

    private Map<String, String> newData = new HashMap<>();
    private Map<String, String> oldData = new HashMap<>();
    private Map<String, Set<String>> cons = new LinkedHashMap<>();

    private String[] keys = {"firstname", "lastname", "username",
            "password", "emailAddress", "phoneNumber"};

    ProfileManager<Owner> ownerProfileManager = new ProfileManager<>();
    DataRetriever dataRetriever = DataRetriever.getInstance();
    MessageService messageService = MessageService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.owner = ownerDataHolder.getOwner();

        // sets the labels with owner info
        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());

        // Makes the size of the container dynamic
        consVbox1.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox1.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxHeight(Region.USE_PREF_SIZE);

        // Makes the size of the container dynamic
        consVbox2.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox2.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setMaxHeight(Region.USE_PREF_SIZE);
    }

    @Override
    public void fillConsBox1(String message) {  // Fills the container with  violation labels
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 10px");
        consVbox1.getChildren().add(consLabel);
        System.out.println(message);
        why1.setVisible(true);
    }

    @Override
    public void fillConsBox2(String message) { // Fills the container with  violation labels
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 10px");
        consVbox2.getChildren().add(consLabel);
        System.out.println(message);
        why2.setVisible(true);
    }

    public void setLoader(String fxmlFile){ // sets the parent container to a new FXML File
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);

        anchorPane.getChildren().clear();   // clears all children elements of the container
        anchorPane.getChildren().add(view);  // loads new fxmlFile with new children elements
    }

    public void handleEditButton(ActionEvent event) {

        System.out.println("You clicked: " + editButton.getText());
        // setLoader("changePasswordFXML");
        firstNameField.setText(owner.getFirstname());
        lastNameField.setText(owner.getLastname());
        usernameField.setText(owner.getUsername());
        emailField.setText(owner.getEmailAddress());
        phoneField.setText(owner.getPhoneNumber());

        editPane.setVisible(true);
        profileVbox.setVisible(false);
    }

    public void handleChangePass(ActionEvent event) {   // loads fxml file
        System.out.println("You clicked: " + changePassword.getText());
        setLoader("changePasswordFXML");
    }

    public void handleDoneButton(ActionEvent event) {   // Updates user info
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();
        why1.setVisible(false);
        why2.setVisible(false);
        violationsLabel.setVisible(false);


        cons.clear();
        oldData.clear();
        newData.clear();

        dataRetriever.getEditDataWithoutPass(this, newData);
        ownerProfileManager.getProfileDataWithoutPass(owner, oldData);
        cons = ownerProfileManager.updateProfileWithoutPass(owner, Owner.class, newData);

        if(cons.isEmpty()) {
            System.out.println("Owner successfully updated!");
            editPane.setVisible(false);
            profileVbox.setVisible(true);
        }
        else {
            violationsLabel.setVisible(true);
            ownerProfileManager.setProfileDataWithoutPass(owner, oldData);
            messageService.showMessages(this, cons, keys);
        }

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());
    }

    public void handleCancelButton(ActionEvent event) {
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();
        violationsLabel.setVisible(false);
        why1.setVisible(false);
        why2.setVisible(false);
        editPane.setVisible(false);
        profileVbox.setVisible(true);
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneNumberField() {
        return phoneField;
    }

    public void showConsBox1(MouseEvent mouseEvent) {
        consVbox1.setVisible(true);
    }

    public void hideConsBox1(MouseEvent mouseEvent) {
        consVbox1.setVisible(false);
    }

    public void showConsBox2(MouseEvent mouseEvent) {
        consVbox2.setVisible(true);
    }

    public void hideConsBox2(MouseEvent mouseEvent) {
        consVbox2.setVisible(false);
    }
}
