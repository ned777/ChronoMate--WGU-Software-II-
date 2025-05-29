package Model;

/**
 * The Country class represents a country record in the system.
 * Stores the country ID and name.
 */
public class Country {
    // Unique country identifier
    private int id;

    // Name of the country
    private String name;

    /**
     * Constructor for Country.
     * @param id   The country ID from the database.
     * @param name The name of the country.
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique ID of the country.
     * @return int country ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the country.
     * @return String country name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the country name when this object is converted to a string.
     * Useful for displaying in ComboBox or TableView.
     * @return String country name.
     */
    @Override
    public String toString() {
        return name;
    }
}
