package model;

/**
 * THIRD level of the hierarchy: Event -> SocialEvent -> Wedding.
 */
public class Wedding extends SocialEvent {

    private static final double BRIDAL_SUITE_FEE = 15000.0;
    private String weddingType; // e.g. Traditional, Modern

    public Wedding(String eventId, String customerName, String eventDate,
                    int guestCount, double baseCost, String theme, String weddingType) {
        super(eventId, "Wedding", customerName, eventDate, guestCount, baseCost, theme);
        this.weddingType = weddingType;
    }

    public String getWeddingType() { return weddingType; }
    public void setWeddingType(String weddingType) { this.weddingType = weddingType; }

    @Override
    public double calculateCost() {
        return super.calculateCost() + BRIDAL_SUITE_FEE;
    }

    @Override
    public String getEventDescription() {
        return "Wedding" + (weddingType != null && !weddingType.isEmpty() ? " (" + weddingType + ")" : "");
    }
}
