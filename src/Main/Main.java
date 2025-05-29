package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point for the Scheduling System application.
 * <p>
 * Initializes the primary stage, loads the login form with
 * internationalization support (English/French), and handles
 * error reporting via dialog boxes.
 * </p>
 *
 * Usage:
 * <ul>
 *   <li>Determines system locale to choose language bundle.</li>
 *   <li>Loads Login.fxml and sets window title from resources.</li>
 *   <li>Displays errors if the FXML fails to load.</li>
 * </ul>
 *
 * @author Nam Nguyen
 */
public class Main extends Application {
    /** Logger for capturing initialization errors. */
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    /** Preferred width of the main window. */
    private static final int WINDOW_WIDTH  = 600;
    /** Preferred height of the main window. */
    private static final int WINDOW_HEIGHT = 600;

    /**
     * JavaFX application start method.
     * <p>
     * Determines the user's language (English or French), loads the appropriate
     * resource bundle, and initializes the login form.
     * Any IOException during FXML loading is caught and reported via an error dialog.
     * </p>
     *
     * @param primaryStage the primary application window
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Determine locale: default to French if system language is "fr"
            Locale locale = Locale.getDefault().getLanguage().equals("fr")
                    ? Locale.FRENCH
                    : Locale.ENGLISH;

            // Load resource bundle from src/main/resources/Language/lang_*.properties
            ResourceBundle bundle = ResourceBundle.getBundle("Language.lang", locale);

            // Load the FXML login form with localization
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/Login.fxml"),
                    bundle
            );
            Parent root = loader.load();

            // Set the stage title from the resource bundle
            primaryStage.setTitle(bundle.getString("title.login"));
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to load Login.fxml", ex);
            showErrorDialog("Unable to load the login screen", ex.getMessage());
        }
    }

    /**
     * Displays an error alert with the given header and content text.
     *
     * @param header  short description of the error
     * @param content detailed error message to display
     */
    private void showErrorDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Application Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Main method launching the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
