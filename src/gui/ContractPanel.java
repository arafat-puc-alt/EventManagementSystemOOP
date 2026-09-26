package gui;

import model.Contract;

import javax.swing.*;
import java.awt.*;

public class ContractPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JPanel body = new JPanel();

    public ContractPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("CONTRACT"));
        add(top, BorderLayout.NORTH);

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIStyle.CARD_BG);
        body.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(UIStyle.BG);
        JButton cancel = UIStyle.dangerButton("CANCEL");
        cancel.addActionListener(e -> {
            if (frame.getCurrentContract() != null) {
                frame.getCurrentContract().setContractStatus("CANCELLED");
            }
            frame.showScreen(MainFrame.SUMMARY);
        });
        JButton accept = UIStyle.primaryButton("ACCEPT CONTRACT");
        accept.addActionListener(e -> {
            frame.getContractService().acceptContract(frame.getCurrentContract());
            frame.getBookingService().confirmBooking(frame.getCurrentBooking());
            frame.showScreen(MainFrame.CONFIRMATION);
        });
        bottom.add(cancel);
        bottom.add(accept);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        body.removeAll();
        Contract c = frame.getCurrentContract();
        if (c == null) {
            body.add(new JLabel("No contract has been generated yet."));
            body.revalidate();
            body.repaint();
            return;
        }
        body.add(row("Contract ID", c.getContractId()));
        body.add(row("Booking ID", c.getBookingId()));
        body.add(row("Customer", c.getCustomer().getName()));
        body.add(row("Event", c.getEvent().getEventDescription()));
        body.add(row("Venue", c.getVenue().getVenueName()));
        body.add(row("Decoration", c.getDecoration() != null ? c.getDecoration().getName() : "Not Selected"));
        body.add(row("Photography", c.getPhotography() != null ? c.getPhotography().getPackageName() : "Not Selected"));
        body.add(row("Catering", c.getCatering() != null ? c.getCatering().getPackageName() : "Not Selected"));
        body.add(row("Customer Discount", (int) c.getDiscount() + "%"));
        body.add(row("Provider Incentive", (int) c.getIncentive() + "%"));
        body.add(row("Final Customer Price", "\u09F3" + String.format("%,.0f", frame.getCurrentBooking().getFinalPrice())));
        body.add(row("Contract Status", c.getContractStatus()));
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
        v.setFont(UIStyle.FONT_BODY);
        p.add(l, BorderLayout.WEST);
        p.add(v, BorderLayout.EAST);
        p.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        return p;
    }
}
