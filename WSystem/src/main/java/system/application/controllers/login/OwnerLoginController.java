package system.application.controllers.login;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.WSystem;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;
import system.backend.services.AuthorizationService;

import java.io.IOException;

public class OwnerLoginController {
    private Logger LOGGER = LogManager.getLogger();
    @FXML
    private TextField usernameField = null;
    @FXML
    private PasswordField passwordField = null;
    @FXML
    private Button loginButton = null;
    @FXML
    private Label userTypeLabel = null;
    @FXML
    private Label loginFalseLabel = null;
    private static final PseudoClass fieldsCheck_pseudoClass = PseudoClass.getPseudoClass("fieldsCheck");



    public void setPseudoClassState(boolean state){
        usernameField.pseudoClassStateChanged(fieldsCheck_pseudoClass, state);
        passwordField.pseudoClassStateChanged(fieldsCheck_pseudoClass, state);
    }

    public void closeStage(ActionEvent event){
        loginButton = (Button) event.getSource();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public void loadStage(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent Root = null;

        try {
            Root = (Parent) loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load the stage.");
        }

        if (Root == null)
            throw new AssertionError();

        Scene ownerPanelScene = new Scene(Root);
        Stage Stage = new Stage();
        Stage.setScene(ownerPanelScene);
        Stage.initStyle(StageStyle.UNDECORATED);

        Stage.show();
    }

    public void setOnAction(ActionEvent actionEvent) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        AuthorizationService<Owner> service = new AuthorizationService<>();
        boolean success = service.authorizeLogin(username, password, Owner.class);
        LOGGER.info("Successfully finished the authorization of the agent");

        if(success){
            WSystem wSystem = WSystem.getInstance();
            Owner owner = wSystem.findOwnerBy2Values(username, password);
            OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();
            ownerDataHolder.setOwner(owner);
            System.out.println("Successfully logged in.");
            setPseudoClassState(false);
            closeStage(actionEvent);
            loadStage("/fxml/ownerPanelFXML.fxml");
        } else{
            System.out.println("Username or password is incorrect.");
            loginFalseLabel.setVisible(true);
            setPseudoClassState(true);
        }
    }
}
