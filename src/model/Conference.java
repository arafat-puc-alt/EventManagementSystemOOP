package model;

/**
 * THIRD level of the hierarchy: Event -> CorporateEvent -> Conference.
 */
public class Conference extends CorporateEvent {

    private static final double AV_EQUIPMENT_FEE = 8000.0;
    private String conferenceTheme;

    public Conference(String eventId, String customerName, String eventDate,
                       int guestCount, double baseCost, String organizationName, String conferenceTheme) {
        super(eventId, "Conference", customerName, eventDate, guestCount, baseCost, organizationName);
        this.conferenceTheme = conferenceTheme;
    }

    public String getConferenceTheme() { return conferenceTheme; }
    public void setConferenceTheme(String conferenceTheme) { this.conferenceTheme = conferenceTheme; }

    @Override
    public double calculateCost() {
        return super.calculateCost() + AV_EQUIPMENT_FEE;
    }

    @Override
    public String getEventDescription() {
        return "Conference" + (conferenceTheme != null && !conferenceTheme.isEmpty()
                ? " - " + conferenceTheme : "");
    }
}
