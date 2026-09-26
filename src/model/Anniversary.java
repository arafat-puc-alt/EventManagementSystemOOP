package model;

/**
 * THIRD level of the hierarchy: Event -> SocialEvent -> Anniversary.
 */
public class Anniversary extends SocialEvent {

    private static final double CELEBRATION_FEE = 3000.0;
    private int yearsCelebrating;

    public Anniversary(String eventId, String customerName, String eventDate,
                        int guestCount, double baseCost, String theme, int yearsCelebrating) {
        super(eventId, "Anniversary", customerName, eventDate, guestCount, baseCost, theme);
        this.yearsCelebrating = yearsCelebrating;
    }

    public int getYearsCelebrating() { return yearsCelebrating; }
    public void setYearsCelebrating(int yearsCelebrating) { this.yearsCelebrating = yearsCelebrating; }

    @Override
    public double calculateCost() {
        return super.calculateCost() + CELEBRATION_FEE;
    }

    @Override
    public String getEventDescription() {
        return "Anniversary" + (yearsCelebrating > 0 ? " - " + yearsCelebrating + " Years" : "");
    }
}
