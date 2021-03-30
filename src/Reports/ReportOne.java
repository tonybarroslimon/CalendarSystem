package Reports;

/**
 * ReportOne Class Declaration
 */
public class ReportOne {
    private String type;
    private int numberOfAppointments;

    /**
     * ReportOne class constructor
     * @param type
     * @param numberOfAppointments
     */
    public ReportOne(String type, int numberOfAppointments) {
        this.type = type;
        this.numberOfAppointments = numberOfAppointments;
    }

    /**
     * Getter Type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for Type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Number of appointments
     * @return numberOfAppointments
     */
    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    /**
     * Setter for Number of Appointments
     * @param numberOfAppointments
     */
    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
