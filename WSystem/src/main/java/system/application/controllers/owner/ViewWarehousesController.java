package system.application.controllers.owner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import system.application.controllers.admin.UserController;
import system.backend.WSystem;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewWarehousesController implements Initializable {
    @FXML
    private AnchorPane pane = null;
    @FXML
    private GridPane grid = null;
    @FXML
    private Label warehouseID = null;
    @FXML
    private Label warehouseType = null;
    private WSystem wSystem;
    private Owner owner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();
        owner = ownerDataHolder.getOwner();
        wSystem = WSystem.getInstance();
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < owner.getWarehouses().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/userFXML.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                UserController userController = fxmlLoader.getController();
                userController.setWarehouse(owner.getWarehouses().get(i));

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
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
