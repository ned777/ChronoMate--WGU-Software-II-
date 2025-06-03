=========================================
Appointment Scheduling Application
=========================================

Title & Purpose
---------------
Appointment Scheduling Application

This Java-based desktop application allows businesses to manage customer information and appointment scheduling through a clean, intuitive interface. Users can add, update, delete, and view customers and appointments, as well as generate important scheduling reports. It is designed to support efficient time management and streamlined communication across departments.

Author & Contact
----------------
Author: Ned Nguyen  
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
1. Ensure you have the following installed:
   - Java SE 17.0.1
   - JavaFX-SDK-17.0.1
   - MySQL Server and MySQL Workbench

2. Download and extract the source project files.

3. Open the project using IntelliJ IDEA Community Edition 2023.2.5.

4. Configure the JavaFX library path in IntelliJ:
   - VM Options (when running the app):
     ```
     --module-path "PATH_TO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml
     ```
   - Replace `PATH_TO_JAVAFX_LIB` with the actual path to your JavaFX SDK `/lib` directory.

5. Ensure the MySQL Connector JAR is added to your project libraries.

6. Import the provided SQL database schema into MySQL and adjust the database connection settings in the `DBConnection.java` class:
   - URL
   - Username
   - Password

7. Build and run the application from IntelliJ.

8. Log in with:
   - Username: `test`
   - Password: `test`

Additional Report Description (Part A3f)
----------------------------------------
**Appointments by Country Report**

The third report implemented displays the number of appointments grouped by country. This helps the business evaluate its customer engagement across different countries, providing insight into regional performance and outreach. The report queries each customer's country through JOINs and groups the appointment count accordingly. It is accessible in the Reports tab of the application interface.

MySQL Connector/J Driver
------------------------
Version: mysql-connector-java-8.0.25
