package controllers;

public class AppointmentScreenController extends MyController {

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
        setScene("appointment_form");
    }

    /**
     * Called when the Modify button is pressed.
     * Redirects the user to the Appointment Form.
     */
    public void modifyButtonListener() {
        setScene("appointment_form");
    }

}
