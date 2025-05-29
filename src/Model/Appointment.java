package Model;

import java.time.LocalDateTime;

/**
 * Represents a scheduled appointment in the system.
 * <p>
 * Contains all relevant details including title, description, location,
 * type, start/end timestamps (in local time), associated customer and user IDs,
 * and both the Contact_ID (database key) and Contact_Name (for display).
 * </p>
 */
public class Appointment {
    /** Unique appointment identifier (auto-generated). */
    private int id;
    /** Title of the appointment. */
    private String title;
    /** Detailed description of the appointment. */
    private String description;
    /** Location where the appointment takes place. */
    private String location;
    /** Appointment type/category (e.g., "Consultation"). */
    private String type;
    /** Start date-time (in the user's local time zone). */
    private LocalDateTime start;
    /** End date-time (in the user's local time zone). */
    private LocalDateTime end;
    /** Customer_ID (foreign key to customers table). */
    private int customerId;
    /** User_ID (foreign key to users table). */
    private int userId;
    /** Contact_ID (foreign key to contacts table). */
    private int contactId;
    /** Contact name for display (from contacts table, not stored in appointments table). */
    private String contactName;

    /**
     * Constructs a full Appointment object.
     *
     * @param id           the unique appointment ID (0 if new)
     * @param title        the title of the appointment
     * @param description  details about the appointment
     * @param location     location of the appointment
     * @param type         type/category (e.g. "Review", "Consultation")
     * @param start        start date-time (local)
     * @param end          end date-time (local)
     * @param customerId   customer foreign key
     * @param userId       user foreign key
     * @param contactId    contact foreign key
     * @param contactName  contact display name (not stored in appointments table)
     */
    public Appointment(
            int id,
            String title,
            String description,
            String location,
            String type,
            LocalDateTime start,
            LocalDateTime end,
            int customerId,
            int userId,
            int contactId,
            String contactName
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    // --- Getters ---
    /** @return the appointment ID */
    public int getId() { return id; }
    /** @return the appointment title */
    public String getTitle() { return title; }
    /** @return the appointment description */
    public String getDescription() { return description; }
    /** @return the appointment location */
    public String getLocation() { return location; }
    /** @return the appointment type/category */
    public String getType() { return type; }
    /** @return the local start date and time */
    public LocalDateTime getStart() { return start; }
    /** @return the local end date and time */
    public LocalDateTime getEnd() { return end; }
    /** @return the customer ID */
    public int getCustomerId() { return customerId; }
    /** @return the user ID */
    public int getUserId() { return userId; }
    /** @return the contact ID */
    public int getContactId() { return contactId; }
    /** @return the contact name (from contacts table) */
    public String getContactName() { return contactName; }

    // --- Setters for fields that may change ---
    /** Set the appointment ID (after DB insert) */
    public void setId(int id) { this.id = id; }
    /** Set the contact display name (from contacts table, used for GUI display) */
    public void setContactName(String contactName) { this.contactName = contactName; }
}
