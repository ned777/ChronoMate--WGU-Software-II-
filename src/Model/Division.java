package Model;

/**
 * The Division class represents a first-level division (state, province, etc.)
 * within a country in the system.
 */
public class Division {
    // Unique identifier for the division
    private int id;

    // Name of the division (e.g., state, province)
    private String name;

    // The ID of the country this division belongs to
    private int countryId;

    /**
     * Constructor for Division.
     * @param id        The unique ID for the division.
     * @param name      The name of the division.
     * @param countryId The ID of the country to which the division belongs.
     */
    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * Gets the ID of the division.
     * @return int division ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the division.
     * @return String division name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the country ID that this division belongs to.
     * @return int country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Returns the division name for display in UI components like ComboBox.
     * @return String division name.
     */
    @Override
    public String toString() {
        return name;
    }
}
