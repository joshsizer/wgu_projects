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
 * Represents the Customers in this Application.
 * Mirrors the customers table in the database.
 */
public class Customer {

    /**
     * The primary key, Customer_ID. Unique for every customer.
     */
    private int customerId;

    /**
     * The name for this Customer.
     */
    private String customerName;

    /**
     * This Customer's address. Does not include country or
     * first-level-division.
     */
    private String address;

    /**
     * This Customer's postal code.
     */
    private String postalCode;

    /**
     * This Customer's Phone number
     */
    private String phone;

    /**
     * This Customer's division id.
     */
    private int divisionId;

    /**
     * The date this Customer was created.
     */
    private ZonedDateTime createdDate;

    /**
     * Information on who created this Customer.
     */
    private String createdBy;

    /**
     * When this Customer's data/information was
     * last updated.
     */
    private ZonedDateTime lastUpdateDate;

    /**
     * Information on who last updated this
     * Customer's information.
     */
    private String lastUpdateBy;

    /**
     * Create a Customer.
     *
     * @param customerId The id for this Customer.
     * @param customerName The name of this Customer.
     * @param address The address of this Customer.
     * @param postalCode The postal code of this Customer.
     * @param phone The phone number of this Customer.
     * @param divisionId The division ID for this Customer.
     * @param createdDate The date when this Customer was created.
     * @param createdBy Who created this Customer.
     * @param lastUpdateDate When this Customer was last updated.
     * @param lastUpdateBy Who last updated this Customer.
     */
    public Customer(int customerId, String customerName, String address,
                    String postalCode, String phone, int divisionId,
                    ZonedDateTime createdDate, String createdBy, ZonedDateTime lastUpdateDate,
                    String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Returns a Customer based on its ID.
     *
     * @param id The ID, primary key, for the Customer.
     * @return The Customer associated with this id.
     * @throws SQLException if a database access error occurs
     *          or this method is called on on a closed connection.
     */
    public static Customer getById(int id) throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.customers WHERE Customer_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return getFromRow(resultSet);
        }

