package controllers;

import javafx.scene.Scene;
import main.ApplicationContext;

/**
 * Superclass for controllers. Made in order to hold generic instances
 * of controllers in the ApplicationScreen class.
 * Also holds the Application's ApplicationContext so that each
 * controller can have access to other ApplicationScreens.
 */
public abstract class MyController {
    /**
     * This controller's applicationContext
     */
    private ApplicationContext appContext;

    /**
     * Set this controller's ApplicationContext.
     *
     * @param context The ApplicationContext for this controller.
     */
    public void setApplicationContext(ApplicationContext context) {
        this.appContext = context;
    }

    /**
     * Get this controller's ApplicationContext.
     * @return This controller's ApplicationContext.
     */
    public ApplicationContext getApplicationContext() {
        return this.appContext;
    }

    /**
     * Set the Application's current scene to that specified
     * by name.
     *
     * @param name The name of the Application screen whose scene should become visible.
     */
    protected void setScene(String name) {
        getApplicationContext().getPrimaryStage().setScene(getApplicationContext().getAppScreen(name).getScene());
    }

    /**
     * Get a scene controller by name.
     *
     * @param name The name of the ApplicationScreen whose controller should be returned.
     * @return The controller for the ApplicationScreen with the specified name.
     */
    protected MyController getController(String name) {
        return getApplicationContext().getAppScreen(name).getController();
    }
}
