package data;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataStore is the in-memory "database" for the application.
 * No external database is used - everything lives in Collections.
 * Demonstrates: ArrayList, HashMap, add/remove/search/iterate operations.
 */
public class DataStore {

    private final List<Venue> venues = new ArrayList<>();
    private final List<Decoration> decorations = new ArrayList<>();
    private final List<PhotographyPackage> photographyPackages = new ArrayList<>();
    private final List<CateringPackage> cateringPackages = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    private final Map<String, Venue> venueMap = new HashMap<>();
    private final Map<String, Booking> bookingMap = new HashMap<>();

    private int bookingCounter = 1000;
    private int contractCounter = 1000;

    public DataStore() {
        loadSampleData();
    }

    private void loadSampleData() {
        // ----- Venues -----
        addVenue(new Venue("V001", "Royal Palace", "Chattogram", 500, 80000, "01711-000001", true));
        addVenue(new Venue("V002", "Grand Convention Hall", "Chattogram", 300, 60000, "01711-000002", true));
        addVenue(new Venue("V003", "Ocean View Convention Center", "Cox's Bazar", 700, 120000, "01711-000003", true));
        addVenue(new Venue("V004", "Garden View", "Chattogram", 150, 40000, "01711-000004", true));
        addVenue(new Venue("V005", "City Community Center", "Chattogram", 250, 35000, "01711-000005", true));

        // ----- Decorations -----
        decorations.add(new Decoration("D001", "Classic Decoration", "Simple elegant stage & floral setup",
                "Classic", 25000, "Chattogram Decor Co.", "01811-100001", true));
        decorations.add(new Decoration("D002", "Modern Decoration", "Contemporary lighting & minimalist theme",
                "Modern", 35000, "Urban Events Decor", "01811-100002", true));
        decorations.add(new Decoration("D003", "Luxury Decoration", "Premium stage, drapery & LED lighting",
                "Luxury", 50000, "Royal Decor House", "01811-100003", true));
        decorations.add(new Decoration("D004", "Floral Theme", "All-natural floral arrangements throughout venue",
                "Floral", 30000, "Petal & Bloom", "01811-100004", true));

        // ----- Photography -----
        photographyPackages.add(new PhotographyPackage("P001", "Basic Photography",
                "Candid + posed photography", "Rafiq Studio", 4, 15000, "01911-200001", true));
        photographyPackages.add(new PhotographyPackage("P002", "Premium Photography",
                "Full-day coverage with edited album", "Lensmen Studio", 8, 30000, "01911-200002", true));
        photographyPackages.add(new PhotographyPackage("P003", "Cinematic Package",
                "Photography + cinematic videography", "FrameWorks Media", 10, 45000, "01911-200003", true));

        // ----- Catering -----
        cateringPackages.add(new CateringPackage("C001", "Standard Catering",
                "Rice, chicken curry, salad, dessert", "Delight Caterers", 450, "01611-300001", true));
        cateringPackages.add(new CateringPackage("C002", "Premium Catering",
                "Biryani, kebab, multiple curries, dessert bar", "Royal Caterers", 650, "01611-300002", true));
        cateringPackages.add(new CateringPackage("C003", "Traditional Bangladeshi Package",
                "Full traditional course with regional specialties", "Heritage Caterers", 550, "01611-300003", true));
    }

    // ----- Generic add/remove/search demonstrating Collections usage -----
    public void addVenue(Venue venue) {
        venues.add(venue);
        venueMap.put(venue.getVenueId(), venue);
    }

    public void removeVenue(String venueId) {
        venues.removeIf(v -> v.getVenueId().equals(venueId));
        venueMap.remove(venueId);
    }

    public Venue getVenueById(String venueId) {
        return venueMap.get(venueId);
    }

    public List<Venue> getVenues() { return venues; }
    public List<Decoration> getDecorations() { return decorations; }
    public List<PhotographyPackage> getPhotographyPackages() { return photographyPackages; }
    public List<CateringPackage> getCateringPackages() { return cateringPackages; }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        bookingMap.put(booking.getBookingId(), booking);
    }

    public Booking getBookingById(String bookingId) {
        return bookingMap.get(bookingId);
    }

    public List<Booking> getBookings() { return bookings; }

    public synchronized String nextBookingId() {
        return "BK-" + (++bookingCounter);
    }

    public synchronized String nextContractId() {
        return "C-" + (++contractCounter);
    }
}
