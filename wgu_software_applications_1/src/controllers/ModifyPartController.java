package controllers;

import datastructure.InHousePart;
import datastructure.Inventory;
import datastructure.OutsourcedPart;
import datastructure.Part;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private Label partFormErrorTextField;

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
        partFormErrorTextField.setText("");
    }

    public void initializeModifyPartForm(Part part) {
        currentPart = part;
        partFormLabel.setText("Modify part");
        partFormErrorTextField.setText("");
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
        String errorMessage = "";
        Part newPart = null;
        int id = Inventory.getNextPartID();
        String name = partFormNameTextField.getText();

        if (name.length() < 1) {
            errorMessage += "Name cannot be blank!\n";
        }

        int inv = 0;

        try {
            inv = Integer.parseInt(partFormInvTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Inv must be an Integer!\n";
        }

        double price = 0;

        try {
            price = Double.parseDouble(partFormPriceTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Price/Cost must be a Double!\n";
        }

        int max = 0;
        boolean minMaxOkay = true;

        try {
            max = Integer.parseInt(partFormMaxTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Max must be an Integer!\n";
            minMaxOkay = false;
        }

        int min = 0;

        try {
            min = Integer.parseInt(partFormMinTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Min must be an Integer!\n";
            minMaxOkay = false;
        }

        if (minMaxOkay && min > max) {
            errorMessage += "Min must be less than max!\n";
        } else if (minMaxOkay && (inv < min || inv > max)) {
            errorMessage += "Inv must be between min and max!\n";
        }

        if (inHouseRadioButton.isSelected()) {
            int machineID = 0;

            try {
                machineID = Integer.parseInt(partFormMachineIDTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Machine ID must be an Integer!\n";
            }

            if (errorMessage.equals("")) {
                newPart = new InHousePart(id, name, price, inv, min, max, machineID);
            }
        } else {
            String companyName = partFormMachineIDTextField.getText();

            if (errorMessage.equals("")) {
                newPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
            }
        }

        if (newPart == null) {
            partFormErrorTextField.setText(errorMessage);
            return;
        } else if (currentPart == null) {
            Inventory.addPart(newPart);
        } else {
            newPart.setId(currentPart.getId());
            Inventory.updatePart(currentPart.getId(), newPart);
        }

        mainWindowController.refreshPartTable();
        primaryStage.setScene(mainScene);
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
