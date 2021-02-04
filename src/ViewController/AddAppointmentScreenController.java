package ViewController;

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
    @FXML private ObservableList contactStrings = FXCollections.observableArrayList();
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
        String customerIDField = customerIDTextField.getText();
        String userIDField = userIDTextField.getText();

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
                    "SELECT Contact_ID, Contact_Name, Email FROM contacts;");

            while (contactResultSet.next()) {
                contactStrings.add(contactResultSet.getString("Contact_Name"));
            }

            contactComboBox.setItems(contactStrings);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
