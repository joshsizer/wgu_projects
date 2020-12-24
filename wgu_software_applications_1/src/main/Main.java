package main;

import controllers.MainWindowController;
import controllers.ModifyPartController;
import controllers.ModifyProductController;
import datastructure.InHousePart;
import datastructure.Inventory;
import datastructure.OutsourcedPart;
import datastructure.Part;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main_window.fxml"));

        FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../fxml/main_window.fxml"));
        Scene mainScene = new Scene(mainWindowLoader.load());
        MainWindowController mainWindowController = (MainWindowController) mainWindowLoader.getController();

        FXMLLoader modifyPartLoader = new FXMLLoader(getClass().getResource("../fxml/modify_part.fxml"));
        Scene modifyPartScene = new Scene(modifyPartLoader.load());
        ModifyPartController modifyPartController = (ModifyPartController) modifyPartLoader.getController();

        FXMLLoader modifyProductLoader = new FXMLLoader(getClass().getResource("../fxml/modify_product.fxml"));
        Scene modifyProductScene = new Scene(modifyProductLoader.load());
        ModifyProductController modifyProductController = (ModifyProductController) modifyProductLoader.getController();

        mainWindowController.setModifyPartScene(modifyPartScene);
        mainWindowController.setModifyPartController(modifyPartController);
        mainWindowController.setModifyProductScene(modifyProductScene);
        mainWindowController.setModifyProductController(modifyProductController);
        mainWindowController.setPrimaryStage(primaryStage);

        modifyPartController.setMainScene(mainScene);
        modifyPartController.setPrimaryStage(primaryStage);
        modifyPartController.setMainWindowController(mainWindowController);
        modifyProductController.setMainScene(mainScene);
        modifyProductController.setPrimaryStage(primaryStage);
        modifyProductController.setMainWindowController(mainWindowController);

        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Part myPart = new InHousePart(0, "Sprocket", 9.99, 19, 5, 30, 1);
        Part myPart2 = new InHousePart(1, "Handle Bar", 18.0, 8, 2, 10, 1);
        Part myPart3 = new OutsourcedPart(2, "Bell", 4.99, 33, 10, 100, "Bells R' Us");

        Inventory.addPart(myPart);
        Inventory.addPart(myPart2);
        Inventory.addPart(myPart3);
        for (Part part : Inventory.getAllParts()) {
            System.out.println(part.getName());
        }

        launch(args);
    }
}
