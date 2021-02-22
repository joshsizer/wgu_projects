package controllers;

import datastructure.Appointment;
import datastructure.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;


/**
 * Holds the functions for operating the Customer Screen.
 */
public class CustomerScreenController extends MyController {

    /**
     * The TableView for displaying a list of
     * Customers.
     */
    @FXML
    TableView<Customer> customerTableView;

    /**
     * The Customer_ID column for the Customer
     * Table View.
     */
    @FXML
    TableColumn<Customer, Integer> customerIdTableColumn;

    /**
     * The Name column for the Customer Table
     * View.
     */
    @FXML
    TableColumn<Customer, String> nameTableColumn;

    /**
     * The Address column for the Customer Table
     * View.
     */
    @FXML
    TableColumn<Customer, String> addressTableColumn;

    /**
     * The First-Level-Division column for the
     * Customer Table View.
     */
    @FXML
    TableColumn<Customer, String> fldTableColumn;

    /**
     * The Country column for the Customer Table View.
     */
    @FXML
    TableColumn<Customer, String> countryTableColumn;

    /**
     * The Postal Code column for the Customer Table
     * View.
     */
    @FXML
    TableColumn<Customer, String> postalCodeTableColumn;

    /**
     * The Phone Number column for the Customer Table
     * View.
     */
    @FXML
    TableColumn<Customer, String> phoneNumberTableColumn;

    /**
     * Called when this controller is first loaded.
     */
    public void initialize() {
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        fldTableColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryTableColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        refreshCustomerTable();
    }

    public void refreshCustomerTable() {
        try {
            customerTableView.setItems(Customer.getAll());
            customerTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the Back button is pressed.
     * Redirects the user to the Home Screen.
     */
    public void backButtonListener() {
        setScene("home_screen");
    }

    /**
     * Called when the Add button is pressed.
     * Redirects the user to the Customer Form.
     */
    public void addButtonListener() {
        ((CustomerFormController)getController("customer_form")).initializeAddCustomerForm();
        setScene("customer_form");
    }

    /**
     * Called when the Modify button is pressed.
     * Redirects the user to the Customer Form.
     */
    public void modifyButtonListener() {
        Customer selected = customerTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        ((CustomerFormController)getController("customer_form")).initializeModifyCustomerForm(selected);
        setScene("customer_form");
    }

    /**
     * Called when the Delete button is pressed.
     * If a Customer is selected, then a diologue box appears.
     */
    public void deleteButtonListener() {
        Customer selected = customerTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        String alertMessage = "Are you sure you want to delete this Customer?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage, ButtonType.CANCEL, ButtonType.YES);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                for (Appointment app : Appointment.getByCustomerId(selected.getCustomerId())) {
                    app.deleteFromDb();
                }
                selected.deleteFromDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            refreshCustomerTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Customer successfully deleted.", ButtonType.OK);
            alert2.showAndWait();
        }
    }
}
