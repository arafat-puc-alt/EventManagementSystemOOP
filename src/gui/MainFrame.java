package gui;

import data.DataStore;
import model.*;
import model.Event;
import service.BookingService;
import service.ContractService;
import service.PricingService;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame hosts every screen as a JPanel switched via CardLayout.
 * It also holds the current "session state" (the objects selected so
 * far as the user walks through the booking wizard) and exposes
 * navigation + service access to every panel.
 */
public class MainFrame extends JFrame {

    public static final String LOGIN = "LOGIN";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String EVENT_CATEGORY = "EVENT_CATEGORY";
    public static final String EVENT_TYPE = "EVENT_TYPE";
    public static final String EVENT_DETAILS = "EVENT_DETAILS";
    public static final String VENUE = "VENUE";
    public static final String DECORATION = "DECORATION";
    public static final String PHOTOGRAPHY = "PHOTOGRAPHY";
    public static final String CATERING = "CATERING";
    public static final String SUMMARY = "SUMMARY";
    public static final String CONTRACT = "CONTRACT";
    public static final String CONFIRMATION = "CONFIRMATION";
    public static final String MY_BOOKINGS = "MY_BOOKINGS";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    // ----- Services / data layer -----
    private final DataStore dataStore = new DataStore();
    private final PricingService pricingService = new PricingService();
    private final BookingService bookingService = new BookingService(dataStore, pricingService);
    private final ContractService contractService = new ContractService(dataStore);

    // ----- Session state (selections made while walking the wizard) -----
    private Customer currentCustomer;
    private String selectedCategory;   // "SOCIAL" or "CORPORATE"
    private Event currentEvent;
    private Venue selectedVenue;
    private Decoration selectedDecoration;
    private PhotographyPackage selectedPhotography;
    private CateringPackage selectedCatering;
    private Booking currentBooking;
    private Contract currentContract;

    // Panels that need a refresh callback when shown
    private final java.util.Map<String, Refreshable> refreshablePanels = new java.util.HashMap<>();

    public interface Refreshable {
        void onShow();
    }

    public MainFrame() {
        super("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 680));
        setLocationRelativeTo(null);

        cardPanel.setBackground(UIStyle.BG);
        addPanel(LOGIN, new LoginPanel(this));
        addPanel(DASHBOARD, new DashboardPanel(this));
        addPanel(EVENT_CATEGORY, new EventCategoryPanel(this));
        addPanel(EVENT_TYPE, new EventTypePanel(this));
        addPanel(EVENT_DETAILS, new EventDetailsPanel(this));
        addPanel(VENUE, new VenuePanel(this));
        addPanel(DECORATION, new DecorationPanel(this));
        addPanel(PHOTOGRAPHY, new PhotographyPanel(this));
        addPanel(CATERING, new CateringPanel(this));
        addPanel(SUMMARY, new BookingSummaryPanel(this));
        addPanel(CONTRACT, new ContractPanel(this));
        addPanel(CONFIRMATION, new ConfirmationPanel(this));
        addPanel(MY_BOOKINGS, new MyBookingsPanel(this));

        add(cardPanel);
        showScreen(LOGIN);
    }

    private void addPanel(String name, JPanel panel) {
        cardPanel.add(panel, name);
        if (panel instanceof Refreshable) {
            refreshablePanels.put(name, (Refreshable) panel);
        }
    }

    public void showScreen(String name) {
        Refreshable r = refreshablePanels.get(name);
        if (r != null) {
            r.onShow();
        }
        cardLayout.show(cardPanel, name);
    }

    /** Resets the entire booking wizard state - called when starting a new event. */
    public void resetWizardState() {
        selectedCategory = null;
        currentEvent = null;
        selectedVenue = null;
        selectedDecoration = null;
        selectedPhotography = null;
        selectedCatering = null;
        currentBooking = null;
        currentContract = null;
    }

    // ----- Getters / setters for session state -----
    public DataStore getDataStore() { return dataStore; }
    public BookingService getBookingService() { return bookingService; }
    public ContractService getContractService() { return contractService; }
    public PricingService getPricingService() { return pricingService; }

    public Customer getCurrentCustomer() { return currentCustomer; }
    public void setCurrentCustomer(Customer currentCustomer) { this.currentCustomer = currentCustomer; }

    public String getSelectedCategory() { return selectedCategory; }
    public void setSelectedCategory(String selectedCategory) { this.selectedCategory = selectedCategory; }

    public Event getCurrentEvent() { return currentEvent; }
    public void setCurrentEvent(Event currentEvent) { this.currentEvent = currentEvent; }

    public Venue getSelectedVenue() { return selectedVenue; }
    public void setSelectedVenue(Venue selectedVenue) { this.selectedVenue = selectedVenue; }

    public Decoration getSelectedDecoration() { return selectedDecoration; }
    public void setSelectedDecoration(Decoration selectedDecoration) { this.selectedDecoration = selectedDecoration; }

    public PhotographyPackage getSelectedPhotography() { return selectedPhotography; }
    public void setSelectedPhotography(PhotographyPackage selectedPhotography) { this.selectedPhotography = selectedPhotography; }

    public CateringPackage getSelectedCatering() { return selectedCatering; }
    public void setSelectedCatering(CateringPackage selectedCatering) { this.selectedCatering = selectedCatering; }

    public Booking getCurrentBooking() { return currentBooking; }
    public void setCurrentBooking(Booking currentBooking) { this.currentBooking = currentBooking; }

    public Contract getCurrentContract() { return currentContract; }
    public void setCurrentContract(Contract currentContract) { this.currentContract = currentContract; }
}
