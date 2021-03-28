package ViewController;

import Model.Appointments;
import Model.Customers;
import Model.Users;
import Utilites.ConnectDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ResourceBundle;
import static ViewController.LoginScreenController.getActiveUser;

/**
 * The Main Screen Controller
 */
public class MainScreenController implements Initializable {

    @FXML private RadioButton weeklyViewRadioButton;
    @FXML private RadioButton monthlyViewRadioButton;
    @FXML private TableView<Appointments> appointmentsTableView;
    @FXML private TableColumn<Appointments, Integer> appointmentIDTableColumn;
    @FXML private TableColumn<Appointments, String> titleTableColumn;
    @FXML private TableColumn<Appointments, String> descriptionTableColumn;
    @FXML private TableColumn<Appointments, String> locationTableColumn;
    @FXML private TableColumn<Appointments, String> contactTableColumn;
    @FXML private TableColumn<Appointments, String> typeTableColumn;
    @FXML private TableColumn<Appointments, Date> startTableColumn;
    @FXML private TableColumn<Appointments, Date> endTableColumn;
    @FXML private TableColumn<Appointments, Integer> customerIDTableColumn;
    @FXML private Button addAppointmentButton;
    @FXML private Button modifyAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, String> nameTableColumn;
    @FXML private TableColumn<Customers, String> addressTableColumn;
    @FXML private TableColumn<Customers, String> firstLevelTableColumn;
    @FXML private TableColumn<Customers, String> postalCodeTableColumn;
    @FXML private TableColumn<Customers, String> phoneTableColumn;
    @FXML private Button addCustomerButton;
    @FXML private Button modifyCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button reportOneButton;
    @FXML private Button reportTwoButton;
    @FXML private Button reportThreeButton;
    @FXML private Button exitButton;
    @FXML private DatePicker datePicker;
    @FXML private LocalDate date;
    @FXML private ResultSet customersResultSet;
    @FXML private static ObservableList<Customers> customersObject = FXCollections.observableArrayList();
    @FXML private ResultSet appointmentsResultSet;
    @FXML private static ObservableList<Appointments> appointmentsObject = FXCollections.observableArrayList();
    @FXML private Users user = getActiveUser();

    private static Customers selectedCustomer;
    private static Appointments selectedAppointment;

    private ToggleGroup radioButtonSelected = new ToggleGroup();

