package Reports;

public class ReportThree {
    int customerId;
    int numberOfAppointments;

    public ReportThree(int customerId, int numberOfAppointments) {
        this.customerId = customerId;
        this.numberOfAppointments = numberOfAppointments;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
