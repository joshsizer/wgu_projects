package controllers;

public class AppointmentFormController extends MyController {

    /**
     * Called when the Cancel button is pressed.
     * Redirects the user to the Appointment Screen.
     */
    public void cancelButtonListener() {
        setScene("appointment_screen");
    }
}