    /**
     *
     * @param fxmlScreen
     * @param actionEvent
     * @param title
     * @throws Exception
     */
    @FXML public void loadNewScreen(String fxmlScreen, ActionEvent actionEvent, String title) throws Exception{
        Parent newScreen = FXMLLoader.load(getClass().getResource(fxmlScreen));
        Scene newScene = new Scene(newScreen);
        Stage newStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newStage.setTitle(title);
        newStage.setScene(newScene);
        newStage.show();
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    @FXML public void radioButtonChanged(ActionEvent event) throws Exception{
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();

        try{
            if (this.radioButtonSelected.getSelectedToggle().equals(this.weeklyViewRadioButton)){
                if (date == null) {
                    LocalDate weeklyLocalTime = LocalDate.now();
                    int weekNumber = weeklyLocalTime.get(WeekFields.SUNDAY_START.weekOfYear());

                    for (Appointments weeklyAppointments : appointmentsObject) {
                        appointmentsTableView.getItems().clear();
                        appointmentsTableView.refresh();
                        LocalDate startDateNoTime = weeklyAppointments.getStart().toLocalDate();
                        int appointmentWeekNumber = startDateNoTime.get(WeekFields.SUNDAY_START.weekOfYear());
                        if (weekNumber == appointmentWeekNumber) {
                            filteredAppointments.add(weeklyAppointments);
                            appointmentsTableView.setItems(filteredAppointments);
                        }
                    }
                    if (appointmentsTableView == null || appointmentsTableView.getItems().size() == 0) {
                        appointmentsTableView.refresh();
                    }
                } else {
                    int weekNumber = date.get(WeekFields.SUNDAY_START.weekOfYear());

                    for (Appointments weeklyAppointments : appointmentsObject) {
                        appointmentsTableView.getItems().clear();
                        appointmentsTableView.refresh();
                        LocalDate startDateNoTime = weeklyAppointments.getStart().toLocalDate();
                        int appointmentWeekNumber = startDateNoTime.get(WeekFields.SUNDAY_START.weekOfYear());
                        if (weekNumber == appointmentWeekNumber) {
                            filteredAppointments.add(weeklyAppointments);
                            appointmentsTableView.setItems(filteredAppointments);
                        }
                    }
                    if (appointmentsTableView == null || appointmentsTableView.getItems().size() == 0) {
                        appointmentsTableView.refresh();
                    }
                }
            }
            if (this.radioButtonSelected.getSelectedToggle().equals(this.monthlyViewRadioButton)){
                if (date == null) {
                    LocalDate monthlyLocalTime = LocalDate.now();
                    YearMonth monthlyYearMonth = YearMonth.from(monthlyLocalTime);

                    for (Appointments monthlyAppointments : appointmentsObject) {
                        appointmentsTableView.getItems().clear();
                        appointmentsTableView.refresh();
                        LocalDate startDateNoTime = monthlyAppointments.getStart().toLocalDate();
                        YearMonth convertedYearMonth = YearMonth.from(startDateNoTime);
                        if (monthlyYearMonth.equals(convertedYearMonth)) {
                            filteredAppointments.add(monthlyAppointments);
                            appointmentsTableView.setItems(filteredAppointments);
                        }

                    }
                    if (appointmentsTableView == null || appointmentsTableView.getItems().size() == 0) {
                        appointmentsTableView.refresh();
                    }
                } else {
                    YearMonth monthlyYearMonth = YearMonth.from(date);

                    for (Appointments monthlyAppointments : appointmentsObject) {
                        appointmentsTableView.getItems().clear();
                        appointmentsTableView.refresh();
                        LocalDate startDateNoTime = monthlyAppointments.getStart().toLocalDate();
                        YearMonth convertedYearMonth = YearMonth.from(startDateNoTime);
                        if (monthlyYearMonth.equals(convertedYearMonth)) {
                            filteredAppointments.add(monthlyAppointments);
                            appointmentsTableView.setItems(filteredAppointments);
                        }

                    }
                    if (appointmentsTableView == null || appointmentsTableView.getItems().size() == 0) {
                        appointmentsTableView.refresh();
                    }
                }
            }

        } catch (NullPointerException e) {
           appointmentsTableView.setPlaceholder(new Label("No appointments to display!"));
        }
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void addAppointmentButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("AddAppointmentScreen.fxml", buttonClicked, "Add Appointment");
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void modifyAppointmentButtonClicked(ActionEvent buttonClicked) throws Exception {
        selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            loadNewScreen("ModifyAppointmentScreen.fxml", buttonClicked, "Modify Appointment");
        }
        else {
            Alert blankAlert = new Alert(Alert.AlertType.ERROR);
            blankAlert.setTitle("Appointment Modification Error");
            blankAlert.setHeaderText("The appointment cannot be modified!");
            blankAlert.setContentText("There was no appointment selected!");
            blankAlert.showAndWait();
        }
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void deleteAppointmentButtonClicked(ActionEvent buttonClicked) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("Press OK to delete the appointment. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Appointments selectedAppointmentToDelete = appointmentsTableView.getSelectionModel().getSelectedItem();

                for (Appointments appointmentToDelete : appointmentsObject) {
                    if (appointmentToDelete.getAppointmentId() == selectedAppointmentToDelete.getAppointmentId()) {
                        appointmentsObject.remove(appointmentToDelete);

                        Connection conn = ConnectDB.makeConnection();
                        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM appointments "
                                        + "WHERE Appointment_ID = ?");
                        preparedStatement.setInt(1, appointmentToDelete.getAppointmentId());

                        int appointmentDeletion = preparedStatement.executeUpdate();

                        System.out.println("Appointment was deleted successfully!");
                    }
                }
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Appointment Deletion Error");
                nullalert.setHeaderText("The appointment was NOT deleted!");
                nullalert.setContentText("There was no appointment selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void addCustomerButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("AddCustomerScreen.fxml", buttonClicked, "Add Customer");
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void modifyCustomerButtonClicked(ActionEvent buttonClicked) throws Exception {
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            loadNewScreen("ModifyCustomerScreen.fxml", buttonClicked, "Modify Customer");
        }
        else {
            Alert blankAlert = new Alert(Alert.AlertType.ERROR);
            blankAlert.setTitle("Customer Modification Error");
            blankAlert.setHeaderText("The customer cannot be modified!");
            blankAlert.setContentText("There was no customer selected!");
            blankAlert.showAndWait();
        }
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void deleteCustomerButtonClicked(ActionEvent buttonClicked) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Are you sure you want to delete this customer?");
        alert.setContentText("Press OK to delete the customer. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Customers selectedCustomerToDelete = customersTableView.getSelectionModel().getSelectedItem();

                for (Customers customerToDelete : customersObject) {
                    for (Appointments appointmentsToConfirm: appointmentsObject) {

                        if (selectedCustomerToDelete.getCustomerId() == appointmentsToConfirm.getCustomerId()) {
                            Alert associatedPartAlert = new Alert(Alert.AlertType.ERROR);
                            associatedPartAlert.setTitle("Customer Deletion Error");
                            associatedPartAlert.setHeaderText("The customer was NOT deleted!");
                            associatedPartAlert.setContentText("Selected customer has associated appointments!"
                                    + "\nDelete associated appointments, then try again");
                            associatedPartAlert.showAndWait();

                        } else if (customerToDelete.getCustomerId() == selectedCustomerToDelete.getCustomerId()) {
                            customersObject.remove(customerToDelete);

                            Connection conn = ConnectDB.makeConnection();
                            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM customers "
                                    + "WHERE Customer_ID = ?");
                            preparedStatement.setInt(1, customerToDelete.getCustomerId());

                            int customerDeletion = preparedStatement.executeUpdate();

                            System.out.println("Customer was deleted successfully!");

                            }
                        }
                    }
                } catch (NullPointerException e) {
                    Alert nullalert = new Alert(Alert.AlertType.ERROR);
                    nullalert.setTitle("Customer Deletion Error");
                    nullalert.setHeaderText("The customer was NOT deleted!");
                    nullalert.setContentText("There was no customer selected!");
                    nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void reportOneButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("ReportOne.fxml", buttonClicked, "Report One Screen");
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void reportTwoButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("ReportTwo.fxml", buttonClicked, "Report Two Screen");
    }

    /**
     *
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void reportThreeButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("ReportThree.fxml", buttonClicked, "Report Three Screen");
    }

    /**
     *
     * @param buttonClicked
     * @throws SQLException
     */
    @FXML public void exitButtonClicked (ActionEvent buttonClicked) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Calendar System");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage mainScreen = (Stage)((Node)buttonClicked.getSource()).getScene().getWindow();
            mainScreen.close();
        }
        else {
            alert.close();
        }
    }

