package DAO;

import Model.Appointment;
import Model.ReportTypeMonth;
import Model.ContactSchedule;
import Model.CustomerAppointmentCount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Data Access Object for Appointment CRUD and reporting queries.
 * Handles saving, updating, deleting, and fetching appointment data (with contact join).
 */
public class AppointmentDAO {

    /**
     * Inserts a new appointment into the database.
     * On success, sets the generated Appointment_ID on the passed-in model.
     */
    public static void addAppointment(Appointment appt) throws SQLException {
        String sql = """
            INSERT INTO appointments
            (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = DBConnection.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());
            // Convert start/end to UTC for storage
            ZonedDateTime zStart = appt.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime zEnd = appt.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            ps.setTimestamp(5, Timestamp.valueOf(zStart.toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(zEnd.toLocalDateTime()));
            ps.setInt(7, appt.getCustomerId());
            ps.setInt(8, appt.getUserId());
            ps.setInt(9, appt.getContactId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    appt.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Updates an existing appointment's fields (all except its ID).
     */
    public static void updateAppointment(Appointment appt) throws SQLException {
        String sql = """
            UPDATE appointments
               SET Title=?, Description=?, Location=?, Type=?,
                   Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=?
             WHERE Appointment_ID=?
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());
            ZonedDateTime zStart = appt.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime zEnd = appt.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            ps.setTimestamp(5, Timestamp.valueOf(zStart.toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(zEnd.toLocalDateTime()));
            ps.setInt(7, appt.getCustomerId());
            ps.setInt(8, appt.getUserId());
            ps.setInt(9, appt.getContactId());
            ps.setInt(10, appt.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Deletes the appointment with the given ID.
     */
    public static void deleteAppointment(int apptId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, apptId);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all appointments, including contact name by joining contacts table.
     * Converts stored UTC timestamps back to local time.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String sql = """
            SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type,
                   a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID,
                   c.Contact_Name
            FROM appointments a
            JOIN contacts c ON a.Contact_ID = c.Contact_ID
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ZonedDateTime zStart = rs.getTimestamp("Start").toLocalDateTime()
                        .atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime zEnd = rs.getTimestamp("End").toLocalDateTime()
                        .atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                list.add(new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        zStart.toLocalDateTime(),
                        zEnd.toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                ));
            }
        }
        return list;
    }

    /**
     * Deletes all appointments for a given customer (used before deleting that customer).
     */
    public static void deleteByCustomerId(int customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }

    /**
     * Fetches all appointments for a customer, including contact name.
     */
    public static ObservableList<Appointment> getAppointmentsByCustomer(int customerId) throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String sql = """
            SELECT a.*, c.Contact_Name
            FROM appointments a
            JOIN contacts c ON a.Contact_ID = c.Contact_ID
            WHERE a.Customer_ID = ?
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ZonedDateTime zStart = rs.getTimestamp("Start").toLocalDateTime()
                            .atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                    ZonedDateTime zEnd = rs.getTimestamp("End").toLocalDateTime()
                            .atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                    list.add(new Appointment(
                            rs.getInt("Appointment_ID"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getString("Location"),
                            rs.getString("Type"),
                            zStart.toLocalDateTime(),
                            zEnd.toLocalDateTime(),
                            rs.getInt("Customer_ID"),
                            rs.getInt("User_ID"),
                            rs.getInt("Contact_ID"),
                            rs.getString("Contact_Name")
                    ));
                }
            }
        }
        return list;
    }

    /**
     * Fetches all appointments for a given user (for alerts, etc), including contact name.
     */
    public static ObservableList<Appointment> getAppointmentsByUser(int userId) throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String sql = """
            SELECT a.*, c.Contact_Name
            FROM appointments a
            JOIN contacts c ON a.Contact_ID = c.Contact_ID
            WHERE a.User_ID = ?
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ZonedDateTime zStart = rs.getTimestamp("Start").toLocalDateTime()
                            .atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                    ZonedDateTime zEnd = rs.getTimestamp("End").toLocalDateTime()
                            .atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                    list.add(new Appointment(
                            rs.getInt("Appointment_ID"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getString("Location"),
                            rs.getString("Type"),
                            zStart.toLocalDateTime(),
                            zEnd.toLocalDateTime(),
                            rs.getInt("Customer_ID"),
                            rs.getInt("User_ID"),
                            rs.getInt("Contact_ID"),
                            rs.getString("Contact_Name")
                    ));
                }
            }
        }
        return list;
    }

    // -------- Reporting queries below (untouched, use as needed) --------

    public static ObservableList<ReportTypeMonth> getCountByTypeAndMonth() throws SQLException {
        ObservableList<ReportTypeMonth> list = FXCollections.observableArrayList();
        String sql = """
            SELECT Type, MONTHNAME(Start) AS Month, COUNT(*) AS C
              FROM appointments
             GROUP BY Type, Month
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReportTypeMonth(
                        rs.getString("Type"),
                        rs.getString("Month"),
                        rs.getInt("C")
                ));
            }
        }
        return list;
    }

    public static ObservableList<ContactSchedule> getContactSchedules() throws SQLException {
        ObservableList<ContactSchedule> list = FXCollections.observableArrayList();
        String sql = """
            SELECT c.Contact_Name,
                   a.Appointment_ID,
                   a.Title,
                   a.Type,
                   a.Description,
                   a.Start,
                   a.End,
                   a.Customer_ID
              FROM appointments a
              JOIN contacts c ON a.Contact_ID = c.Contact_ID
             ORDER BY c.Contact_Name, a.Start
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end   = rs.getTimestamp("End").toLocalDateTime();
                list.add(new ContactSchedule(
                        rs.getString("Contact_Name"),
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Type"),
                        rs.getString("Description"),
                        start, end,
                        rs.getInt("Customer_ID")
                ));
            }
        }
        return list;
    }

    public static ObservableList<CustomerAppointmentCount> getCountByCustomer() throws SQLException {
        ObservableList<CustomerAppointmentCount> list = FXCollections.observableArrayList();
        String sql = """
            SELECT cu.Customer_Name AS Name, COUNT(a.Appointment_ID) AS C
              FROM customers cu
         LEFT JOIN appointments a ON cu.Customer_ID = a.Customer_ID
             GROUP BY cu.Customer_Name
        """;
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new CustomerAppointmentCount(
                        rs.getString("Name"),
                        rs.getInt("C")
                ));
            }
        }
        return list;
    }
}
