package ViewController;

import Model.Appointments;
import Model.Contacts;
import Model.Users;
import Utilites.ConnectDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.SimpleFormatter;

import static Utilites.HelperMethods.appointmentTextFieldValidator;
import static Utilites.HelperMethods.emptyAppointmentTextFieldValidator;
import static ViewController.LoginScreenController.getActiveUser;
import static ViewController.MainScreenController.getSelectedAppointment;

/**
 * Initializes the Modify Appointment Controller
 */
public class ModifyAppointmentScreenController implements Initializable {

    @FXML private Label titleHeaderLabel;
    @FXML private Label appointmentIDLabel;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label contactLabel;
    @FXML private Label typeLabel;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label customerIDLabel;
    @FXML private Label userIDLabel;
    @FXML private TextField appointmentIDTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private ComboBox contactComboBox;
    @FXML private TextField typeTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox startTimeComboBox;
    @FXML private ComboBox startMinutesComboBox;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox endTimeComboBox;
    @FXML private ComboBox endMinutesComboBox;
    @FXML private TextField customerIDTextField;
    @FXML private TextField userIDTextField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private ObservableList<Contacts> contactsForComboBox = FXCollections.observableArrayList();
    private ResultSet contactResultSet;
    @FXML private String emptyAppointmentField = new String();
    @FXML private String appointmentTextField = new String();

    @FXML private ObservableList<String> hours = FXCollections.observableArrayList(
            "00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23");
    @FXML private ObservableList<String> minutes = FXCollections.observableArrayList(
            "00","15","30","45");
    private int contactID;
    @FXML private Appointments selectedAppointment;
    @FXML private int selectedAppointmentID;
    @FXML private LocalDateTime selectedAppointmentStartDate;
    @FXML private LocalDateTime selectedAppointmentEndDate;

