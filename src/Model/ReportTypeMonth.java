package Model;

/**
 * Represents the count of appointments grouped by type and month.
 * Used in reporting to display how many appointments of each type
 * occur in each month.
 */
public class ReportTypeMonth {
    /** The appointment type (e.g., "Consultation"). */
    private final String type;
    /** The month name (e.g., "January"). */
    private final String month;
    /** The total number of appointments for this type and month. */
    private final int count;

    /**
     * Constructs a ReportTypeMonth entry.
     *
     * @param type  the appointment type
     * @param month the month name
     * @param count the number of appointments
     */
    public ReportTypeMonth(String type, String month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    /**
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the month name
     */
    public String getMonth() {
        return month;
    }

    /**
     * @return the appointment count for this type and month
     */
    public int getCount() {
        return count;
    }
}
