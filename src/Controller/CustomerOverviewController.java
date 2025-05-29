package Controller;

import DAO.CustomerDAO;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the Customer Overview screen.
 * Handles displaying, adding, updating, and deleting customers using a TableView.
 */
public class CustomerOverviewController implements Initializable {

    // Table and column fields linked to the FXML file
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> idCol;
    @FXML private TableColumn<Customer, String> nameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postalCodeCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> divisionCol;
    @FXML private TableColumn<Customer, String> countryCol;

    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;

    // Holds all customers for the TableView
    private ObservableList<Customer> allCustomers;

    /**
     * This method is called automatically after the FXML file is loaded.
     * Sets up column value factories and loads customer data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link each TableColumn to the appropriate Customer getter using a Simple*Property
        idCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId()).asObject());
        nameCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        addressCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getAddress()));
        postalCodeCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getPostalCode()));
        phoneCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getPhone()));
        divisionCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getDivisionName()));
        countryCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getCountryName()));

        // Load all customers from the database and display in the table
        loadCustomers();
    }

    /**
     * Loads all customers from the DAO and sets them in the TableView.
     * Handles database exceptions gracefully.
     */
    private void loadCustomers() {
        try {
            allCustomers = CustomerDAO.getAllCustomers();
            customerTable.setItems(allCustomers);
        } catch (SQLException e) {
            showError("Database error loading customers: " + e.getMessage());
        }
    }

    /**
     * Handles the Add Customer button click.
     * Opens the add/update dialog in "add" mode.
     */
    @FXML
    private void onAddCustomer(ActionEvent event) {
        showAddUpdateCustomerDialog(null);
    }

    /**
     * Handles the Update Customer button click.
     * Opens the add/update dialog in "update" mode with the selected customer.
     */
    @FXML
    private void onUpdateCustomer(ActionEvent event) {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showAddUpdateCustomerDialog(selected);
        } else {
            showWarning("Please select a customer to update.");
        }
    }

    /**
     * Handles the Delete Customer button click.
     * Deletes the selected customer after confirmation.
     * Catches SQLExceptions so the GUI does not crash.
     */
    @FXML
    private void onDeleteCustomer(ActionEvent event) {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (confirm("Delete selected customer?")) {
                try {
                    CustomerDAO.deleteCustomer(selected.getId());
                    loadCustomers(); // Refresh the table after deletion
                } catch (SQLException e) {
                    showError("Database error deleting customer: " + e.getMessage());
                }
            }
        } else {
            showWarning("Please select a customer to delete.");
        }
    }

    /**
     * Opens the add/update customer dialog.
     * Passes the selected customer if updating, or null if adding.
     */
    private void showAddUpdateCustomerDialog(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddUpdateCustomer.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the customer object for editing
            AddUpdateCustomerController controller = loader.getController();
            controller.setCustomerToEdit(customer);

            // Show the dialog in a new window and wait for it to close
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(customer == null ? "Add Customer" : "Update Customer");
            stage.showAndWait();

            // Refresh the customer table after adding/updating
            loadCustomers();
        } catch (IOException e) {
            showError("Could not load customer form:\n" + e.getMessage());
        }
    }

    /**
     * Shows a warning dialog with a message.
     */
    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }

    /**
     * Shows an error dialog with a message.
     */
    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    /**
     * Shows a confirmation dialog and returns true if the user clicks YES.
     */
    private boolean confirm(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }
}
