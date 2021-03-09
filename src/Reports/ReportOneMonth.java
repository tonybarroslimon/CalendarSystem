package Reports;

public class ReportOneMonth {
    private int month;
    private int numberOfMonthAppointments;

    public ReportOneMonth(int month, int numberOfMonthAppointments) {
        this.month = month;
        this.numberOfMonthAppointments = numberOfMonthAppointments;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNumberOfMonthAppointments() {
        return numberOfMonthAppointments;
    }

    public void setNumberOfMonthAppointments(int numberOfMonthAppointments) {
        this.numberOfMonthAppointments = numberOfMonthAppointments;
    }
}
