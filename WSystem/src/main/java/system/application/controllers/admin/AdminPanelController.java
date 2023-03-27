package system.application.controllers.admin;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.application.controllers.AbstractController;
import system.application.controllers.FxmlLoader;
import system.backend.WSystem;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

import java.io.IOException;

public class AdminPanelController extends AbstractController {
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Button exitButton = null;
    @FXML
    private Button createOwnerButton = null;
    @FXML
    private Button createAgentButton = null;
    @FXML
    protected Button profilesButton = new Button();
    protected static Button profilesButton_static = new Button();
    @FXML
    private AnchorPane loaderPane = null;
    @FXML
    private Button logoutButton = null;

    public void initialize() {
        profilesButton_static = profilesButton;
        System.out.println("AdminPanel initialized.");
       profilesState();
    }

    public void profilesState(){
        WSystem wSystem = WSystem.getInstance();
        if (wSystem.hasProfiles())
            profilesButton_static.setVisible(true);
        else
            profilesButton_static.setVisible(false);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setLoader(String fxmlFile) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
        initialize();
    }

    public void onExitAction(ActionEvent actionEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }

    public void handleButton1Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + createOwnerButton.getText());
        setLoader("registerOwnerFXML");
    }

    public void handleButton2Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + createAgentButton.getText());
        setLoader("registerAgentFXML");
    }

    public void handleButton3Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + profilesButton.getText());
        setLoader("profilesFXML");
    }

    public void closeStage(ActionEvent event){
        logoutButton = (Button) event.getSource();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }
    public void loadStage(String fxmlPath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent loginScreenRoot = null;

        try {
            loginScreenRoot = (Parent) loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load the stage.");
        }

        if (loginScreenRoot == null)
            throw new AssertionError();

        Scene loginScreenScene = new Scene(loginScreenRoot);
        Stage loginScreenStage = new Stage();
        loginScreenStage.setScene(loginScreenScene);
        loginScreenStage.initStyle(StageStyle.UNDECORATED);

        loginScreenStage.show();
    }

    public void handleLogoutButton(ActionEvent event) {
        closeStage(event);
       loadStage("/fxml/loginScreenFXML.fxml");
    }

    public AnchorPane getLoaderPane() {
        return loaderPane;
    }
}
