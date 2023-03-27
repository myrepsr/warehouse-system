package system.application.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoadingScreenController {
    @FXML
private Button exitButton = null;
    @FXML
private ProgressBar progressBar = new ProgressBar();

    public LoadingScreenController() {

    }

    public void updateProgress(double v){
        progressBar.setProgress(v);
        System.out.println("progress set to: " + progressBar.getProgress());
        }


    public void onClickEvent(MouseEvent mouseEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }

}
