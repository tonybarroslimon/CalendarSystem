package Model;

/**
 * Contacts Class Declaration
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private String email;

    /**
     * Contacts Class Constructor
     * @param contactId
     * @param contactName
     * @param email
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
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

    /**
     * Getter for Contact name
     * @return Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for Contact Name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for Email
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for Email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
