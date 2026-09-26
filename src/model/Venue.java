package model;

import interfaces.Trackable;

/**
 * Venue is a MANDATORY component of every booking.
 * Demonstrates: INTERFACE IMPLEMENTATION (Trackable), ENCAPSULATION.
 */
public class Venue implements Trackable {
    private String venueId;
    private String venueName;
    private String location;
    private int capacity;
    private double price;
    private String contactInfo;
    private boolean available;

    public Venue(String venueId, String venueName, String location,
                 int capacity, double price, String contactInfo, boolean available) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
        this.contactInfo = contactInfo;
        this.available = available;
    }

    public String getVenueId() { return venueId; }
    public void setVenueId(String venueId) { this.venueId = venueId; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public String getContactInfo() { return contactInfo; }

    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    @Override
    public String toString() {
        return venueName + " | " + location + " | Capacity: " + capacity +
                " | Price: \u09F3" + price + " | " + (available ? "Available" : "Unavailable");
    }
}
