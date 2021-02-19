package controllers;

import datastructure.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AppointmentScreenController extends MyController {

    /**
     * The TableView for displaying a list
     * of Appointments.
     */
    @FXML
    TableView<Appointment> appointmentTableView;

    /**
     * The Appointment_ID column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, Integer> appointmentIdTableColumn;

    /**
     * The Title column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> titleTableColumn;

    /**
     * The Description column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> descriptionTableColumn;

    /**
     * The Location column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> locationTableColumn;

    /**
     * The Contact column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> contactTableColumn;

    /**
     * The Type column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> typeTableColumn;

    /**
     * The Start Date column for the Appointment Table View.
     */
    @FXML
    TableColumn<Appointment, String> startDateTableColumn;

    /**
     * The End Date column for the Appointment Table View.
     */
    @FXML
    TableColumn<Appointment, String> endDateTableColumn;

    /**
     * The Start Date Time for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> startTimeTableColumn;

    /**
     * The End Date Time for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, String> endTimeTableColumn;

    /**
     * The Customer_ID column for the Appointment
     * Table View.
     */
    @FXML
    TableColumn<Appointment, Integer> customerIdTableColumn;

    /**
     * Called when this controller is first loaded.
     */
    public void initialize() {
        appointmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));;
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateFormatted"));
        endDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("EndDateFormatted"));
        startTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("StartTimeFormatted"));
        endTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("EndTimeFormatted"));
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        refreshAppointmentTable();
    }

    public void refreshAppointmentTable() {
        try {
            appointmentTableView.setItems(Appointment.getAll());
            appointmentTableView.refresh();
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
     * Redirects the user to the Appointment Form.
     */
    public void addButtonListener() {
        ((AppointmentFormController)getController("appointment_form")).initializeAddAppointmentForm();
        setScene("appointment_form");
    }

    /**
     * Called when the Modify button is pressed.
     * Redirects the user to the Appointment Form.
     */
    public void modifyButtonListener() {
        Appointment selected = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        ((AppointmentFormController)getController("appointment_form")).initializeModifyAppointmentForm(selected);
        setScene("appointment_form");
    }

    /**
     * Called when the Delete button is pressed.
     * If an Appointment is selected, then a dialogue box appears.
     */
    public void deleteButtonListener() {
        Appointment selected = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        String alertMessage = "Are you sure you want to delete this Appointment?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage, ButtonType.CANCEL, ButtonType.YES);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                selected.deleteFromDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            refreshAppointmentTable();
            alertMessage = "Appointment with ID " + selected.getAppointmentId() +
                    " of type " + selected.getType() + " deleted.";
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, alertMessage, ButtonType.OK);
            alert2.showAndWait();
        }
    }

}
