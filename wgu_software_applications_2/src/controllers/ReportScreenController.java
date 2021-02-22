package controllers;

import datastructure.Appointment;
import datastructure.Contact;
import datastructure.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the Reports Screen.
 *
 * 2 Lambda functions are used in this class.
 * They are in the functions:
 * 1. initialize()
 * 2. refreshAppointmentTable()
 */
public class ReportScreenController extends MyController {

    /**
     * The text area for report 1.
     */
    @FXML
    private TextArea report1TextArea;

    /**
     * The text area for report 3.
     */
    @FXML
    private TextArea report3TextArea;

    /**
     * The Combo Box for selecting contacts.
     */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /**
     * The TableView for displaying a list
     * of Appointments.
     */
    @FXML
    private TableView<Appointment> appointmentTableView;

    /**
     * The Appointment_ID column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdTableColumn;

    /**
     * The Title column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> titleTableColumn;

    /**
     * The Description column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> descriptionTableColumn;

    /**
     * The Location column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> locationTableColumn;

    /**
     * The Type column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> typeTableColumn;

    /**
     * The Start Date column for the Appointment Table View.
     */
    @FXML
    private TableColumn<Appointment, String> startDateTableColumn;

    /**
     * The End Date column for the Appointment Table View.
     */
    @FXML
    private TableColumn<Appointment, String> endDateTableColumn;

    /**
     * The Start Date Time for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> startTimeTableColumn;

    /**
     * The End Date Time for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> endTimeTableColumn;

    /**
     * The Customer_ID column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdTableColumn;

    /**
     * Called when this Controller is first loaded.
     *
     * A lambda statement is used here in order to allow
     * the contact combo box to display the correct text in
     * order to represent each contact object. This makes the code better
     * because we don't need to make an anonymous object that implements
     * the CallBack interface. Instead, we can directly implement the
     * function in line.
     */
    public void initialize() {
        initializeReport1();
        initializeReport3();
        appointmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateFormatted"));
        endDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("EndDateFormatted"));
        startTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("StartTimeFormatted"));
        endTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("EndTimeFormatted"));
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        Callback<ListView<Contact>, ListCell<Contact>> contactFactory = lv -> new ListCell<Contact>() {

            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getContactName() + " (ID: " + item.getContactId() + ")");
            }
        };

        contactComboBox.setCellFactory(contactFactory);
        contactComboBox.setButtonCell(contactFactory.call(null));
        try {
            contactComboBox.setItems(Contact.getAll());
            contactComboBox.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshAppointmentTable();
    }

    /**
     * Initializes and refreshes report 1.
     */
    private void initializeReport1() {
        HashMap<String, HashMap<String, Integer>> counts = new HashMap<>();


        try {
            for (Appointment app : Appointment.getAll()) {
                String monthName = app.getStartZonedDateTime().getMonth().name();
                String type = app.getType();

                HashMap<String, Integer> currentMonthHash = counts.get(monthName);
                if (currentMonthHash == null) {
                    currentMonthHash = new HashMap<>();
                    counts.put(monthName, currentMonthHash);
                }
                Integer currentMonthTypeCountInteger = currentMonthHash.get(type);
                if (currentMonthTypeCountInteger == null) {
                    currentMonthHash.put(type, Integer.valueOf(1));
                } else {
                    int currentMonthTypeCount = currentMonthHash.get(type).intValue();
                    currentMonthTypeCount++;
                    currentMonthHash.put(type, Integer.valueOf(currentMonthTypeCount));
                }
                counts.put(monthName, currentMonthHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        report1TextArea.setText("Month\t\t\t\tType\t\t\t\tCount\t\t\n");
        for (Map.Entry<String, HashMap<String, Integer>> months : counts.entrySet()) {
            for (Map.Entry<String, Integer> types : months.getValue().entrySet()) {
                if (types.getValue() != 0) {

                    report1TextArea.setText(report1TextArea.getText() + months.getKey() + "\t\t\t\t" + types.getKey() + "\t\t\t\t" + types.getValue() + "\n");
                }
            }
        }
    }

    /**
     * Initializes and refreshes report 3.
     */
    public void initializeReport3() {
        report3TextArea.setText("Customer_ID\t\t\t\tCount\t\t\n");
        try {
            ObservableList<Customer> customers = Customer.getAll();

            for (Customer customer : customers) {
                int count = Appointment.getByCustomerId(customer.getCustomerId()).size();
                report3TextArea.setText(report3TextArea.getText() + customer.getCustomerId() + "\t\t\t\t" + count + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the Appointment Table to display
     * Appointments for the currently selected Contact.
     *
     * A lambda is used here to generate a Comparator. The Comparator
     * is passed to Collections.sort in order to sort each appointment
     * by its start ZonedDateTime. The lambda is used here so we don't
     * need extra syntax, like the "new" statment or extra block levels (curly braces).
     */
    public void refreshAppointmentTable() {
        ObservableList<Appointment> appsToDisplay = FXCollections.observableArrayList();
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        try {
            appsToDisplay = Appointment.getByContactId(selectedContact.getContactId());

            Comparator<Appointment> appComp = (obj1, obj2)->obj1.getStartZonedDateTime().compareTo(obj2.getStartZonedDateTime());

            Collections.sort(appsToDisplay, appComp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentTableView.setItems(appsToDisplay);
        appointmentTableView.refresh();
    }

    /**
     * Refresh all the reports.
     */
    public void refresh() {
        initializeReport1();
        initializeReport3();
        refreshAppointmentTable();
    }
    /**
     * Called when the User selects an item in the
     * Contact combo box.
     */
    public void contactComboBoxListener() {
        refreshAppointmentTable();
    }

    /**
     * Called when the Back Button is pressed.
     * Redirects the User to the Home Screen.
     */
    public void backButtonListener() {
        setScene("home_screen");
    }
}
