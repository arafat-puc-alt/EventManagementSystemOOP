package model;

/**
 * Contract is generated once a booking is validated and priced.
 * Only mentions services that were actually selected.
 */
public class Contract {
    private String contractId;
    private String bookingId;
    private Customer customer;
    private Event event;
    private Venue venue;
    private Decoration decoration;             // may be null
    private PhotographyPackage photography;    // may be null
    private CateringPackage catering;          // may be null
    private double discount;   // percentage, e.g. 10.0
    private double incentive;  // percentage, e.g. 5.0
    private String contractStatus; // DRAFT, ACCEPTED, CANCELLED

    public Contract(String contractId, String bookingId, Customer customer, Event event,
                     Venue venue, Decoration decoration, PhotographyPackage photography,
                     CateringPackage catering, double discount, double incentive) {
        this.contractId = contractId;
        this.bookingId = bookingId;
        this.customer = customer;
        this.event = event;
        this.venue = venue;
        this.decoration = decoration;
        this.photography = photography;
        this.catering = catering;
        this.discount = discount;
        this.incentive = incentive;
        this.contractStatus = "DRAFT";
    }

    public String getContractId() { return contractId; }
    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Event getEvent() { return event; }
    public Venue getVenue() { return venue; }
    public Decoration getDecoration() { return decoration; }
    public PhotographyPackage getPhotography() { return photography; }
    public CateringPackage getCatering() { return catering; }
    public double getDiscount() { return discount; }
    public double getIncentive() { return incentive; }

    public String getContractStatus() { return contractStatus; }
    public void setContractStatus(String contractStatus) { this.contractStatus = contractStatus; }

    @Override
    public String toString() {
        return "Contract " + contractId + " for Booking " + bookingId + " [" + contractStatus + "]";
    }
}
