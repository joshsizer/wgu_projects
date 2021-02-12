package main;

import datastructure.User;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * A class that holds the application's context. In other words,
 * this class has a reference to the application's primary stage,
 * as well as all other ApplicationScreens in the program. These
 * ApplicationScreens can be referenced by name using the
 * getAppScreen() method.
 */
public class ApplicationContext {
    /**
     * The HashMap used to keep track of all Application Screens.
     **/
    private HashMap<String, ApplicationScreen> appScreenHashMap;

    /**
     * The Application's primary stage.
     */
    private Stage primaryStage;

    /**
     * The currently logged in User
     */
    private User currentUser;

    /**
     * Create the ApplicationContext
     *
     * @param primaryStage The application's primary stage.
     */
    public ApplicationContext(Stage primaryStage) {
        appScreenHashMap = new HashMap<>();
        this.primaryStage = primaryStage;
    }

    /**
     * Add an ApplicationScreen to this ApplicationContext.
     *
     * @param name The name used to reference the ApplicationScreen.
     * @param screen The ApplicationScreen itself.
     */
    public void addAppScreen(String name, ApplicationScreen screen) {
        appScreenHashMap.put(name, screen);
    }

    /**
     * Get an ApplicationScreen by name.
     *
     * @param name The name of the ApplicationScreen to return.
     * @return The ApplicationScreen with the given name.
     */
    public ApplicationScreen getAppScreen(String name) {
        return appScreenHashMap.get(name);
    }

    /**
     * Get the underlying HashMap used to associate
     * ApplicationScreens and their name.
     * Useful for iterating over all ApplicationScreens.
     *
     * @return The underlying HashMap.
     */
    public HashMap<String, ApplicationScreen> getHashMap() {
        return appScreenHashMap;
    }

    /**
     * Get this ApplicationContext's primary stage
     *
     * @return The primary stage for this ApplicationContext.
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Set the Application's currently logged in User.
     *
     * @param user The user that is currently logged in.
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Get the Application's currently logged in User.
     *
     * @return The User currently logged in.
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
}
