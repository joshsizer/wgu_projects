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
     * @return An ArrayList of Customers from the database.
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
}
