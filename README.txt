=========================================
Appointment Scheduling Application
=========================================

Title & Purpose
---------------
Appointment Scheduling Application

This Java-based application allows users to manage customer and appointment records for a business. Users can view, add, update, and delete customers and appointments, generate reports, and review their schedules in a streamlined, user-friendly interface.

Author & Contact
----------------
Author: Ned Nguyen  
Contact: ned.nguyen@email.com  
Student Application Version: 1.0  
Date: May 29, 2025

Development Environment
-----------------------
IDE: IntelliJ IDEA Community Edition 2023.2.5  
JDK Version: Java SE 17.0.1  
JavaFX Version: JavaFX-SDK-17.0.1  
MySQL Connector/J Version: mysql-connector-java-8.0.25

How to Run the Program
----------------------
1. Ensure you have Java SE 17.0.1 and JavaFX-SDK-17.0.1 installed on your system.
2. Download and extract the project source files.
3. Open the project in IntelliJ IDEA Community Edition 2023.2.5.
4. Add the JavaFX SDK and MySQL Connector/J to the project’s library/module path.
5. Update the database connection settings (username, password, URL) if needed in the `DBConnection` class.
6. Build and run the application from IntelliJ.
   - VM options for JavaFX may be required (e.g.):
     ```
     --module-path "PATH_TO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml
     ```
7. Log in using provided test credentials or create a new user via the database.

Additional Report Description (Part A3f)
----------------------------------------
[Replace this text with your report details. Example:]
The "Appointments by Customer Division" report was implemented as the additional report. This report displays the total number of appointments grouped by each first-level division (e.g., state or province), helping management analyze appointment distribution across geographic regions.

MySQL Connector/J Driver
------------------------
Version: mysql-connector-java-8.0.25

