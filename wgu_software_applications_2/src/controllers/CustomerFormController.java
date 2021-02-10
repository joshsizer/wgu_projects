package controllers;

public class CustomerFormController extends MyController {

    /**
     * Called when the Cancel button is pressed.
     * Redirects the user to the Customer Screen.
     */
    public void cancelButtonListener() {
        setScene("customer_screen");
    }
}
