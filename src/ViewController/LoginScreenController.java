package ViewController;

import Model.Users;
import Utilites.ConnectDB;
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
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The Login Screen Controller, validates a username and password.
 * Localized for English and French and logs login attempts
 */
public class LoginScreenController implements Initializable {

    @FXML private Button loginButton;
    @FXML private Button exitButton;
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Label loginLabel;
    @FXML private Label locationLabel;
    @FXML private static ZoneId localZoneId = ZoneId.systemDefault();
    @FXML private static Locale localLanguage = Locale.getDefault();
    @FXML private String systemLanguage = localLanguage.getLanguage();
    @FXML private ResultSet loginResultSet;
    @FXML private Boolean loginResultBoolean = false;
    private static Users activeUser;

    /**
     *
     * @return the active user
     */
    public static Users getActiveUser() {
        return activeUser;
    }

    /**
     * The method that handles the exit button being pressed
     * @param buttonClicked activated after clicking the exit button
     */
    @FXML public void exitButtonClicked (ActionEvent buttonClicked) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Calendar System");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage loginScreen = (Stage)((Node)buttonClicked.getSource()).getScene().getWindow();
            loginScreen.close();
        }
        else {
            alert.close();
        }

    }

    /**
     * Parts of this method were inspired by https://www.javaguides.net/2019/07/login-form-using-javafx-with-mysql-database.html
     * @param buttonClicked
     * @throws IOException
     */
    @FXML public void loginButtonClicked (ActionEvent buttonClicked) throws IOException {

        // Checks if there is a username entered in the username field
        if (userNameTextField.getText().isEmpty()) {
            writeToFile(false);

            if (systemLanguage == "fr") {
                alertBuilder(Alert.AlertType.WARNING, "Erreur d'identification", "s'il vous plaît entrez votre nom d'utilisateur");
                return;
            }
            else {
                alertBuilder(Alert.AlertType.WARNING, "Login Error", "Please enter your username");
                return;
            }
        }

        // Checks if there is a password entered in the password field
        if (passwordTextField.getText().isEmpty()) {
            writeToFile(false);

            if (systemLanguage == "fr") {
                alertBuilder(Alert.AlertType.WARNING, "Erreur d'identification", "s'il vous plait entrez votre mot de passe");
                return;
            }
            else {
                alertBuilder(Alert.AlertType.WARNING, "Login Error", "Please enter your password");
                return;
            }
        }

        String enteredUsername = userNameTextField.getText();
        String enteredPassword = passwordTextField.getText();

        // Goes to the database and grabs the information for username and password to validate the data
        try (Connection conn = new ConnectDB().makeConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("SELECT * FROM users WHERE user_name = ? AND password = ?")) {
                preparedStatement.setString(1, enteredUsername);
                preparedStatement.setString(2, enteredPassword);

                loginResultSet = preparedStatement.executeQuery();

                if (loginResultSet.next()) {
                    loginResultBoolean = true;
                    activeUser = new Users(
                            loginResultSet.getInt("User_ID"),
                            loginResultSet.getString("User_Name"),
                            loginResultSet.getString("Password"),
                            loginResultSet.getDate("Create_Date"),
                            loginResultSet.getString("Created_By"),
                            loginResultSet.getTimestamp("Last_Update"),
                            loginResultSet.getString("Last_Updated_By")
                    );
                }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        // checks to see if the username and password match an entry in the database
        if (!loginResultBoolean) {
            writeToFile(false);

            if (systemLanguage == "fr") {
                confirmationAlert("identifiant ou mot de passe incorrect", "Erreur d'identification");
            }
            else {
                confirmationAlert("Incorrect username or password", "Login Error");
            }

        }
        else {
            writeToFile(true);

            Parent newScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene newScene = new Scene(newScreen);
            Stage newStage = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();
            newStage.setTitle("Main Screen");
            newStage.setScene(newScene);
            newStage.show();
        }
    }

    /**
     * This method is used for creating a Confirmation alert
     * @param contentText is the string that holds the Content title information
     * @param title is the string that holds the title information
     */
    public static void confirmationAlert(String contentText, String title) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(contentText);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * This method is used for creating various kinds of alerts
     * @param alertType so we can set what type of alert this will be
     * @param title is the string that holds the title information
     * @param message is the string that holds the content text
     */
    public static void alertBuilder(Alert.AlertType alertType, String title, String message) {

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * This is the logger that logs all of the login attempts
     * @param loginSuccess true or false to determine if the login was successful or not
     */
    public static void writeToFile(boolean loginSuccess) {

        Logger log = Logger.getLogger("./login_activity.txt");
        try {
            FileHandler updateLoginActivityFile = new FileHandler("./login_activity.txt", true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            updateLoginActivityFile.setFormatter(simpleFormatter);
            log.addHandler(updateLoginActivityFile);
            if (loginSuccess) {
                Instant loginAttemptDateTime = Instant.now();
                log.info(String.valueOf(new StringBuilder().append("\n").append(loginAttemptDateTime.toString()).append(" Login Successful")));
                System.out.println("Successfully wrote to the file.");
            }
            else {
                Instant loginAttemptDateTime = Instant.now();
                log.info(String.valueOf(new StringBuilder().append("\n").append(loginAttemptDateTime.toString()).append(" Login Failed")));
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // sets the users zoneID location to the location label
        locationLabel.setText("Location: " + localZoneId.toString());

        if (systemLanguage == "fr") {
            loginLabel.setText("Portail de connexion");
            loginButton.setText("Entrer");
            exitButton.setText("Sortir");
            userNameTextField.setPromptText("Entrez votre nom d'utilisateur");
            passwordTextField.setPromptText("tapez votre mot de passe");
            locationLabel.setText("emplacement: " + localZoneId.toString());
        }

    }

}
