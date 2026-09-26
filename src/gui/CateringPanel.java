package gui;

import model.CateringPackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Optional service screen: Catering. Price scales with guest count. */
public class CateringPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JPanel cardList = new JPanel();

    public CateringPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("CATERING"));
        top.add(UIStyle.subtitle("Optional service - you may skip this step."));
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
        back.addActionListener(e -> frame.showScreen(MainFrame.PHOTOGRAPHY));
        JButton skip = UIStyle.secondaryButton("SKIP CATERING");
        skip.addActionListener(e -> {
            frame.setSelectedCatering(null);
            frame.showScreen(MainFrame.SUMMARY);
        });
        bottom.add(back);
        bottom.add(skip);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        cardList.removeAll();
        int guestCount = frame.getCurrentEvent() != null ? frame.getCurrentEvent().getGuestCount() : 0;
        for (CateringPackage c : frame.getDataStore().getCateringPackages()) {
            cardList.add(buildCard(c, guestCount));
            cardList.add(Box.createVerticalStrut(12));
        }
        cardList.revalidate();
        cardList.repaint();
    }

    private JPanel buildCard(CateringPackage c, int guestCount) {
        boolean selected = c.equals(frame.getSelectedCatering());
        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(16, 0));
        card.setMaximumSize(new Dimension(900, 140));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (selected) {
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIStyle.PRIMARY, 2, true),
                    new EmptyBorder(14, 16, 14, 16)));
        }

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIStyle.CARD_BG);
        JLabel name = new JLabel(c.getPackageName() + (selected ? "  ✓ Selected" : ""));
        name.setFont(UIStyle.FONT_SECTION);
        info.add(name);
        info.add(Box.createVerticalStrut(4));
        info.add(new JLabel(c.getMenuDescription()));
        info.add(new JLabel("Provider: " + c.getProviderName()));
        info.add(new JLabel("Price per person: \u09F3" + String.format("%,.0f", c.getPricePerPerson())));
        double total = c.getPricePerPerson() * guestCount;
        JLabel totalLabel = new JLabel(guestCount + " guests \u00D7 \u09F3"
                + String.format("%,.0f", c.getPricePerPerson()) + " = \u09F3" + String.format("%,.0f", total));
        totalLabel.setFont(UIStyle.FONT_BOLD_BODY);
        info.add(totalLabel);
        info.add(new JLabel("Contact: " + c.getContactInfo()));
        card.add(info, BorderLayout.CENTER);

        JButton selectBtn = UIStyle.primaryButton("SELECT");
        selectBtn.setEnabled(c.isAvailable());
        selectBtn.addActionListener(e -> {
            frame.setSelectedCatering(c);
            frame.showScreen(MainFrame.SUMMARY);
        });
        JPanel wrap = new JPanel(new GridBagLayout());
        wrap.setBackground(UIStyle.CARD_BG);
        wrap.add(selectBtn);
        card.add(wrap, BorderLayout.EAST);
        return card;
    }
}
