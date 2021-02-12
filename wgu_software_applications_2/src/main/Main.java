package main;

import controllers.*;
import datastructure.Appointment;
import datastructure.Customer;
import datastructure.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.TimeZone;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //TimeZone.setDefault( TimeZone.getTimeZone( "Pacific/Auckland" ));

        FXMLLoader loginFormLoader = new FXMLLoader(getClass().getResource("../fxml/login_form.fxml"));
        Scene loginFormScene = new Scene(loginFormLoader.load());
        LoginFormController loginFormController = (LoginFormController) loginFormLoader.getController();
        ApplicationScreen loginForm = new ApplicationScreen(loginFormScene, loginFormController);

        FXMLLoader homeScreenLoader = new FXMLLoader(getClass().getResource("../fxml/home_screen.fxml"));
        Scene homeScreenScene = new Scene(homeScreenLoader.load());
        HomeScreenController homeScreenController = (HomeScreenController) homeScreenLoader.getController();
        ApplicationScreen homeScreen = new ApplicationScreen(homeScreenScene, homeScreenController);

        FXMLLoader customerScreenLoader = new FXMLLoader(getClass().getResource("../fxml/customer_screen.fxml"));
        Scene customerScreenScene = new Scene(customerScreenLoader.load());
        CustomerScreenController customerScreenController = (CustomerScreenController) customerScreenLoader.getController();
        ApplicationScreen customerScreen = new ApplicationScreen(customerScreenScene, customerScreenController);

        FXMLLoader appointmentScreenLoader = new FXMLLoader(getClass().getResource("../fxml/appointment_screen.fxml"));
        Scene appointmentScreenScene = new Scene(appointmentScreenLoader.load());
        AppointmentScreenController appointmentScreenController = (AppointmentScreenController) appointmentScreenLoader.getController();
        ApplicationScreen appointmentScreen = new ApplicationScreen(appointmentScreenScene, appointmentScreenController);

        FXMLLoader customerFormLoader = new FXMLLoader(getClass().getResource("../fxml/customer_form.fxml"));
        Scene customerFormScene = new Scene(customerFormLoader.load());
        CustomerFormController customerFormController = (CustomerFormController) customerFormLoader.getController();
        ApplicationScreen customerForm = new ApplicationScreen(customerFormScene, customerFormController);

        FXMLLoader appointmentFormLoader = new FXMLLoader(getClass().getResource("../fxml/appointment_form.fxml"));
        Scene appointmentFormScene = new Scene(appointmentFormLoader.load());
        AppointmentFormController appointmentFormController = (AppointmentFormController) appointmentFormLoader.getController();
        ApplicationScreen appointmentForm = new ApplicationScreen(appointmentFormScene, appointmentFormController);

        ApplicationContext context = new ApplicationContext(primaryStage);
        context.addAppScreen("login_form", loginForm);
        context.addAppScreen("home_screen", homeScreen);
        context.addAppScreen("customer_screen", customerScreen);
        context.addAppScreen("appointment_screen", appointmentScreen);
        context.addAppScreen("customer_form", customerForm);
        context.addAppScreen("appointment_form", appointmentForm);

        for (ApplicationScreen appScreen : context.getHashMap().values()) {
            appScreen.getController().setApplicationContext(context);
        }

        ArrayList<Appointment> appointments = Appointment.getAll();
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }

        primaryStage.setTitle("Scheduling Software");
        primaryStage.setScene(appointmentFormController.getApplicationContext().getAppScreen("login_form").getScene());
        primaryStage.show();

        ConnectionManager.closeConnection();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
