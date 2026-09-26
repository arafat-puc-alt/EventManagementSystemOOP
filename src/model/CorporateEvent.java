package model;

/**
 * CorporateEvent is the SECOND level of the hierarchy (Event -> CorporateEvent).
 * Demonstrates: INHERITANCE, METHOD OVERRIDING.
 */
public class CorporateEvent extends Event {

    private static final double CORPORATE_SERVICE_FEE = 10000.0;
    private String organizationName;

    public CorporateEvent(String eventId, String eventName, String customerName,
                           String eventDate, int guestCount, double baseCost, String organizationName) {
        super(eventId, eventName, customerName, eventDate, guestCount, baseCost);
        this.organizationName = organizationName;
    }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    protected double getCorporateServiceFee() { return CORPORATE_SERVICE_FEE; }

    @Override
    public double calculateCost() {
        return getBaseCost() + CORPORATE_SERVICE_FEE;
    }

    @Override
    public String getEventDescription() {
        return "Corporate Event" + (organizationName != null && !organizationName.isEmpty()
                ? " - " + organizationName : "");
    }
}
