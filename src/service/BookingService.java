package service;

import data.DataStore;
import exceptions.CapacityExceededException;
import model.*;

/**
 * BookingService owns the business rules for creating a booking:
 * venue-capacity validation and assembly of the Booking object.
 * GUI classes call into this layer instead of embedding logic
 * in ActionListeners.
 */
public class BookingService {

    private final DataStore dataStore;
    private final PricingService pricingService;

    public BookingService(DataStore dataStore, PricingService pricingService) {
        this.dataStore = dataStore;
        this.pricingService = pricingService;
    }

    /** Throws a checked exception if the venue cannot hold the guest count. */
    public void validateVenueCapacity(Venue venue, int guestCount) throws CapacityExceededException {
        if (venue == null) {
            throw new CapacityExceededException("Please select a venue before continuing.");
        }
        if (!venue.isAvailable()) {
            throw new CapacityExceededException("The selected venue is not available.");
        }
        if (guestCount > venue.getCapacity()) {
            throw new CapacityExceededException(
                    "The selected venue cannot accommodate " + guestCount + " guests. " +
                            "Please select another venue.");
        }
    }

    public Booking createBooking(Customer customer, Event event, Venue venue,
                                  Decoration decoration, PhotographyPackage photography,
                                  CateringPackage catering) throws CapacityExceededException {

        validateVenueCapacity(venue, event.getGuestCount());

        String bookingId = dataStore.nextBookingId();
        Booking booking = new Booking(bookingId, customer, event, venue, decoration, photography, catering);
        pricingService.calculateBookingTotal(booking);
        booking.setStatus("PENDING");

        dataStore.addBooking(booking);
        return booking;
    }

    public void confirmBooking(Booking booking) {
        booking.setStatus("CONFIRMED");
    }

    public DataStore getDataStore() { return dataStore; }
}
