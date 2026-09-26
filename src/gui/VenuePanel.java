package gui;

import exceptions.CapacityExceededException;
import model.Venue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Venue selection is MANDATORY - there is deliberately no "Skip" button
 * and no Continue button; selecting a venue advances the wizard.
 * Demonstrates: GUI + service-layer capacity validation via
 * CapacityExceededException (checked exception, caught here).
 */
public class VenuePanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JPanel cardList = new JPanel();
    private final JLabel subtitleLabel;

    public VenuePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("SELECT VENUE"));
        subtitleLabel = UIStyle.subtitle("Venue selection is required. Choose one venue to continue.");
        top.add(subtitleLabel);
        add(top, BorderLayout.NORTH);

        cardList.setLayout(new BoxLayout(cardList, BoxLayout.Y_AXIS));
        cardList.setBackground(UIStyle.BG);
        JScrollPane scroll = new JScrollPane(cardList);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(UIStyle.BG);
        JButton back = UIStyle.secondaryButton("BACK");
        back.addActionListener(e -> frame.showScreen(MainFrame.EVENT_DETAILS));
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        cardList.removeAll();
        int guestCount = frame.getCurrentEvent() != null ? frame.getCurrentEvent().getGuestCount() : 0;

        List<Venue> venues = frame.getDataStore().getVenues();
        for (Venue venue : venues) {
            cardList.add(buildCard(venue, guestCount));
            cardList.add(Box.createVerticalStrut(12));
        }
        cardList.revalidate();
        cardList.repaint();
    }

    private JPanel buildCard(Venue venue, int guestCount) {
        boolean insufficientCapacity = guestCount > venue.getCapacity();

        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(16, 0));
        card.setMaximumSize(new Dimension(900, 130));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        boolean isSelected = venue.equals(frame.getSelectedVenue());
        if (isSelected) {
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIStyle.PRIMARY, 2, true),
                    new EmptyBorder(14, 16, 14, 16)));
        }

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIStyle.CARD_BG);

        JLabel name = new JLabel(venue.getVenueName() + (isSelected ? "  ✓ Selected" : ""));
        name.setFont(UIStyle.FONT_SECTION);
        info.add(name);
        info.add(Box.createVerticalStrut(4));
        info.add(new JLabel("Location: " + venue.getLocation()));
        info.add(new JLabel("Capacity: " + venue.getCapacity()
                + (insufficientCapacity ? "   (Insufficient Capacity for " + guestCount + " guests)" : "")));
        info.add(new JLabel("Price: \u09F3" + fmt(venue.getPrice())));
        info.add(new JLabel("Contact: " + venue.getContactInfo()));
        JLabel status = new JLabel(venue.isAvailable() ? "Available" : "Unavailable");
        status.setForeground(venue.isAvailable() ? UIStyle.SUCCESS : UIStyle.DANGER);
        status.setFont(UIStyle.FONT_BOLD_BODY);
        info.add(status);

        card.add(info, BorderLayout.CENTER);

        JButton selectBtn = UIStyle.primaryButton("SELECT");
        selectBtn.setEnabled(venue.isAvailable() && !insufficientCapacity);
        selectBtn.addActionListener(e -> handleSelect(venue));
        JPanel btnWrap = new JPanel(new GridBagLayout());
        btnWrap.setBackground(UIStyle.CARD_BG);
        btnWrap.add(selectBtn);
        card.add(btnWrap, BorderLayout.EAST);

        return card;
    }

    private void handleSelect(Venue venue) {
        try {
            int guestCount = frame.getCurrentEvent().getGuestCount();
            frame.getBookingService().validateVenueCapacity(venue, guestCount);
            frame.setSelectedVenue(venue);
            frame.showScreen(MainFrame.DECORATION);
        } catch (CapacityExceededException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Venue Not Suitable", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String fmt(double value) {
        return String.format("%,.0f", value);
    }
}
