package controllers;

import datastructure.InHousePart;
import datastructure.Inventory;
import datastructure.Part;
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

    }

    @FXML
    public void addProductButtonListener() {

    }

    @FXML
    public void deleteProductButtonListener() {

    }

    @FXML
    public void exitButtonListener() {
        Platform.exit();
    }

    public void refreshPartTable() {
        partTableView.refresh();
    }
}
