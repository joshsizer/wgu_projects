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
 * Represents the Countries in this Application.
 * Mirrors the countries table in the database.
 */
public class Country {

    /**
     * The primary key, Country_ID. Unique for every Country.
     */
    private int countryId;

    /**
     * The country's name.
     */
    private String country;

    /**
     * The date this Country was created.
     */
    private ZonedDateTime createdDate;

    /**
     * Information on who created this Country.
     */
    private String createdBy;

    /**
     * When this Country's data/information was
     * last updated.
     */
    private ZonedDateTime lastUpdateDate;

    /**
     * Information on who last updated this
     * Country's information.
     */
    private String lastUpdateBy;

    /**
     * Create a Country.
     *
     * @param countryId The id for this Country.
     * @param country The name for this Country.
     * @param createdDate The date when this Country was created.
     * @param createdBy Who created this Country.
     * @param lastUpdateDate When this Country was last updated.
     * @param lastUpdateBy Who last updated this Country.
     */
    public Country(int countryId, String country,
                   ZonedDateTime createdDate, String createdBy,
                   ZonedDateTime lastUpdateDate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Returns a Country object from the current cursor in a ResultSet.
     *
     * @param currentRow A ResultSet that contains every column of the countries table
     * @return A Country represented by the current row/cursor position of the ResultSet
     * @throws SQLException if a database access error occurs
     *           or this method is called on on a closed connection.
     */
    private static Country getFromRow(ResultSet currentRow) throws SQLException {
        int countryId = currentRow.getInt("Country_ID");
        String country = currentRow.getString("Country");
        Timestamp createDate = currentRow.getTimestamp("Create_Date");
        String createdBy = currentRow.getString("Created_By");
        Timestamp lastUpdateDate = currentRow.getTimestamp("Last_Update");
        String lastUpdateBy = currentRow.getString("Last_Updated_By");

        return new Country(countryId, country, createDate.toLocalDateTime().atZone(ZoneId.of("UTC")),
                createdBy, lastUpdateDate.toLocalDateTime().atZone(ZoneId.of("UTC")), lastUpdateBy);
    }

    /**
     * Queries the database for all Countries.
     *
     * @return An ArrayList of Countries from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ArrayList<Country> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.countries";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ArrayList<Country> ret = new ArrayList<>();

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
        return  "Country_ID: " + this.countryId
                + " | Country: " + this.country
                + " | Create_Date: " + this.createdDate
                + " | Created_By: " + this.createdBy
                + " | Last_Update: " + this.lastUpdateDate
                + " | Last_Updated_By: " + this.lastUpdateBy;
    }
}
