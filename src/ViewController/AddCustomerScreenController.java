package ViewController;

import Model.Countries;
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

public class AddCustomerScreenController implements Initializable {

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
    @FXML private ObservableList<FirstLevelDivisions> firstLevelObjects = FXCollections.observableArrayList();
    @FXML private int selectedCountryID = 0;
    @FXML private int countrySelectedIndex;
    @FXML private Object selectedCountry;

    @FXML private int firstLevelCountryID;

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

    @FXML public void cancelButtonClicked(ActionEvent buttonClicked) throws IOException {
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
        // firstLevelListView.setVisible(false);
        // firstLevelLabel.setVisible(false);

        // LAMBDA expression to handle the selection of the country from the country combo box
        countryComboBox.setOnAction((event) -> {
            countrySelectedIndex = countryComboBox.getSelectionModel().getSelectedIndex();
            selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        });

        try (Connection conn = ConnectDB.makeConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM countries")){
                countriesResultSet = preparedStatement.executeQuery();

                while (countriesResultSet.next()) {
                    countryComboBox.getItems().addAll(countriesResultSet.getString("Country"));
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
