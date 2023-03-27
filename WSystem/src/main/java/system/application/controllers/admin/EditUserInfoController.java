package system.application.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import system.application.others.DataRetriever;
import system.application.others.MessageService;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.profiles.ProfileManager;
import system.backend.services.ValidationService;


import java.util.*;

public class EditUserInfoController extends UserController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Button exitButton;
    @FXML
    private Hyperlink why1;
    @FXML
    private Hyperlink why2;
    @FXML
    private VBox consVbox1;
    @FXML
    private VBox consVbox2;
    @FXML
    private Label violationsLabel;
    private Owner owner;
    private Agent agent;

    private Map<String, String> newData = new HashMap<>();
    private Map<String, String> oldData = new HashMap<>();
    private Map<String, Set<String>> cons = new LinkedHashMap<>();
    private String[] keys = {"firstname", "lastname", "username",
            "password", "emailAddress", "phoneNumber"};

    //profile
    private ProfileManager<Owner> ownerProfileManager = new ProfileManager<>();
    private ProfileManager<Agent> agentProfileManager = new ProfileManager<>();

    //services
    private ValidationService validationService = ValidationService.getInstance();
    private DataRetriever dataRetriever = DataRetriever.getInstance();
    private MessageService messageService = MessageService.getInstance();

    public void setWidthAndHeight(){
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

    public void closeStage(ActionEvent event){
        exitButton = (Button) event.getSource();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        cons.clear();
        stage.close();
    }

    public void setDataForOwner(Owner owner){
        this.owner = owner;

        firstNameField.setText(owner.getFirstname());
        lastNameField.setText(owner.getLastname());
        usernameField.setText(owner.getUsername());
        passwordField.setText(owner.getPassword());
        emailField.setText(owner.getEmailAddress());
        phoneNumberField.setText(owner.getPhoneNumber());
    }
    public void setDataForAgent(Agent agent){
        this.agent = agent;

        firstNameField.setText(agent.getFirstname());
        lastNameField.setText(agent.getLastname());
        usernameField.setText(agent.getUsername());
        passwordField.setText(agent.getPassword());
        emailField.setText(agent.getEmailAddress());
        phoneNumberField.setText(agent.getPhoneNumber());

    }
    public void fillConsBox1(String message) {

        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox1.getChildren().add(consLabel);
        System.out.println(message);
        why1.setVisible(true);
    }
    public void fillConsBox2(String message) {
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox2.getChildren().add(consLabel);
        System.out.println(message);
        why2.setVisible(true);
    }

    public void handleDoneButton(ActionEvent event) {
        cons.clear();
        oldData.clear();
        newData.clear();

        System.out.println("Edit complete.");
        violationsLabel.setVisible(false);
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();

        dataRetriever.getEditData(this, newData);

        if(owner != null){
            ownerProfileManager.getProfileData(owner, oldData);
            cons = ownerProfileManager.updateProfile(owner, Owner.class, newData);

            if(cons.isEmpty())
                closeStage(event);
            else {
                ownerProfileManager.setProfileData(owner, oldData);
                setWidthAndHeight();
                violationsLabel.setVisible(true);
                System.out.println("Couldn't update");
                messageService.showMessages(this, cons, keys);
            }
            static_firstName.setText(owner.getFirstname());
            static_lastName.setText(owner.getLastname());
            static_phoneNumber.setText(owner.getPhoneNumber());
        } else {
            agentProfileManager.getProfileData(agent, oldData);
            cons = agentProfileManager.updateProfile(agent, Agent.class, newData);

            if(cons.isEmpty())
                closeStage(event);
            else {
                agentProfileManager.setProfileData(agent, oldData);
                setWidthAndHeight();
                violationsLabel.setVisible(true);
                System.out.println("Couldn't update");
                messageService.showMessages(this, cons, keys);
            }
            static_firstName.setText(agent.getFirstname());
            static_lastName.setText(agent.getLastname());
            static_phoneNumber.setText(agent.getPhoneNumber());
        }
    }

    public void handleExitButton(ActionEvent event) {
       closeStage(event);
    }

    public void showConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(true); }

    public void hideConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(false); }

    public void showConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(true); }

    public void hideConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(false); }

    @Override
    public TextField getFirstNameField() {
        return firstNameField;
    }

    @Override
    public TextField getLastNameField() {
        return lastNameField;
    }

    @Override
    public TextField getUsernameField() {
        return usernameField;
    }

    @Override
    public PasswordField getPasswordField() {
        return passwordField;
    }

    @Override
    public TextField getEmailField() {
        return emailField;
    }

    @Override
    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }

    @Override
    public Label getViolationsLabel() {
        return violationsLabel;
    }
}
