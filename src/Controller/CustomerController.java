package Controller;

import DAO.CustomerDAO;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory; // <-- Add this import!
import java.sql.SQLException;

/**
 * Controller for managing Customer CRUD operations and TableView.
 * Supports add, update, and delete, with country/division filtering.
 */
public class CustomerController {
    @FXML private TextField idField, nameField, addressField, postalField, phoneField;
    @FXML private ComboBox<String> countryCombo, divisionCombo;
    @FXML private Label messageLabel;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> idCol;
    @FXML private TableColumn<Customer, String> nameCol, addressCol, postalCol, phoneCol, divisionCol, countryCol;
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns (PropertyValueFactory) and load customers
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        try {
            customers.setAll(CustomerDAO.getAllCustomers());
            customerTable.setItems(customers);
            countryCombo.setItems(CustomerDAO.getAllCountries());
        } catch (SQLException e) {
            messageLabel.setText("Error loading customers: " + e.getMessage());
        }
        // When a country is selected, filter the division ComboBox
        countryCombo.setOnAction(e -> {
            String country = countryCombo.getValue();
            try {
                divisionCombo.setItems(CustomerDAO.getDivisionsByCountry(country));
            } catch (SQLException ex) {
                divisionCombo.setItems(FXCollections.observableArrayList());
            }
        });
        // Auto-fill form on row select
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, cust) -> {
            if (cust != null) {
                idField.setText(String.valueOf(cust.getId()));
                nameField.setText(cust.getName());
                addressField.setText(cust.getAddress());
                postalField.setText(cust.getPostalCode());
                phoneField.setText(cust.getPhone());
                countryCombo.setValue(cust.getCountryName());
                try {
                    divisionCombo.setItems(CustomerDAO.getDivisionsByCountry(cust.getCountryName()));
                } catch (SQLException ex) {
                    divisionCombo.setItems(FXCollections.observableArrayList());
                }
                divisionCombo.setValue(cust.getDivisionName());
            }
        });
    }

    @FXML
    private void onSaveCustomer() {
        try {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String postal = postalField.getText().trim();
            String phone = phoneField.getText().trim();
            String country = countryCombo.getValue();
            String division = divisionCombo.getValue();
            if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty() || country == null || division == null) {
                messageLabel.setText("Please fill all fields!");
                return;
            }
            int divisionId = CustomerDAO.getDivisionIdByName(division);
            int id = idField.getText().isEmpty() ? 0 : Integer.parseInt(idField.getText());
            if (id == 0) {
                // Add
                CustomerDAO.addCustomer(new Customer(0, name, address, postal, phone, divisionId, division, country));
                messageLabel.setText("Customer added!");
            } else {
                // Update
                CustomerDAO.updateCustomer(new Customer(id, name, address, postal, phone, divisionId, division, country));
                messageLabel.setText("Customer updated!");
            }
            customers.setAll(CustomerDAO.getAllCustomers());
            clearForm();
        } catch (SQLException e) {
            messageLabel.setText("Error saving customer: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteCustomer() {
        Customer cust = customerTable.getSelectionModel().getSelectedItem();
        if (cust == null) {
            messageLabel.setText("Select a customer to delete.");
            return;
        }
        try {
            CustomerDAO.deleteCustomer(cust.getId());
            messageLabel.setText("Customer and all appointments deleted!");
            customers.setAll(CustomerDAO.getAllCustomers());
            clearForm();
        } catch (SQLException e) {
            messageLabel.setText("Error deleting customer: " + e.getMessage());
        }
    }

    private void clearForm() {
        idField.clear();
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryCombo.setValue(null);
        divisionCombo.setValue(null);
    }
}
