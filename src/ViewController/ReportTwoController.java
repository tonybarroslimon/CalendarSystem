package ViewController;

import Model.Appointments;
import Model.Contacts;
import Utilites.ConnectDB;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ReportTwoController implements Initializable {

    @FXML private Label reportTwoLabel;
    @FXML private Button contactOneButton;
    @FXML private Button contactTwoButton;
    @FXML private Button contactThreeButton;
    @FXML private Button backButton;
    @FXML private TableView<Appointments> appointmentsTableView;
    @FXML private TableColumn<Appointments, String> appointmentIDColumn;
    @FXML private TableColumn<Appointments, String> titleColumn;
    @FXML private TableColumn<Appointments, String> typeColumn;
    @FXML private TableColumn<Appointments, String> descriptionColumn;
    @FXML private TableColumn<Appointments, String> startColumn;
    @FXML private TableColumn<Appointments, String> endColumn;
    @FXML private TableColumn<Appointments, String> customerIDColumn;
    @FXML private ResultSet contactResultSet;
    @FXML private ResultSet appointmentsResultSet;
    @FXML private ObservableList<Appointments> contactOneObservableList = FXCollections.observableArrayList();
    @FXML private ObservableList<Appointments> contactTwoObservableList = FXCollections.observableArrayList();
    @FXML private ObservableList<Appointments> contactThreeObservableList = FXCollections.observableArrayList();
    @FXML private ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    @FXML private ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();

    @FXML public void contactOneButtonPressed(ActionEvent buttonClicked) {
        appointmentsTableView.getItems().clear();

        for (Appointments appointment : allAppointments) {
            if (appointment.getContactId() == 1) {
                contactOneObservableList.addAll(appointment);
            }
        }
        appointmentsTableView.setItems(contactOneObservableList);
    }

    @FXML public void contactTwoButtonPressed(ActionEvent buttonClicked) {
        appointmentsTableView.getItems().clear();

        for (Appointments appointment : allAppointments) {
            if (appointment.getContactId() == 2) {
                contactTwoObservableList.addAll(appointment);
            }
        }
        appointmentsTableView.setItems(contactTwoObservableList);
    }

    @FXML public void contactThreeButtonPressed(ActionEvent buttonClicked) {
        appointmentsTableView.getItems().clear();

        for (Appointments appointment : allAppointments) {
            if (appointment.getContactId() == 3) {
                contactThreeObservableList.addAll(appointment);
            }
        }
        appointmentsTableView.setItems(contactThreeObservableList);
    }

    @FXML public void backButtonPressed(ActionEvent buttonClicked) throws Exception{
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen?");
            alert.setHeaderText("Are you sure you want to exit to the main screen?");
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

    @FXML public void loadNewScreen(String fxmlScreen, ActionEvent actionEvent, String title) throws Exception{
        Parent newScreen = FXMLLoader.load(getClass().getResource(fxmlScreen));
        Scene newScene = new Scene(newScreen);
        Stage newStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newStage.setTitle(title);
        newStage.setScene(newScene);
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            Connection conn = ConnectDB.makeConnection();
            PreparedStatement contactPreparedStatement = conn.prepareStatement("SELECT * FROM contacts;");
            contactResultSet = contactPreparedStatement.executeQuery();

            while (contactResultSet.next()) {
                contactsObservableList.addAll(new Contacts(
                        contactResultSet.getInt("Contact_ID"),
                        contactResultSet.getString("Contact_Name"),
                        contactResultSet.getString("Email")
                ));
            }

            PreparedStatement appointmentsPreparedStatement = conn.prepareStatement("SELECT * FROM appointments;");
            appointmentsResultSet = appointmentsPreparedStatement.executeQuery();

            while (appointmentsResultSet.next()) {
                allAppointments.addAll(new Appointments(
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

        for (Contacts contact : contactsObservableList) {
            if (contact.getContactId() == 1) {
                contactOneButton.setText(contact.getContactName());
            }

            if (contact.getContactId() == 2) {
                contactTwoButton.setText(contact.getContactName());
            }

            if (contact.getContactId() == 3) {
                contactThreeButton.setText(contact.getContactName());
            }
        }

        appointmentsTableView.setPlaceholder(new Label("No appointments to show!"));
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
}
