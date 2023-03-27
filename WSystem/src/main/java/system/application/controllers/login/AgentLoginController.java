package system.application.controllers.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.profiles.Agent;
import system.backend.services.AuthorizationService;

public class AgentLoginController {
    private Logger LOGGER = LogManager.getLogger();
    @FXML
    private TextField usernameField = null;
    @FXML
    private PasswordField passwordField = null;
    @FXML
    private Button loginButton = null;
    @FXML
    private Label userTypeLabel = null;

    public void setOnAction(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        AuthorizationService<Agent> service = new AuthorizationService<>();
        boolean success = service.authorizeLogin(username, password, Agent.class);
        LOGGER.info("Successfully finished the authorization of the agent");

        if(success){
            System.out.println("Successfully logged in.");
        } else{
            System.out.println("Username or password is incorrect.");
        }

//        if (passwordField.getText().trim().equals("") && usernameField.getText().trim().equals(""))
//            System.out.println("Text fields are empty!");
//        else if (passwordField.getText().trim().equals("") || usernameField.getText().trim().equals(""))
//            System.out.println("Text field is empty!");
//        else {
//            String username = usernameField.getText().trim();
//            String password = passwordField.getText().trim();
//            System.out.println("Username: " + username + " Password: " + password);
    }
}
