package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for the Appointment Overview screen.
 * <p>
 * Handles displaying, filtering, adding, updating, and deleting appointments.
 * Populates both weekly and monthly appointment tables and handles
 * navigation to the Customers and Reports windows.
 * </p>
 */
public class AppointmentOverviewController implements Initializable {

    /** Add Appointment button. */
    @FXML private Button addButton;
    /** Update Appointment button. */
    @FXML private Button updateButton;
    /** Delete Appointment button. */
    @FXML private Button deleteButton;
    /** Opens the Customers window. */
    @FXML private Button customersButton;
    /** Opens the Reports window. */
    @FXML private Button reportsButton;
    /** TabPane containing Weekly and Monthly appointment tables. */
    @FXML private TabPane tabPane;

    // --- Weekly Table columns ---
    /** TableView for displaying appointments for the week. */
    @FXML private TableView<Appointment> weeklyTable;
    /** Weekly appointment ID column. */
    @FXML private TableColumn<Appointment, Integer> weekIdCol;
    /** Weekly appointment title column. */
    @FXML private TableColumn<Appointment, String> weekTitleCol;
    /** Weekly appointment description column. */
    @FXML private TableColumn<Appointment, String> weekDescCol;
    /** Weekly appointment location column. */
    @FXML private TableColumn<Appointment, String> weekLocationCol;
    /** Weekly appointment contact name column. */
    @FXML private TableColumn<Appointment, String> weekContactCol;
    /** Weekly appointment type column. */
    @FXML private TableColumn<Appointment, String> weekTypeCol;
    /** Weekly appointment start date/time column. */
    @FXML private TableColumn<Appointment, String> weekStartCol;
    /** Weekly appointment end date/time column. */
    @FXML private TableColumn<Appointment, String> weekEndCol;
    /** Weekly appointment customer ID column. */
    @FXML private TableColumn<Appointment, Integer> weekCustomerCol;
    /** Weekly appointment user ID column. */
    @FXML private TableColumn<Appointment, Integer> weekUserCol;

    // --- Monthly Table columns ---
    /** TableView for displaying appointments for the month. */
    @FXML private TableView<Appointment> monthlyTable;
    /** Monthly appointment ID column. */
    @FXML private TableColumn<Appointment, Integer> monthIdCol;
    /** Monthly appointment title column. */
    @FXML private TableColumn<Appointment, String> monthTitleCol;
    /** Monthly appointment description column. */
    @FXML private TableColumn<Appointment, String> monthDescCol;
    /** Monthly appointment location column. */
    @FXML private TableColumn<Appointment, String> monthLocationCol;
    /** Monthly appointment contact name column. */
    @FXML private TableColumn<Appointment, String> monthContactCol;
    /** Monthly appointment type column. */
    @FXML private TableColumn<Appointment, String> monthTypeCol;
    /** Monthly appointment start date/time column. */
    @FXML private TableColumn<Appointment, String> monthStartCol;
    /** Monthly appointment end date/time column. */
    @FXML private TableColumn<Appointment, String> monthEndCol;
    /** Monthly appointment customer ID column. */
    @FXML private TableColumn<Appointment, Integer> monthCustomerCol;
    /** Monthly appointment user ID column. */
    @FXML private TableColumn<Appointment, Integer> monthUserCol;

    /** List containing all appointments loaded from the database. */
    private ObservableList<Appointment> allAppointments;

    /** Stores the resources bundle for i18n support (unused). */
    private ResourceBundle resources;

