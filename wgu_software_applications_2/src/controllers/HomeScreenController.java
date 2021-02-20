package controllers;

import datastructure.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class HomeScreenController extends MyController {

    /**
     * The label for displaying if an appointment
     * is scheduled within 15 minutes of logging in.
     */
    @FXML
    private Label appointmentNotifyLabel;

    /**
     * Called when the user successfully logs in.
     */
    public void initializeOnLogin() {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault());
        ObservableList<Appointment> appsSoon = FXCollections.observableArrayList();
        try {
            for (Appointment app : Appointment.getAll()) {
                ZonedDateTime toCompare = app.getStartZonedDateTime().withZoneSameInstant(ZoneId.systemDefault());
                long between = ChronoUnit.MINUTES.between(now, toCompare);
                if (between < 15 && between >= 0) {
                    appsSoon.add(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (appsSoon.size() > 0) {
            Appointment selected = appsSoon.get(0);
            appointmentNotifyLabel.setText("You have an appointment scheduled within the next 15 minutes.\n" +
                    "Appointment ID: " + selected.getAppointmentId() + "\n" +
            "Date: " + selected.getStartDateFormatted() + "\n" +
            "Time: " + selected.getStartTimeFormatted());
        } else {
            appointmentNotifyLabel.setText("You have no scheduled appointments in the next 15 minutes.");
        }
    }

    /**
     * Called when the View Appointments button is pressed.
     * Redirects the user to the Appointments screen.
     */
    public void viewAppointmentsButtonListener() {
        setScene("appointment_screen");
    }

    /**
     * Called when the View Customers button is pressed.
     * Redirects the user to the Customers screen.
     */
    public void viewCustomersButtonListener() {
        setScene("customer_screen");
    }

    /**
     * Called when the View Reports button is pressed.
     * Redirects the user to the Reports screen.
     */
    public void viewReportsButtonListener() {
        setScene("report_screen");
    }

    /**
     * Called when the Log Out button is pressed.
     * Resets the login form and
     * redirects the user to the Login Form.
     */
    public void logoutButtonListener() {
        getApplicationContext().setCurrentUser(null);
        ((LoginFormController)getController("login_form")).initialize();
        setScene("login_form");
    }
}
