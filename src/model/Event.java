package model;

/**
 * Event is the ROOT of the three-level inheritance hierarchy:
 *      Event -> SocialEvent -> Birthday/Wedding/Anniversary
 *      Event -> CorporateEvent -> Conference/AlumniEvent
 *
 * Demonstrates: ABSTRACTION, ENCAPSULATION, CONSTRUCTOR OVERLOADING,
 * METHOD OVERLOADING (calculateCost() / calculateCost(int)).
 */
public abstract class Event {

    private String eventId;
    private String eventName;
    private String customerName;
    private String eventDate;
    private int guestCount;
    private double baseCost;

    // Full constructor
    public Event(String eventId, String eventName, String customerName,
                 String eventDate, int guestCount, double baseCost) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.customerName = customerName;
        this.eventDate = eventDate;
        this.guestCount = guestCount;
        this.baseCost = baseCost;
    }

    // Overloaded constructor - used when customer/date are not yet known
    public Event(String eventId, String eventName, int guestCount, double baseCost) {
        this(eventId, eventName, "Unknown", "TBD", guestCount, baseCost);
    }

    // ----- Abstract methods: implemented differently by every subclass -----
    public abstract double calculateCost();
    public abstract String getEventDescription();

    // ----- Overloaded method: same name, different signature -----
    // Recalculates cost if the guest count changes without mutating this event.
    public double calculateCost(int newGuestCount) {
        double perGuestAdjustment = (newGuestCount - this.guestCount) * 50.0;
        return calculateCost() + Math.max(perGuestAdjustment, 0);
    }

    // ----- Getters / Setters (Encapsulation) -----
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public int getGuestCount() { return guestCount; }
    public void setGuestCount(int guestCount) {
        if (guestCount <= 0) {
            throw new IllegalArgumentException("Guest count must be greater than zero.");
        }
        this.guestCount = guestCount;
    }

    public double getBaseCost() { return baseCost; }
    public void setBaseCost(double baseCost) { this.baseCost = baseCost; }

    @Override
    public String toString() {
        return eventName + " (" + eventId + ") for " + customerName +
                " on " + eventDate + " | Guests: " + guestCount;
    }
}