    /** Formatter used for displaying date and time in tables. */
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Initializes the appointment overview by loading appointments and populating the tables.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        loadAndShowAppointments();
    }

    /**
     * Loads all appointments from the database, sorts them, and populates both weekly and monthly tables.
     * <p>
     * This method uses several lambda expressions for the following reasons:
     * <ul>
     *   <li>To create concise and readable custom cell value factories for formatting start/end date columns.</li>
     *   <li>To define in-place filtering logic for `FilteredList` (weekly and monthly views), which makes
     *       the filter criteria easy to maintain and closely tied to where they're used.</li>
     *   <li>To provide a comparator for sorting the appointments list by start time in a single line.</li>
     * </ul>
     * Using lambdas here greatly improves code clarity and reduces boilerplate that would otherwise be
     * required with full anonymous classes.
     * </p>
     */
    private void loadAndShowAppointments() {
        try {
            allAppointments = AppointmentDAO.getAllAppointments();
            // Sort appointments by start date (lambda comparator for clarity and brevity)
            allAppointments.sort((a, b) -> a.getStart().compareTo(b.getStart()));

            // --- Set up weekly table columns ---
            weekIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            weekTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            weekDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            weekLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            weekContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            weekTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            // Lambda for formatting date/time as a string in the table
            weekStartCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStart().format(displayFormatter)));
            weekEndCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEnd().format(displayFormatter)));
            weekCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            weekUserCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

            // --- Set up monthly table columns ---
            monthIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            monthTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            monthDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            monthLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            monthContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            monthTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            // Lambda for formatting date/time as a string in the table
            monthStartCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStart().format(displayFormatter)));
            monthEndCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEnd().format(displayFormatter)));
            monthCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            monthUserCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

            // --- Filter and display appointments for the current week ---
            LocalDate today = LocalDate.now();
            LocalDate weekStart = today.with(DayOfWeek.MONDAY);
            LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);
            // Lambda here defines filter condition for weekly view
            FilteredList<Appointment> weekly = new FilteredList<>(allAppointments,
                    appt -> {
                        LocalDate d = appt.getStart().toLocalDate();
                        return !d.isBefore(weekStart) && !d.isAfter(weekEnd);
                    }
            );
            weeklyTable.setItems(weekly);

            // --- Filter and display appointments for the current month ---
            Month currentMonth = today.getMonth();
            int year = today.getYear();
            // Lambda here defines filter condition for monthly view
            FilteredList<Appointment> monthly = new FilteredList<>(allAppointments,
                    appt -> {
                        LocalDate d = appt.getStart().toLocalDate();
                        return d.getMonth() == currentMonth && d.getYear() == year;
                    }
            );
            monthlyTable.setItems(monthly);

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Could not load appointments:\n" + ex.getMessage())
                    .showAndWait();
        }
    }

    /**
     * Opens the Add Appointment dialog window when the Add button is clicked.
     *
     * @param event The action event triggered by clicking the Add button.
     */
    @FXML
    private void onAddAppointment(ActionEvent event) {
        showAddUpdateDialog(null, event);
    }

    /**
     * Opens the Update Appointment dialog window for the selected appointment in the current table.
     * Shows a warning if no appointment is selected.
     *
     * @param event The action event triggered by clicking the Update button.
     */
    @FXML
    private void onUpdateAppointment(ActionEvent event) {
        TableView<Appointment> table = (tabPane.getSelectionModel().getSelectedIndex() == 0)
                ? weeklyTable : monthlyTable;
        Appointment sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Please select an appointment first.")
                    .showAndWait();
            return;
        }
        showAddUpdateDialog(sel, event);
    }

    /**
     * Deletes the selected appointment from the current table.
     * Shows a warning if no appointment is selected.
     *
     * @param event The action event triggered by clicking the Delete button.
     */
    @FXML
    private void onDeleteAppointment(ActionEvent event) {
        TableView<Appointment> table = (tabPane.getSelectionModel().getSelectedIndex() == 0)
                ? weeklyTable : monthlyTable;
        Appointment sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Please select an appointment to delete.")
                    .showAndWait();
            return;
        }
        try {
            AppointmentDAO.deleteAppointment(sel.getId());
            allAppointments.remove(sel);
            loadAndShowAppointments();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Error deleting appointment:\n" + ex.getMessage())
                    .showAndWait();
        }
    }

    /**
     * Opens the Add or Update Appointment form as a dialog, optionally pre-filling with appointment data.
     * After the dialog is closed, reloads appointments in the overview.
     *
     * @param appt  The appointment to edit (null if adding new)
     * @param event The action event that triggered this dialog
     */
    private void showAddUpdateDialog(Appointment appt, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/AddUpdateAppointment.fxml"),
                    resources
            );
            Parent root = loader.load();
            Object controller = loader.getController();
            // Only call setAppointment if the method exists
            if (appt != null && controller != null) {
                try {
                    controller.getClass()
                            .getMethod("setAppointment", Appointment.class)
                            .invoke(controller, appt);
                } catch (NoSuchMethodException ignored) {
                    // Do nothing if method does not exist
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Stage dialog = new Stage();
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.setTitle(appt == null ? "Add Appointment" : "Edit Appointment");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
            loadAndShowAppointments();
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Could not load form:\n" + ex.getMessage())
                    .showAndWait();
        }
    }

    /**
     * Opens the Customer Overview window.
     *
     * @param event The action event triggered by clicking the Customers button.
     */
    @FXML
    private void onCustomers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerOverview.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Customer Overview");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Could not open Customer window:\n" + e.getMessage()).showAndWait();
        }
    }

    /**
     * Opens the Reports window.
     *
     * @param event The action event triggered by clicking the Reports button.
     */
    @FXML
    private void onReports(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Reports.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reports");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Could not open Reports window:\n" + e.getMessage()).showAndWait();
        }
    }
}
