package Utilites;

import Model.Appointments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Customers;
import ViewController.MainScreenController;
import ViewController.MainScreenController.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * HelperMethods Class Declaration
 */
public class HelperMethods {
    @FXML private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    @FXML private static ObservableList<Appointments> customerAppointments = FXCollections.observableArrayList();
    @FXML private static ResultSet allAppointmentsResultSet;
    @FXML private static boolean overlappingAppointments = false;


    /**
     * Checks to see if there are empty fields in the customer creation and modification screens
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param firstLevelSelection
     * @param emptyValidator
     * @return emptyValidator
     */
    public static String emptyCustomerTextFieldValidator(
            String name,
            String address,
            String postalCode,
            String phone,
            String firstLevelSelection,
            String emptyValidator) {

        if (name.equals("")) {
            emptyValidator += "\nThe Customer Name field cannot be empty!";
        }

        if (address.equals("")) {
            emptyValidator += "\nThe Address field cannot be empty!";
        }

        if (postalCode.equals("")) {
            emptyValidator += "\nThe Postal Code field cannot be empty!";
        }

        if (phone.equals("")) {
            emptyValidator += "\nThe Phone field cannot be empty!";
        }

        if (firstLevelSelection.equals(null)) {
            emptyValidator += "\nA First Level Division must be selected!";
        }

        return emptyValidator;
    }

    /**
     * Validates the customer text fields data
     * @param postalCode
     * @param phone
     * @param countryName
     * @param textValidator
     * @return textValidator
     */
    public static String customerTextFieldValidator(
            String postalCode,
            String phone,
            String countryName,
            String textValidator) {

        String postalCodeRegex = "^[0-9]{5}(?:-[0-9]{4})?$"
                + "|^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$"
                + "|^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern postalCodePattern = Pattern.compile(postalCodeRegex);
        Matcher postalCodeMatcher = postalCodePattern.matcher(postalCode);
        Boolean postalCodeMatch = postalCodeMatcher.matches();

        if (!postalCodeMatch) {
            textValidator += "\nPlease enter a valid postal code!";
        }

        String phoneNumberRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        Pattern phonePattern = Pattern.compile(phoneNumberRegex);
        Matcher phoneMatcher = phonePattern.matcher(phone);
        Boolean phoneMatches = phoneMatcher.matches();

        if (!phoneMatches) {
            textValidator += "\nPlease enter a valid phone number!";
        }

        return textValidator;
    }

    /**
     * Checks the appointment text fields for empty fields
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param startDate
     * @param startTime
     * @param startMinutes
     * @param endDate
     * @param endTime
     * @param endMinutes
     * @param customerID
     * @param userID
     * @param emptyAppointment
     * @return emptyAppointment
     */
    public static String emptyAppointmentTextFieldValidator(
            String title,
            String description,
            String location,
            String contact,
            String type,
            String startDate,
            String startTime,
            String startMinutes,
            String endDate,
            String endTime,
            String endMinutes,
            String customerID,
            String userID,
            String emptyAppointment) {
        if (title.equals("")) {
            emptyAppointment += "\nThe Title field cannot be empty!";
        }

        if (description.equals("")) {
            emptyAppointment += "\nThe Description field cannot be empty!";
        }

        if (location.equals("")) {
            emptyAppointment += "\nThe Location field cannot be empty!";
        }

        if (contact.equals("")) {
            emptyAppointment += "\nPlease select a Contact!";
        }

        if (type.equals("")) {
            emptyAppointment += "\nThe Type field cannot be empty!";
        }

        if (startDate.equals("")) {
            emptyAppointment += "\nPlease select a start date!";
        }

        if (startTime.equals("")) {
            emptyAppointment += "\nPlease select a start hour!";
        }

        if (startMinutes.equals("")) {
            emptyAppointment += "\nPlease select the minutes for the start of the appointment!";
        }

        if (endDate.equals("")) {
            emptyAppointment += "\nPlease select an end date!";
        }

        if (endTime.equals("")) {
            emptyAppointment += "\nPlease select an end hour!";
        }

        if (endMinutes.equals("")) {
            emptyAppointment += "\nPlease select the minutes for the end of the appointment!";
        }

        if (customerID.equals("")) {
            emptyAppointment += "\nThe Customer ID field cannot be empty!";
        }

        if (userID.equals("")) {
            emptyAppointment += "\nThe User ID field cannot be empty!";
        }

        return emptyAppointment;
    }

