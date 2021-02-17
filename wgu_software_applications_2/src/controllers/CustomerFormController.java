package controllers;

import datastructure.Country;
import datastructure.Customer;
import datastructure.FirstLevelDivision;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CustomerFormController extends MyController {

    /**
     * The current Customer in the case that a Customer is
     * being modified. Null if a new customer is being input.
     */
    Customer currentCustomer;

    /**
     * The disabled TextField for displaying the Customer_ID.
     */
    @FXML
    private TextField customerIdTextField;

    /**
     * The TextField for displaying and entering the
     * Customer Name.
     */
    @FXML
    private TextField nameTextField;

    /**
     * The TextField for displaying and entering the
     * Customer's Address.
     */
    @FXML
    private TextField addressTextField;

    /**
     * The ComboBox for displaying and entering the
     * Customer's Country.
     */
    @FXML
    private ComboBox<Country> countryComboBox;

    /**
     * The ComboBox for displaying and entering the
     * Customer's First Level Division
     */
    @FXML
    private ComboBox<FirstLevelDivision> fldComboBox;

    /**
     * The TextField for displaying and entering the
     * Customer's Postal Code.
     */
    @FXML
    private TextField postalCodeTextField;

    /**
     * The TextField for displaying and entering the
     * Customer's Phone Number.
     */
    @FXML
    private TextField phoneNumberTextField;

    /**
     * The Label for displaying any error messages
     * to the User.
     */
    @FXML
    private Label errorMessageLabel;

    /**
     * Called when this Controller is first loaded.
     */
    public void initialize() {
        Callback<ListView<Country>, ListCell<Country>> countryFactory = lv -> new ListCell<Country>() {

            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getCountryName());
            }
        };

        countryComboBox.setCellFactory(countryFactory);
        countryComboBox.setButtonCell(countryFactory.call(null));
        try {
            countryComboBox.setItems(Country.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Callback<ListView<FirstLevelDivision>, ListCell<FirstLevelDivision>> fldFactory = lv -> new ListCell<FirstLevelDivision>() {

            @Override
            protected void updateItem(FirstLevelDivision item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDivisionName());
            }
        };

        fldComboBox.setCellFactory(fldFactory);
        fldComboBox.setButtonCell(fldFactory.call(null));
    }

    /**
     * Called when the Add Customer button is
     * pressed. Blanks out the Customer Form.
     */
    public void initializeAddCustomerForm() {
        currentCustomer = null;
        try {
            customerIdTextField.setText(Integer.toString(Customer.getNextId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        nameTextField.setText("");
        addressTextField.setText("");
        postalCodeTextField.setText("");
        phoneNumberTextField.setText("");
        errorMessageLabel.setText("");

        countryComboBox.getSelectionModel().select(0);
        countryComboBoxListener();
    }

    /**
     * Called when the Modify Customer button is
     * pressed. Fills in the Customer Form with
     * the Customer's respective information.
     */
    public void initializeModifyCustomerForm(Customer customer) {
        currentCustomer = customer;
        customerIdTextField.setText(Integer.toString(customer.getCustomerId()));
        nameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneNumberTextField.setText(customer.getPhone());
        errorMessageLabel.setText("");

        try {
            FirstLevelDivision fld = FirstLevelDivision.getById(customer.getDivisionId());
            countryComboBox.getSelectionModel().select(Country.getById(fld.getCountryId()));
            countryComboBoxListener();
            fldComboBox.getSelectionModel().select(fld);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the Save Button is pressed.
     * Either adds a new Customer or saves a modified
     * customer.
     */
    public void saveButtonListener() {
        String errorMessage = "";
        int customerId = Integer.parseInt(customerIdTextField.getText());
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        Country country = countryComboBox.getSelectionModel().getSelectedItem();
        FirstLevelDivision fld = fldComboBox.getSelectionModel().getSelectedItem();
        String postalCode = postalCodeTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        ZonedDateTime lastUpdateDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        String lastUpdatedBy = getApplicationContext().getCurrentUser().getUserName();

        if (name.length() < 1) {
            errorMessage += "Name cannot be blank!\n";
        }
        if (address.length() < 1) {
            errorMessage += "Address cannot be blank!\n";
        }
        if (postalCode.length() < 1) {
            errorMessage += "Postal Code cannot be blank!\n";
        }
        if (phoneNumber.length() < 1) {
            errorMessage += "Phone Number cannot be blank!\n";
        }

        errorMessageLabel.setText(errorMessage);
        if (!"".equals(errorMessage)) {
            return;
        }

        if (currentCustomer != null) {
            currentCustomer.setCustomerId(customerId);
            currentCustomer.setCustomerName(name);
            currentCustomer.setAddress(address);
            currentCustomer.setPostalCode(postalCode);
            currentCustomer.setDivisionId(fld.getDivisionId());
            currentCustomer.setPhone(phoneNumber);
            currentCustomer.setLastUpdateDate(lastUpdateDate);
            currentCustomer.setLastUpdateBy(lastUpdatedBy);
            try {
                currentCustomer.saveToDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            ZonedDateTime createdDate = lastUpdateDate;
            String createdBy = lastUpdatedBy;
            Customer newCustomer = new Customer(customerId, name, address, postalCode, phoneNumber, fld.getDivisionId(),createdDate, createdBy, lastUpdateDate, lastUpdatedBy);
            try {
                newCustomer.saveToDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ((CustomerScreenController)getController("customer_screen")).refreshCustomerTable();
        setScene("customer_screen");
    }

    /**
     * Called when an item is selected from the
     * Country Combo Box.
     */
    public void countryComboBoxListener() {
        Country selected = countryComboBox.getSelectionModel().getSelectedItem();
        try {
            fldComboBox.setItems(FirstLevelDivision.getByCountryId(selected.getCountryId()));
            fldComboBox.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the Cancel button is pressed.
     * Redirects the user to the Customer Screen.
     */
    public void cancelButtonListener() {
        setScene("customer_screen");
    }
}
