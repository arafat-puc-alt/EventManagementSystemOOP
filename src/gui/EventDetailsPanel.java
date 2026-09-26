package gui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.*;

/**
 * Collects customer + event details and builds the concrete Event
 * subclass (Birthday, Wedding, Anniversary, Conference, AlumniEvent)
 * chosen on the previous screen. Demonstrates DYNAMIC BINDING later,
 * since all of these are simply stored as an "Event" reference.
 */
public class EventDetailsPanel extends JPanel implements MainFrame.Refreshable {

    // Set by EventTypePanel just before navigating here.
    public static String pendingTypeLabel = "BIRTHDAY";

    private final MainFrame frame;
    private final JTextField nameField = new JTextField(20);
    private final JTextField phoneField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField dateField = new JTextField(20);
    private final JTextField guestField = new JTextField(20);
    private int eventCounter = 100;

    public EventDetailsPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("EVENT DETAILS"));
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(UIStyle.BG);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        form.add(labeled("Customer Name", nameField));
        form.add(labeled("Phone", phoneField));
        form.add(labeled("Email", emailField));
        form.add(labeled("Event Date (DD Month YYYY)", dateField));
        form.add(labeled("Number of Guests", guestField));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setBackground(UIStyle.BG);
        JButton continueBtn = UIStyle.primaryButton("CONTINUE");
        continueBtn.addActionListener(e -> handleContinue());
        JButton back = UIStyle.secondaryButton("BACK");
        back.addActionListener(e -> frame.showScreen(MainFrame.EVENT_TYPE));
        buttons.add(back);
        buttons.add(continueBtn);
        form.add(buttons);

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel labeled(String label, JTextField field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(UIStyle.BG);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(UIStyle.FONT_BOLD_BODY);
        field.setMaximumSize(new Dimension(400, 32));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));
        p.add(field);
        p.add(Box.createVerticalStrut(14));
        return p;
    }

    @Override
    public void onShow() {
        if (frame.getCurrentCustomer() != null) {
            nameField.setText(frame.getCurrentCustomer().getName());
            phoneField.setText(frame.getCurrentCustomer().getPhone());
            emailField.setText(frame.getCurrentCustomer().getEmail());
        }
        dateField.setText("");
        guestField.setText("");
    }

    private void handleContinue() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String date = dateField.getText().trim();
        String guestsText = guestField.getText().trim();

        if (name.isEmpty() || date.isEmpty() || guestsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int guestCount;
        try {
            guestCount = Integer.parseInt(guestsText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of guests.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (guestCount <= 0) {
            JOptionPane.showMessageDialog(this, "Guest count must be greater than zero.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer customer = new Customer(
                frame.getCurrentCustomer() != null ? frame.getCurrentCustomer().getCustomerId() : "CUST-1",
                name, phone, email);
        frame.setCurrentCustomer(customer);

        String eventId = "EVT-" + (++eventCounter);
        Event event = buildEvent(eventId, customer, date, guestCount);
        frame.setCurrentEvent(event);
        frame.setSelectedVenue(null);

        frame.showScreen(MainFrame.VENUE);
    }

    /** Builds the correct concrete Event subclass based on the type chosen earlier. */
    private Event buildEvent(String eventId, Customer customer, String date, int guests) {
        double defaultBaseCost = 20000; // starting point; venue/services add the rest
        switch (pendingTypeLabel) {
            case "WEDDING":
                return new Wedding(eventId, customer.getName(), date, guests, defaultBaseCost, "Classic", "Traditional");
            case "ANNIVERSARY":
                return new Anniversary(eventId, customer.getName(), date, guests, defaultBaseCost, "Elegant", 0);
            case "CONFERENCE":
                return new Conference(eventId, customer.getName(), date, guests, defaultBaseCost, "N/A", "General");
            case "ALUMNI ASSOCIATION EVENT":
                return new AlumniEvent(eventId, customer.getName(), date, guests, defaultBaseCost, "N/A", "");
            case "BIRTHDAY":
            default:
                return new Birthday(eventId, customer.getName(), date, guests, defaultBaseCost, "Festive", 0);
        }
    }
}
