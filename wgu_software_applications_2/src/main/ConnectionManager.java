package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles the connection to the database.
 */
public class ConnectionManager {
    private static Connection connection;
    private static String databaseURL = "jdbc:mysql://wgudb.ucertify.com/WJ07mIl";
    private static String databaseUsername = "U07mIl";
    private static String databasePassword = "53689071265";
    private static String jdbcDriverName = "com.mysql.cj.jdbc.Driver";

    /**
     * Get a connection to the database.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(jdbcDriverName);
                connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close the connection to the database.
     */
    public static void closeConnection() {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
