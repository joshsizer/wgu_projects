package datastructure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
     * Returns an Appointment based on its ID.
     *
     * @param id The ID, primary key, for the Appointment.
     * @return The Appointment associated with this id.
     * @throws SQLException if a database access error occurs
     *          or this method is called on on a closed connection.
     */
    public static Appointment getById(int id) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.appointments WHERE Appointment_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return getFromRow(resultSet);
        }

        return null;
    }

    /**
     * Returns Appointments based on its Customer_ID.
     *
     * @param id The Customer_ID, for the Appointment.
     * @return The Appointment associated with this id.
     * @throws SQLException if a database access error occurs
     *          or this method is called on on a closed connection.
     */
    public static ObservableList<Appointment> getByCustomerId(int id) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.appointments WHERE Customer_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        ObservableList<Appointment> ret = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
    }

    /**
     * Returns an Appointment based on its Contact_ID.
     *
     * @param id The Contact_ID appointment associated with this id.
     * @throws SQLException if a database access error occurs
     *          or this method is called on on a closed connection.
     */
    public static ObservableList<Appointment> getByContactId(int id) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.appointments WHERE Contact_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        ObservableList<Appointment> ret = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
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
     * @return An ObservableList of Appointments from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ObservableList<Appointment> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.appointments";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ObservableList<Appointment> ret = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
    }

    public static ObservableList<Appointment> getBetween(ZonedDateTime start, ZonedDateTime end) throws SQLException {
        ObservableList<Appointment> toRet = FXCollections.observableArrayList();
        for (Appointment app : Appointment.getAll()) {
            if ((app.getStartZonedDateTime().isAfter(start)
                    || app.getStartZonedDateTime().equals(start))
                    && (app.getStartZonedDateTime().isBefore(end) || app.getStartZonedDateTime().equals(end))) {
                toRet.add(app);
            }
        }
        return toRet;
    }

    /**
     * Returns the next available ID value for a new Appointment.
     * @return The next available ID.
     * @throws SQLException if a database access error occurs
     *       or this method is called on on a closed connection.
     */
    public static int getNextId() throws SQLException {
        String sql = "SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.TABLES\n" +
                "WHERE TABLE_SCHEMA = \"WJ07mIl\"\n" +
                "AND TABLE_NAME = \"appointments\"";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("AUTO_INCREMENT");
        }

        return -1;
    }

    /**
     * If an Appointment with this Appointment_ID exists
     * in the database, then that record is updated.
     * If not, a new record is inserted.
     * @throws SQLException if a database access error occurs
     *             or this method is called on on a closed connection.
     */
    public void saveToDb() throws SQLException {
        String updateSql = "UPDATE WJ07mIl.appointments\n" +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?," +
                " Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? \n" +
                "WHERE Appointment_ID = ?";
        String insertSql = "INSERT INTO WJ07mIl.appointments\n" +
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                " Customer_ID, User_ID, Contact_ID) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));

        if (Appointment.getById(this.getAppointmentId()) == null) {
            stmt = ConnectionManager.getConnection().prepareStatement(insertSql);
            stmt.setString(1, this.title);
            stmt.setString(2, this.description);
            stmt.setString(3, this.location);
            stmt.setString(4, this.type);
            stmt.setString(5, formatter.format(this.start));
            stmt.setString(6, formatter.format(this.end));
            stmt.setString(7, formatter.format(this.createdDate));
            stmt.setString(8, this.createdBy);
            stmt.setString(9, formatter.format(this.lastUpdateDate));
            stmt.setString(10, this.lastUpdateBy);
            stmt.setInt(11, this.customerId);
            stmt.setInt(12, this.userId);
            stmt.setInt(13, this.contactId);
        } else {
            stmt = ConnectionManager.getConnection().prepareStatement(updateSql);
            stmt.setString(1, this.title);
            stmt.setString(2, this.description);
            stmt.setString(3, this.location);
            stmt.setString(4, this.type);
            stmt.setString(5, formatter.format(this.start));
            stmt.setString(6, formatter.format(this.end));
            stmt.setString(7, formatter.format(this.lastUpdateDate));
            stmt.setString(8, this.lastUpdateBy);
            stmt.setInt(9, this.customerId);
            stmt.setInt(10, this.userId);
            stmt.setInt(11, this.contactId);
            stmt.setInt(12, this.appointmentId);
        }

        stmt.execute();
    }

    /**
     * The Appointment row in the database with this
     * Appointment's ID is deleted.
     * @throws SQLException if a database access error occurs
     *                   or this method is called on on a closed connection.
     */
    public void deleteFromDb() throws SQLException {
        String sql = "DELETE FROM WJ07mIl.appointments WHERE Appointment_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, this.appointmentId);
        stmt.execute();
    }

    /**
     * Get this Appointment's ID.
     * @return This Appointment's ID.
     */
    public int getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Get this Appointment's Title.
     * @return This Appointment's Title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get this Appointment's Description.
     * @return This Appointment's Description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get this Appointment's Location.
     * @return This Appointment's Location.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Get this Appointment's Type.
     * @return This Appointment's Type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get this Appointment's Customer ID.
     * @return This Appointment's Customer ID.
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Get this Appointment's User ID.
     * @return This Appointment's User ID.
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Get this Appointment's Contact ID.
     * @return This Appointment's Contact ID.
     */
    public int getContactId() {
        return this.contactId;
    }

    /**
     * Get this Appointment's Contact Name.
     * @return This Appointment's Contact Name.
     * @throws SQLException if a database access error occurs
     *      * or this method is called on on a closed connection.
     */
    public String getContactName() throws SQLException {
        return Contact.getById(getContactId()).getContactName();
    }

    /**
     * Get this Appointment's Start Date Time.
     * @return This Appointment's Start Date Time.
     */
    public String getStartDateTime() {
        return this.start.withZoneSameInstant(ZoneId.systemDefault()).toString();
    }

    /**
     * Get this Appointment's Start ZonedDateTime.
     * @return This Appointment's Start ZonedDateTime.
     */
    public ZonedDateTime getStartZonedDateTime() {
        return this.start;
    }

    /**
     * Get this Appointment's End ZonedDateTime.
     * @return This Appointment's End ZonedDateTime.
     */
    public ZonedDateTime getEndZonedDateTime() {
        return this.end;
    }

    /**
     * Get this Appointment's End Date Time.
     * @return This Appointment's End Date Time.
     */
    public String getEndDateTime() {
        return this.end.withZoneSameInstant(ZoneId.systemDefault()).toString();
    }

    /**
     * Get the Appointment's Start Date formatted
     * 'yyyy-MM-dd'.
     * @return The Appointment's Start Date only in string format.
     */
    public String getStartDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
        return formatter.format(this.start);
    }

    /**
     * Get the Appointment's End Date formatted
     * 'yyyy-MM-dd'.
     * @return The Appointment's End Date only in string format.
     */
    public String getEndDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
        return formatter.format(this.end);
    }

    /**
     * Get the Appointment's Start time formatted
     * 'HH:mm:ss'.
     * @return The Appointment's Start time in string format.
     */
    public String getStartTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
        return formatter.format(this.start);
    }

    /**
     * Get the Appointment's End time formatted
     * 'HH:mm:ss'.
     * @return The Appointment's End time in string format.
     */
    public String getEndTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
        return formatter.format(this.end);
    }

    /**
     * Set this Appointment's ID.
     * @param id The new ID for this Appointment.
     */
    public void setAppointmentId(int id) {
        this.appointmentId = id;
    }

    /**
     * Set this Appointment's Title.
     * @param title The new Title for this Appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set this Appointment's Description.
     * @param description The new Description for this Appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set this Appointment's Location.
     * @param location The new Location for this Appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Set this Appointment's Type.
     * @param type The new Type for this Appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Set this Appointment's Contact ID.
     * @param contactId The new Contact ID for this Appointment.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Set this Appointment's Customer ID.
     * @param customerId The new Customer ID for this Appointment.
     */
    public void setCustomerId(int customerId) {
       this.customerId = customerId;
    }

    /**
     * Set this Appointment's User ID.
     * @param userId The new User ID for this Appointment.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Set this Appointment's Start Date Time.
     * @param startDateTime The new Appointment's Start Time.
     */
    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.start = startDateTime;
    }

    /**
     * Set this Appointment's End Date Time.
     * @param endDateTime The new Appointment's End Time.
     */
    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.end = endDateTime;
    }

    /**
     * Set this Appointment's Last Update Date.
     * @param lastUpdateDate The new Last Update Date for this Appointment.
     */
    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Set this Appointment's Last Update By.
     * @param lastUpdateBy The new Last Update By for this Appointment.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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
