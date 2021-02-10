package controllers;

public class CustomerScreenController extends MyController {
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
        setScene("customer_form");
    }

    /**
     * Called when the Modify button is pressed.
     * Redirects the user to the Customer Form.
     */
    public void modifyButtonListener() {
        setScene("customer_form");
    }
}
