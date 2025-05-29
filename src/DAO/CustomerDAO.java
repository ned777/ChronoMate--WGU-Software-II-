package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Data Access Object for Customer CRUD and queries.
 * Handles all database operations for the Customer table, including division/country lookups.
 */
public class CustomerDAO {

    /**
     * Fetches all customers, including their division and country names (for display in TableView).
     *
     * @return ObservableList of Customer objects (includes divisionName and countryName)
     * @throws SQLException if a database access error occurs
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        // Join to get readable division and country names
        String sql = """
            SELECT cu.Customer_ID, cu.Customer_Name, cu.Address, cu.Postal_Code, cu.Phone,
                   cu.Division_ID, d.Division, c.Country
            FROM customers cu
            JOIN first_level_divisions d ON cu.Division_ID = d.Division_ID
            JOIN countries c ON d.Country_ID = c.Country_ID
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer cust = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getString("Country")
                );
                list.add(cust);
            }
        }
        return list;
    }

    /**
     * Adds a new customer to the database.
     * @param customer The Customer object (ID is ignored/auto-generated)
     * @throws SQLException if a database access error occurs
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String sql = """
            INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivisionId());
            ps.executeUpdate();
            // Optionally get generated ID:
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Updates an existing customer record in the database.
     * @param customer The updated Customer object (ID must be set)
     * @throws SQLException if a database access error occurs
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        String sql = """
            UPDATE customers SET
                Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?
            WHERE Customer_ID = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivisionId());
            ps.setInt(6, customer.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Deletes a customer record and all related appointments (due to foreign key constraints).
     * @param customerId The ID of the customer to delete
     * @throws SQLException if a database access error occurs
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        // Delete all appointments for this customer first
        AppointmentDAO.deleteByCustomerId(customerId);
        // Then delete the customer
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }

    /**
     * Returns all unique country names from the database for ComboBox.
     */
    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Country FROM countries ORDER BY Country";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Country"));
            }
        }
        return list;
    }

    /**
     * Returns all first-level divisions (states/provinces) for a given country.
     */
    public static ObservableList<String> getDivisionsByCountry(String country) throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = """
            SELECT d.Division
            FROM first_level_divisions d
            JOIN countries c ON d.Country_ID = c.Country_ID
            WHERE c.Country = ?
            ORDER BY d.Division
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, country);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("Division"));
                }
            }
        }
        return list;
    }

    /**
     * Looks up and returns the division ID for a given division name.
     */
    public static int getDivisionIdByName(String divisionName) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, divisionName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Division_ID");
                }
            }
        }
        return -1; // Not found
    }
}
