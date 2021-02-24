package ViewController;

import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static ViewController.MainScreenController.getSelectedCustomer;

public class ModifyCustomerScreenController implements Initializable {

    @FXML private Label titleHeaderLabel;
    @FXML private Label customerIDLabel;
    @FXML private Label customerNameLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label addressLabel;
    @FXML private Label postalCodeLabel;
    @FXML private Label countryLabel;
    @FXML private Label firstLevelLabel;
    @FXML private TextField customerIDTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private ComboBox countryComboBox;
    @FXML private ListView firstLevelListView;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private ResultSet countriesResultSet;
    private ResultSet firstLevelDivisionsResultsSet;
    @FXML private ObservableList<Countries> countryObjects = FXCollections.observableArrayList();
    @FXML private ObservableList<FirstLevelDivisions> firstLevelObjects = FXCollections.observableArrayList();
    @FXML private String selectedCountry;
    @FXML private int selectedCountryId;
    @FXML private String emptyCustomerField = new String();
    @FXML private String customerTextField = new String();
    private int divisionId;
    @FXML private Customers selectedCustomer;
    @FXML private int selectedCustomerId;

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

    public ObservableList<FirstLevelDivisions> filteredAreas(int countryId) {
        ObservableList<FirstLevelDivisions> filteredList = FXCollections.observableArrayList();
        for (FirstLevelDivisions countryToFilter: firstLevelObjects) {
            if (countryToFilter.getCountryId() == countryId) {
                filteredList.add(countryToFilter);
            }
        }
        return filteredList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedCustomer = getSelectedCustomer();
        selectedCustomerId = selectedCustomer.getCustomerId();
        customerIDTextField.setText("Auto Gen Customer ID: " + selectedCustomerId);
        customerNameTextField.setText(selectedCustomer.getCustomerName());
        phoneNumberTextField.setText(selectedCustomer.getPhone());
        addressTextField.setText(selectedCustomer.getAddress());
        postalCodeTextField.setText(selectedCustomer.getPostalCode());

        /*
            @FXML private ComboBox countryComboBox;
            How do I set the default. I need to convert a division ID to find the corresponding country name and then
            set the selected item in the country Combo Box
            @FXML private ListView firstLevelListView;
            I need to convert the Division ID to the Division name and then assign it as the selected item in the
            first level list view
         */


        // LAMBDA expression to handle the selection of the country from the country combo box
        countryComboBox.setOnAction((event) -> {
            selectedCountry = countryComboBox.getSelectionModel().getSelectedItem().toString();
            firstLevelListView.getItems().clear();

            for (Countries selectedCountryToFilter: countryObjects) {
                if (selectedCountryToFilter.getCountry() == selectedCountry) {
                    selectedCountryId = selectedCountryToFilter.getCountryId();
                }
            }

            for (FirstLevelDivisions areasToFilter: filteredAreas(selectedCountryId)) {
                firstLevelListView.getItems().addAll(areasToFilter.getDivision());
            }

        });

        try (Connection conn = ConnectDB.makeConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM countries")){
            countriesResultSet = preparedStatement.executeQuery();

            while (countriesResultSet.next()) {
                countryObjects.addAll(new Countries(
                        countriesResultSet.getInt("Country_ID"),
                        countriesResultSet.getString("Country"),
                        countriesResultSet.getDate("Create_Date"),
                        countriesResultSet.getString("Created_By"),
                        countriesResultSet.getTimestamp("Last_Update"),
                        countriesResultSet.getString("Last_Updated_By")
                ));
            }

            for (Countries country: countryObjects) {
                countryComboBox.getItems().addAll(country.getCountry());
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try (Connection conn = ConnectDB.makeConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM first_level_divisions")){
            firstLevelDivisionsResultsSet = preparedStatement.executeQuery();

            while (firstLevelDivisionsResultsSet.next()) {
                firstLevelObjects.addAll(new FirstLevelDivisions(
                        firstLevelDivisionsResultsSet.getInt("Division_ID"),
                        firstLevelDivisionsResultsSet.getString("Division"),
                        firstLevelDivisionsResultsSet.getDate("Create_Date"),
                        firstLevelDivisionsResultsSet.getString("Created_By"),
                        firstLevelDivisionsResultsSet.getTimestamp("Last_Update"),
                        firstLevelDivisionsResultsSet.getString("Last_Updated_By"),
                        firstLevelDivisionsResultsSet.getInt("COUNTRY_ID")
                ));
            }

            for (FirstLevelDivisions firstLevelDivisions: firstLevelObjects) {
                firstLevelListView.getItems().addAll(firstLevelDivisions.getDivision());
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
