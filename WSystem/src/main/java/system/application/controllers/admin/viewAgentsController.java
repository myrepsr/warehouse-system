package system.application.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import system.application.controllers.FxmlLoader;
import system.backend.WSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class viewAgentsController implements Initializable {
    @FXML
    private AnchorPane pane = null;
    @FXML
    private Button backButton = null;
    @FXML
    private Label firstNameLabel = null;
    @FXML
    private Label lastNameLabel = null;
    @FXML
    private Label phoneNumberLabel = null;
    @FXML
    private GridPane grid = null;
    private WSystem wSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wSystem = WSystem.getInstance();
        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < wSystem.getAgents().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/userFXML.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                UserController userController = fxmlLoader.getController();
                userController.setAgent(wSystem.getAgents().get(i));

                if (column == 3){
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(10));
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        pane.getChildren().clear();
        pane.getChildren().add(view);
    }

    public void handleBackButton(ActionEvent event) {
        setLoader("profilesFXML");
    }


}
