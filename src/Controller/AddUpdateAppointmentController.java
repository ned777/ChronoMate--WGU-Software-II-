package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controller for the Add/Update Appointment form.
 * <p>
 * Handles form population, validation, data conversion, and all actions related to
 * adding or updating an appointment. Ensures business rules are enforced for
 * overlapping appointments and business hours, and interacts with the DAO layer.
 * </p>
 */
public class AddUpdateAppointmentController implements Initializable {

    /** Text field for appointment ID (auto-generated, not editable). */
    @FXML private TextField appointmentIdField;
    /** Title input for the appointment. */
    @FXML private TextField titleField;
    /** Description input for the appointment. */
    @FXML private TextArea descriptionArea;
    /** Location input for the appointment. */
    @FXML private TextField locationField;
    /** ComboBox for selecting a contact by name. */
    @FXML private ComboBox<String> contactCombo;
    /** Input for appointment type. */
    @FXML private TextField typeField;
    /** Date picker for appointment start date. */
    @FXML private DatePicker startDatePicker;
    /** ComboBox for selecting appointment start time (15-minute intervals). */
    @FXML private ComboBox<LocalTime> startTimeCombo;
    /** Date picker for appointment end date. */
    @FXML private DatePicker endDatePicker;
    /** ComboBox for selecting appointment end time (15-minute intervals). */
    @FXML private ComboBox<LocalTime> endTimeCombo;
    /** ComboBox for selecting the associated customer. */
    @FXML private ComboBox<Customer> customerCombo;
    /** ComboBox for selecting the associated user. */
    @FXML private ComboBox<User> userCombo;
    /** Button to save the appointment. */
    @FXML private Button saveButton;
    /** Button to delete the appointment. */
    @FXML private Button deleteButton;
    /** Label for displaying validation and status messages. */
    @FXML private Label messageLabel;

    /** The appointment being edited (null when adding a new appointment). */
    private Appointment currentAppointment;

    /**
     * Initializes the form, populates ComboBoxes, and sets up time selection.
     * <p>
     * This method uses a lambda expression to generate all possible 15-minute
     * time slots in a day. The lambda improves readability and performance
     * by using streams to efficiently build the list for the ComboBox.
     * </p>
     *
     * @param url        The location used to resolve relative paths for the root object, or null if unknown.
     * @param rb         The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentIdField.setDisable(true);
        try {
            // Populate contacts, customers, and users
            contactCombo.setItems(FXCollections.observableArrayList(ContactDAO.getAllContactNames()));
            customerCombo.setItems(CustomerDAO.getAllCustomers());
            userCombo.setItems(UserDAO.getAllUsers());
        } catch (SQLException e) {
            messageLabel.setText("Error loading data: " + e.getMessage());
        }
        // Populate time ComboBoxes (15-min intervals, lambda used for clarity and efficiency)
        ObservableList<LocalTime> times = IntStream.range(0, 24)
                .boxed()
                .flatMap(hour -> IntStream.range(0, 4)
                        .mapToObj(i -> LocalTime.of(hour, i * 15)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        startTimeCombo.setItems(times);
        endTimeCombo.setItems(times);
    }

    /**
     * Pre-fills the form fields for editing an existing appointment.
     *
     * @param appt The Appointment to edit (null if adding new)
     */
    public void setAppointment(Appointment appt) {
        this.currentAppointment = appt;
        appointmentIdField.setText(String.valueOf(appt.getId()));
        titleField.setText(appt.getTitle());
        descriptionArea.setText(appt.getDescription());
        locationField.setText(appt.getLocation());
        contactCombo.setValue(appt.getContactName());
        typeField.setText(appt.getType());
        startDatePicker.setValue(appt.getStart().toLocalDate());
        startTimeCombo.setValue(appt.getStart().toLocalTime());
        endDatePicker.setValue(appt.getEnd().toLocalDate());
        endTimeCombo.setValue(appt.getEnd().toLocalTime());
        // Select customer and user in ComboBoxes by ID
        customerCombo.getSelectionModel().select(
                customerCombo.getItems().stream().filter(c -> c.getId() == appt.getCustomerId()).findFirst().orElse(null)
        );
        userCombo.getSelectionModel().select(
                userCombo.getItems().stream().filter(u -> u.getId() == appt.getUserId()).findFirst().orElse(null)
        );
    }

