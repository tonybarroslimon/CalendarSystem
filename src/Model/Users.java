package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Establishes the Users Class
 */
public class Users {
    private int userId; // Primary Key
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * Users Constructor
     * @param userId
     * @param userName
     * @param password
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public Users(int userId, String userName, String password, LocalDateTime createDate, String createdBy, Timestamp lastUpdate,
                 String lastUpdatedBy) {
        this.userId = userId; //
        this.userName = userName; //
        this.password = password; //
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getting method for User ID
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method for User ID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter method for User Name
     * @return the User Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for User Name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for Password
     * @return the Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for Password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for the Create Date
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
     * Getter method for the Created By Field
     * @return who Created the user
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for the created by field
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter method of getting the last update date
     * @return last update date
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter method for the last update date
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter method for the last updated by
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter method for the last updated by
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
