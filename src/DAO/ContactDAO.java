package DAO;

import Model.Contact; // <-- Make sure this import matches your file location
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for contact records.
 * Provides methods to fetch contact data from the database.
 */
public class ContactDAO {

    /**
     * Retrieves all contact names from the contacts table.
     *
     * @return an ObservableList of contact name Strings
     * @throws SQLException if a database access error occurs
     */
    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM contacts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Contact_Name"));
            }
        }
        return list;
    }

    /**
     * Retrieves all contacts as pairs of Contact_ID and Contact_Name.
     * Useful for mapping a name to an ID when saving appointments.
     *
     * @return an ObservableList of Contact objects (with id and name)
     * @throws SQLException if a database access error occurs
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> list = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID, Contact_Name FROM contacts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                list.add(new Contact(id, name));
            }
        }
        return list;
    }

    /**
     * Gets the Contact_ID for a given Contact_Name.
     * Returns -1 if not found.
     *
     * @param contactName the name to look up
     * @return the Contact_ID, or -1 if not found
     * @throws SQLException if a database access error occurs
     */
    public static int getContactIdByName(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, contactName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Contact_ID");
                }
            }
        }
        return -1;
    }
}
