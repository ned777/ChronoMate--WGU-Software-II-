# Appointment Scheduling Application

A Java desktop application for managing customers and appointments, built with JavaFX and backed by a MySQL database.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17.0.1-blue.svg)](https://openjfx.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

**Author:** Ned Nguyen | **Version:** 1.0 | **Date:** May 29, 2025

---

## Overview

This application provides a full-featured scheduling workflow: create and manage customer records, book and modify appointments, and generate regional reports that group appointment data by country using SQL JOINs. The interface is built with JavaFX and FXML, with all data persisted to a MySQL backend via JDBC.

## Features

- **Customer Management** — Create, update, delete, and view customer records in a sortable table
- **Appointment Scheduling** — Book, modify, and cancel appointments with date/time filtering
- **Regional Reports** — View appointment counts grouped by country to assess geographic engagement
- **Multi-department Support** — Coordinate scheduling across different business units
- **MySQL Backend** — Full JDBC integration for persistent data storage
- **JavaFX UI** — Clean, FXML-driven desktop interface

## Tech Stack

| Component | Version |
|-----------|---------|
| JDK | Java SE 17.0.1 |
| JavaFX | JavaFX-SDK-17.0.1 |
| MySQL Connector | mysql-connector-java-8.0.25 |
| IDE | IntelliJ IDEA Community 2023.2.5 |

## Prerequisites

- Java SE 17.0.1+
- JavaFX SDK 17.0.1
- MySQL Server (any recent version)
- IntelliJ IDEA (recommended)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/ned777/appointment-scheduling.git
cd appointment-scheduling
```

### 2. Database Setup

1. Open MySQL Workbench or your preferred MySQL client
2. Import `database/schema.sql`
3. Note your connection details (URL, username, password)

### 3. Configure the Database Connection

Edit `src/DBConnection.java`:

```java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/your_database";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### 4. Open in IntelliJ IDEA

Go to **File → Open** and select the project directory. Wait for indexing to complete.

### 5. Configure JavaFX

Go to **Run → Edit Configurations** and add VM options:

```
--module-path "PATH_TO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml
```

Example paths:
- **Windows:** `C:\Program Files\Java\javafx-sdk-17.0.1\lib`
- **Linux/macOS:** `/usr/lib/javafx-sdk-17.0.1/lib`

### 6. Add MySQL Connector

Go to **File → Project Structure → Libraries**, click **+**, and add:
```
mysql-connector-java-8.0.25.jar
```

### 7. Build and Run

**Build → Build Project**, then **Run → Run 'Main'**

## Usage

**Test credentials:** username `test` / password `test`

### Customer Management

Navigate to the **Customers** tab to add, edit, or delete customer records. All records display in a sortable table.

### Appointment Scheduling

Navigate to the **Appointments** tab to create, modify, or cancel appointments. Filter by date range to narrow results.

### Reports

Navigate to the **Reports** tab. The **Appointments by Country** report shows appointment volume per country, which is useful for evaluating regional engagement and resource allocation. It queries customer country data via SQL JOINs and groups results by country.

## Project Structure

```
appointment-scheduling/
├── src/
│   ├── main/
│   │   ├── Main.java
│   │   ├── controller/        # JavaFX controllers
│   │   ├── model/             # Customer, Appointment models
│   │   ├── dao/               # Database access objects
│   │   ├── utils/             # Utility classes
│   │   └── DBConnection.java  # JDBC connection manager
│   └── resources/
│       ├── fxml/              # FXML view files
│       └── css/               # Stylesheets
├── database/
│   └── schema.sql
├── lib/
│   └── mysql-connector-java-8.0.25.jar
└── README.md
```

## Troubleshooting

**`Error: JavaFX runtime components are missing`**  
Verify that VM options in your Run Configuration include the correct `--module-path` pointing to your JavaFX `lib` directory.

**`SQLException: Access denied for user`**  
Check that MySQL is running and that the credentials in `DBConnection.java` match your local setup. Confirm the schema has been imported.

**`ClassNotFoundException: com.mysql.cj.jdbc.Driver`**  
The MySQL Connector JAR isn't on the classpath. Add it via **File → Project Structure → Libraries**.

---

**Contact:** Ned Nguyen — nmdcnn@gmail.com — [GitHub](https://github.com/ned777)
