package controllers;

import datastructure.InHousePart;
import datastructure.Inventory;
import datastructure.Part;
import datastructure.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController
{
    @FXML
    private Button modifyPartButton;

    @FXML
    private Button addPartButton;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIDTableColumn;

    @FXML
    private TableColumn<Part, String> partNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> partInvTableColumn;

    @FXML
    private TableColumn<Part, Double> partPriceTableColumn;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIDTableColumn;

    @FXML
    private TableColumn<Product, String> productNameTableColumn;

    @FXML
    private TableColumn<Product, Integer> productInvTableColumn;

    @FXML
    private TableColumn<Product, Double> productPriceTableColumn;

    private Stage primaryStage;
    private Scene modifyPartScene;
    private ModifyPartController modifyPartController;
    private Scene modifyProductScene;
    private ModifyProductController modifyProductController;

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public void setModifyPartScene(Scene scene) {
        modifyPartScene = scene;
    }

    public void setModifyProductScene(Scene scene) {
        modifyProductScene = scene;
    }

    public void setModifyPartController(ModifyPartController controller) {
        modifyPartController = controller;
    }

    public void setModifyProductController(ModifyProductController controller) {
        modifyProductController = controller;
    }

    // This optional method is called when the FXML file is loaded.
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

    @FXML
    public void modifyPartButtonListener() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        modifyPartController.initializeModifyPartForm(selectedPart);
        primaryStage.setScene(modifyPartScene);
    }

    @FXML
    public void addPartButtonListener() {
        modifyPartController.initializeNewPartForm();
        primaryStage.setScene(modifyPartScene);
    }

    @FXML
    public void deletePartButtonListener() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Inventory.deletePart(selectedPart);
            refreshPartTable();
        }
    }

    @FXML
    public void modifyProductButtonListener() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        modifyProductController.initializeModifyProductForm(selectedProduct);
        primaryStage.setScene(modifyProductScene);
    }

    @FXML
    public void addProductButtonListener() {
        modifyProductController.initializeNewProductForm();
        primaryStage.setScene(modifyProductScene);
    }

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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Inventory.deleteProduct(selectedProduct);
            refreshProductTable();
        }
    }

    @FXML
    public void exitButtonListener() {
        Platform.exit();
    }

    public void refreshPartTable() {
        partTableView.refresh();
    }

    public void refreshProductTable() {
        productTableView.refresh();
    }
}
