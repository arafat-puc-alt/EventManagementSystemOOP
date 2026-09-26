package model;

/**
 * THIRD level of the hierarchy: Event -> SocialEvent -> Birthday.
 * Demonstrates: three-level INHERITANCE, super(), super.method(), OVERRIDING.
 */
public class Birthday extends SocialEvent {

    private static final double CAKE_AND_DECOR_FEE = 2000.0;
    private int ageMilestone; // 0 if not specified

    public Birthday(String eventId, String customerName, String eventDate,
                     int guestCount, double baseCost, String theme, int ageMilestone) {
        super(eventId, "Birthday Party", customerName, eventDate, guestCount, baseCost, theme);
        this.ageMilestone = ageMilestone;
    }

    public int getAgeMilestone() { return ageMilestone; }
    public void setAgeMilestone(int ageMilestone) { this.ageMilestone = ageMilestone; }

    @Override
    public double calculateCost() {
        // Reuses the SocialEvent calculation, then adds a birthday-specific fee
        return super.calculateCost() + CAKE_AND_DECOR_FEE;
    }

    @Override
    public String getEventDescription() {
        String base = "Birthday Celebration";
        if (ageMilestone > 0) {
            base += " - Turning " + ageMilestone;
        }
        return base;
    }
}
