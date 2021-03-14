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
    @FXML private ComboBox<ReportOne> typeComboBox = new ComboBox<ReportOne>();
    @FXML private Label typeLabel;
    @FXML private ComboBox<ReportOneMonth> monthComboBox = new ComboBox<ReportOneMonth>();
    @FXML private Label monthLabel;
    @FXML private Button backButton;
    @FXML private ResultSet reportOneTypeResultSet;
    @FXML private ObservableList<ReportOne> reportOneTypeObjects = FXCollections.observableArrayList();
    @FXML private ResultSet reportOneMonthResultSet;
    @FXML private ObservableList<ReportOneMonth> reportOneMonthObjects = FXCollections.observableArrayList();

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
            PreparedStatement typePreparedStatement = conn.prepareStatement("SELECT Type, COUNT(*) AS numAppointments FROM appointments GROUP BY Type");
                PreparedStatement monthPreparedStatement = conn.prepareStatement("SELECT month(Start) AS Month, COUNT(*) as numAppointments FROM appointments GROUP BY Month");

            reportOneTypeResultSet = typePreparedStatement.executeQuery();
            reportOneMonthResultSet = monthPreparedStatement.executeQuery();

            while (reportOneTypeResultSet.next()) {
                reportOneTypeObjects.addAll(new ReportOne(
                        reportOneTypeResultSet.getString("Type"),
                        reportOneTypeResultSet.getInt("numAppointments")
                ));
            }

            while (reportOneMonthResultSet.next()) {
                reportOneMonthObjects.addAll(new ReportOneMonth(
                        reportOneMonthResultSet.getInt("Month"),
                        reportOneMonthResultSet.getInt("numAppointments")
                ));
            }

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }

        typeComboBox.setItems(reportOneTypeObjects);

        Callback<ListView<ReportOne>, ListCell<ReportOne>> cellFactory = new Callback<ListView<ReportOne>, ListCell<ReportOne>>() {

            @Override
            public ListCell<ReportOne> call(ListView<ReportOne> reportOneListView) {
                return new ListCell<ReportOne>() {

                    @Override
                    protected void updateItem(ReportOne type, boolean empty) {
                        super.updateItem(type, empty);
                        if (type == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(type.getType());
                        }
                    }
                };
            }
        };

        typeComboBox.setButtonCell(cellFactory.call(null));
        typeComboBox.setCellFactory(cellFactory);

        monthComboBox.setItems(reportOneMonthObjects);

        Callback<ListView<ReportOneMonth>, ListCell<ReportOneMonth>> cellFactoryMonth = new Callback<ListView<ReportOneMonth>, ListCell<ReportOneMonth>>() {

            @Override
            public ListCell<ReportOneMonth> call(ListView<ReportOneMonth> reportOneMonthListView) {
                return new ListCell<ReportOneMonth>() {

                    @Override
                    protected void updateItem(ReportOneMonth month, boolean empty) {
                        super.updateItem(month, empty);
                        if (month == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(String.valueOf(month.getMonth()));
                        }
                    }
                };
            }
        };

        monthComboBox.setButtonCell(cellFactoryMonth.call(null));
        monthComboBox.setCellFactory(cellFactoryMonth);

        typeComboBox.setOnAction((event) -> {
            ReportOne selectedType = typeComboBox.getSelectionModel().getSelectedItem();

            for (ReportOne selectedTypeIterator : reportOneTypeObjects) {
                if (selectedTypeIterator.getType().equals(selectedType.getType())) {
                    typeLabel.setText(String.valueOf(selectedTypeIterator.getNumberOfAppointments()));
                }
            }
        });

        monthComboBox.setOnAction((event) -> {
            String selectedMonth = monthComboBox.getSelectionModel().getSelectedItem().toString();
            int selectedMonthInt = Integer.parseInt(selectedMonth);

            for (ReportOneMonth selectedMonthIterator : reportOneMonthObjects) {
                if (selectedMonthIterator.getMonth() == selectedMonthInt) {
                    monthLabel.setText(String.valueOf(selectedMonthIterator.getNumberOfMonthAppointments()));
                }
            }
        });
    }
}
