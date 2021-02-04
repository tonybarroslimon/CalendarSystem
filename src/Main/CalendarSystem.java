package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utilites.ConnectDB;

import java.sql.SQLException;
import java.util.Locale;

public class CalendarSystem extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ViewController/LoginScreen.fxml"));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Locale.setDefault(new Locale("fr"));
        ConnectDB.makeConnection();
        launch(args);
        ConnectDB.closeConnection();

    }
}
