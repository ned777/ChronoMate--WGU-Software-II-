// File: src/Model/Contact.java
package Model;

/**
 * Represents a contact person (from the contacts table).
 * Used for appointment linking and ComboBox display.
 */
public class Contact {
    private int id;         // Contact_ID from the database
    private String name;    // Contact_Name from the database

    /**
     * Constructs a Contact object with ID and name.
     * @param id   Contact_ID
     * @param name Contact_Name
     */
    public Contact(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /** @return the Contact_ID */
    public int getId() { return id; }

    /** @return the Contact_Name */
    public String getName() { return name; }

    /**
     * For ComboBox display and string conversion.
     */
    @Override
    public String toString() {
        return name;
    }
}
