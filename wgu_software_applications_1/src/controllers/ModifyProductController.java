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

public class ModifyProductController {

    private Stage primaryStage;
    private Scene mainScene;
    private MainWindowController mainWindowController;

    Product currentProduct;

    @FXML
    private Label productFormLabel;

    @FXML
    private TextField productFormIDTextField;

    @FXML
    private TextField productFormNameTextField;

    @FXML
    private TextField productFormInvTextField;

    @FXML
    private TextField productFormPriceTextField;

    @FXML
    private TextField productFormMaxTextField;

    @FXML
    private TextField productFormMinTextField;

    @FXML
    private Label productFormErrorLabel;

    @FXML
    private TableView<Part> productFormPartTableView;

    @FXML
    private TableColumn<Part, Integer> productFormPartIDTableColumn;

    @FXML
    private TableColumn<Part, String> productFormPartNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> productFormPartInvTableColumn;

    @FXML
    private TableColumn<Part, Double> productFormPartPriceTableColumn;

    @FXML
    private TableView<Part> productFormAssociatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> productFormAssociatedPartIDTableColumn;

    @FXML
    private TableColumn<Part, String> productFormAssociatedPartNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> productFormAssociatedPartInvTableColumn;

    @FXML
    private TableColumn<Part, Double> productFormAssociatedPartPriceTableColumn;

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

    public void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }

    public void setMainScene(Scene scene) {
        mainScene = scene;
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

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

        ObservableList<Part> associatedPartsCopy = FXCollections.observableArrayList();
        productFormAssociatedPartTableView.setItems(associatedPartsCopy);
    }

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
    }

    @FXML
    private void productFormSaveButtonListener()  {
        String errorMessage = "";
        Product newProduct = null;
        int id = Inventory.getNextProductID();
        String name = productFormNameTextField.getText();

        if (name.length() < 1) {
            errorMessage += "Name cannot be blank!\n";
        }

        int inv = 0;

        try {
            inv = Integer.parseInt(productFormInvTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Inv must be an Integer!\n";
        }

        double price = 0;

        try {
            price = Double.parseDouble(productFormPriceTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Price/Cost must be a Double!\n";
        }

        int max = 0;
        boolean minMaxOkay = true;

        try {
            max = Integer.parseInt(productFormMaxTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Max must be an Integer!\n";
            minMaxOkay = false;
        }

        int min = 0;

        try {
            min = Integer.parseInt(productFormMinTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Min must be an Integer!\n";
            minMaxOkay = false;
        }

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

    @FXML
    private void productFormCancelButtonListener() {
        primaryStage.setScene(mainScene);
    }

    @FXML
    private void productFormAddAssociatedPartButtonListener() {
        Part selectedPart = productFormPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        productFormAssociatedPartTableView.getItems().add(selectedPart);
        productFormAssociatedPartTableView.refresh();
    }

    @FXML
    private void productFormRemoveAssociatedPartButtonListener() {
        Part selectedPart = productFormAssociatedPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        productFormAssociatedPartTableView.getItems().remove(selectedPart);
        productFormAssociatedPartTableView.refresh();
    }
}
