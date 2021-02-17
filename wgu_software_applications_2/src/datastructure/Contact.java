package datastructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Represents a Contact in this Application.
 * Mirrors the contacts table in the database.
 */
public class Contact {

    /**
     * The primary key, Contact_ID. Unique for each Contact.
     */
    private int contactId;

    /**
     * The name of this Contact.
     */
    private String contactName;

    /**
     * The email for this Contact
     */
    private String email;

    /**
     * Create a Contact.
     *
     * @param contactId The id for this Contact.
     * @param contactName The name for this Contact.
     * @param email The email for this Contact.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Returns a Contact based on its ID.
     *
     * @param id The ID, primary key, for the Contact.
     * @return The Contact associated with this id.
     * @throws SQLException if a database access error occurs
     *          or this method is called on on a closed connection.
     */
    public static Contact getById(int id) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.contacts WHERE Contact_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return getFromRow(resultSet);
        }

        return null;
    }

    /**
     * Returns a Contact object from the current cursor in a ResultSet.
     *
     * @param currentRow A ResultSet that contains every column of the contacts table
     * @return A User represented by the current row/cursor position of the ResultSet
     * @throws SQLException if a database access error occurs
     *           or this method is called on on a closed connection.
     */
    private static Contact getFromRow(ResultSet currentRow) throws SQLException {
        int contactId = currentRow.getInt("Contact_ID");
        String contactName = currentRow.getString("Contact_Name");
        String email = currentRow.getString("Email");

        return new Contact(contactId, contactName, email);
    }

    /**
     * Queries the database for all Contacts.
     *
     * @return An ObservableList of Contacts from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ObservableList<Contact> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.contacts";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ObservableList<Contact> ret = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
    }

    /**
     * Get this Contact's ID.
     * @return This Contact's ID.
     */
    public int getContactId() {
        return this.contactId;
    }

    /**
     * Get this Contact's Name.
     * @return This Contact's Name.
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * Get this Contact's Email.
     * @return This Contact's Email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Each field of this object is appended to the output,
     * separated by a "|".
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        return  "Contact_ID: " + this.contactId
                + " | Contact_Name: " + this.contactName
                + " | Email: " + this.email;
    }
}
