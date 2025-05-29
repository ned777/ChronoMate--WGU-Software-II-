package Model;

/**
 * Represents an application user in the scheduling system.
 * <p>
 * Contains user identification for authentication and assignment
 * of appointments.</p>
 */
public class User {
    /** Unique user identifier. */
    private int id;
    /** The username used for login display. */
    private String name;

    /**
     * Constructs a User with the given ID and username.
     *
     * @param id   the user's unique ID
     * @param name the user's login name
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the user's ID.
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's ID (used after database insert).
     *
     * @param id the new user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user's login name.
     *
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's login name.
     *
     * @param name the new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's name for display in UI elements.
     *
     * @return the username
     */
    @Override
    public String toString() {
        return name;
    }
}
