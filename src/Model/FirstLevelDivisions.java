package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Establishes the First Level Divisions Class
 */
public class FirstLevelDivisions {
    private int divisionId; // Primary Key
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId; // Foreign Key from the Countries class

    /**
     * First Level Divisions Constructor
     * @param divisionId
     * @param division
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param countryId
     */
    public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate, String createdBy, Timestamp lastUpdate,
                               String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * Getter method for Division ID
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter method for Division ID
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter method for the Division
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter method for the Division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter method for Create Date
     * @return the Create Date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter method for the Create Date
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter method for Created By
     * @return the Created By
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for Created By
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter method for Last Update
     * @return the Last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for the Last Update
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter method for the Last Update By
     * @return The last Updated By
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for the Last update By
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter Method for Country ID
     * @return Country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter Method for Country ID
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
