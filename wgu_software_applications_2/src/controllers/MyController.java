package controllers;

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

    protected void setScene(String name) {
        getApplicationContext().getPrimaryStage().setScene(getApplicationContext().getAppScreen(name).getScene());
    }
}
