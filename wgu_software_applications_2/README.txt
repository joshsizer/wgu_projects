Title: Scheduling and Reporting Application
Purpose: To keep track of and modify Customers and their respective Appointments.

Author: Joshua Sizer
    --> Email: jsizer@wgu.edu

Application Version:    1.0.0
Application Date:       2/21/2021

Versions:
    --> IDE:    IntelliJ IDEA Community Edition 2020.3.2
    --> JDK:    jdk-11.0.10
    --> JavaFX: javafx-sdk-11.0.2
    --> MySQL:  mysql-connector-java-8.0.23

Directions:
    --> 1. Login with correct username and password (test, test) or (admin, admin) to reach the Home Screen.
    --> 2. From the Home Screen, Navigate to either the Appointments, Customers, or Reports page.
           Alternatively, Logout.
    --> 3. On the Appointments page, you can Add, Modify, or Delete Appointments. Alternatively return to the
           Home Screen.
    --> 4. On the Customers page, you can Add, Modify, or Delete Customers. Alternatively, return to the
           Home Screen.
    --> 5. On the Reports page, view Report 1 and 3. For Report 2, select which Contact to view the schedule of.
           Alternatively, return to the Home Screen.
            ==> a. Report 1 shows the count of Appointments by both Month and Type.
            ==> b. Report 2 shows the count of Appointments by Customer_ID.
            ==> c. Report 3 shows the schedule for each Contact.

Location of Lambda: The lambda expressions can be found in the ReportScreenController class. They occur in two
    separate methods.
    --> 1. initialize()
    --> 2. refreshAppointmentTable()