package ViewController;

import Reports.ReportOne;
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

public class ReportOneController implements Initializable {
    @FXML private TableView<ReportOne> reportOneTableView;
    @FXML private TableColumn<ReportOne, String> typeColumn;
    @FXML private TableColumn<ReportOne, Integer> monthColumn;
    @FXML private TableColumn<ReportOne, Integer> numberOfAppointmentsColumn;
    @FXML private Button backButton;
    @FXML private ResultSet reportOneResultSet;
    @FXML private ObservableList<ReportOne> reportOneObservableList = FXCollections.observableArrayList();

    @FXML public void loadNewScreen(String fxmlScreen, ActionEvent actionEvent, String title) throws Exception{
        Parent newScreen = FXMLLoader.load(getClass().getResource(fxmlScreen));
        Scene newScene = new Scene(newScreen);
        Stage newStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newStage.setTitle(title);
        newStage.setScene(newScene);
        newStage.show();
    }

    @FXML public void backButtonClicked(ActionEvent buttonClicked) throws Exception {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try (Connection conn = ConnectDB.makeConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT Type, month(Start) AS Month, "
                     + "COUNT(*) AS numAppointments FROM appointments GROUP BY Type, Month")){
            reportOneResultSet = preparedStatement.executeQuery();

            while (reportOneResultSet.next()) {
                reportOneObservableList.addAll(new ReportOne(
                        reportOneResultSet.getString("Type"),
                        reportOneResultSet.getInt("Month"),
                        reportOneResultSet.getInt("numAppointments")
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        numberOfAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));
        reportOneTableView.setItems(reportOneObservableList);
    }
}
