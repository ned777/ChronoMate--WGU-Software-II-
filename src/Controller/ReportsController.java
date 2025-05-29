package Controller;

import Model.ReportTypeMonth;
import Model.ContactSchedule;
import Model.CustomerAppointmentCount;
import DAO.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the Reports screen.
 * Displays:
 * 1. Appointment count by type and month.
 * 2. Appointment schedule per contact.
 * 3. Appointment count per customer.
 */
public class ReportsController implements Initializable {

    // --- Tab 1: Appointments by Type & Month ---
    @FXML private TableView<ReportTypeMonth> typeMonthTable;
    @FXML private TableColumn<ReportTypeMonth, String> typeCol;
    @FXML private TableColumn<ReportTypeMonth, String> monthCol;
    @FXML private TableColumn<ReportTypeMonth, Integer> countCol;

    // --- Tab 2: Appointments per Contact ---
    @FXML private TableView<ContactSchedule> contactTable;
    @FXML private TableColumn<ContactSchedule, String> contactNameCol;
    @FXML private TableColumn<ContactSchedule, Integer> apptIdCol2;
    @FXML private TableColumn<ContactSchedule, String> titleCol2;
    @FXML private TableColumn<ContactSchedule, String> typeCol2;
    @FXML private TableColumn<ContactSchedule, String> descCol2;
    @FXML private TableColumn<ContactSchedule, LocalDateTime> startCol2;
    @FXML private TableColumn<ContactSchedule, LocalDateTime> endCol2;
    @FXML private TableColumn<ContactSchedule, Integer> custIdCol2;

    // --- Tab 3: Appointment Count per Customer ---
    @FXML private TableView<CustomerAppointmentCount> custCountTable;
    @FXML private TableColumn<CustomerAppointmentCount, String> custNameCol3;
    @FXML private TableColumn<CustomerAppointmentCount, Integer> custCountCol3;

    /**
     * Initializes report tables and loads report data from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // --- Setup columns for Tab 1 ---
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

            ObservableList<ReportTypeMonth> tmList = AppointmentDAO.getCountByTypeAndMonth();
            typeMonthTable.setItems(tmList);

            // --- Setup columns for Tab 2 ---
            contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            apptIdCol2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol2.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
            descCol2.setCellValueFactory(new PropertyValueFactory<>("description"));
            startCol2.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol2.setCellValueFactory(new PropertyValueFactory<>("end"));
            custIdCol2.setCellValueFactory(new PropertyValueFactory<>("customerId"));

            ObservableList<ContactSchedule> csList = AppointmentDAO.getContactSchedules();
            contactTable.setItems(csList);

            // --- Setup columns for Tab 3 ---
            custNameCol3.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custCountCol3.setCellValueFactory(new PropertyValueFactory<>("count"));

            ObservableList<CustomerAppointmentCount> ccList = AppointmentDAO.getCountByCustomer();
            custCountTable.setItems(ccList);

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Could not load reports from the database:\n" + ex.getMessage()
            ).showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Unexpected error loading reports:\n" + ex.getMessage()
            ).showAndWait();
        }
    }
}
