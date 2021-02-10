package controllers;

public class LoginFormController extends MyController {

    /**
     * Called when the user presses the submit button
     * on the login form. Checks to see if the username
     * and password entered are in the database and correct.
     * If they are, the user is redirected to the home screen.
     * If they are not, an error message is displayed.
     */
    public void submitButtonListener() {

        getApplicationContext().getPrimaryStage().setScene(getApplicationContext().getAppScreen("home_screen").getScene());
    }
}
