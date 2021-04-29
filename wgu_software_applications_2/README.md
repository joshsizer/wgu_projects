# Meeting Scheduler

This application allows users to login, add,
delete, or modify Customers and Appointments.
Additionally, users can view interesting reports
created from the Appointment and Customer data.
The application connects to a remote MySQL
database in the backend.

## FULL GUI

The application features a full GUI written with
JavaFX. Improvements to this app could include a
GUI layed out in a relative, rather than absolute fashion.

## SQL Backend

To query Appointment and Customer information, the
app connects to a MySQL database using the JDBC
protocol.  

## Report for class assignment

Title: Scheduling and Reporting Application  
Purpose: To keep track of and modify Customers and
their respective Appointments.  

Author: Joshua Sizer  

Application Version:    1.0.1  
Application Date:       2/21/2021  

### Versions  

- IDE:    IntelliJ IDEA Community Edition 2020.3.2
- JDK:    jdk-11.0.10  
- JavaFX: javafx-sdk-11.0.2  
- MySQL:  mysql-connector-java-8.0.23  

### Directions

1. Login with correct username and password (test, test) or (admin, admin) to reach the Home Screen. 
2. From the Home Screen, Navigate to either the Appointments, Customers, or Reports page. Alternatively, Logout.  
3. On the Appointments page, you can Add, Modify,
    or Delete Appointments. Alternatively return
    to the  Home Screen.
4. On the Customers page, you can Add, Modify, or
    Delete Customers. Alternatively, return to the
    Home Screen. 
5. On the Reports page, view
    Report 1 and 3. For Report 2, select which
    Contact to view the schedule of.
    Alternatively, return to the Home Screen.

       - Report 1 shows the count of Appointments by both Month and Type.
       - Report 2 shows the count of Appointments by Customer_ID.
       - Report 3 shows the schedule for each Contact.

### Location of Lambda

The lambda expressions can be found in the ReportScreenController class. They occur in two
    separate methods.

1. initialize()
2. refreshAppointmentTable()

## Project Requirements - INTRODUCTION

Throughout your career in software design and
development, you will be asked to create
applications with various features and criteria
based on a variety of business requirements. For
this assessment, you will create your own
GUI-based Java application with requirements that
mirror those you will likely encounter in a
real-world job assignment.

The skills you will showcase in this assessment
are also directly relevant to technical interview
questions for future employment. This application
may become a portfolio piece for you to show to
future employers.

Several attachments and links have been included
to help you complete this task. Refer to the
“MySQL Virtual Access Instruction” attachment for
help accessing the database for your application.
Note that this database is for functional purposes
only. The attached “Database ERD” shows the entity
relationship diagram (ERD) for this database,
which you can reference as you create your
application.

The preferred integrated development environment
(IDE) for this assignment is NetBeans version 11.1
or later, or IntelliJ IDEA (Community Edition).
Use the links in the Web Links section of this
assessment to install one of these IDEs. If you
choose to use another IDE, you must export your
project into NetBeans 11.1 or later, or IntelliJ
format, or your submission will be returned. This
assessment also requires the following software:
JDK 11, JavaFX SDK, and Scene Builder, which are
also available for download in the Web Links
section of this assessment.

Your submission should include a zip file with all
the necessary code files to compile, support, and
run your application. Your submission should also
include an index.html file with reflective Javadoc
comments in the .java files. The zip file
submission must keep the project file and folder
structure intact for the IDE.

In NetBeans, zip your file by going to File>Export
Project>To ZIP and click Export. In IntelliJ Idea,
go to File>Export to Zip File and click OK. If you
try to zip your project files with an external
program, it will include the build files and make
the zip files too large for submission.

* Note: You may receive an error message upon
submitting your files because the automated
plagiarism detectors at WGU will not be able to
access a zipped file, but the evaluation team will
run their checks manually when evaluating your
submission.

## SCENARIO

