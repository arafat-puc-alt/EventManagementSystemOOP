package model;

/**
 * Booking aggregates every object involved in a single reservation.
 * Demonstrates: COMPOSITION. Venue is REQUIRED; Decoration, Photography
 * and Catering are OPTIONAL (may be null - never causes NPE, callers
 * always null-check via the service layer).
 */
public class Booking {
    private String bookingId;
    private Customer customer;
    private Event event;
    private Venue venue;
    private Decoration decoration;             // optional
    private PhotographyPackage photography;    // optional
    private CateringPackage catering;          // optional
    private Contract contract;
    private String status; // PENDING, CONFIRMED, CANCELLED
    private double subtotal;
    private double discount;
    private double finalPrice;

    public Booking(String bookingId, Customer customer, Event event, Venue venue,
                   Decoration decoration, PhotographyPackage photography, CateringPackage catering) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.event = event;
        this.venue = venue;
        this.decoration = decoration;
        this.photography = photography;
        this.catering = catering;
        this.status = "PENDING";
    }

    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Event getEvent() { return event; }
    public Venue getVenue() { return venue; }
    public Decoration getDecoration() { return decoration; }
    public PhotographyPackage getPhotography() { return photography; }
    public CateringPackage getCatering() { return catering; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    @Override
    public String toString() {
        return bookingId + " | " + event.getEventName() + " | " + status +
                " | Final: \u09F3" + finalPrice;
    }
}
