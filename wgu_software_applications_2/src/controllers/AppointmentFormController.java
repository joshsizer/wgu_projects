package controllers;

import datastructure.Appointment;
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
     * The current appointment being modified, or
     * null if a new appointment is being added.
     */
    Appointment currentAppointment;

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
     * The label that error messages are written to.
     */
    @FXML
    private Label errorMessageLabel;

    /**
     * Called when this Controller is first loaded.
     */
    public void initialize() {
        appointmentIdTextField.setText("");
        titleTextField.setText("");
        descriptionTextArea.setText("");
        locationTextField.setText("");
        errorMessageLabel.setText("");

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
                setText(empty ? "" : item.getCustomerName() + " (ID: " + item.getCustomerId() + ")");
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
                setText(empty ? "" : item.getUserName() + " (ID: " + item.getUserId() + ")");
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
        currentAppointment = null;
        try {
            appointmentIdTextField.setText(Integer.toString(Appointment.getNextId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        titleTextField.setText("");
        descriptionTextArea.setText("");
        contactComboBox.getSelectionModel().select(0);
        typeComboBox.getSelectionModel().select(0);
        locationTextField.setText("");
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        initializeTimeComboBoxes();
        startTimeComboBox.getSelectionModel().select(0);
        endTimeComboBox.getSelectionModel().select(0);
        customerComboBox.getSelectionModel().select(0);
        userComboBox.getSelectionModel().select(0);
    }

    /**
     * Called when the Modify Appointment button
     * is pressed. Fills the Appointment Form with
     * the respective Appointment's details.
     */
    public void initializeModifyAppointmentForm(Appointment app) {
        currentAppointment = app;
        errorMessageLabel.setText("");
        appointmentIdTextField.setText(Integer.toString(currentAppointment.getAppointmentId()));
        titleTextField.setText(currentAppointment.getTitle());
        descriptionTextArea.setText(currentAppointment.getDescription());
        typeComboBox.getSelectionModel().select(currentAppointment.getType());
        locationTextField.setText(currentAppointment.getLocation());
        try {
            contactComboBox.getSelectionModel().select(Contact.getById(currentAppointment.getContactId()));
            customerComboBox.getSelectionModel().select(Customer.getById(currentAppointment.getCustomerId()));
            userComboBox.getSelectionModel().select(User.getById(currentAppointment.getUserId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        startDatePicker.setValue(LocalDate.parse(currentAppointment.getStartDateFormatted()));
        endDatePicker.setValue(LocalDate.parse(currentAppointment.getEndDateFormatted()));
        initializeTimeComboBoxes();
        startTimeComboBox.getSelectionModel().select(currentAppointment.getStartTimeFormatted());
        endTimeComboBox.getSelectionModel().select(currentAppointment.getEndTimeFormatted());
    }

    public void initializeTimeComboBoxes() {
        String lastSelectedStartTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        String lastSelectedEndTime = endTimeComboBox.getSelectionModel().getSelectedItem();
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

        currentAppTime = endDatePicker.getValue().atTime(leastHour, 30).atZone(ZoneId.of("America/New_York"));
        while (currentAppTime.getHour() < greatestHour) {
            availableAppEndTimes.add(formatter.format(currentAppTime));
            currentAppTime = currentAppTime.plusMinutes(30);
        }

        availableAppEndTimes.add(formatter.format(currentAppTime));

        endTimeComboBox.setItems(availableAppEndTimes);

        if (lastSelectedStartTime == null) {
            startTimeComboBox.getSelectionModel().select(0);
            endTimeComboBox.getSelectionModel().select(0);
        } else {
            boolean adjustStart = true;
            boolean adjustEnd = true;
            for (String startTime : startTimeComboBox.getItems()) {
                if (startTime.equals(lastSelectedStartTime)) {
                    adjustStart = false;
                    break;
                }
            }
            for (String endTime : endTimeComboBox.getItems()) {
                if (endTime.equals(lastSelectedEndTime)) {
                    adjustEnd = false;
                    break;
                }
            }

            if (adjustStart ) {
                startTimeComboBox.getSelectionModel().select(0);
            }
            if (adjustEnd) {
                endTimeComboBox.getSelectionModel().select(0);
            }
        }

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
        initializeTimeComboBoxes();
        startTimeListener();
    }

    /**
     * Called when the user selects an end date.
     */
    public void endDatePickerListener() {
        if (startDatePicker.getValue().compareTo(endDatePicker.getValue()) > 0) {
            endDatePicker.setValue(startDatePicker.getValue());
        }
        initializeTimeComboBoxes();
        startTimeListener();
    }

    /**
     * Called when the user selects a start time.
     */
    public void startTimeListener() {
        int startTimeIndex = startTimeComboBox.getSelectionModel().getSelectedIndex();
        int endTimeIndex = endTimeComboBox.getSelectionModel().getSelectedIndex();
        //System.out.println(startTimeComboBox.getValue());
        if (startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {
            return;
        }

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

        if (startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {
            return;
        }

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
        String errorMessage = "";
        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        ZonedDateTime startDateTime = startDate.atTime(startTime).atZone(ZoneId.systemDefault());
        ZonedDateTime endDateTime = endDate.atTime(endTime).atZone(ZoneId.systemDefault());

        if (ChronoUnit.HOURS.between(startDateTime, endDateTime) > 14) {
            errorMessage += "Error: An appointment must occur between 8am and 10pm EST!\n";
        }

        int id = Integer.parseInt(appointmentIdTextField.getText());

        String title = titleTextField.getText();

        if (title.length() < 1) {
            errorMessage += "Title cannot be blank!\n";
        }

        String description = descriptionTextArea.getText();

        if (description.length() < 1) {
            errorMessage += "Description cannot be blank!\n";
        }

        String location = locationTextField.getText();

        if (location.length() < 1) {
            errorMessage += "Location cannot be blank!\n";
        }

        Contact contact = contactComboBox.getSelectionModel().getSelectedItem();

        String type = typeComboBox.getSelectionModel().getSelectedItem();

        Customer customer = customerComboBox.getSelectionModel().getSelectedItem();

        User user = userComboBox.getSelectionModel().getSelectedItem();

        ZonedDateTime lastUpdateDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));

        String lastUpdatedBy = getApplicationContext().getCurrentUser().getUserName();

        ObservableList<Appointment> apps = FXCollections.observableArrayList();
        try {
            apps = Appointment.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Appointment app : apps) {
            if (app.getCustomerId() == customer.getCustomerId() ) {
                if ((startDateTime.isAfter(app.getStartZonedDateTime())
                        && startDateTime.isBefore(app.getEndZonedDateTime()))
                    || (endDateTime.isAfter(app.getStartZonedDateTime())
                        && endDateTime.isBefore(app.getEndZonedDateTime()))) {
                    errorMessage += "Error: selected dates and times causes customer to have overlapping appointments.\n";
                    break;
                }
            }
        }

        errorMessageLabel.setText(errorMessage);
        if (!"".equals(errorMessage)) {
            return;
        }

        if (currentAppointment == null) {
            ZonedDateTime createdDate = lastUpdateDate;
            String createdBy = lastUpdatedBy;
            Appointment newAppointment = new Appointment(id, title, description, location, type,
                    customer.getCustomerId(), user.getUserId(), contact.getContactId(), startDateTime, endDateTime,
                    createdDate, createdBy, lastUpdateDate, lastUpdatedBy);
            try {
                newAppointment.saveToDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            currentAppointment.setAppointmentId(id);
            currentAppointment.setTitle(title);
            currentAppointment.setDescription(description);
            currentAppointment.setLocation(location);
            currentAppointment.setType(type);
            currentAppointment.setContactId(contact.getContactId());
            currentAppointment.setCustomerId(customer.getCustomerId());
            currentAppointment.setUserId(user.getUserId());
            currentAppointment.setStartDateTime(startDateTime);
            currentAppointment.setEndDateTime(endDateTime);
            currentAppointment.setLastUpdateDate(lastUpdateDate);
            currentAppointment.setLastUpdateBy(lastUpdatedBy);
            try {
                currentAppointment.saveToDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ((AppointmentScreenController)getController("appointment_screen")).refreshAppointmentTable();
        setScene("appointment_screen");
    }
}