    /**
     * Method for loading new scnee
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
     * Action for when the Save button is pressedd
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void saveButtonClicked(ActionEvent buttonClicked) throws Exception {
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String contact = contactComboBox.getSelectionModel().getSelectedItem().toString();
        String type = typeTextField.getText();
        String startDate = startDatePicker.getValue().toString();
        String startTime = startTimeComboBox.getSelectionModel().getSelectedItem().toString();
        String startMinutes = startMinutesComboBox.getSelectionModel().getSelectedItem().toString();
        String endDate = endDatePicker.getValue().toString();
        String endTime = endTimeComboBox.getSelectionModel().getSelectedItem().toString();
        String endMinutes = endMinutesComboBox.getSelectionModel().getSelectedItem().toString();
        String customerID = customerIDTextField.getText();
        String userID = userIDTextField.getText();


        // Grabs the active user for the last update by
        Users user = getActiveUser();

        // Grabs the system timestamp to be inserted into the database
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = user.getUserName();

        // For loop to find the contact ID
        for (Contacts selectedContact : contactsForComboBox) {
            if (selectedContact.getContactName() == contact) {
                contactID = selectedContact.getContactId();
            }
        }

        DateTimeFormatter start = DateTimeFormatter.ofPattern("yyyy/mm/dd");
        LocalDate startLocalDate = LocalDate.parse(startDate, start);
        LocalDateTime startLocalDateTime = LocalDateTime.of(
                startLocalDate.getYear(),
                startLocalDate.getMonthValue(),
                startLocalDate.getDayOfMonth(),
                Integer.parseInt(startTime),
                Integer.parseInt(startMinutes));

        DateTimeFormatter end = DateTimeFormatter.ofPattern("yyyy/mm/dd");
        LocalDate endLocalDate = LocalDate.parse(endDate, end);
        LocalDateTime endLocalDateTime = LocalDateTime.of(
                endLocalDate.getYear(),
                endLocalDate.getMonthValue(),
                endLocalDate.getDayOfMonth(),
                Integer.parseInt(endTime),
                Integer.parseInt(endMinutes));

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startLocalDateTime, ZoneId.systemDefault());
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endLocalDateTime, ZoneId.systemDefault());
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime startUTC = startZonedDateTime.withZoneSameInstant(utc);
        ZonedDateTime endUTC = endZonedDateTime.withZoneSameInstant(utc);

        emptyAppointmentField = emptyAppointmentTextFieldValidator(
                title,
                description,
                location,
                contact,
                type,
                startDate,
                startTime,
                startMinutes,
                endDate,
                endTime,
                endMinutes,
                customerID,
                userID,
                emptyAppointmentField);

        appointmentTextField = appointmentTextFieldValidator(
                startDate,
                startTime,
                startMinutes,
                endDate,
                endTime,
                endMinutes,
                customerID,
                userID,
                appointmentTextField);

        try {
            if (appointmentTextField.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment Modification Warning");
                alert.setHeaderText("The appointment was NOT modified!");
                alert.setContentText("Invalid data input in one or more fields!" + appointmentTextField + emptyAppointmentField);
                alert.showAndWait();
                appointmentTextField = "";
                emptyAppointmentField = "";
            } else {
                Connection conn = ConnectDB.makeConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ? "
                            + "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                            + "WHERE Appointment_ID = ?;");
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, type);
                preparedStatement.setObject(5, startUTC);
                preparedStatement.setObject(6, endUTC);
                preparedStatement.setTimestamp(7, lastUpdate);
                preparedStatement.setString(8, lastUpdatedBy);
                preparedStatement.setInt(9, Integer.parseInt(customerID));
                preparedStatement.setInt(10, Integer.parseInt(userID));
                preparedStatement.setInt(11, contactID);
                preparedStatement.setInt(12, selectedAppointmentID);

                int appointmentUpdate = preparedStatement.executeUpdate();

                System.out.println("Appointment was updated successfully!");

                loadNewScreen("MainScreen.fxml", buttonClicked, "Main Screen");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment Modification Warning");
            alert.setHeaderText("The appointment was NOT modified!");
            alert.setContentText("Invalid data input in one or more fields!" + appointmentTextField + emptyAppointmentField);
            alert.showAndWait();
            appointmentTextField = "";
            emptyAppointmentField = "";
        }
    }

    /**
     * Action for when the Cancel button is clicked
     * @param buttonClicked
     * @throws Exception
     */
    @FXML public void cancelButtonClicked(ActionEvent buttonClicked) throws Exception {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen?");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to Main Screen.\nPress Cancel to stay on this screen.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                loadNewScreen("MainScreen.fxml", buttonClicked, "Main Screen");
            }
            else {
                alert.close();
            }
        }
        catch (IOException e) {} catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
        pulls the contact names from the Contacts table and loads them into the Contacts combo box
         */
        try{
            Connection conn = new ConnectDB().makeConnection();
            contactResultSet = conn.createStatement().executeQuery(
                    "SELECT * FROM contacts;");

            while (contactResultSet.next()) {
                contactsForComboBox.addAll(new Contacts(
                        contactResultSet.getInt("Contact_ID"),
                        contactResultSet.getString("Contact_Name"),
                        contactResultSet.getString("Email")
                ));
            }

            for (Contacts contact: contactsForComboBox) {
                contactComboBox.getItems().addAll(contact.getContactName());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        startTimeComboBox.setItems(hours);
        startMinutesComboBox.setItems(minutes);

        endTimeComboBox.setItems(hours);
        endMinutesComboBox.setItems(minutes);

        selectedAppointment = getSelectedAppointment();
        selectedAppointmentID = selectedAppointment.getAppointmentId();
        appointmentIDTextField.setText("Auto Gen Appointment ID: " + selectedAppointmentID);
        titleTextField.setText(selectedAppointment.getTitle());
        descriptionTextField.setText(selectedAppointment.getDescription());
        locationTextField.setText(selectedAppointment.getLocation());
        for (Contacts comboBoxContacts : contactsForComboBox) {
            if (comboBoxContacts.getContactId() == selectedAppointment.getContactId()) {
                contactComboBox.getSelectionModel().select(comboBoxContacts.getContactName());
            }
        }
        typeTextField.setText(selectedAppointment.getType());
        selectedAppointmentStartDate = selectedAppointment.getStart();
        selectedAppointmentEndDate = selectedAppointment.getEnd();

        ZoneId systemZone = ZoneId.systemDefault();
        ZonedDateTime startSystemZone = ZonedDateTime.of(selectedAppointmentStartDate, systemZone);
        ZonedDateTime endSystemZone = ZonedDateTime.of(selectedAppointmentEndDate, systemZone);

        DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("yyyy/mm/dd");
        String formattedStart = startSystemZone.format(startFormatter);
        LocalDate startDatePickerDate = LocalDate.parse(formattedStart, startFormatter);
        startDatePicker.setValue(startDatePickerDate);
        startTimeComboBox.getSelectionModel().select(startSystemZone.getHour());
        startMinutesComboBox.getSelectionModel().select(startSystemZone.getMinute());

        DateTimeFormatter endFormatter = DateTimeFormatter.ofPattern("yyyy/mm/dd");
        String formattedEnd = endSystemZone.format(endFormatter);
        LocalDate endDatePickerDate = LocalDate.parse(formattedEnd, endFormatter);
        endDatePicker.setValue(endDatePickerDate);
        endTimeComboBox.getSelectionModel().select(endSystemZone.getHour());
        endMinutesComboBox.getSelectionModel().select(endSystemZone.getMinute());

        customerIDTextField.setText(String.valueOf(selectedAppointment.getCustomerId()));
        userIDTextField.setText(String.valueOf(selectedAppointment.getUserId()));

    }
}
