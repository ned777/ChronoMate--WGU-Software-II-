package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utility class for displaying an alert if the logged-in user
 * has any appointments within 15 minutes of login.
 */
public class AppointmentAlertUtil {

    /**
     * Shows an alert dialog with information about the next appointment
     * within 15 minutes, or notifies if there are none.
     *
     * @param userId The ID of the logged-in user.
     */
    public static void showUpcomingAppointmentAlert(int userId) {
        try {
            // Fetch all appointments for this user (should be in LOCAL time)
            ObservableList<Appointment> appts = AppointmentDAO.getAppointmentsByUser(userId);

            // Get the current time (system local)
            LocalDateTime now = LocalDateTime.now();

            Appointment nextAppt = null;
            long soonestMinutes = 16; // Init to 16 (only interested in <=15)

            // Search for the soonest appointment within 15 minutes
            for (Appointment appt : appts) {
                LocalDateTime start = appt.getStart();
                long minutes = Duration.between(now, start).toMinutes();

                // We want appointments starting in 0–15 minutes (not negative!)
                if (minutes >= 0 && minutes <= 15) {
                    if (nextAppt == null || start.isBefore(nextAppt.getStart())) {
                        nextAppt = appt;
                        soonestMinutes = minutes;
                    }
                }
            }

            // Create and show the alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (nextAppt != null) {
                // There is an appointment within 15 minutes!
                alert.setTitle("Upcoming Appointment Alert");
                alert.setHeaderText("You have an appointment soon!");
                alert.setContentText(
                        "Appointment ID: " + nextAppt.getId() +
                                "\nDate: " + nextAppt.getStart().toLocalDate() +
                                "\nTime: " + nextAppt.getStart().toLocalTime().withSecond(0).withNano(0)
                );
            } else {
                // No upcoming appointments
                alert.setTitle("Appointment Notification");
                alert.setHeaderText("No Upcoming Appointments");
                alert.setContentText("You have no appointments within the next 15 minutes.");
            }
            alert.showAndWait();

        } catch (Exception e) {
            // If something goes wrong (DB, etc.), show an error dialog
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Checking Appointments");
            errorAlert.setHeaderText("Unable to check for upcoming appointments.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