You are working for a software company that has
been contracted to develop a GUI-based scheduling
desktop application. The contract is with a global
consulting organization that conducts business in
multiple languages and has main offices in
Phoenix, Arizona; White Plains, New York;
Montreal, Canada; and London, England. The
consulting organization has provided a MySQL
database that the application must pull data from.
The database is used for other systems, so its
structure cannot be modified.

The organization outlined specific business
requirements that must be met as part of the
application. From these requirements, a system
analyst at your company created solution
statements for you to implement in developing the
application. These statements are listed in the
requirements section.

Your company acquires Country and
First-Level-Division data from a third party that
is updated once per year. These tables are
prepopulated with read-only data. Please use the
attachment “Locale Codes for Region and Language”
to review division data. Your company also
supplies a list of contacts, which are
prepopulated in the Contacts table; however,
administrative functions such as adding users are
beyond the scope of the application and done by
your company’s IT support staff. Your application
should be organized logically using one or more
design patterns and generously commented using
Javadoc so your code can be read and maintained by
other programmers.

## REQUIREMENTS

### GENERAL

Your submission must be your original work. No
more than a combined total of 30% of the
submission and no more than a 10% match to any one
individual source can be directly quoted or
closely paraphrased from sources, even if cited
correctly. The originality report that is provided
when you submit your task can be used as a guide.

 You must use the rubric to direct the creation of
 your submission because it provides detailed
 criteria that will be used to evaluate your work.
 Each requirement below may be evaluated by more
 than one rubric aspect. The rubric aspect titles
 may contain hyperlinks to relevant portions of
 the course.

* You must use "test" as the username and password
  to login to your application.

Tasks may not be submitted as cloud links, such as
links to Google Docs, Google Slides, OneDrive,
etc., unless specified in the task requirements.
All other submissions must be file types that are
uploaded and submitted as attachments (e.g.,
.docx, .pdf, .ppt).

### SPECIFIC

<style type="text/css">
    ol ol { list-style-type: decimal; } 
    ol ol ol { list-style-type: lower-roman; }
    ol ol ol ol { list-style-type: square; }
    ol { list-style-type: upper-alpha; }
</style>

