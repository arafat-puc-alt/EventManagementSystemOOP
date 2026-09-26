package gui;

import exceptions.CapacityExceededException;
import model.Booking;

import javax.swing.*;
import java.awt.*;

/**
 * Shows the full price breakdown. All numbers come from Booking/PricingService
 * objects - nothing here is hard-coded.
 */
public class BookingSummaryPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JPanel body = new JPanel();
    private final JButton contractBtn;
    private final JButton confirmBtn;

    public BookingSummaryPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("BOOKING SUMMARY"));
        add(top, BorderLayout.NORTH);

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIStyle.CARD_BG);
        body.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(UIStyle.BG);
        JButton back = UIStyle.secondaryButton("BACK");
        back.addActionListener(e -> frame.showScreen(MainFrame.CATERING));
        contractBtn = UIStyle.secondaryButton("VIEW CONTRACT");
        contractBtn.addActionListener(e -> {
            frame.getContractService().generateContract(frame.getCurrentBooking());
            frame.setCurrentContract(frame.getCurrentBooking().getContract());
            frame.showScreen(MainFrame.CONTRACT);
        });
        confirmBtn = UIStyle.primaryButton("CONFIRM BOOKING");
        confirmBtn.addActionListener(e -> {
            if (frame.getCurrentBooking().getContract() == null) {
                frame.getContractService().generateContract(frame.getCurrentBooking());
            }
            frame.getBookingService().confirmBooking(frame.getCurrentBooking());
            frame.getContractService().acceptContract(frame.getCurrentBooking().getContract());
            frame.showScreen(MainFrame.CONFIRMATION);
        });
        bottom.add(back);
        bottom.add(contractBtn);
        bottom.add(confirmBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        try {
            Booking booking = frame.getBookingService().createBooking(
                    frame.getCurrentCustomer(),
                    frame.getCurrentEvent(),
                    frame.getSelectedVenue(),
                    frame.getSelectedDecoration(),
                    frame.getSelectedPhotography(),
                    frame.getSelectedCatering());
            frame.setCurrentBooking(booking);
            render(booking);
        } catch (CapacityExceededException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Booking Error", JOptionPane.ERROR_MESSAGE);
            frame.showScreen(MainFrame.VENUE);
        }
    }

    private void render(Booking booking) {
        body.removeAll();
        body.add(section("CUSTOMER", booking.getCustomer().getName()));
        body.add(section("EVENT", booking.getEvent().getEventDescription()));
        body.add(section("DATE", booking.getEvent().getEventDate()));
        body.add(section("GUESTS", String.valueOf(booking.getEvent().getGuestCount())));
        body.add(divider());

        body.add(section("VENUE", booking.getVenue().getVenueName()
                + "  -  \u09F3" + fmt(booking.getVenue().getPrice())));

        body.add(section("DECORATION", booking.getDecoration() != null
                ? booking.getDecoration().getName() + "  -  \u09F3" + fmt(booking.getDecoration().getPrice())
                : "Not Selected"));

        body.add(section("PHOTOGRAPHY", booking.getPhotography() != null
                ? booking.getPhotography().getPackageName() + "  -  \u09F3" + fmt(booking.getPhotography().getPrice())
                : "Not Selected"));

        int guests = booking.getEvent().getGuestCount();
        body.add(section("CATERING", booking.getCatering() != null
                ? booking.getCatering().getPackageName() + "  (" + guests + " \u00D7 \u09F3"
                    + fmt(booking.getCatering().getPricePerPerson()) + ")  -  \u09F3"
                    + fmt(booking.getCatering().getPricePerPerson() * guests)
                : "Not Selected"));

        body.add(divider());
        body.add(priceRow("SUBTOTAL", "\u09F3" + fmt(booking.getSubtotal()), false));
        body.add(priceRow("CUSTOMER DISCOUNT (10%)", "-\u09F3" + fmt(booking.getDiscount()), false));
        body.add(divider());
        body.add(priceRow("FINAL PRICE", "\u09F3" + fmt(booking.getFinalPrice()), true));

        body.revalidate();
        body.repaint();
    }

    private JPanel section(String label, String value) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(UIStyle.CARD_BG);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(UIStyle.FONT_BOLD_BODY);
        l.setForeground(UIStyle.TEXT_MUTED);
        JLabel v = new JLabel(value);
        v.setFont(UIStyle.FONT_BODY);
        p.add(l);
        p.add(v);
        p.add(Box.createVerticalStrut(10));
        return p;
    }

    private JPanel priceRow(String label, String value, boolean emphasize) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UIStyle.CARD_BG);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(600, 34));
        JLabel l = new JLabel(label);
        JLabel v = new JLabel(value);
        Font f = emphasize ? UIStyle.FONT_PRICE : UIStyle.FONT_BOLD_BODY;
        l.setFont(f);
        v.setFont(f);
        if (emphasize) {
            l.setForeground(UIStyle.PRIMARY);
            v.setForeground(UIStyle.PRIMARY);
        }
        p.add(l, BorderLayout.WEST);
        p.add(v, BorderLayout.EAST);
        return p;
    }

    private JSeparator divider() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(900, 2));
        sep.setForeground(UIStyle.BORDER);
        return sep;
    }

    private String fmt(double v) {
        return String.format("%,.0f", v);
    }
}
