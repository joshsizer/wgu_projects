package datastructure;

import main.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Represents Appointments in this Application.
 * Mirrors the appointments table in the database.
 */
public class Appointment {
    /**
     * The primary key, Appointment_ID. Unique for every appointment.
     */
    private int appointmentId;

    /**
     * The title for this Appointment.
     */
    private String title;

    /**
     * The description for this Appointment.
     */
    private String description;

    /**
     * The location for this Appointment.
     */
    private String location;

    /**
     * The type for this Appointment.
     */
    private String type;

    /**
     * The Customer_ID for this Appointment.
     */
    private int customerId;

    /**
     * The User_ID for this Appointment.
     */
    private int userId;

    /**
     * The Contact_ID for this Appointment.
     */
    private int contactId;

    /**
     * The start date and time for this Appointment.
     */
    private ZonedDateTime start;

    /**
     * The end date and time for this Appointment.
     */
    private ZonedDateTime end;

    /**
     * The date this Appointment was created.
     */
    private ZonedDateTime createdDate;

    /**
     * Information on who created this Appointment.
     */
    private String createdBy;

    /**
     * When this Appointment's data/information was
     * last updated.
     */
    private ZonedDateTime lastUpdateDate;

    /**
     * Information on who last updated this
     * Appointment's information.
     */
    private String lastUpdateBy;

    /**
     * Create an appointment.
     *
     * @param appointmentId The id for this Appointment.
     * @param title The title for this Appointment.
     * @param description The description for this Appointment.
     * @param location The location for this Appointment.
     * @param customerId The Customer_ID for this Appointment.
     * @param userId The User_ID for this Appointment.
     * @param contactId The Contact_ID for this Appointment.
     * @param type The type for this Appointment.
     * @param start The start of this Appointment.
     * @param end The end of this Appointment.
     * @param createdDate The date when this Appointment was created.
     * @param createdBy Who created this Appointment.
     * @param lastUpdateDate When this Appointment was last updated.
     * @param lastUpdateBy Who last updated this Appointment.
     */
    public Appointment(int appointmentId, String title, String description,
                       String location, String type, int customerId,
                       int userId, int contactId, ZonedDateTime start,
                       ZonedDateTime end, ZonedDateTime createdDate,
                       String createdBy, ZonedDateTime lastUpdateDate,
                       String lastUpdateBy) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Returns an Appointment object from the current cursor in a ResultSet.
     *
     * @param currentRow A ResultSet that contains every column of the appointments table
     * @return An Appointment represented by the current row/cursor position of the ResultSet
     * @throws SQLException if a database access error occurs
     *           or this method is called on on a closed connection.
     */
    private static Appointment getFromRow(ResultSet currentRow) throws SQLException {
        int appointmentId = currentRow.getInt("Appointment_ID");
        String title = currentRow.getString("Title");
        String description = currentRow.getString("Description");
        String location = currentRow.getString("Location");
        String type = currentRow.getString("Type");
        int customerId = currentRow.getInt("Customer_ID");
        int userId = currentRow.getInt("User_ID");
        int contactId = currentRow.getInt("Contact_ID");
        Timestamp start = currentRow.getTimestamp("Start");
        Timestamp end = currentRow.getTimestamp("End");
        Timestamp createDate = currentRow.getTimestamp("Create_Date");
        String createdBy = currentRow.getString("Created_By");
        Timestamp lastUpdateDate = currentRow.getTimestamp("Last_Update");
        String lastUpdateBy = currentRow.getString("Last_Updated_By");

        return new Appointment(appointmentId, title, description, location,
                type, customerId, userId, contactId,
                start.toLocalDateTime().atZone(ZoneId.of("UTC")),
                end.toLocalDateTime().atZone(ZoneId.of("UTC")),
                createDate.toLocalDateTime().atZone(ZoneId.of("UTC")),
                createdBy, lastUpdateDate.toLocalDateTime().atZone(ZoneId.of("UTC")), lastUpdateBy);
    }

    /**
     * Queries the database for all Appointments.
     *
     * @return An ArrayList of Appointments from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ArrayList<Appointment> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.appointments";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ArrayList<Appointment> ret = new ArrayList<>();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
    }

    /**
     * Each field of this object is appended to the output,
     * separated by a "|".
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        return  "Appointment_ID: " + this.appointmentId
                + " | Title: " + this.title
                + " | Description: " + this.description
                + " | Location: " + this.location
                + " | Type: " + this.type
                + " | Customer_ID " + this.customerId
                + " | User_ID " + this.userId
                + " | Contact_ID " + this.contactId
                + " | Start " + this.start
                + " | End " + this.end
                + " | Create_Date: " + this.createdDate
                + " | Created_By: " + this.createdBy
                + " | Last_Update: " + this.lastUpdateDate
                + " | Last_Updated_By: " + this.lastUpdateBy;
    }
}