    /**
     * Validates the data in the appointment text fields
     * @param startDate
     * @param startTime
     * @param startMinutes
     * @param endDate
     * @param endTime
     * @param endMinutes
     * @param customerID
     * @param userID
     * @param appointmentValidator
     * @return appointmentValidator
     */
    public static String appointmentTextFieldValidator(
            String startDate,
            String startTime,
            String startMinutes,
            String endDate,
            String endTime,
            String endMinutes,
            String customerID,
            String userID,
            String appointmentValidator) {

        overlappingAppointments = false;

        DateTimeFormatter start = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = LocalDate.parse(startDate, start);
        LocalDateTime startLocalDateTime = LocalDateTime.of(
                startLocalDate.getYear(),
                startLocalDate.getMonthValue(),
                startLocalDate.getDayOfMonth(),
                Integer.parseInt(startTime),
                Integer.parseInt(startMinutes));

        DateTimeFormatter end = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endLocalDate = LocalDate.parse(endDate, end);
        LocalDateTime endLocalDateTime = LocalDateTime.of(
                endLocalDate.getYear(),
                endLocalDate.getMonthValue(),
                endLocalDate.getDayOfMonth(),
                Integer.parseInt(endTime),
                Integer.parseInt(endMinutes));

        boolean isAfter = endLocalDateTime.isAfter(startLocalDateTime);

        if (!isAfter) {
            appointmentValidator += "\nEnd Date must be after the Start Date!";
        }

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startLocalDateTime, ZoneId.systemDefault());
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endLocalDateTime, ZoneId.systemDefault());
        ZoneId est = ZoneId.of("America/New_York");
        ZonedDateTime startEST = startZonedDateTime.withZoneSameInstant(est);
        ZonedDateTime endEST = endZonedDateTime.withZoneSameInstant(est);

        if (!(8 <= startEST.getHour() && endEST.getHour() < 22)) {
            appointmentValidator += "\nThe selected times fall outside of our business hours. Please select times between 8AM and 10PM Eastern time!";
        }

        try {
            Connection conn = ConnectDB.makeConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Customer_ID FROM customers WHERE Customer_ID = ?");
            int intCustomerID = Integer.parseInt(customerID);
            preparedStatement.setInt(1, intCustomerID);
            ResultSet customerIDResults = preparedStatement.executeQuery();

            if (customerIDResults.next() == false) {
                appointmentValidator += "\nPlease enter a valid Customer ID!";
            }
        } catch (NumberFormatException ex){
            appointmentValidator += "\nPlease enter a valid Customer ID!";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = ConnectDB.makeConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT User_ID FROM users WHERE User_ID = ?");
            int intUserID = Integer.parseInt(userID);
            preparedStatement.setInt(1, intUserID);
            ResultSet userIDResults = preparedStatement.executeQuery();

            if (userIDResults.next() == false) {
                appointmentValidator += "\nPlease enter a valid User ID!";
            }
        } catch (NumberFormatException ex){
            appointmentValidator += "\nPlease enter a valid User ID!";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = ConnectDB.makeConnection();
            PreparedStatement appointmentStatement = conn.prepareStatement("SELECT * FROM appointments");
            allAppointmentsResultSet = appointmentStatement.executeQuery();

            while (allAppointmentsResultSet.next()) {
                allAppointments.addAll(new Appointments(
                        allAppointmentsResultSet.getInt("Appointment_ID"),
                        allAppointmentsResultSet.getString("Title"),
                        allAppointmentsResultSet.getString("Description"),
                        allAppointmentsResultSet.getString("Location"),
                        allAppointmentsResultSet.getString("Type"),
                        allAppointmentsResultSet.getObject("Start", LocalDateTime.class),
                        allAppointmentsResultSet.getObject("End", LocalDateTime.class),
                        allAppointmentsResultSet.getObject("Create_Date", LocalDateTime.class),
                        allAppointmentsResultSet.getString("Created_By"),
                        allAppointmentsResultSet.getTimestamp("Last_Update"),
                        allAppointmentsResultSet.getString("Last_Updated_By"),
                        allAppointmentsResultSet.getInt("Customer_ID"),
                        allAppointmentsResultSet.getInt("User_ID"),
                        allAppointmentsResultSet.getInt("Contact_ID")
                ));
            }

            int parsedCustomerID = Integer.parseInt(customerID);

            for (Appointments all : allAppointments) {
                if (all.getCustomerId() == parsedCustomerID) {
                    customerAppointments.addAll(all);
                }
            }

            ZoneId utc = ZoneId.of("UTC");
            ZonedDateTime startUTC = startZonedDateTime.withZoneSameInstant(utc);
            ZonedDateTime endUTC = endZonedDateTime.withZoneSameInstant(utc);

            for (Appointments matching : customerAppointments) {
                ZonedDateTime matchingStartZoned = ZonedDateTime.of(matching.getStart(), utc);
                ZonedDateTime matchingEndZoned = ZonedDateTime.of(matching.getEnd(), utc);

                if (startUTC.isBefore(matchingEndZoned) && matchingStartZoned.isBefore(endUTC)) {
                    overlappingAppointments = true;
                }
            }

            if (overlappingAppointments) {
                appointmentValidator += "\nCustomers cannot have overlapping Appointments. Please select different times.";
            }
        } catch (NumberFormatException ex){
            appointmentValidator += "\nPlease enter a valid Customer ID!";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return appointmentValidator;
    }
}
