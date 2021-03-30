package ViewController;

import Reports.ReportOne;
import Reports.ReportOneMonth;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportOneController implements Initializable {
    @FXML private Label reportOneLabel;
    @FXML private Button backButton;
    @FXML private TableView<ReportOne> typeTableView;
    @FXML private TableColumn<ReportOne, String> typeColumn;
    @FXML private TableColumn<ReportOne, Integer> typeNumAppointmentsColumn;
    @FXML private TableView<ReportOneMonth> monthTableView;
    @FXML private TableColumn<ReportOneMonth, Integer> monthColumn;
    @FXML private TableColumn<ReportOneMonth, Integer> monthNumAppointmentsColumn;
    @FXML private ResultSet typeResultSet;
    @FXML private ObservableList<ReportOne> typeObjects = FXCollections.observableArrayList();
    @FXML private ResultSet monthResultSet;
    @FXML private ObservableList<ReportOneMonth> monthObjects = FXCollections.observableArrayList();

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

        try {
            Connection conn = ConnectDB.makeConnection();
            PreparedStatement typePreparedStatement = conn.prepareStatement("SELECT Type, COUNT(*) AS numAppointments FROM appointments GROUP BY Type;");
            PreparedStatement monthPreparedStatement = conn.prepareStatement("SELECT month(Start) AS Month, COUNT(*) as numAppointments FROM appointments GROUP BY Month;");

            typeResultSet = typePreparedStatement.executeQuery();
            monthResultSet = monthPreparedStatement.executeQuery();

            while (typeResultSet.next()) {
                typeObjects.addAll(new ReportOne(
                        typeResultSet.getString("Type"),
                        typeResultSet.getInt("numAppointments")
                ));
            }

            while (monthResultSet.next()) {
                monthObjects.addAll(new ReportOneMonth(
                        monthResultSet.getInt("Month"),
                        monthResultSet.getInt("numAppointments")
                ));
            }

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeNumAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));
        typeTableView.setItems(typeObjects);

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthNumAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfMonthAppointments"));
        monthTableView.setItems(monthObjects);
    }
}
