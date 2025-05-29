package Model;

/**
 * Represents the number of appointments associated with a specific customer.
 * Used in reporting the total appointments per customer.
 */
public class CustomerAppointmentCount {
    /** Name of the customer. */
    private final String customerName;
    /** Total count of appointments for this customer. */
    private final int count;

    /**
     * Constructs a CustomerAppointmentCount instance.
     *
     * @param customerName the customer's full name
     * @param count        the number of appointments for the customer
     */
    public CustomerAppointmentCount(String customerName, int count) {
        this.customerName = customerName;
        this.count = count;
    }

    /**
     * @return the customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return the total appointment count for this customer
     */
    public int getCount() {
        return count;
    }
}
