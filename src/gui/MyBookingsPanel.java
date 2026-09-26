package gui;

import model.Booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Displays booking history pulled straight from the DataStore's ArrayList<Booking>. */
public class MyBookingsPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public MyBookingsPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("MY BOOKINGS"));
        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Booking ID", "Event", "Date", "Venue", "Final Price", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(UIStyle.FONT_BODY);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(UIStyle.BG);
        JButton viewDetails = UIStyle.secondaryButton("VIEW SELECTED BOOKING");
        viewDetails.addActionListener(e -> viewSelected());
        JButton back = UIStyle.primaryButton("BACK TO DASHBOARD");
        back.addActionListener(e -> frame.showScreen(MainFrame.DASHBOARD));
        bottom.add(viewDetails);
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        tableModel.setRowCount(0);
        List<Booking> bookings = frame.getDataStore().getBookings();
        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                    b.getBookingId(),
                    b.getEvent().getEventDescription(),
                    b.getEvent().getEventDate(),
                    b.getVenue().getVenueName(),
                    "\u09F3" + String.format("%,.0f", b.getFinalPrice()),
                    b.getStatus()
            });
        }
        if (bookings.isEmpty()) {
            tableModel.addRow(new Object[]{"-", "No bookings yet", "-", "-", "-", "-"});
        }
    }

    private void viewSelected() {
        int row = table.getSelectedRow();
        List<Booking> bookings = frame.getDataStore().getBookings();
        if (row < 0 || row >= bookings.size()) {
            JOptionPane.showMessageDialog(this, "Please select a booking first.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Booking b = bookings.get(row);
        frame.setCurrentBooking(b);
        frame.setCurrentContract(b.getContract());
        frame.showScreen(MainFrame.CONFIRMATION);
    }
}
