package controllers;

import datastructure.Contact;
import datastructure.Customer;
import datastructure.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AppointmentFormController extends MyController {
    /**
     * Private member to keep track of the last selected
     * start time.
     */
    private ZonedDateTime lastStartDateTime;

    /**
     * Private member to keep track of the last selected
     * end time.
     */
    private ZonedDateTime lastEndDateTime;

    /**
     * The Appointment_ID TextField for displaying
     * the Appointment's ID.
     */
    @FXML
    private TextField appointmentIdTextField;

    /**
     * The Title TextField for displaying and entering
     * this Appointment's Title.
     */
    @FXML
    private TextField titleTextField;

    /**
     * The Description TextArea for displaying and
     * entering this Appointment's Description.
     */
    @FXML
    private TextArea descriptionTextArea;

    /**
     * The Contact ComboBox for displaying and
     * selecting this Appointment's Contact.
     */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /**
     * The Type ComboBox for displaying and selecting
     * this Appointment's Type.
     */
    @FXML
    private ComboBox<String> typeComboBox;

    /**
     * The Location TextField for displaying and
     * entering this Appointment's Location.
     */
    @FXML
    private TextField locationTextField;

    /**
     * The Date Picker for displaying and
     * entering this Appointment's Start Date.
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * The Date Picker for displaying and
     * entering this Appointment's End Date.
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * The Start Time ComboBox for displaying
     * and selecting Appointment start time.
     */
    @FXML
    private ComboBox<String> startTimeComboBox;

    /**
     * The End Time ComboBox for displaying
     * and selecting Appointment end time.
     */
    @FXML
    private ComboBox<String> endTimeComboBox;

    /**
     * The Customer ComboBox for displaying
     * and selecting this Appointment's Customer.
     */
    @FXML
    private ComboBox<Customer> customerComboBox;

    /**
     * The User ComboBox for displaying
     * and selecting this Appointment's User.
     */
    @FXML
    private ComboBox<User> userComboBox;

    /**
     * Called when this Controller is first loaded.
     */
    public void initialize() {
        appointmentIdTextField.setText("");
        titleTextField.setText("");
        descriptionTextArea.setText("");
        locationTextField.setText("");

        Callback<ListView<Contact>, ListCell<Contact>> contactFactory = lv -> new ListCell<Contact>() {

            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getContactName());
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

        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Planning Session");
        types.add("De-Briefing");
        types.add("Team Meeting");
        types.add("Stakeholder Update");
        types.add("Change Control");
        typeComboBox.setItems(types);
        typeComboBox.getSelectionModel().select(0);

        Callback<ListView<Customer>, ListCell<Customer>> customerFactory = lv -> new ListCell<Customer>() {

            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getCustomerName());
            }
        };

        customerComboBox.setCellFactory(customerFactory);
        customerComboBox.setButtonCell(customerFactory.call(null));
        try {
            customerComboBox.setItems(Customer.getAll());
            customerComboBox.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Callback<ListView<User>, ListCell<User>> userFactory = lv -> new ListCell<User>() {

            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getUserName());
            }
        };

        userComboBox.setCellFactory(userFactory);
        userComboBox.setButtonCell(userFactory.call(null));
        try {
            userComboBox.setItems(User.getAll());
            userComboBox.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        initializeTimeComboBoxes();
    }

    /**
     * Called when the Add Appointment button
     * is pressed. Blanks the Appointment Form.
     */
    public void initializeAddAppointmentForm() {

    }

    /**
     * Called when the Modify Appointment button
     * is pressed. Fills the Appointment Form with
     * the respective Appointment's details.
     */
    public void initializeModifyCustomerForm() {

    }

    public void initializeTimeComboBoxes() {
        int leastHour = 8;
        int greatestHour = 22;

        ObservableList<String> availableAppStartTimes = FXCollections.observableArrayList();
        ObservableList<String> availableAppEndTimes = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

        ZonedDateTime currentAppTime = startDatePicker.getValue().atTime(leastHour, 0).atZone(ZoneId.of("America/New_York"));
        while (currentAppTime.getHour() <= greatestHour - 1) {
            availableAppStartTimes.add(formatter.format(currentAppTime));
            currentAppTime = currentAppTime.plusMinutes(30);
        }

        startTimeComboBox.setItems(availableAppStartTimes);
        startTimeComboBox.getSelectionModel().select(0);

        currentAppTime = endDatePicker.getValue().atTime(leastHour, 30).atZone(ZoneId.of("America/New_York"));
        while (currentAppTime.getHour() < greatestHour) {
            availableAppEndTimes.add(formatter.format(currentAppTime));
            currentAppTime = currentAppTime.plusMinutes(30);
        }

        availableAppEndTimes.add(formatter.format(currentAppTime));

        endTimeComboBox.setItems(availableAppEndTimes);
        endTimeComboBox.getSelectionModel().select(0);

        startTimeListener();
    }

    /**
     * Called when the user selects a start date.
     */
    public void startDatePickerListener() {
        // checks to see if the end date picker is ahead or behind
        if (startDatePicker.getValue().compareTo(endDatePicker.getValue()) > 0) {
            endDatePicker.setValue(startDatePicker.getValue());
        }
        startTimeListener();
    }

    /**
     * Called when the user selects an end date.
     */
    public void endDatePickerListener() {
        if (startDatePicker.getValue().compareTo(endDatePicker.getValue()) > 0) {
            endDatePicker.setValue(startDatePicker.getValue());
        }
        startTimeListener();
    }

    /**
     * Called when the user selects a start time.
     */
    public void startTimeListener() {
        int startTimeIndex = startTimeComboBox.getSelectionModel().getSelectedIndex();
        int endTimeIndex = endTimeComboBox.getSelectionModel().getSelectedIndex();

        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue());

        if (startTimeIndex > endTimeIndex) {
            endTime = startTime.plusMinutes(30);
        }

        if (endTime.compareTo(startTime) < 0 && startDatePicker.getValue().equals(endDatePicker.getValue())) {
            endDatePicker.setValue(endDatePicker.getValue().plusDays(1));
        }

        endTimeComboBox.getSelectionModel().select(endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * Called when the user selects an end time.
     */
    public void endTimeListener() {
        int startTimeIndex = startTimeComboBox.getSelectionModel().getSelectedIndex();
        int endTimeIndex = endTimeComboBox.getSelectionModel().getSelectedIndex();

        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue());

        if (startTimeIndex > endTimeIndex) {
            endTime = startTime.plusMinutes(30);
        }

        endTimeComboBox.getSelectionModel().select(endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        startTimeListener();
    }

    /**
     * Called when the Cancel button is pressed.
     * Redirects the user to the Appointment Screen.
     */
    public void cancelButtonListener() {
        setScene("appointment_screen");
    }

    /**
     * Called when the Save button is pressed.
     * Saves the Appointment if there are no errors and
     * redirects the user to the Appointment Screen.
     * If there are errors, they are displayed and no
     * screen change occurs.
     */
    public void saveButtonListener() {
        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        ZonedDateTime startDateTime = startDate.atTime(startTime).atZone(ZoneId.systemDefault());
        ZonedDateTime endDateTime = endDate.atTime(endTime).atZone(ZoneId.systemDefault());

        System.out.println(startDateTime.withZoneSameInstant(ZoneId.of("America/New_York")));
        System.out.println(endDateTime.withZoneSameInstant(ZoneId.of("America/New_York")));
        System.out.println("Difference: " + ChronoUnit.HOURS.between(startDateTime, endDateTime));
    }
}
