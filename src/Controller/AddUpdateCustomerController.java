package Controller;

import DAO.CustomerDAO;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller for adding or updating a customer.
 * Handles form logic for AddUpdateCustomer.fxml.
 */
public class AddUpdateCustomerController {

    // Form fields matching FXML fx:id attributes
    @FXML private TextField customerIdField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalField;
    @FXML private TextField phoneField;

    @FXML private ComboBox<String> countryCombo;
    @FXML private ComboBox<String> divisionCombo;

    @FXML private Label messageLabel;

    @FXML private Button saveButton;
    @FXML private Button deleteButton;

    // Holds the customer being edited, or null for adding
    private Customer customerToEdit;

    /**
     * Initializes the form with default or existing data.
     */
    @FXML
    public void initialize() {
        // Initialize combo boxes (replace with actual data loading if needed)
        countryCombo.getItems().addAll("USA", "Canada");
        divisionCombo.getItems().addAll("California", "Texas", "Ontario", "Quebec");
        messageLabel.setText("");
        customerIdField.setDisable(true); // ID is usually auto-generated and not editable
    }

    /**
     * Loads customer data into the form for editing.
     * @param customer the customer to edit, or null for add mode
     */
    public void setCustomerToEdit(Customer customer) {
        this.customerToEdit = customer;
        if (customer != null) {
            customerIdField.setText(String.valueOf(customer.getId()));
            nameField.setText(customer.getName());
            addressField.setText(customer.getAddress());
            postalField.setText(customer.getPostalCode());
            phoneField.setText(customer.getPhone());
            divisionCombo.setValue(customer.getDivisionName());
            countryCombo.setValue(customer.getCountryName());
        } else {
            customerIdField.setText("Auto");
            nameField.setText("");
            addressField.setText("");
            postalField.setText("");
            phoneField.setText("");
            divisionCombo.setValue(null);
            countryCombo.setValue(null);
        }
    }

    /**
     * Handles saving the customer record.
     * Called by the Save button.
     */
    @FXML
    private void onSave(ActionEvent event) {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String postal = postalField.getText().trim();
        String phone = phoneField.getText().trim();
        String divisionName = divisionCombo.getValue();
        String countryName = countryCombo.getValue();

        // Basic validation
        if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()
                || divisionName == null || countryName == null) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        try {
            int divisionId = 1; // Placeholder, replace with actual lookup if needed
            if (customerToEdit == null) {
                // Add new customer
                Customer newCustomer = new Customer(
                        0,
                        name,
                        address,
                        postal,
                        phone,
                        divisionId,
                        divisionName,
                        countryName
                );
                CustomerDAO.addCustomer(newCustomer);
                messageLabel.setText("Customer added.");
            } else {
                // Update existing customer
                customerToEdit.setName(name);
                customerToEdit.setAddress(address);
                customerToEdit.setPostalCode(postal);
                customerToEdit.setPhone(phone);
                customerToEdit.setDivisionId(divisionId);
                customerToEdit.setDivisionName(divisionName);
                customerToEdit.setCountryName(countryName);
                CustomerDAO.updateCustomer(customerToEdit);
                messageLabel.setText("Customer updated.");
            }
            closeWindow();
        } catch (Exception e) {
            messageLabel.setText("Error saving customer: " + e.getMessage());
        }
    }

    /**
     * Handles deleting the customer record.
     * Called by the Delete button.
     */
    @FXML
    private void onDelete(ActionEvent event) {
        if (customerToEdit != null) {
            try {
                CustomerDAO.deleteCustomer(customerToEdit.getId());
                messageLabel.setText("Customer deleted.");
                closeWindow();
            } catch (Exception e) {
                messageLabel.setText("Error deleting customer: " + e.getMessage());
            }
        } else {
            messageLabel.setText("No customer to delete.");
        }
    }

    /**
     * Closes the window.
     */
    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
