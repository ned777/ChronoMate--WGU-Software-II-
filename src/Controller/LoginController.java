package Controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the login screen. Handles user authentication, locale-aware UI,
 * login button enabling, activity logging, and screen navigation.
 */
public class LoginController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private static final DateTimeFormatter LOG_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label zoneLabel;
    @FXML private Label messageLabel;

    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        // Show the user's system time zone
        zoneLabel.setText(String.format("%s %s",
                resources.getString("label.zone"),
                ZoneId.systemDefault()
        ));

        // Disable login until both fields have text
        loginButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> usernameField.getText().trim().isEmpty() ||
                                passwordField.getText().trim().isEmpty(),
                        usernameField.textProperty(),
                        passwordField.textProperty()
                )
        );

        // Pressing Enter in password field triggers login
        passwordField.setOnAction(evt -> onLogin(evt));
    }

    /**
     * Called when the login button is clicked or Enter is pressed in the password field.
     * Validates input, attempts authentication, shows messages, logs the attempt, and
     * on success, transitions to the Appointment Overview screen.
     */
    @FXML
    private void onLogin(ActionEvent event) {
        // Clear any old message
        messageLabel.setText("");
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        // DEBUG: print credentials
        System.out.println("DEBUG → user: '" + user + "', pass: '" + pass + "'");

        // Hard-coded check (replace with real authentication later)
        boolean success = user.equals("sqlUser") && pass.equals("Passw0rd!");
        System.out.println("DEBUG → success: " + success);

        if (!success) {
            messageLabel.setText("Invalid username or password");
            passwordField.clear();
            logLoginAttempt(user, success);
            return;
        }

        // Log the successful attempt
        logLoginAttempt(user, success);

        // -------------------------------------------------------
        // *** APPOINTMENT ALERT LOGIC ***
        // Get the user's ID after login. For now, hard-coded as 1.
        // Replace '1' with your actual userId variable from your user authentication.
        int userId = 1;
        AppointmentAlertUtil.showUpcomingAppointmentAlert(userId);
        // -------------------------------------------------------

        // Transition to Home.fxml
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/Home.fxml"),
                    resources  // pass along resource bundle for i18n
            );
            Parent root = loader.load();
            // Grab current stage via the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Swap in the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(resources.getString("title.appOverview"));
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load Home.fxml", e);
            showErrorDialog("Unable to load the main screen", e.getMessage());
        }
    }

    /**
     * Appends a login attempt record to "login_activity.txt" in the working directory.
     *
     * @param username the username entered
     * @param success  true if authentication succeeded, false otherwise
     */
    private void logLoginAttempt(String username, boolean success) {
        String timestamp = LocalDateTime.now(ZoneId.systemDefault()).format(LOG_FMT);
        String status = success ? "SUCCESS" : "FAILURE";
        String record = String.format("%s - User: %s - %s%n", timestamp, username, status);
        try (FileWriter fw = new FileWriter("login_activity.txt", true)) {
            fw.write(record);
        } catch (IOException ioEx) {
            LOGGER.log(Level.WARNING, "Failed to write login_activity.txt", ioEx);
        }
    }

    /**
     * Utility to show an error dialog.
     */
    private void showErrorDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Application Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
