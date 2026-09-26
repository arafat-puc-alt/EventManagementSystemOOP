package service;

import model.Booking;

/**
 * PricingService centralizes all price/discount/incentive math so the
 * GUI never hard-codes a total.
 */
public class PricingService {

    public static final double CUSTOMER_DISCOUNT_RATE = 0.10; // 10%
    public static final double PROVIDER_INCENTIVE_RATE = 0.05; // 5%

    /**
     * Calculates subtotal, discount and final price for a booking and
     * writes them back onto the Booking object.
     */
    public void calculateBookingTotal(Booking booking) {
        double venueCost = booking.getVenue() != null ? booking.getVenue().getPrice() : 0;

        double decorationCost = booking.getDecoration() != null
                ? booking.getDecoration().getPrice() : 0;

        double photographyCost = booking.getPhotography() != null
                ? booking.getPhotography().getPrice() : 0;

        double cateringCost = booking.getCatering() != null
                ? booking.getCatering().getPricePerPerson() * booking.getEvent().getGuestCount()
                : 0;

        double subtotal = venueCost + decorationCost + photographyCost + cateringCost;
        double discount = subtotal * CUSTOMER_DISCOUNT_RATE;
        double finalPrice = subtotal - discount;

        booking.setSubtotal(subtotal);
        booking.setDiscount(discount);
        booking.setFinalPrice(finalPrice);
    }

    /** The 5% amount the platform earns from providers on a successful booking. */
    public double calculateProviderIncentive(Booking booking) {
        return booking.getSubtotal() * PROVIDER_INCENTIVE_RATE;
    }

    public double getCateringTotal(model.CateringPackage catering, int guestCount) {
        return catering == null ? 0 : catering.getPricePerPerson() * guestCount;
    }
}
