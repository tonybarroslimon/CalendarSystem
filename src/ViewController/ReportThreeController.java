package ViewController;

import Reports.ReportThree;
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
import java.util.ResourceBundle;

public class ReportThreeController implements Initializable {

    @FXML private Label reportThreeLabel;
    @FXML private Button backButton;
    @FXML private TableView<ReportThree> reportThreeTableView;
    @FXML private TableColumn<ReportThree, Integer> customerIdColumn;
    @FXML private TableColumn<ReportThree, Integer> customerIdNumAppointmentsColumn;
    @FXML private ResultSet customerIdResultSet;
    @FXML private ObservableList<ReportThree> customerIDObjects = FXCollections.observableArrayList();

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
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Customer_ID, COUNT(*) AS numAppointments FROM appointments GROUP BY Customer_ID;");
            customerIdResultSet = preparedStatement.executeQuery();

            while (customerIdResultSet.next()) {
                customerIDObjects.addAll(new ReportThree(
                        customerIdResultSet.getInt("Customer_ID"),
                        customerIdResultSet.getInt("numAppointments")
                ));
            }
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerIdNumAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));
        reportThreeTableView.setItems(customerIDObjects);

    }
}