        return null;
    }

    /**
     * Returns a Customer object from the current cursor in a ResultSet.
     *
     * @param currentRow A ResultSet that contains every column of the customers table
     * @return A Customer represented by the current row/cursor position of the ResultSet
     * @throws SQLException if a database access error occurs
     *           or this method is called on on a closed connection.
     */
    private static Customer getFromRow(ResultSet currentRow) throws SQLException {
        int customerId = currentRow.getInt("Customer_ID");
        String customerName = currentRow.getString("Customer_Name");
        String address = currentRow.getString("Address");
        String postalCode = currentRow.getString("Postal_Code");
        String phone = currentRow.getString("Phone");
        int divisionId = currentRow.getInt("Division_ID");
        Timestamp createDate = currentRow.getTimestamp("Create_Date");
        String createdBy = currentRow.getString("Created_By");
        Timestamp lastUpdateDate = currentRow.getTimestamp("Last_Update");
        String lastUpdateBy = currentRow.getString("Last_Updated_By");

        return new Customer(customerId, customerName, address, postalCode, phone, divisionId, createDate.toLocalDateTime().atZone(ZoneId.of("UTC")),
                createdBy, lastUpdateDate.toLocalDateTime().atZone(ZoneId.of("UTC")), lastUpdateBy);
    }

    /**
     * Queries the database for all Customers.
     *
     * @return An ObservableList of Customers from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ObservableList<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.customers";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ObservableList<Customer> ret = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ret.add(getFromRow(resultSet));
        }

        return ret;
    }

    /**
     * Returns the next available ID value for a new Customer.
     * @return The next available ID.
     * @throws SQLException if a database access error occurs
     *       or this method is called on on a closed connection.
     */
    public static int getNextId() throws SQLException {
        String sql = "SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.TABLES\n" +
                "WHERE TABLE_SCHEMA = \"WJ07mIl\"\n" +
                "AND TABLE_NAME = \"customers\"";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("AUTO_INCREMENT");
        }

        return -1;
    }

    /**
     * If a Customer with this Customer_ID exists
     * in the database, then that record is updated.
     * If not, a new record is inserted.
     * @throws SQLException if a database access error occurs
     *             or this method is called on on a closed connection.
     */
    public void saveToDb() throws SQLException {
        String updateSql = "UPDATE WJ07mIl.customers\n" +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?\n" +
                "WHERE Customer_ID = ?";
        String insertSql = "INSERT INTO WJ07mIl.customers\n" +
                "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));

        if (Customer.getById(this.getCustomerId()) == null) {
            stmt = ConnectionManager.getConnection().prepareStatement(insertSql);
            stmt.setString(1, this.customerName);
            stmt.setString(2, this.address);
            stmt.setString(3, this.postalCode);
            stmt.setString(4, this.phone);
            stmt.setString(5, formatter.format(this.createdDate));
            stmt.setString(6, this.createdBy);
            stmt.setString(7, formatter.format(this.lastUpdateDate));
            stmt.setString(8, this.lastUpdateBy);
            stmt.setInt(9, this.divisionId);
        } else {
            stmt = ConnectionManager.getConnection().prepareStatement(updateSql);
            stmt.setString(1, this.customerName);
            stmt.setString(2, this.address);
            stmt.setString(3, this.postalCode);
            stmt.setString(4, this.phone);
            stmt.setString(5, formatter.format(this.lastUpdateDate));
            stmt.setString(6, this.lastUpdateBy);
            stmt.setInt(7, this.divisionId);
            stmt.setInt(8, this.customerId);
        }

        stmt.execute();
    }

    /**
     * The Customer row in the database with this
     * Customer's ID is deleted.
     * @throws SQLException if a database access error occurs
     *                   or this method is called on on a closed connection.
     */
    public void deleteFromDb() throws SQLException {
        String sql = "DELETE FROM WJ07mIl.customers WHERE Customer_ID = ?";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        stmt.setInt(1, this.customerId);
        stmt.execute();
    }

    /**
     * Get this Customer's Customer_ID
     * @return This Customer's Customer_ID
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Get this Customer's Name.
     * @return This Customer's Name.
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Get this Customer's Address.
     * @return This Customer's Address.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Get this Customer's Postal Code.
     * @return This Customer's Postal Code.
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * Get this Customer's Phone Number.
     * @return This Customer's Phone Number.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Get this Customer's Division_ID.
     * @return This Customer's Division_ID.
     */
    public int getDivisionId() {
        return this.divisionId;
    }

    /**
     * Get this Customer's Division Name.
     * @return This Customer's Division Name.
     * @throws SQLException if a database access error occurs
     *      * or this method is called on on a closed connection.
     */
    public String getDivisionName() throws SQLException {
        return FirstLevelDivision.getById(getDivisionId()).getDivisionName();
    }

    /**
     * Get this Customer's Country.
     * @return This Customer's Country.
     * @throws SQLException if a database access error occurs
     *      * or this method is called on on a closed connection.
     */
    public String getCountryName() throws SQLException {
        return Country.getById(FirstLevelDivision.getById(getDivisionId()).getCountryId()).getCountryName();
    }

    /**
     * Set this Customer's ID.
     * @param id The new Customer ID.
     */
    public void setCustomerId(int id) {
        this.customerId = id;
    }

    /**
     * Set this Customer's Name.
     * @param name The new Name for this Customer.
     */
    public void setCustomerName(String name) {
        this.customerName = name;
    }

    /**
     * Set this Customer's Address.
     * @param address The new Address for this Customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set this Customer's Postal Code.
     * @param postalCode The new Postal Code for this Customer.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Set this Customer's Phone Number.
     * @param phone The new Phone Number for this Customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Set this Customer's Division ID.
     * @param id The new Division ID for this Customer.
     */
    public void setDivisionId(int id) {
        this.divisionId = id;
    }

    /**
     * Set this Customer's Last Update Date.
     * @param lastUpdateDate The new Last Update Date for this Customer.
     */
    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Set this Customer's Last Update By.
     * @param lastUpdateBy The new Last Update By for this Customer.
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
        return  "Customer_ID: " + this.customerId
                + " | Customer_Name: " + this.customerName
                + " | Address: " + this.address
                + " | Postal_Code: " + this.postalCode
                + " | Phone: " + this.phone
                + " | Division_ID " + this.divisionId
                + " | Create_Date: " + this.createdDate
                + " | Created_By: " + this.createdBy
                + " | Last_Update: " + this.lastUpdateDate
                + " | Last_Updated_By: " + this.lastUpdateBy;
    }

    /**
     * Returns true if the object passed
     * is a Customer and its ID is equal to this
     * Customer's ID.
     * @param other The object to compare.
     * @return True if this Customer's ID is equal to the passed
     * object's ID.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Customer)) {
            return false;
        }
        Customer otherC = (Customer)other;
        return otherC.getCustomerId() == this.getCustomerId();
    }
}
