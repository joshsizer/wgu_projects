package datastructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ConnectionManager;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a User in this Application. Mirrors the
 * users table in the database.
 */
public class User {
    /**
     * The primary key, User ID. Unique for each User.
     */
    private int userId;

    /**
     * The User Name used to log in and to
     * identify the user apart from the User ID.
     */
    private String userName;

    /**
     * The Password used to log in.
     */
    private String password;

    /**
     * The date this User was created.
     */
    private ZonedDateTime createdDate;

    /**
     * Information on who created this User.
     */
    private String createdBy;

    /**
     * When this User's data/information was
     * last updated.
     */
    private ZonedDateTime lastUpdateDate;

    /**
     * Information on who last updated this
     * User's information.
     */
    private String lastUpdateBy;

    /**
     * Create a User.
     *
     * @param userId A unique number used to identify this User. The primary key in the database.
     * @param userName Used by the User when logging in.
     * @param password Used by the User when logging in.
     * @param createdDate The date when this User was created.
     * @param createdBy Who created this User.
     * @param lastUpdateDate When this User was last updated.
     * @param lastUpdateBy Who last updated this User.
     */
    public User(int userId, String userName, String password, ZonedDateTime createdDate,
                String createdBy, ZonedDateTime lastUpdateDate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Returns a User based on the User's username.
     */
    public static User getByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.users WHERE User_Name = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setString(1, userName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return getFromRow(rs);
        }

        return null;
    }

    /**
     * Returns a User based on its ID.
     *
     * @param id The ID, primary key, for the User.
     * @return The User associated with this id.
     * @throws SQLException if a database access error occurs
     *          or this method is called on on a closed connection.
     */
    public static User getById(int id) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.users WHERE User_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return getFromRow(resultSet);
        }

        return null;
    }

    /**
     * Returns a User object from the current cursor in a ResultSet.
     *
     * @param currentRow A ResultSet that contains every column of the user table
     * @return A User represented by the current row/cursor position of the ResultSet
     * @throws SQLException if a database access error occurs
     *           or this method is called on on a closed connection.
     */
    private static User getFromRow(ResultSet currentRow) throws SQLException {
        int userId = currentRow.getInt("User_ID");
        String userName = currentRow.getString("User_Name");
        String password = currentRow.getString("Password");
        Timestamp createDate = currentRow.getTimestamp("Create_Date");
        String createdBy = currentRow.getString("Created_By");
        Timestamp lastUpdateDate = currentRow.getTimestamp("Last_Update");
        String lastUpdateBy = currentRow.getString("Last_Updated_By");

        return new User(userId, userName, password, createDate.toLocalDateTime().atZone(ZoneId.of("UTC")),
                createdBy, lastUpdateDate.toLocalDateTime().atZone(ZoneId.of("UTC")), lastUpdateBy);
    }

    /**
     * Queries the database for all Users.
     *
     * @return An ObservableList of Users from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ObservableList<User> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.users";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ObservableList<User> ret = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
    }

    /**
     * Get this User's ID.
     * @return This User's ID.
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Get this User's User Name.
     * @return This User's User Name.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Get this User's password.
     *
     * @return This User's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Each field of this object is appended to the output,
     * separated by a "|".
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        return  "User_ID: " + this.userId
                + " | User_Name: " + this.userName
                + " | Password: " + this.password
                + " | Create_Date: " + this.createdDate
                + " | Created_By: " + this.createdBy
                + " | Last_Update: " + this.lastUpdateDate
                + " | Last_Updated_By: " + this.lastUpdateBy;
    }

    /**
     * Returns true if the object passed
     * is a User and its ID is equal to this
     * User's ID.
     * @param other The object to compare.
     * @return True if this User's ID is equal to the passed
     * User's ID.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }
        User otherC = (User)other;
        return otherC.getUserId() == this.getUserId();
    }
}
