package controllers;

public class HomeScreenController extends MyController {

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
     * Redirects the user to the Login Form.
     */
    public void logoutButtonListener() {
        setScene("login_form");
    }
}
