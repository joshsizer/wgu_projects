package controllers;

import datastructure.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;


/**
 * Holds the functions for operating the Login Form.
 */
public class LoginFormController extends MyController {

    /**
     * The TextField used to enter a User's username.
     */
    @FXML
    private TextField userNameTextField;

    /**
     * The TextField used to enter a User's password.
     */
    @FXML
    private TextField passwordTextField;

    /**
     * The Label used to show the header information.
     */
    @FXML
    private Label headerLabel;

    /**
     * The Label used to display the User's ZoneId.
     */
    @FXML
    private Label locationLabel;

    /**
     * The Label used to denote the username TextField.
     */
    @FXML
    private Label userNameLabel;

    /**
     * The Label used to denote the password TextField.
     */
    @FXML
    private Label passwordLabel;


    /**
     * The Label used to display login error messages.
     */
    @FXML
    private Label loginErrorLabel;

    /**
     * The Submit button for attempting to Log In.
     */
    @FXML
    private Button submitButton;

    /**
     * Called when this controller is first loaded.
     * Blanks out the login form and sets the User's Location.
     */
    public void initialize() {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            headerLabel.setText("Formulaire de Connexion");
            locationLabel.setText("Lieu: " + ZoneId.systemDefault());
            userNameLabel.setText("Nom d'utilisateur:");
            passwordLabel.setText("Le mot de passe:");
            submitButton.setText("Connexion");
        } else {
            locationLabel.setText("Location: " + ZoneId.systemDefault());
        }

        userNameTextField.setText("");
        passwordTextField.setText("");
        loginErrorLabel.setText("");
    }

    /**
     * Called when the user presses the submit button
     * on the login form. Checks to see if the username
     * and password entered are in the database and correct.
     * If they are, the user is redirected to the home screen.
     * If they are not, an error message is displayed.
     */
    public void submitButtonListener() {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        User currentUser = null;

        try {
            currentUser = User.getByUserName(userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (currentUser == null) {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                loginErrorLabel.setText("Erreur: veuillez saisir un nom d'utilisateur valide");
            } else {
                loginErrorLabel.setText("Error: please enter a valid username");
            }
            writeLoginAttemptToFile(false);
            return;
        } else if (!password.equals(currentUser.getPassword())) {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                loginErrorLabel.setText("Erreur: mot de passe incorrect");
            } else {
                loginErrorLabel.setText("Error: password incorrect");
            }
            writeLoginAttemptToFile(false);
            return;
        }

        writeLoginAttemptToFile(true);
        getApplicationContext().setCurrentUser(currentUser);
        ((HomeScreenController)getController("home_screen")).initializeOnLogin();
        setScene("home_screen");
    }

    /**
     * Records each login attempt as a failure or success.
     * @param success If the login attempt was successful.
     */
    private void writeLoginAttemptToFile(boolean success) {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        File toWrite = new File("login_activity.txt");
        String textToWrite = now.toString() + ": ";
        if (success) {
            textToWrite += "success\n";
        } else {
            textToWrite += "failure\n";
        }
        try {
            toWrite.createNewFile();
            FileWriter myWriter = new FileWriter(toWrite, true);
            myWriter.append(textToWrite);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
