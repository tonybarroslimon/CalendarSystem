package Reports;

public class ReportOne {
    private String type;
    private int numberOfAppointments;

    public ReportOne(String type, int numberOfAppointments) {
        this.type = type;
        this.numberOfAppointments = numberOfAppointments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
