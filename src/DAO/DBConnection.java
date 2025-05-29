package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages a singleton JDBC connection to the MySQL database.
 * Uses the JDBC URL, username, and password defined in this class
 * to establish and cache a single Connection instance.
 * Subsequent calls to getConnection() will return the
 * existing connection if it’s still open.
 */
public class DBConnection {
    /** JDBC URL for connecting to the MySQL database. */
    private static final String URL = "jdbc:mysql://localhost:3306/client_schedule";
    /** Username for the database connection. */
    private static final String USER = "sqlUser";
    /** Password for the database connection. */
    private static final String PASS = "Passw0rd!";
    /** Cached singleton Connection instance. */
    private static Connection conn = null;

    /**
     * Returns a singleton database connection.
     * If no connection exists or the previous one is closed,
     * a new connection is created via DriverManager.getConnection.
     *
     * @return a valid, open Connection to the database
     * @throws SQLException if a database access error occurs or the URL is invalid
     */
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}
