package ViewController;

import Model.Contacts;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddAppointmentScreenController implements Initializable {

    @FXML private Label titleHeaderLabel;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label contactLabel;
    @FXML private Label typeLabel;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label customerIDLabel;
    @FXML private Label userIDLabel;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private ComboBox contactComboBox;
    @FXML private TextField typeTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox startTimeComboBox;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox endTimeComboBox;
    @FXML private TextField customerIDTextField;
    @FXML private TextField userIDTextField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private ObservableList<Contacts> contactsForComboBox = FXCollections.observableArrayList();
    private ResultSet contactResultSet;

    @FXML public void loadNewScreen(String fxmlScreen, ActionEvent actionEvent, String title) throws Exception{
        Parent newScreen = FXMLLoader.load(getClass().getResource(fxmlScreen));
        Scene newScene = new Scene(newScreen);
        Stage newStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newStage.setTitle(title);
        newStage.setScene(newScene);
        newStage.show();
    }

    @FXML public void saveButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO
        String titleField = titleTextField.getText();
        String descriptionField = descriptionTextField.getText();
        String locationField = locationTextField.getText();
        String contactField = contactComboBox.getSelectionModel().getSelectedItem().toString();
        String typeField = typeTextField.getText();
        String startDateField = startDatePicker.getValue().toString();
        String startTimeField = startTimeComboBox.getSelectionModel().getSelectedItem().toString();
        String endDateField = endDatePicker.getValue().toString();
        String endTimeField = endTimeComboBox.getSelectionModel().getSelectedItem().toString();
        int customerIDField = Integer.parseInt(customerIDTextField.getText());
        int userIDField = Integer.parseInt(userIDTextField.getText());



        loadNewScreen("MainScreen.fxml", buttonClicked, "Main Screen");
    }

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

        startTimeComboBox.getItems().addAll(
                "00:00","00:15","00:30","00:45",
                "01:00","01:15","01:30","01:45",
                "02:00","02:15","02:30","02:45",
                "03:00","03:15","03:30","03:45",
                "04:00","04:15","04:30","04:45",
                "05:00","05:15","05:30","05:45",
                "06:00","06:15","06:30","06:45",
                "07:00","07:15","07:30","07:45",
                "08:00","08:15","08:30","08:45",
                "09:00","09:15","09:30","09:45",
                "10:00","10:15","10:30","10:45",
                "11:00","11:15","11:30","11:45",
                "12:00","12:15","12:30","12:45",
                "13:00","13:15","13:30","13:45",
                "14:00","14:15","14:30","14:45",
                "15:00","15:15","15:30","15:45",
                "16:00","16:15","16:30","16:45",
                "17:00","17:15","17:30","17:45",
                "18:00","18:15","18:30","18:45",
                "19:00","19:15","19:30","19:45",
                "20:00","20:15","20:30","20:45",
                "21:00","21:15","21:30","21:45",
                "22:00","22:15","22:30","22:45",
                "23:00","23:15","23:30","23:45");

        endTimeComboBox.getItems().addAll(
                "00:00","00:15","00:30","00:45",
                "01:00","01:15","01:30","01:45",
                "02:00","02:15","02:30","02:45",
                "03:00","03:15","03:30","03:45",
                "04:00","04:15","04:30","04:45",
                "05:00","05:15","05:30","05:45",
                "06:00","06:15","06:30","06:45",
                "07:00","07:15","07:30","07:45",
                "08:00","08:15","08:30","08:45",
                "09:00","09:15","09:30","09:45",
                "10:00","10:15","10:30","10:45",
                "11:00","11:15","11:30","11:45",
                "12:00","12:15","12:30","12:45",
                "13:00","13:15","13:30","13:45",
                "14:00","14:15","14:30","14:45",
                "15:00","15:15","15:30","15:45",
                "16:00","16:15","16:30","16:45",
                "17:00","17:15","17:30","17:45",
                "18:00","18:15","18:30","18:45",
                "19:00","19:15","19:30","19:45",
                "20:00","20:15","20:30","20:45",
                "21:00","21:15","21:30","21:45",
                "22:00","22:15","22:30","22:45",
                "23:00","23:15","23:30","23:45");

    }
}
