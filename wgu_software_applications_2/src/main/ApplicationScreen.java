package main;

import controllers.MyController;
import javafx.scene.Scene;

/**
 * An ApplicationScreen packages a JavaFX scene with its controller.
 * Simple wrapper to store these two objects.
 */
public class ApplicationScreen {
    /**
     * The scene used for this application screen
     */
    private Scene scene;

    /**
     * The controller used for this application screen
     */
    private MyController controller;

    /**
     * Create an ApplicationScreen.
     *
     * @param scene This screen's scene.
     * @param controller This screen's controller.
     */
    public ApplicationScreen(Scene scene, MyController controller) {
        this.scene = scene;
        this.controller = controller;
    }

    /**
     * Get this ApplicationScreen's scene.
     *
     * @return The scene associated with this ApplicationScreen.
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Get this ApplicationScreen's controller.
     *
     * @return The controller associated with this ApplicationScreen.
     */
    public MyController getController() {
        return this.controller;
    }
}