    /**
     *
     * @return
     */
    public static Customers getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     *
     * @return
     */
    public static Appointments getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        TODO:
        Filter data by what radio button is pressed
        JavaDocs
        */

        monthlyViewRadioButton.setToggleGroup(radioButtonSelected);
        weeklyViewRadioButton.setToggleGroup(radioButtonSelected);

        // Lambda to handle the selection of a date on the Main Controller
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                date = datePicker.getValue();
                try {
                    radioButtonChanged((ActionEvent) t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {Connection conn = ConnectDB.makeConnection();
             PreparedStatement customerPreparedStatement = conn.prepareStatement("SELECT * FROM customers;");
            customersResultSet = customerPreparedStatement.executeQuery();

            while (customersResultSet.next()) {
                customersObject.addAll(new Customers(
                        customersResultSet.getInt("Customer_ID"),
                        customersResultSet.getString("Customer_Name"),
                        customersResultSet.getString("Address"),
                        customersResultSet.getString("Postal_Code"),
                        customersResultSet.getString("Phone"),
                        customersResultSet.getObject("Create_Date", LocalDateTime.class),
                        customersResultSet.getString("Created_By"),
                        customersResultSet.getTimestamp("Last_Update"),
                        customersResultSet.getString("Last_Updated_By"),
                        customersResultSet.getInt("Division_ID")
                ));
            }

            PreparedStatement appointmentPreparedStatement = conn.prepareStatement("SELECT * FROM appointments WHERE User_ID = ?;");
            appointmentPreparedStatement.setInt(1, user.getUserId());
            appointmentsResultSet = appointmentPreparedStatement.executeQuery();

            while (appointmentsResultSet.next()) {
                appointmentsObject.addAll(new Appointments(
                        appointmentsResultSet.getInt("Appointment_ID"),
                        appointmentsResultSet.getString("Title"),
                        appointmentsResultSet.getString("Description"),
                        appointmentsResultSet.getString("Location"),
                        appointmentsResultSet.getString("Type"),
                        appointmentsResultSet.getObject("Start", LocalDateTime.class),
                        appointmentsResultSet.getObject("End", LocalDateTime.class),
                        appointmentsResultSet.getObject("Create_Date", LocalDateTime.class),
                        appointmentsResultSet.getString("Created_By"),
                        appointmentsResultSet.getTimestamp("Last_Update"),
                        appointmentsResultSet.getString("Last_Updated_By"),
                        appointmentsResultSet.getInt("Customer_ID"),
                        appointmentsResultSet.getInt("User_ID"),
                        appointmentsResultSet.getInt("Contact_ID")
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        firstLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customersTableView.setItems(customersObject);
        customersTableView.setPlaceholder(new Label("No customers to display!"));

        appointmentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTableColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTableColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentsTableView.setItems(appointmentsObject);
        appointmentsTableView.setPlaceholder(new Label("No appointments to display!"));

    }
}
