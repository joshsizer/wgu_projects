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

/**
 * Handles interactions with the part form.
 *
 * @author Joshua Sizer
 */
public class ModifyPartController {

    /**
     * A reference to the application's primary stage.
     */
    private Stage primaryStage;

    /**
     * A reference to the application's main scene.
     */
    private Scene mainScene;

    /**
     * A reference to the main scene's controller
     * for communication purposes.
     */
    private MainWindowController mainWindowController;

    /**
     * The current part associated with a
     * modify action.
     */
    private Part currentPart;

    /**
     * The top text in the part form indicating
     * whether the current use of the form is in
     * modify or add mode.
     */
    @FXML
    private Label partFormLabel;

    /**
     * The radio button indicating the part is
     * made in-house.
     */
    @FXML
    private RadioButton inHouseRadioButton;

    /**
     * The radio button indicating the part
     * is outsourced.
     */
    @FXML
    private RadioButton outsourcedRadioButton;

    /**
     * The text field used to display the part's
     * ID.
     */
    @FXML
    private TextField partFormIDTextField;

    /**
     * The text field used to enter or
     * display a part's name.
     */
    @FXML
    private TextField partFormNameTextField;

    /**
     * The text field used to enter or
     * display a part's inventory level.
     */
    @FXML
    private TextField partFormInvTextField;

    /**
     * The text field used to enter or display
     * a part's price.
     */
    @FXML
    private TextField partFormPriceTextField;

    /**
     * The text field used to enter or display
     * a part's maximum allowable inventory
     * quantity.
     */
    @FXML
    private TextField partFormMaxTextField;

    /**
     * The text field used to enter or display
     * a part's minimum allowable inventory
     * quantity.
     */
    @FXML
    private TextField partFormMinTextField;

    /**
     * The text field used to enter or display
     * a part's Machine ID or Company Name.
     */
    @FXML
    private TextField partFormMachineIDTextField;

    /**
     * The label used to display the errors
     * found when adding or modifying a part.
     */
    @FXML
    private Label partFormErrorTextField;

    /**
     * The label that switched between
     * "Machine ID" and "Company Name,"
     * depending on the state of the inHouseRadioButton
     * and outsourcedRadioButton.
     */
    @FXML
    private Label partFormMachineIDLabel;

    /**
     * Sets this class's reference to the mainWindowController.
     * Used to refresh the main window's part and product TableViews.
     *
     * @param controller The application's main scene/window
     */
    public void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }

    /**
     * Sets this class's reference to the main scene.
     *
     * @param scene The application's main scene.
     */
    public void setMainScene(Scene scene) {
        mainScene = scene;
    }

    /**
     * Sets this class's reference to the primary stage.
     *
     * @param stage The application's primary stage.
     */
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Called from the mainWindowController when the
     * add part button is pressed. Sets all the input
     * text fields to empty and gives the new part a unique id.
     */
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

    /**
     * Called from the mainWindowController when the
     * modify part button is pressed. Sets all the input
     * text fields to be populated by the selected part's
     * values.
     *
     * @param part The part to modify.
     */
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

    /**
     * Called when the save button is pressed.
     * Will save the modified part or add a new part,
     * as long as no input has invalid values and return
     * to the main scene.
     * If there are invalid values, the partFormErrorTextField
     * will be set to display the errors and the application stays on
     * the part form.
     */
    @FXML
    public void partFormSaveButtonListener() {
        String errorMessage = "";
        Part newPart = null;
        int id = Inventory.getNextPartID();
        String name = partFormNameTextField.getText();

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
            inv = Integer.parseInt(partFormInvTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Inv must be an Integer!\n";
        }

        double price = 0;

        /*
         * Try to convert the price text field to a double.
         * If that fails, write the error to the error box.
         */
        try {
            price = Double.parseDouble(partFormPriceTextField.getText());
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
            max = Integer.parseInt(partFormMaxTextField.getText());
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
            min = Integer.parseInt(partFormMinTextField.getText());
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

        if (inHouseRadioButton.isSelected()) {
            int machineID = 0;

            /*
             * Try to convert the machine ID to an integer.
             * If that fails, write the error to the error box.
             */
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

            /*
             * Check to see if the company name field is blank.
             * If it is, write the error to the error box.
             */
            if (companyName.length() < 1) {
                errorMessage += "Company name cannot be blank!\n";
            }

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

    /**
     * Called when the cancel button is pressed.
     * Returns the application to the main scene,
     * discarding any changes to a part.
     */
    @FXML
    public void partFormCancelButtonListener() {
        primaryStage.setScene(mainScene);
    }

    /**
     * Called when the inHouseRadioButton is selected.
     * Sets the partFormMachineIDLabel to "Machine ID"
     */
    @FXML
    public void inHouseRadioButtonListener() {
        partFormMachineIDLabel.setText("Machine ID");
    }

    /**
     * Called when the outsourcedRadioButton is selected.
     * Sets the partFormMachineIDLabel to "Company Name"
     */
    @FXML
    public void outsourcedRadioButtonListener() {
        partFormMachineIDLabel.setText("Company Name");
    }
}
