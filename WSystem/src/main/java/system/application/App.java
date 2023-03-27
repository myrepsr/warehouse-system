package system.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.application.controllers.LoadingScreenController;
import system.backend.Configuration;

public class App extends Application
{
    @Override
    public void start(Stage loadingStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loadingScreenFXML.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/loginScreenFXML.fxml"));
        Parent loadingScreenRoot = (Parent) loader.load();
        Parent loginScreenRoot = (Parent) loader2.load();

        LoadingScreenController loadingScreenController = new LoadingScreenController();
        Scene loadingScreenScene = new Scene(loadingScreenRoot);
        Scene loginScreenScene = new Scene(loginScreenRoot);
        Stage loginStage = new Stage();

        loginStage.setScene(loginScreenScene);
        loadingStage.setScene(loadingScreenScene);
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loginStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.show();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final double progress = i * 0.1;
                loadingScreenController.updateProgress(progress);

            }
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    loadingStage.close();
                    Platform.setImplicitExit(false);
                    loginStage.show();
                }
            });
        }).start();
    }

    public static void main(String[] args) {
        // Configuring the application
        Configuration config = Configuration.getInstance();
        config.configure();
        // Launching the application
        launch(args);
    }
}
