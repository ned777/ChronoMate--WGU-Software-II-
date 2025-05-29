package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for the Country model.
 * Handles database operations related to countries.
 */
public class CountryDAO {

    /**
     * Retrieves all countries from the database.
     *
     * @return ObservableList<Country> - a list of Country objects
     */
    public static ObservableList<Country> getAllCountries() {
        // List to hold all country objects
        ObservableList<Country> countries = FXCollections.observableArrayList();

        // SQL query to select country ID and name
        String sql = "SELECT Country_ID, Country FROM countries";

        // Try-with-resources to auto-close resources
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Execute the query and get the result set
            ResultSet rs = ps.executeQuery();

            // Loop through all results and add to the list
            while (rs.next()) {
                countries.add(new Country(
                        rs.getInt("Country_ID"),      // Get Country_ID as int
                        rs.getString("Country")       // Get Country name as String
                ));
            }
        } catch (SQLException e) {
            // Print error details for debugging
            e.printStackTrace();
        }

        // Return the list of countries (can be empty if exception occurred)
        return countries;
    }
}
