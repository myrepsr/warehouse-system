package system.application.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import system.application.others.DataRetriever;
import system.application.others.MessageService;
import system.backend.WSystem;
import system.backend.profiles.Agent;
import system.backend.profiles.ProfileManager;
import system.backend.services.ValidationService;

import java.net.URL;
import java.util.*;

public class RegisterAgentController extends AdminPanelController implements Initializable {
    @FXML
    private TextField usernameField = null;
    @FXML
    private TextField firstNameField = null;
    @FXML
    private TextField lastNameField = null;
    @FXML
    private PasswordField passwordField = null;
    @FXML
    private PasswordField confirmField = null;
    @FXML
    private TextField emailField = null;
    @FXML
    private TextField phoneNumberField = null;
    @FXML
    private Button registerButton = null;
    @FXML
    private Label userTypeLabel = null;
    @FXML
    private Label successLabel = null;
    @FXML
    private VBox consVbox1 = null;
    @FXML
    private VBox consVbox2 = null;
    @FXML
    private Label violationsLabel = null;
    @FXML
    private Hyperlink why1 = null;
    @FXML
    private Hyperlink why2 = null;
    private WSystem wSystem = WSystem.getInstance();

    private Map<String, String> data = new HashMap<>();
    private Map<String, Set<String>> cons = new LinkedHashMap<>();
    private String[] keys = {"firstname", "lastname", "username",
            "password", "emailAddress", "phoneNumber"};

    private MessageService messageService = MessageService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        consVbox1.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox2.setMaxWidth(Region.USE_PREF_SIZE);

        consVbox1.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxHeight(Region.USE_PREF_SIZE);
        consVbox2.setMaxHeight(Region.USE_PREF_SIZE);

    }

    public void fillConsBox1(String message) {
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox1.getChildren().add(consLabel);
        System.out.println(message);
        why1.setVisible(true);
    }

    public void fillConsBox2(String message){
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox2.getChildren().add(consLabel);
        System.out.println(message);
        why2.setVisible(true);
    }

    public void registerButtonAction(ActionEvent actionEvent) {
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();
        why1.setVisible(false);
        why2.setVisible(false);

        System.out.println("Register Button Clicked.");

        DataRetriever dataRetriever = DataRetriever.getInstance();
        dataRetriever.getRegisterData(this, data);

        ValidationService validationService = ValidationService.getInstance();

        if(validationService.allDataFilled(data)) {
            if (validationService.passwordsMatch(data.get("password"), data.get("confirmPassword")))
                registerOrViolate();
            else messageService.passwordMatchMessage(this);
        } else messageService.fillDataMessage(this, data);
    }

    public void registerOrViolate(){
        cons.clear();

        ProfileManager<Agent> profileManager = new ProfileManager<>();
        cons = profileManager.createProfile(Agent.class, data);

        if (cons.isEmpty()) {
            violationsLabel.setVisible(false);
            successLabel.setVisible(true);
            profilesState();    // checks if there are profiles. true => sets button visible. false => sets button invisible
        } else {
            violationsLabel.setVisible(true);
            successLabel.setVisible(false);
            messageService.showMessages(this, cons, keys);
        }
    }

    public void showConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(true); }

    public void hideConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(false); }

    public void showConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(true); }

    public void hideConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(false); }

    @Override
    public Label getSuccessLabel() {
        return successLabel;
    }

    @Override
    public Label getViolationsLabel() {
        return violationsLabel;
    }

    @Override
    public TextField getUsernameField() {
        return usernameField;
    }

    @Override
    public TextField getFirstNameField() {
        return firstNameField;
    }

    @Override
    public TextField getLastNameField() {
        return lastNameField;
    }

    @Override
    public PasswordField getPasswordField() {
        return passwordField;
    }

    @Override
    public PasswordField getConfirmField() {
        return confirmField;
    }

    @Override
    public TextField getEmailField() {
        return emailField;
    }

    @Override
    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }
}