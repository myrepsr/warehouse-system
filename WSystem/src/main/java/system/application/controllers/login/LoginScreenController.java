package system.application.controllers.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.application.controllers.FxmlLoader;
import system.backend.services.CryptoService;

import java.io.IOException;
import java.util.Base64;

public class LoginScreenController {
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Button exitButton = null;
    @FXML
    private Button administratorButton = null;
    @FXML
    private Button ownerButton = null;
    @FXML
    private Button agentButton = null;
    @FXML
    private AnchorPane loaderPane = null;

    public void onExitClick(MouseEvent mouseEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }
    public void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
    }
    public void handleButton1Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + administratorButton.getText());
        setLoader("administratorLoginFXML");
    }

    public void handleButton2Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + ownerButton.getText());
        setLoader("ownerLoginFXML");
    }

    public void handleButton3Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + agentButton.getText());
        setLoader("agentLoginFXML");
    }

    public void loadStage(String fxmlPath) {
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

        Scene scene = new Scene(loginScreenRoot);
        Stage loginScreenStage = new Stage();
        loginScreenStage.setScene(scene);
        loginScreenStage.initStyle(StageStyle.UNDECORATED);

        loginScreenStage.show();
    }
}
