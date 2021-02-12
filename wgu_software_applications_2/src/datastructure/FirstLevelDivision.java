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
 * Represents the First Level Divisions in this Application.
 * A First Level Division is akin to a state or province. The
 * first way that a country is divided into differently named
 * locations.
 * Mirrors the first_level_divisions table in the database.
 */
public class FirstLevelDivision {

    /**
     * The primary key, Division_ID. Unique for every FLD.
     */
    private int divisionId;

    /**
     * This division's name
     */
    private String division;

    /**
     * The Country_ID for this FLD. The country this
     * fld belongs to.
     */
    private int countryId;

    /**
     * The date this FLD was created.
     */
    private ZonedDateTime createdDate;

    /**
     * Information on who created this FLD.
     */
    private String createdBy;

    /**
     * When this FLD's data/information was
     * last updated.
     */
    private ZonedDateTime lastUpdateDate;

    /**
     * Information on who last updated this
     * FLD's information.
     */
    private String lastUpdateBy;

    /**
     * Create a FLD.
     *
     * @param divisionId The id for this FLD.
     * @param division The name of this FLD.
     * @param countryId The country id of this FLD.
     * @param createdDate The date when this FLD was created.
     * @param createdBy Who created this FLD.
     * @param lastUpdateDate When this FLD was last updated.
     * @param lastUpdateBy Who last updated this FLD.
     */
    public FirstLevelDivision(int divisionId, String division, int countryId,
                              ZonedDateTime createdDate, String createdBy,
                              ZonedDateTime lastUpdateDate, String lastUpdateBy) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Returns a FLD object from the current cursor in a ResultSet.
     *
     * @param currentRow A ResultSet that contains every column of the FLD table
     * @return An FLD represented by the current row/cursor position of the ResultSet
     * @throws SQLException if a database access error occurs
     *           or this method is called on on a closed connection.
     */
    private static FirstLevelDivision getFromRow(ResultSet currentRow) throws SQLException {
        int divisionId = currentRow.getInt("Division_ID");
        String division = currentRow.getString("Division");
        int countryId = currentRow.getInt("COUNTRY_ID");
        Timestamp createDate = currentRow.getTimestamp("Create_Date");
        String createdBy = currentRow.getString("Created_By");
        Timestamp lastUpdateDate = currentRow.getTimestamp("Last_Update");
        String lastUpdateBy = currentRow.getString("Last_Updated_By");

        return new FirstLevelDivision(divisionId, division, countryId, createDate.toLocalDateTime().atZone(ZoneId.of("UTC")),
                createdBy, lastUpdateDate.toLocalDateTime().atZone(ZoneId.of("UTC")), lastUpdateBy);
    }

    /**
     * Queries the database for all FLDs.
     *
     * @return An ArrayList of FLDs from the database.
     * @throws SQLException if a database access error occurs
     * or this method is called on on a closed connection.
     */
    public static ArrayList<FirstLevelDivision> getAll() throws SQLException {
        String sql = "SELECT * FROM WJ07mIl.first_level_divisions";
        PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();

        ArrayList<FirstLevelDivision> ret = new ArrayList<>();

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
        return  "Division_ID: " + this.divisionId
                + " | Division: " + this.division
                + " | COUNTRY_ID: " + this.countryId
                + " | Create_Date: " + this.createdDate
                + " | Created_By: " + this.createdBy
                + " | Last_Update: " + this.lastUpdateDate
                + " | Last_Updated_By: " + this.lastUpdateBy;
    }
}
