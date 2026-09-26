package model;

/**
 * THIRD level of the hierarchy: Event -> CorporateEvent -> AlumniEvent.
 */
public class AlumniEvent extends CorporateEvent {

    private static final double REUNION_LOGISTICS_FEE = 4000.0;
    private String batchYear;

    public AlumniEvent(String eventId, String customerName, String eventDate,
                        int guestCount, double baseCost, String organizationName, String batchYear) {
        super(eventId, "Alumni Association Event", customerName, eventDate, guestCount, baseCost, organizationName);
        this.batchYear = batchYear;
    }

    public String getBatchYear() { return batchYear; }
    public void setBatchYear(String batchYear) { this.batchYear = batchYear; }

    @Override
    public double calculateCost() {
        return super.calculateCost() + REUNION_LOGISTICS_FEE;
    }

    @Override
    public String getEventDescription() {
        return "Alumni Association Event" + (batchYear != null && !batchYear.isEmpty()
                ? " - Batch " + batchYear : "");
    }
}
