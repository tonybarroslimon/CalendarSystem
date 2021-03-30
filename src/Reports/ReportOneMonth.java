package Reports;

/**
 * ReportOneMonth Class Declaration
 */
public class ReportOneMonth {
    private int month;
    private int numberOfMonthAppointments;

    /**
     * ReportOneMonth class constructor
     * @param month
     * @param numberOfMonthAppointments
     */
    public ReportOneMonth(int month, int numberOfMonthAppointments) {
        this.month = month;
        this.numberOfMonthAppointments = numberOfMonthAppointments;
    }

    /**
     * Getter for Month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter for Month
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Getter for Number of appointments
     * @return numberOfMonthAppointments
     */
    public int getNumberOfMonthAppointments() {
        return numberOfMonthAppointments;
    }

    /**
     * Setter for number of appointments
     * @param numberOfMonthAppointments
     */
    public void setNumberOfMonthAppointments(int numberOfMonthAppointments) {
        this.numberOfMonthAppointments = numberOfMonthAppointments;
    }
}
