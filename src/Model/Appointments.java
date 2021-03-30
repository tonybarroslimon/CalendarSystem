package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Appointments class declaration
 */
public class Appointments {
    private int appointmentId; // Primary Key
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId; // foreign key from the Customer class
    private int userId; // foreign key from the Users class
    private int contactId; // foreign key from the Contacts class

    /**
     * Appointments class constructor
     * @param appointmentId appointment id
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start
     * @param end appointment end
     * @param createDate appointment created date
     * @param createdBy appointment created by
     * @param lastUpdate appointment last update
     * @param lastUpdatedBy appointment last updated by
     * @param customerId appointment customer ID (foreign key from Customers class)
     * @param userId appointment user ID (foreign key from Users class)
     * @param contactId appointment contact ID (foreign key from Contacts class)
     */
    public Appointments(int appointmentId, String title, String description, String location, String type,
                        LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                        int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Getter method for appointment ID
     * @return appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Setter method for appointment ID
     * @param appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter method for appointment title
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for appointment title
     * @param title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for appointment description
     * @return appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for appointment Description
     * @param description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for appointment Location
     * @return appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for appointment Location
     * @param location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter method for appointment type
     * @return appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for appointment type
     * @param type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Start
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter for Start
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter for End
     * @return End
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter for End
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
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

    /**
     * Getter for Customer ID
     * @return Customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for Customer ID
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for User ID
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for User ID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for Contact ID
     * @return Contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter for Contact ID
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
