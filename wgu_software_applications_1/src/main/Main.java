package main;

import controllers.MainWindowController;
import controllers.ModifyPartController;
import controllers.ModifyProductController;
import datastructure.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for this application.
 * This houses the main method which launches the javafx application.
 *
 * One feature I'd add to this application if I continued working on it
 * would be to disallow the entering of any non-numeric characters in
 * the form fields for the product/part forms where Integers or Doubles
 * are required. Currently, you can enter non-numeric values into these
 * text fields, and an error is printed below the form fields. A better
 * approach would be to stop any non-numeric character from being entered
 * in the first place.
 *
 * Another feature I'd add would be to have the error messages
 * appear directly below the form field with the error, and have the
 * form field turn red, like you often see on websites.
 *
 * The error that I came across and corrected is in the Inventory class's
 * method updateProduct.
 *
 * @author Joshua Sizer
 */
public class Main extends Application {

    /**
     * Initializes each controller to have references to the other
     * controllers and scenes, as well as initializes the application
     * to start on the main scene.
     *
     * @param primaryStage The application's primary stage.
     * @throws Exception
     */
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

    /**
     * The entry point for the program. Creates a few dummy parts and products
     * and launches the javafx application.
     *
     * @param args
     */
    public static void main(String[] args) {
        Part myPart = new InHousePart(0, "Sprocket", 9.99, 19, 5, 30, 1);
        Part myPart2 = new InHousePart(1, "Handle Bar", 18.0, 8, 2, 10, 1);
        Part myPart3 = new OutsourcedPart(2, "Bell", 4.99, 33, 10, 100, "Bells R' Us");

        Inventory.addPart(myPart);
        Inventory.addPart(myPart2);
        Inventory.addPart(myPart3);

        Product myProduct = new Product(0, "Bicycle", 300.0, 20, 5, 40);
        myProduct.addAssociatedPart(myPart);
        myProduct.addAssociatedPart(myPart2);

        Inventory.addProduct(myProduct);

        launch(args);
    }
}
