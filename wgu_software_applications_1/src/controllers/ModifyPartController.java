package controllers;

import datastructure.InHousePart;
import datastructure.Inventory;
import datastructure.OutsourcedPart;
import datastructure.Part;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyPartController {

    private Stage primaryStage;
    private Scene mainScene;
    private MainWindowController mainWindowController;
    private Part currentPart;

    @FXML
    private Label partFormLabel;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField partFormIDTextField;

    @FXML
    private TextField partFormNameTextField;

    @FXML
    private TextField partFormInvTextField;

    @FXML
    private TextField partFormPriceTextField;

    @FXML
    private TextField partFormMaxTextField;

    @FXML
    private TextField partFormMinTextField;

    @FXML
    private TextField partFormMachineIDTextField;

    @FXML
    private Label partFormMachineIDLabel;

    public void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }

    public void setMainScene(Scene scene) {
        mainScene = scene;
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public void initializeNewPartForm() {
        currentPart = null;
        partFormLabel.setText("Add part");
        inHouseRadioButton.setSelected(true);
        inHouseRadioButtonListener();
        partFormIDTextField.setText(Integer.toString(Inventory.getNextPartID()));
        partFormNameTextField.setText("");
        partFormInvTextField.setText("");
        partFormPriceTextField.setText("");
        partFormMaxTextField.setText("");
        partFormMinTextField.setText("");
        partFormMachineIDTextField.setText("");
    }

    public void initializeModifyPartForm(Part part) {
        currentPart = part;
        partFormLabel.setText("Modify part");
        if (part instanceof InHousePart) {
            inHouseRadioButton.setSelected(true);
            inHouseRadioButtonListener();
            int machineId = ((InHousePart)part).getMachineId();
            partFormMachineIDTextField.setText(Integer.toString(machineId));
        } else {
            outsourcedRadioButton.setSelected(true);
            outsourcedRadioButtonListener();
            String companyName = ((OutsourcedPart)part).getCompanyName();
            partFormMachineIDTextField.setText(companyName);
        }

        partFormIDTextField.setText(Integer.toString(part.getId()));
        partFormNameTextField.setText(part.getName());
        partFormInvTextField.setText(Integer.toString(part.getStock()));
        partFormPriceTextField.setText(Double.toString(part.getPrice()));
        partFormMaxTextField.setText(Integer.toString(part.getMax()));
        partFormMinTextField.setText(Integer.toString(part.getMin()));
    }

    @FXML
    public void partFormSaveButtonListener() {

    }

    @FXML
    public void partFormCancelButtonListener() {
        primaryStage.setScene(mainScene);
    }

    @FXML
    public void inHouseRadioButtonListener() {
        partFormMachineIDLabel.setText("Machine ID");
    }

    @FXML
    public void outsourcedRadioButtonListener() {
        partFormMachineIDLabel.setText("Company Name");
    }
}
