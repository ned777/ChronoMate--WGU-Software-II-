package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for user records.
 * Provides methods to retrieve user information from the database.
 */
public class UserDAO {

    /**
     * Fetches all users for populating user combo boxes.
     *
     * @return an ObservableList of User objects representing all users
     * @throws SQLException if a database access error occurs
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT User_ID, User_Name FROM users";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name")
                );
                list.add(user);
            }
        }
        return list;
    }

    // Additional user-related DAO methods (addUser, deleteUser, etc.) can be added here.
}
