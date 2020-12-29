package controllers;

import datastructure.Inventory;
import datastructure.Part;
import datastructure.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Handles interactions with the product form.
 *
 * @author Joshua Sizer
 */
public class ModifyProductController {

    /**
     * A reference to the application's primary stage.
     */
    private Stage primaryStage;

    /**
     * A reference to the application's main scene.
     */
    private Scene mainScene;

    /**
     * A reference to the application's main scene controller
     * used for refreshing the productTableView in the main
     * scene.
     */
    private MainWindowController mainWindowController;

    /**
     * The product being modified.
     */
    Product currentProduct;

    /**
     * The text at the top of the product form.
     * Changes between "Modify product" and
     * "Add product"
     */
    @FXML
    private Label productFormLabel;

    /**
     * The text field used for displaying the product ID
     */
    @FXML
    private TextField productFormIDTextField;

    /**
     * The text field used for entering and displaying
     * a product's name.
     */
    @FXML
    private TextField productFormNameTextField;

    /**
     * The text field used for entering and displaying
     * a product's inventory level.
     */
    @FXML
    private TextField productFormInvTextField;

    /**
     * The text field used for entering and displaying
     * a product's price.
     */
    @FXML
    private TextField productFormPriceTextField;

    /**
     * The text field used for entering and displaying
     * a product's maximum allowable inventory level.
     */
    @FXML
    private TextField productFormMaxTextField;

    /**
     * The text field used for entering and displaying
     * a product's minimum allowable inventory level.
     */
    @FXML
    private TextField productFormMinTextField;

    /**
     * The label used to show input errors.
     */
    @FXML
    private Label productFormErrorLabel;

    /**
     * The text field used for searching the part TableView
     * in the top right of the form.
     */
    @FXML
    private TextField partSearchTextField;

    /**
     * The TableView for showing available parts to
     * associate with the product.
     */
    @FXML
    private TableView<Part> productFormPartTableView;

    /**
     * The ID column for the productFormPartTableView.
     */
    @FXML
    private TableColumn<Part, Integer> productFormPartIDTableColumn;

    /**
     * The name column for the productFormPartTableView.
     */
    @FXML
    private TableColumn<Part, String> productFormPartNameTableColumn;

    /**
     * The inventory column for the productFormPartTableView.
     */
    @FXML
    private TableColumn<Part, Integer> productFormPartInvTableColumn;

    /**
     * The price column for the productFormPartTableView.
     */
    @FXML
    private TableColumn<Part, Double> productFormPartPriceTableColumn;

    /**
     * The table showing the product's associated parts.
     */
    @FXML
    private TableView<Part> productFormAssociatedPartTableView;

    /**
     * The ID column for the productFormAssociatedPartTableView
     */
    @FXML
    private TableColumn<Part, Integer> productFormAssociatedPartIDTableColumn;

    /**
     * The name column for the productFormAssociatedPartTableView
     */
    @FXML
    private TableColumn<Part, String> productFormAssociatedPartNameTableColumn;

    /**
     * The inventory column for the productFormAssociatedPartTableView
     */
    @FXML
    private TableColumn<Part, Integer> productFormAssociatedPartInvTableColumn;

    /**
     * The price column for the productFormAssociatedPartTableView
     */
    @FXML
    private TableColumn<Part, Double> productFormAssociatedPartPriceTableColumn;

    /**
     * Called when the product form scene is loaded.
     * Binds the data from the Inventory's list of parts
     * and the current product's associated parts into the
     * respective tables.
     */
    public void initialize() {
        productFormPartIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productFormPartNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productFormPartInvTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productFormPartPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productFormPartTableView.setItems(Inventory.getAllParts());

        productFormAssociatedPartIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productFormAssociatedPartNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productFormAssociatedPartInvTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productFormAssociatedPartPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Sets this class's reference to the mainWindowController.
     *
     * @param controller The application's mainWindowController.
     */
    public void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }

    /**
     * Sets this class's reference to the main scene.
     *
     * @param scene The application's main scene
     */
    public void setMainScene(Scene scene) {
        mainScene = scene;
    }

    /**
     * Sets this class's reference to the Application's primary stage
     *
     * @param stage The application's primary stage.
     */
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Clears all data from the product form's text fields
     * and sets the ID text field to the next available unique
     * ID field. Also clears the partSearchTextField and leaves
     * the part table unfiltered.
     */
    public void initializeNewProductForm() {
        currentProduct = null;
        productFormLabel.setText("Add product");
        productFormIDTextField.setText(Integer.toString(Inventory.getNextProductID()));
        productFormNameTextField.setText("");
        productFormInvTextField.setText("");
        productFormPriceTextField.setText("");
        productFormMaxTextField.setText("");
        productFormMinTextField.setText("");
        productFormErrorLabel.setText("");

        ObservableList<Part> emptyAssociatedParts = FXCollections.observableArrayList();
        productFormAssociatedPartTableView.setItems(emptyAssociatedParts);
        productFormPartTableView.refresh();

        partSearchTextField.setText("");
        partSearchOnEnter();
    }

