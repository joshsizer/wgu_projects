package controllers;

import datastructure.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentScreenController extends MyController {

    /**
     * Keeps track of the currently selected time filter start
     */
    private ZonedDateTime currentPeriodStart;

    /**
     * Keeps track of the currently selected time filter end
     */
    private ZonedDateTime currentPeriodEnd;

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
     * The Contact column for the Appointment
     * Table View.
     */
    @FXML
    private TableColumn<Appointment, String> contactTableColumn;

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
     * The All Radio Button for filtering
     * the displayed Appointments.
     */
    @FXML
    private RadioButton allRadioButton;

    /**
     * The Month Radio Button for filtering
     * the displayed Appointments.
     */
    @FXML
    private RadioButton monthRadioButton;

    /**
     * The Week Radio Button for filtering
     * the displayed Appointments.
     */
    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private Label currentPeriodLabel;

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
        updateCurrentPeriodLabelForAll();
        refreshAppointmentTable();
        currentPeriodStart = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault());
        currentPeriodEnd = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault());
    }

    public void refreshAppointmentTable() {
        ObservableList<Appointment> appsToDisplay = FXCollections.observableArrayList();
        try {
            if (allRadioButton.isSelected()) {
                appsToDisplay = Appointment.getAll();
            } else {
                appsToDisplay = Appointment.getBetween(currentPeriodStart, currentPeriodEnd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentTableView.setItems(appsToDisplay);
        appointmentTableView.refresh();
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

    /**
     * Called when the All radio button is pressed.
     */
    public void allRadioButtonListener() {
        currentPeriodStart = setTimeToStartOfDay(ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault()));
        currentPeriodEnd = setTimeToEndOfDay(ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault()));
        updateCurrentPeriodLabelForAll();
        refreshAppointmentTable();
    }

    /**
     * Called when the Month radio button is pressed.
     */
    public void monthRadioButtonListener() {
        currentPeriodStart = currentPeriodStart.withDayOfMonth(1);
        currentPeriodEnd = currentPeriodStart.withDayOfMonth(currentPeriodStart.toLocalDate().lengthOfMonth());

        currentPeriodStart = setTimeToStartOfDay(currentPeriodStart);
        currentPeriodEnd = setTimeToEndOfDay(currentPeriodEnd);

        updateCurrentPeriodLabelForMonth();
        refreshAppointmentTable();
    }

    /**
     * Called when the Week radio button is pressed.
     */
    public void weekRadioButtonListener() {
        currentPeriodEnd = currentPeriodStart;
        while (currentPeriodStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            currentPeriodStart = currentPeriodStart.minusDays(1);
        }

        while (currentPeriodEnd.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currentPeriodEnd = currentPeriodEnd.plusDays(1);
        }
        currentPeriodStart = setTimeToStartOfDay(currentPeriodStart);
        currentPeriodEnd = setTimeToEndOfDay(currentPeriodEnd);

        updateCurrentPeriodLabelForWeek();
        refreshAppointmentTable();
    }

    /**
     * Called when the user pressed the "<"
     * button to scroll to the last time period.
     */
    public void lastPeriodButtonListener() {
        if (allRadioButton.isSelected()) {
            return;
        } else if (monthRadioButton.isSelected()) {
            currentPeriodStart = currentPeriodStart.minusMonths(1);
            currentPeriodEnd = currentPeriodStart.withDayOfMonth(currentPeriodStart.toLocalDate().lengthOfMonth());
            updateCurrentPeriodLabelForMonth();
        } else {
            currentPeriodStart = currentPeriodStart.minusDays(7);
            currentPeriodEnd = currentPeriodEnd.minusDays(7);
            updateCurrentPeriodLabelForWeek();
        }

        currentPeriodStart = setTimeToStartOfDay(currentPeriodStart);
        currentPeriodEnd = setTimeToEndOfDay(currentPeriodEnd);
        refreshAppointmentTable();
    }

    /**
     * Called when the user presses the ">"
     * button to scroll to the next time period.
     */
    public void nextPeriodButtonListener() {
        if (allRadioButton.isSelected()) {
            return;
        } else if (monthRadioButton.isSelected()) {
            currentPeriodStart = currentPeriodStart.plusMonths(1);
            currentPeriodEnd = currentPeriodStart.withDayOfMonth(currentPeriodStart.toLocalDate().lengthOfMonth());
            updateCurrentPeriodLabelForMonth();
        } else {
            currentPeriodStart = currentPeriodStart.plusDays(7);
            currentPeriodEnd = currentPeriodEnd.plusDays(7);
            updateCurrentPeriodLabelForWeek();
        }

        currentPeriodStart = setTimeToStartOfDay(currentPeriodStart);
        currentPeriodEnd = setTimeToEndOfDay(currentPeriodEnd);
        refreshAppointmentTable();
    }

    /**
     * Changes the Period Label to display the time
     * range of the current week.
     */
    private void updateCurrentPeriodLabelForWeek() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd").withZone(ZoneId.systemDefault());
        String start = formatter.format(currentPeriodStart);
        String end = formatter.format(currentPeriodEnd);
        currentPeriodLabel.setText("Week: " + start + " to " + end);
    }

    /**
     * Changes the Period Label to display the name
     * of the current month.
     */
    private void updateCurrentPeriodLabelForMonth() {
        String currentMonth = currentPeriodStart.getMonth().name().toLowerCase();
        char c[] = currentMonth.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        currentMonth = new String(c);
        currentPeriodLabel.setText("Month: " + currentMonth);
    }

    /**
     * Changes the Period Label to display the
     * string "Viewing All Appointments"
     */
    private void updateCurrentPeriodLabelForAll() {
        currentPeriodLabel.setText("Viewing All Appointments");
    }

    private ZonedDateTime setTimeToStartOfDay(ZonedDateTime time) {
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private ZonedDateTime setTimeToEndOfDay(ZonedDateTime time) {
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }
}
