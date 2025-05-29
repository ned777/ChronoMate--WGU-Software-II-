package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HomeController manages navigation from the home screen
 * to Appointments, Customers, and Reports windows.
 */
public class HomeController {

    /**
     * Handles the event when the user clicks the Appointments button.
     * Opens the AppointmentOverview window.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void goToAppointments(ActionEvent event) {
        openWindow("/view/AppointmentOverview.fxml", "Appointments");
    }

    /**
     * Handles the event when the user clicks the Customers button.
     * Opens the CustomerOverview window.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void goToCustomers(ActionEvent event) {
        openWindow("/view/CustomerOverview.fxml", "Customers");
    }

    /**
     * Handles the event when the user clicks the Reports button.
     * Opens the Reports window.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void goToReports(ActionEvent event) {
        openWindow("/view/Reports.fxml", "Reports");
    }

    /**
     * Helper method to open a new window using the specified FXML file.
     * The new window will have the given title.
     * The current window stays open.
     *
     * @param fxmlPath The path to the FXML file to load.
     * @param title    The title for the new window.
     */
    private void openWindow(String fxmlPath, String title) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Create a new stage (window) and set its title and scene
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            // Show the new window
            stage.show();
        } catch (Exception e) {
            // Print stack trace if something goes wrong (useful for debugging)
            e.printStackTrace();
        }
    }
}
