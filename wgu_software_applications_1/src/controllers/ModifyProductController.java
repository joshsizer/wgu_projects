package controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModifyProductController {

    private Stage primaryStage;
    private Scene mainScene;
    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }

    public void setMainScene(Scene scene) {
        mainScene = scene;
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
}