    /**
     * Handles Save button action to add or update an appointment.
     * <p>
     * Validates form fields, checks business rules (hours and overlap), and saves to database.
     * Any errors are displayed in {@code messageLabel}.
     * </p>
     *
     * @param event The action event triggered by the Save button.
     */
    @FXML
    private void onSave(ActionEvent event) {
        messageLabel.setText(""); // Clear previous message

        // Gather user input
        String title = titleField.getText().trim();
        String desc  = descriptionArea.getText().trim();
        String loc   = locationField.getText().trim();
        String type  = typeField.getText().trim();
        String contactName = contactCombo.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime startTime = startTimeCombo.getValue();
        LocalTime endTime = endTimeCombo.getValue();
        Customer customer = customerCombo.getValue();
        User user = userCombo.getValue();

        // Input validation
        if (title.isEmpty() || contactName == null || startDate == null || startTime == null ||
                endDate == null || endTime == null || customer == null || user == null || type.isEmpty()) {
            messageLabel.setText("Please fill out all fields.");
            return;
        }

        // Build date-times
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end   = LocalDateTime.of(endDate, endTime);

        if (!end.isAfter(start)) {
            messageLabel.setText("End time must be after start time.");
            return;
        }

        // Business hours validation
        if (!isWithinBusinessHours(start, end)) {
            messageLabel.setText("Appointment must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
            return;
        }

        try {
            // Overlap check for customer appointments
            int currentId = (appointmentIdField.getText().isEmpty()) ? 0 : Integer.parseInt(appointmentIdField.getText());
            int contactId = ContactDAO.getContactIdByName(contactName);
            if (contactId == -1) {
                messageLabel.setText("Invalid contact selection.");
                return;
            }

            // Check overlap with existing appointments
            ObservableList<Appointment> appts = AppointmentDAO.getAppointmentsByCustomer(customer.getId());
            for (Appointment appt : appts) {
                // Skip current appointment if editing
                if (currentId != 0 && appt.getId() == currentId) continue;
                LocalDateTime existStart = appt.getStart();
                LocalDateTime existEnd   = appt.getEnd();
                // Overlap: if start is before another's end, and end after another's start
                if (start.isBefore(existEnd) && end.isAfter(existStart)) {
                    messageLabel.setText("This customer has an overlapping appointment at this time.");
                    return;
                }
            }

            // Save or update the appointment
            Appointment appt = new Appointment(
                    currentId, title, desc, loc, type, start, end,
                    customer.getId(), user.getId(), contactId, contactName
            );
            if (currentId == 0) {
                AppointmentDAO.addAppointment(appt);
                messageLabel.setText("Appointment added!");
            } else {
                AppointmentDAO.updateAppointment(appt);
                messageLabel.setText("Appointment updated!");
            }

            // Close window
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Checks if the appointment times are within business hours (8:00 a.m. – 10:00 p.m. ET).
     *
     * @param start Start time (local)
     * @param end   End time (local)
     * @return True if both times are inside business hours and end >= start; false otherwise.
     */
    private boolean isWithinBusinessHours(LocalDateTime start, LocalDateTime end) {
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime zStart = start.atZone(localZone).withZoneSameInstant(estZone);
        ZonedDateTime zEnd   = end.atZone(localZone).withZoneSameInstant(estZone);
        LocalTime businessOpen  = LocalTime.of(8, 0);   // 8:00 a.m.
        LocalTime businessClose = LocalTime.of(22, 0);  // 10:00 p.m.
        boolean validStart = !zStart.toLocalTime().isBefore(businessOpen) && !zStart.toLocalTime().isAfter(businessClose);
        boolean validEnd   = !zEnd.toLocalTime().isBefore(businessOpen) && !zEnd.toLocalTime().isAfter(businessClose);
        return validStart && validEnd && !zEnd.isBefore(zStart);
    }

    /**
     * Handles the Delete button action to remove an existing appointment.
     * Deletes the current appointment from the database and closes the window.
     *
     * @param event The action event triggered by the Delete button.
     */
    @FXML
    private void onDelete(ActionEvent event) {
        if (currentAppointment != null) {
            try {
                AppointmentDAO.deleteAppointment(currentAppointment.getId());
                messageLabel.setText("Appointment deleted!");
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                messageLabel.setText("Error deleting: " + e.getMessage());
            }
        }
    }
}
