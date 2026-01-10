# Appointment Scheduling Application

> Java-based desktop application for efficient customer and appointment management with comprehensive reporting features

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17.0.1-blue.svg)](https://openjfx.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

**Author:** Ned Nguyen  
**Version:** 1.0  
**Date:** May 29, 2025

---

## Overview

This Java-based desktop application allows businesses to manage customer information and appointment scheduling through a clean, intuitive interface. Users can add, update, delete, and view customers and appointments, as well as generate important scheduling reports. It is designed to support efficient time management and streamlined communication across departments.

## Features

- ✅ **Customer Management** - Add, update, delete, and view customer records
- ✅ **Appointment Scheduling** - Schedule, modify, and cancel appointments
- ✅ **Regional Reports** - View appointments grouped by country
- ✅ **Multi-department Support** - Coordinate across different business units
- ✅ **MySQL Integration** - Robust database backend for data persistence
- ✅ **JavaFX UI** - Modern, user-friendly desktop interface

## Development Environment

| Component | Version |
|-----------|---------|
| IDE | IntelliJ IDEA Community Edition 2023.2.5 |
| JDK | Java SE 17.0.1 |
| JavaFX | JavaFX-SDK-17.0.1 |
| MySQL Connector | mysql-connector-java-8.0.25 |

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java SE 17.0.1** or higher
- **JavaFX SDK 17.0.1**
- **MySQL Server** (any recent version)
- **MySQL Workbench** (optional, for database management)
- **IntelliJ IDEA** (recommended IDE)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/ned777/appointment-scheduling.git
cd appointment-scheduling
```

### 2. Database Setup

1. Open MySQL Workbench or your MySQL client
2. Import the provided SQL database schema
3. Note your database connection details:
   - Database URL
   - Username
   - Password

### 3. Configure Database Connection

Open `src/DBConnection.java` and update the following:

```java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/your_database";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### 4. Open Project in IntelliJ IDEA

1. Open IntelliJ IDEA Community Edition 2023.2.5
2. Select **File → Open** and choose the project directory
3. Wait for IntelliJ to index the project

### 5. Configure JavaFX

1. Go to **Run → Edit Configurations**
2. Add VM options:

```
--module-path "PATH_TO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml
```

Replace `PATH_TO_JAVAFX_LIB` with your actual JavaFX SDK path, for example:
- **Windows:** `C:\Program Files\Java\javafx-sdk-17.0.1\lib`
- **Linux/Mac:** `/usr/lib/javafx-sdk-17.0.1/lib`

### 6. Add MySQL Connector

1. Go to **File → Project Structure → Libraries**
2. Click **+** and add the MySQL Connector JAR:
   - `mysql-connector-java-8.0.25.jar`

### 7. Build and Run

1. Build the project: **Build → Build Project**
2. Run the application: **Run → Run 'Main'**

## Usage

### Login Credentials

For testing purposes, use the following credentials:

- **Username:** `test`
- **Password:** `test`

### Main Features

#### Customer Management
- Navigate to the Customers tab
- Add new customers with contact information
- Update or delete existing customer records
- View all customers in a sortable table

#### Appointment Scheduling
- Navigate to the Appointments tab
- Create new appointments with date/time and customer
- Modify or cancel existing appointments
- Filter appointments by date range

#### Reports
- Navigate to the Reports tab
- **Appointments by Country Report** - View appointment distribution across countries
- Additional reports for business insights

## Reports

### Appointments by Country Report

The **Appointments by Country** report displays the number of appointments grouped by country. This helps businesses evaluate customer engagement across different regions, providing insights into:

- Regional performance
- International outreach effectiveness
- Resource allocation needs

The report uses SQL JOINs to query customer country data and groups appointment counts accordingly. It is accessible in the Reports tab of the application interface.

## Project Structure

```
appointment-scheduling/
├── src/
│   ├── main/
│   │   ├── Main.java              # Application entry point
│   │   ├── controller/            # JavaFX controllers
│   │   ├── model/                 # Data models (Customer, Appointment)
│   │   ├── dao/                   # Database access objects
│   │   ├── utils/                 # Utility classes
│   │   └── DBConnection.java      # Database connection manager
│   └── resources/
│       ├── fxml/                  # FXML view files
│       └── css/                   # Stylesheets
├── database/
│   └── schema.sql                 # Database schema
├── lib/
│   └── mysql-connector-java-8.0.25.jar
└── README.md
```

## Technologies Used

- **Java 17** - Core programming language
- **JavaFX 17** - Desktop UI framework
- **MySQL 8.0** - Relational database
- **JDBC** - Database connectivity
- **IntelliJ IDEA** - Development environment

## Troubleshooting

### JavaFX Module Not Found

**Error:** `Error: JavaFX runtime components are missing`

**Solution:** Verify VM options are set correctly in Run Configuration:
```
--module-path "PATH_TO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml
```

### Database Connection Failed

**Error:** `SQLException: Access denied for user`

**Solution:** 
1. Check MySQL server is running
2. Verify username/password in `DBConnection.java`
3. Ensure database exists and schema is imported

### MySQL Connector Not Found

**Error:** `ClassNotFoundException: com.mysql.cj.jdbc.Driver`

**Solution:** Add MySQL Connector JAR to project libraries (File → Project Structure → Libraries)

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## License

This project is for educational purposes. All rights reserved.

## Contact

**Author:** Ned Nguyen  
**Email:** [Your Email]  
**GitHub:** [https://github.com/YOUR_USERNAME](https://github.com/YOUR_USERNAME)

---

**Version:** 1.0  
**Last Updated:** May 29, 2025

