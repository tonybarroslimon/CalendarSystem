package Reports;

public class ReportOne {
    private String type;
    private int month;
    private int numberOfAppointments;

    public ReportOne(String type, int month, int numberOfAppointments) {
        this.type = type;
        this.month = month;
        this.numberOfAppointments = numberOfAppointments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