    /**
     * Sets the product form's text fields to hold the
     * current product's data. Also populates the associated
     * parts table with the product's associated parts.
     *
     * @param product The product being modified.
     */
    public void initializeModifyProductForm(Product product) {
        currentProduct = product;
        productFormLabel.setText("Modify product");
        productFormIDTextField.setText(Integer.toString(product.getId()));
        productFormNameTextField.setText(product.getName());
        productFormInvTextField.setText(Integer.toString(product.getStock()));
        productFormPriceTextField.setText(Double.toString(product.getPrice()));
        productFormMaxTextField.setText(Integer.toString(product.getMax()));
        productFormMinTextField.setText(Integer.toString(product.getMin()));
        productFormErrorLabel.setText("");


        ObservableList<Part> associatedPartsCopy = FXCollections.observableArrayList();
        for (Part part : product.getAllAssociatedParts()) {
            associatedPartsCopy.add(part);
        }
        productFormAssociatedPartTableView.setItems(associatedPartsCopy);
        productFormAssociatedPartTableView.refresh();
        productFormPartTableView.refresh();
        partSearchTextField.setText("");
        partSearchOnEnter();
    }

    /**
     * Called when the save button is pressed.
     * Will save the modified product or add a new product,
     * as long as no input has invalid values; then return
     * to the main scene.
     * If there are invalid values, the productFormErrorLabel
     * will be set to display the errors and the application stays on
     * the product form.
     */
    @FXML
    private void productFormSaveButtonListener()  {
        String errorMessage = "";
        Product newProduct = null;
        int id = Inventory.getNextProductID();
        String name = productFormNameTextField.getText();

        // if the name length is 0, then write the error to
        // the error box.
        if (name.length() < 1) {
            errorMessage += "Name cannot be blank!\n";
        }

        int inv = 0;

        /*
         * Try to convert the inventory level text field
         * to an integer.
         * If that fails, write the error to the error box.
         */
        try {
            inv = Integer.parseInt(productFormInvTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Inv must be an Integer!\n";
        }

        double price = 0;

        /*
         * Try to convert the price text field to a double.
         * If that fails, write the error to the error box.
         */
        try {
            price = Double.parseDouble(productFormPriceTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Price/Cost must be a Double!\n";
        }

        int max = 0;
        boolean minMaxOkay = true;

        /*
         * Try to convert the max text field to an integer.
         * If that fails, write the error to the error box.
         */
        try {
            max = Integer.parseInt(productFormMaxTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Max must be an Integer!\n";
            minMaxOkay = false;
        }

        int min = 0;

        /*
         * Try to convert the min text field to an integer.
         * If that fails, write the error to the error box.
         */
        try {
            min = Integer.parseInt(productFormMinTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Min must be an Integer!\n";
            minMaxOkay = false;
        }

        /*
         * Check that the min is less than max only if
         * the min and max were successfully captured.
         * If that fails, write the error to the error box.
         * Do the same for checking that the entered
         * inventory level is between min and max.
         */
        if (minMaxOkay && min > max) {
            errorMessage += "Min must be less than max!\n";
        } else if (minMaxOkay && (inv < min || inv > max)) {
            errorMessage += "Inv must be between min and max!\n";
        }

        if (errorMessage.equals("")) {
            newProduct = new Product(id, name, price, inv, min, max);
            for (Part part : productFormAssociatedPartTableView.getItems()) {
                newProduct.addAssociatedPart(part);
            }
        }

        if (newProduct == null) {
            productFormErrorLabel.setText(errorMessage);
            return;
        } else if (currentProduct == null) {
            Inventory.addProduct(newProduct);
        } else {
            newProduct.setId(currentProduct.getId());
            Inventory.updateProduct(currentProduct.getId(), newProduct);
        }

        mainWindowController.refreshProductTable();
        primaryStage.setScene(mainScene);
    }

    /**
     * Called when the cancel button is pressed.
     * Discards any changes to the product and returns to the
     * main scene.
     */
    @FXML
    private void productFormCancelButtonListener() {
        primaryStage.setScene(mainScene);
    }

    /**
     * Called when the add associated part button is pressed.
     * If no part is selected, the function returns without
     * performing any actions. If a part is selected,
     * it is added to the associated part table.
     */
    @FXML
    private void productFormAddAssociatedPartButtonListener() {
        Part selectedPart = productFormPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        productFormAssociatedPartTableView.getItems().add(selectedPart);
        productFormAssociatedPartTableView.refresh();
    }

    /**
     * Called when the remove associated part button is pressed.
     * If no part is selected, the function returns without
     * performing any actions. If a part is selected, it is
     * removed from the associated part table.
     */
    @FXML
    private void productFormRemoveAssociatedPartButtonListener() {
        Part selectedPart = productFormAssociatedPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        productFormAssociatedPartTableView.getItems().remove(selectedPart);
        productFormAssociatedPartTableView.refresh();
    }

    /**
     * Filters the parts shown in the part table
     * based on the text in the partSearchTextField
     * when the enter button is pressed while focused
     * on the partSearchTextField.
     */
    @FXML
    public void partSearchOnEnter() {
        String text = partSearchTextField.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(text);

        productFormPartTableView.setItems(foundParts);
    }
}
