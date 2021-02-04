package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Customers class declaration
 */
public class Customers {
    private int customerId; //Primary Key
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId; // Foreign Key from FirstLevelDivisions class

    /**
     * Constructor for Customers class
     * @param customerId customer ID (primary key)
     * @param customerName customer name
     * @param address address
     * @param postalCode postal code
     * @param phone phone
     * @param createDate created date
     * @param createdBy created by
     * @param lastUpdate last update time
     * @param lastUpdatedBy last updated by
     * @param divisionId division id (foreign key from FirstLevelDivisions class)
     */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone,
                     Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * Getter method of customer ID
     * @return the customer Id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method of customer ID
     * @param customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for customer name
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter method for customer name
     * @param customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter method for address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for address
     * @param address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for postal code
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter method for postal code
     * @param postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter method for phone number
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for phone number
     * @param phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter method for created Date
     * @return create date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Setter method for created date
     * @param createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter method for created by
     * @return the name who created the customer
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for created by
     * @param createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter method for the last update
     * @return the last update time
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter method for the last update
     * @param lastUpdate to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter method for the last updated by
     * @return the name of who last updated
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter method for the last updated by
     * @param lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter method for division ID
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter method for division ID
     * @param divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
