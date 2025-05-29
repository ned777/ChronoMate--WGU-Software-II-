package DAO;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for Division model.
 * Handles database operations related to first-level divisions.
 */
public class DivisionDAO {

    /**
     * Gets all first-level divisions for a specific country.
     *
     * @param countryId The ID of the country for which to retrieve divisions.
     * @return ObservableList<Division> - a list of Division objects for the specified country.
     */
    public static ObservableList<Division> getDivisionsByCountry(int countryId) {
        // List to hold all division objects
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        // SQL query to select divisions where the country matches
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = ?";

        // Try-with-resources to ensure resources are closed automatically
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the country ID in the prepared statement
            ps.setInt(1, countryId);

            // Execute the query and process the results
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisions.add(new Division(
                        rs.getInt("Division_ID"),     // Division ID as int
                        rs.getString("Division"),     // Division name as String
                        countryId                     // Country ID as provided
                ));
            }
        } catch (SQLException e) {
            // Print stack trace for debugging
            e.printStackTrace();
        }

        // Return the list of divisions (empty if none or on error)
        return divisions;
    }
}
