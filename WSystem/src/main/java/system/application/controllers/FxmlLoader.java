package system.application.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlLoader {
    private AnchorPane view;

    public AnchorPane getView(String FXMLName) {

        try {
            URL fileUrl = getClass().getResource("/fxml/" + FXMLName + ".fxml");

            if (fileUrl == null)
                throw new java.io.FileNotFoundException("FXML file can't be found");

            view = new FXMLLoader().load(fileUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
