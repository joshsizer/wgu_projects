package controllers;

import datastructure.Inventory;
import datastructure.Part;
import datastructure.Product;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Provides methods for handling button clicks and stores references
 * to UI elements for programmatic interaction.
 *
 * @author Joshua Sizer
 */
public class MainWindowController
{
    /**
     * The search text field for parts.
     */
    @FXML
    private TextField partSearchTextField;

    /**
     * The search text field for products.
     */
    @FXML
    private TextField productSearchTextField;

    /**
     * The TableView for parts. It displays all parts
     * on load and a filtered list of parts after entering
     * text into the partSearchTextField.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * The ID column in the partTableView.
     * Displays a part's ID.
     */
    @FXML
    private TableColumn<Part, Integer> partIDTableColumn;

    /**
     * The Name column in the partTableView.
     * Displays a part's name.
     */
    @FXML
    private TableColumn<Part, String> partNameTableColumn;

    /**
     * The Inventory column in the partTableView.
     * Displays a part's inventory level.
     */
    @FXML
    private TableColumn<Part, Integer> partInvTableColumn;

    /**
     * The Price column in the partTableView.
     * Displays a part's price.
     */
    @FXML
    private TableColumn<Part, Double> partPriceTableColumn;

    /**
     * The TableView for products. It displays all products
     * on load and a filtered list of products after entering
     * text into the productSearchTextField.
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * The ID column in the productTableView.
     * Displays a product's ID.
     */
    @FXML
    private TableColumn<Product, Integer> productIDTableColumn;

    /**
     * The Name column in the productTableView.
     * Display's a product's name.
     */
    @FXML
    private TableColumn<Product, String> productNameTableColumn;

    /**
     * The Inventory column in the productTableView.
     * Display's a product's inventory level.
     */
    @FXML
    private TableColumn<Product, Integer> productInvTableColumn;

    /**
     * The Price column in the productTableView.
     * Display's a product's price.
     */
    @FXML
    private TableColumn<Product, Double> productPriceTableColumn;

    /**
     * A reference to the application's primary stage.
     */
    private Stage primaryStage;

    /**
     * A reference to the part form scene.
     * Used to switch from the main scene
     * to the part form scene.
     */
    private Scene modifyPartScene;

    /**
     * A reference to the part form controller.
     * Used to communicate with the part
     * form scene to initialize the form.
     */
    private ModifyPartController modifyPartController;

    /**
     * A reference to the product scene.
     * Used to switch from the main scene
     * to the product form scene.
     */
    private Scene modifyProductScene;

    /**
     * A reference to the product form controller.
     * Used to communicate with the product form
     * scene to initialize the form.
     */
    private ModifyProductController modifyProductController;

    /**
     * Set this class's reference to the primary stage.
     *
     * @param stage The application's primary stage.
     */
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Set this class's reference to the part form scene.
     *
     * @param scene The application's part form scene.
     */
    public void setModifyPartScene(Scene scene) {
        modifyPartScene = scene;
    }

    /**
     * Set this class's reference to the product form scene.
     *
     * @param scene The application's product form scene.
     */
    public void setModifyProductScene(Scene scene) {
        modifyProductScene = scene;
    }

    /**
     * Set this class's reference to the part form's controller.
     *
     * @param controller The application's part form controller.
     */
    public void setModifyPartController(ModifyPartController controller) {
        modifyPartController = controller;
    }

    /**
     * Set this class's reference to the product form's controller.
     *
     * @param controller The application's product form controller.
     */
    public void setModifyProductController(ModifyProductController controller) {
        modifyProductController = controller;
    }

    /**
     * Called when the main scene is loaded.
     * This method binds the Inventory part and product
     * data to the part and product TableViews.
     */
    public void initialize() {
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());
        refreshPartTable();

        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.setItems(Inventory.getAllProducts());
        refreshProductTable();
    }

    /**
     * Opens the part form in "modify mode" when
     * the modify button under the partTableView is pressed.
     * Grabs the currently selected part from the
     * partTableView and passes it along to the
     * part form scene.
     */
    @FXML
    public void modifyPartButtonListener() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        modifyPartController.initializeModifyPartForm(selectedPart);
        primaryStage.setScene(modifyPartScene);
    }

    /**
     * Opens the part form in "add mode"
     * when the add button under the partTableView
     * is pressed.
     */
    @FXML
    public void addPartButtonListener() {
        modifyPartController.initializeNewPartForm();
        primaryStage.setScene(modifyPartScene);
    }

    /**
     * If a part is selected in the partTableView,
     * an alert dialog will appear asking the user
     * if they're sure they want to delete the selected part
     * when the delete button under the partTableView is
     * pressed.
     */
    @FXML
    public void deletePartButtonListener() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?", ButtonType.CANCEL, ButtonType.YES);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Inventory.deletePart(selectedPart);
            refreshPartTable();
        }
    }

    /**
     * Opens the product form in "modify mode" when
     * the modify button under the productTableView is pressed.
     * Grabs the currently selected product from the
     * productTableView and passes it along to the
     * product form scene.
     */
    @FXML
    public void modifyProductButtonListener() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        modifyProductController.initializeModifyProductForm(selectedProduct);
        primaryStage.setScene(modifyProductScene);
    }

    /**
     * Opens the product form in "add mode"
     * when the add button under the productTableView
     * is pressed.
     */
    @FXML
    public void addProductButtonListener() {
        modifyProductController.initializeNewProductForm();
        primaryStage.setScene(modifyProductScene);
    }

    /**
     * If a product is selected in the productTableView,
     * an alert dialog will appear asking the user
     * if they're sure they want to delete the selected product
     * when the delete button under the productTableView is
     * pressed. If the product contains associated parts,
     * a different alert is shown to the user indicating
     * that the product cannot be deleted because it contains
     * associated parts.
     */
    @FXML
    public void deleteProductButtonListener() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        } else if (selectedProduct.getAllAssociatedParts().size() > 0 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot remove product because it has associated parts.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", ButtonType.CANCEL, ButtonType.YES);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Inventory.deleteProduct(selectedProduct);
            refreshProductTable();
        }
    }

    /**
     * Terminates the application when the exit
     * button is pressed.
     */
    @FXML
    public void exitButtonListener() {
        Platform.exit();
    }

    /**
     * Filters the parts shown in the partTableView
     * based on the text in the partSearchTextField
     * when the enter button is pressed while focused
     * on the partSearchTextField.
     */
    @FXML
    public void partSearchOnEnter() {
        String text = partSearchTextField.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(text);

        partTableView.setItems(foundParts);
    }

    /**
     * Filters the products shown in the productTableView
     * based on the text in the productSearchTextField
     * when the enter button is pressed while focused
     * on the productSearchTextField.
     */
    @FXML
    public void productSearchOnEnter() {
        String text = productSearchTextField.getText();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(text);

        productTableView.setItems(foundProducts);
    }

    /**
     * Refreshes the data in the partTableView.
     */
    public void refreshPartTable() {
        partTableView.refresh();
    }

    /**
     * Refreshed the data in the productTableView.
     */
    public void refreshProductTable() {
        productTableView.refresh();
    }
}
