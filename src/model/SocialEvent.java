package model;

/**
 * SocialEvent is the SECOND level of the hierarchy (Event -> SocialEvent).
 * Demonstrates: INHERITANCE, METHOD OVERRIDING.
 */
public class SocialEvent extends Event {

    private static final double SOCIAL_SERVICE_FEE = 5000.0;
    private String theme;

    public SocialEvent(String eventId, String eventName, String customerName,
                        String eventDate, int guestCount, double baseCost, String theme) {
        super(eventId, eventName, customerName, eventDate, guestCount, baseCost);
        this.theme = theme;
    }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    protected double getSocialServiceFee() { return SOCIAL_SERVICE_FEE; }

    @Override
    public double calculateCost() {
        return getBaseCost() + SOCIAL_SERVICE_FEE;
    }

    @Override
    public String getEventDescription() {
        return "Social Event" + (theme != null && !theme.isEmpty() ? " - " + theme : "");
    }
}
