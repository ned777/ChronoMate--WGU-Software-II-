package Model;

import java.time.LocalDateTime;

/**
 * Represents an entry in the schedule for a single contact.
 * Contains appointment details for display in a contact-specific report.
 */
public class ContactSchedule {
    /** Name of the contact for this appointment. */
    private final String contactName;
    /** Unique identifier for the appointment. */
    private final int appointmentId;
    /** Title of the appointment. */
    private final String title;
    /** Business-specific type of the appointment. */
    private final String type;
    /** Detailed description of the appointment. */
    private final String description;
    /** Local date-time when the appointment starts. */
    private final LocalDateTime start;
    /** Local date-time when the appointment ends. */
    private final LocalDateTime end;
    /** ID of the customer associated with this appointment. */
    private final int customerId;

    /**
     * Constructs a ContactSchedule entry with all fields.
     *
     * @param contactName   the name of the contact person
     * @param appointmentId the unique appointment ID
     * @param title         the appointment title
     * @param type          the type/category of the appointment
     * @param description   detailed description of the appointment
     * @param start         the local start date and time
     * @param end           the local end date and time
     * @param customerId    the ID of the associated customer
     */
    public ContactSchedule(String contactName, int appointmentId,
                           String title, String type, String description,
                           LocalDateTime start, LocalDateTime end,
                           int customerId) {
        this.contactName = contactName;
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the start date and time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return the end date and time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }
}
