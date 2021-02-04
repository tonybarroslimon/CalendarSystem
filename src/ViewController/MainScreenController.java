package ViewController;

import Model.Appointments;
import Model.Customers;
import Utilites.ConnectDB;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    private ToggleGroup radioButtonSelected = new ToggleGroup();

    @FXML public void loadNewScreen(String fxmlScreen, ActionEvent actionEvent, String title) throws Exception{
        Parent newScreen = FXMLLoader.load(getClass().getResource(fxmlScreen));
        Scene newScene = new Scene(newScreen);
        Stage newStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newStage.setTitle(title);
        newStage.setScene(newScene);
        newStage.show();
    }

    @FXML public void addAppointmentButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("AddAppointmentScreen.fxml", buttonClicked, "Add Appointment");
    }

    @FXML public void modifyAppointmentButtonClicked(ActionEvent buttonClicked) throws Exception {
        //TODO - finish this method
        loadNewScreen("ModifyAppointmentScreen.fxml", buttonClicked, "Modify Appointment");
    }

    @FXML public void deleteAppointmentButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO
    }

    @FXML public void addCustomerButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("AddCustomerScreen.fxml", buttonClicked, "Add Customer");
    }

    @FXML public void modifyCustomerButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO - finish this method
        loadNewScreen("ModifyCustomerScreen.fxml", buttonClicked, "Modify Customer");
    }

    @FXML public void deleteCustomerButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO
    }

    @FXML public void reportOneButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO
    }

    @FXML public void reportTwoButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO
    }

    @FXML public void reportThreeButtonClicked(ActionEvent buttonClicked) throws Exception {
        // TODO
    }

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

    public void setToggleGroup() {
        monthlyViewRadioButton.setToggleGroup(radioButtonSelected);
        weeklyViewRadioButton.setToggleGroup(radioButtonSelected);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        TODO:
        Initialize table data for appointments
        Initialize table data for customers
        Modify appointment button being pressed
        Delete appointment button being pressed
        Add customer button being pressed
        Modify customer button being pressed
        Delete customer button being pressed
        Report one button being pressed
        Report two button being pressed
        Report three button being pressed
        Filter data by what radio button is pressed
        JavaDocs
        Error messages
        */

        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        firstLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        appointmentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTableColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTableColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
}