1. Create a GUI-based application for the company
   in the scenario. Regarding your file
   submission—the use of non-Java API libraries
   are not allowed with the exception of JavaFX
   SDK and MySQL JDBC Driver. If you are using the
   NetBeans IDE, the custom library for your
   JavaFX .jar files in your IDE must be named
   JavaFX.

   Note: If you are using IntelliJ IDEA, the
      folder where the JavaFX SDK resides will be
      used as the library name as shown in the
      “JavaFX SDK with IntelliJ IDEA webinar.
    1. Create a log-in form with the following capabilities:

        1. accepts a user ID and password and provides an appropriate error message

        2. determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form

        3. displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form

        4. automatically translates error control
           messages into English or French based
           on the user’s computer language setting
        
            Note: Some operating systems require a
      reboot when changing the language settings.

    2. Write code that provides the following customer record functionalities:
        1. Customer records and appointments can be added, updated, and deleted.
            1. When deleting a customer record, all of the customer’s appointments must be deleted first, due to foreign key constraints.
        2. When adding and updating a customer, text fields are used to collect the following data: customer name, address, postal code, and phone number.
             1. Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.
                1. Note: The address text field should not include first-level division and country data. Please use the following examples to format addresses:
                2. U.S. address: 123 ABC Street, White Plains
                3. Canadian address: 123 ABC Street, Newmarket

                4. UK address: 123 ABC Street, Greenwich, London
             2. When updating a customer, the customer data autopopulates in the form.
        3. Country and first-level division data is prepopulated in separate combo boxes or lists in the user interface for the user to choose. The first-level list should be filtered by the user’s selection of a country (e.g., when choosing U.S., filter so it only shows states).
        4. All of the original customer information is displayed on the update form.
            1. Customer_ID must be disabled.
        5. All of the fields can be updated except for Customer_ID.
        6. Customer data is displayed using a TableView, including first-level division data. A list of all the customers and their information may be viewed in a TableView, and updates of the data can be performed in text fields on the form.
        7. When a customer record is deleted, a
           custom message should display in the
           user interface.  
    3. Add scheduling functionalities to the GUI-based application by doing the following:
        1. Write code that enables the user to add, update, and delete appointments. The code should also include the following functionalities:
            1. A contact name is assigned to an appointment using a drop-down menu or combo box.
            2. A custom message is displayed in the user interface with the Appointment_ID and type of appointment canceled.
            3. The Appointment_ID is auto-generated and disabled throughout the application.
            4. When adding and updating an appointment, text fields are used to record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
            5. All of the original appointment information is displayed on the update form in local time zone.
            6. All of the appointment fields can
               be updated except Appointment_ID,
               which must be disabled.
        2. Write code that enables the user to view appointment schedules by month and week using a TableView and allows the user to choose between these two options using tabs or radio buttons for filtering appointments. Please include each of the following requirements as columns:
            1. Appointment_ID
            2. Title
            3. Description
            4. Location
            5. Contact
            6. Type
            7. Start Date and Time
            8. End Date and Time
            9. Customer_ID
        3. Write code that enables the user to adjust appointment times. While the appointment times should be stored in Coordinated Universal Time (UTC), they should be automatically and consistently updated according to the local time zone set on the user’s computer wherever appointments are displayed in the application.
            Note: There are up to three time zones in effect. Coordinated Universal Time (UTC) is used for storing the time in the database, the user’s local time is used for display purposes, and Eastern Standard Time (EST) is used for the company’s office hours. Local time will be checked against EST business hours before they are stored in the database as UTC.
        4. Write code to implement input validation and logical error checks to prevent each of the following changes when adding or updating information; display a custom message specific for each error check in the user interface:
            1. scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends
            2. scheduling overlapping appointments for customers
            3. entering an incorrect username and password

        5. Write code to provide an alert when
            there is an appointment within 15
            minutes of the user’s log-in. A custom
            message should be displayed in the
            user interface and include the
            appointment ID, date, and time. If the
            user does not have any appointments
            within 15 minutes of logging in,
            display a custom message in the user
            interface indicating there are no
            upcoming appointments.

            Note: Since evaluation may be testing your
            application outside of business hours,
            your alerts must be robust enough to
            trigger an appointment within 15 minutes
            of the local time set on the user’s
            computer, which may or may not be EST.

        6. Write code that generates accurate information in each of the following reports and will display the reports in the user interface

            Note: You do not need to save and print
            the reports to a file or provide a
            screenshot.

            1. the total number of customer appointments by type and month
            2. a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID
            3. an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C

2. Write at least two different lambda expressions to improve your code.
3. Write code that provides the ability to track user activity by recording all user log-in attempts, dates, and time stamps and whether each attempt was successful in a file named login_activity.txt. Append each new record to the existing file, and save to the root folder of the application.
4. Provide descriptive Javadoc comments for at least 70 percent of the classes and their members throughout the code, and create an index.html file of your comments to include with your submission based on Oracle’s guidelines for the Javadoc tool best practices. Your comments should include a justification for each lambda expression in the method where it is used.

    Note: The comments on the lambda need to be located in the comments describing the method where it is located for it to export properly.

5. Create a README.txt file that includes the following information:

    1. title and purpose of the application
    2. author, contact information, student application version, and date
    3. IDE including version number (e.g., IntelliJ Community 2020.01), full JDK of version 11 used (e.g., Java SE 11.0.4), and JavaFX version compatible with JDK 11 (e.g. JavaFX-SDK-11.0.2)
    4. directions for how to run the program
    5. a description of the additional report of your choice you ran in part A3f
    6. the MySQL Connector driver version number, including the update number (e.g., mysql-connector-java-8.1.23)

F.  Demonstrate professional communication in the content and presentation of your submission.

