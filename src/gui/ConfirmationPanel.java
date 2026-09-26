package gui;

import model.Booking;

import javax.swing.*;
import java.awt.*;

public class ConfirmationPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JPanel body = new JPanel();

    public ConfirmationPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        JLabel check = UIStyle.title("\u2713 BOOKING CONFIRMED");
        check.setForeground(UIStyle.SUCCESS);
        top.add(check);
        add(top, BorderLayout.NORTH);

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIStyle.CARD_BG);
        body.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(body, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(UIStyle.BG);
        JButton viewBooking = UIStyle.secondaryButton("VIEW BOOKING");
        viewBooking.addActionListener(e -> frame.showScreen(MainFrame.MY_BOOKINGS));
        JButton backHome = UIStyle.primaryButton("BACK TO DASHBOARD");
        backHome.addActionListener(e -> {
            frame.resetWizardState();
            frame.showScreen(MainFrame.DASHBOARD);
        });
        bottom.add(viewBooking);
        bottom.add(backHome);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        body.removeAll();
        Booking b = frame.getCurrentBooking();
        if (b == null) {
            body.revalidate();
            body.repaint();
            return;
        }
        body.add(row("Booking ID", b.getBookingId()));
        body.add(row("Contract ID", b.getContract() != null ? b.getContract().getContractId() : "N/A"));
        body.add(row("Event", b.getEvent().getEventDescription()));
        body.add(row("Date", b.getEvent().getEventDate()));
        body.add(row("Guests", String.valueOf(b.getEvent().getGuestCount())));
        body.add(row("Venue", b.getVenue().getVenueName()));
        body.add(row("Decoration", b.getDecoration() != null ? b.getDecoration().getName() : "Not Selected"));
        body.add(row("Photography", b.getPhotography() != null ? b.getPhotography().getPackageName() : "Not Selected"));
        body.add(row("Catering", b.getCatering() != null ? b.getCatering().getPackageName() : "Not Selected"));
        body.add(row("Final Price", "\u09F3" + String.format("%,.0f", b.getFinalPrice())));
        body.add(row("Status", b.getStatus()));
        body.revalidate();
        body.repaint();
    }

    private JPanel row(String label, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UIStyle.CARD_BG);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(700, 32));
        JLabel l = new JLabel(label + ":");
        l.setFont(UIStyle.FONT_BOLD_BODY);
        JLabel v = new JLabel(value);
        p.add(l, BorderLayout.WEST);
        p.add(v, BorderLayout.EAST);
        p.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        return p;
    }
}
