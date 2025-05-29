package Model;

/**
 * Represents a customer record in the system.
 * Contains basic customer information, including name, address, postal code,
 * phone number, and associated first-level division and country.
 */
public class Customer {
    /** Unique customer identifier (auto-generated). */
    private int id;
    /** Full name of the customer. */
    private String name;
    /** Mailing address (street and city). */
    private String address;
    /** Postal or ZIP code. */
    private String postalCode;
    /** Phone number. */
    private String phone;
    /** ID of the first-level division (state, province, etc.). */
    private int divisionId;
    /** Name of the first-level division (e.g., state/province). */
    private String divisionName;
    /** Name of the country. */
    private String countryName;

    /**
     * Constructs a Customer with all fields specified.
     *
     * @param id           the unique customer ID (0 if new)
     * @param name         the customer's name
     * @param address      the street and city portion of the address
     * @param postalCode   the postal or ZIP code
     * @param phone        the phone number
     * @param divisionId   the ID of the first-level division (state/province)
     * @param divisionName the name of the division (state/province)
     * @param countryName  the name of the country
     */
    public Customer(int id, String name, String address,
                    String postalCode, String phone, int divisionId,
                    String divisionName, String countryName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    // Getters/setters for TableView and DAO
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
}
