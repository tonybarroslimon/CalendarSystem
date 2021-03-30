package Reports;

/**
 * ReportThree class declaration
 */
public class ReportThree {
    int customerId;
    int numberOfAppointments;

    /**
     * ReportThree class constructor
     * @param customerId
     * @param numberOfAppointments
     */
    public ReportThree(int customerId, int numberOfAppointments) {
        this.customerId = customerId;
        this.numberOfAppointments = numberOfAppointments;
    }

    /**
     * Getter for Customer ID
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for Customer Id
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for number of appointments
     * @return numberOfAppointments
     */
    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    /**
     * Setter for number of appointments
     * @param numberOfAppointments
     */
    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
