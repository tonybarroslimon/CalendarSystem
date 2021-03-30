package Model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Countries Class Declaration
 */
public class Countries {
    private int countryId; // Primary Key
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * Countries class constructor
     * @param countryId
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public Countries(int countryId, String country, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for Country ID
     * @return Country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for Country ID
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Getter for Country
     * @return Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for Country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for Create Date
     * @return Create Date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter for Create Date
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for Created By
     * @return Created By
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for Created By
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for Last Update
     * @return Last Update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for Last Update
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for Last Updated By
     * @return Last Updated By
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for Last Updated By
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}
