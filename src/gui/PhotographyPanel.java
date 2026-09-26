package gui;

import model.PhotographyPackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Optional service screen: Photography. */
public class PhotographyPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JPanel cardList = new JPanel();

    public PhotographyPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("PHOTOGRAPHY"));
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
        back.addActionListener(e -> frame.showScreen(MainFrame.DECORATION));
        JButton skip = UIStyle.secondaryButton("SKIP PHOTOGRAPHY");
        skip.addActionListener(e -> {
            frame.setSelectedPhotography(null);
            frame.showScreen(MainFrame.CATERING);
        });
        bottom.add(back);
        bottom.add(skip);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        cardList.removeAll();
        for (PhotographyPackage p : frame.getDataStore().getPhotographyPackages()) {
            cardList.add(buildCard(p));
            cardList.add(Box.createVerticalStrut(12));
        }
        cardList.revalidate();
        cardList.repaint();
    }

    private JPanel buildCard(PhotographyPackage p) {
        boolean selected = p.equals(frame.getSelectedPhotography());
        JPanel card = UIStyle.card();
        card.setLayout(new BorderLayout(16, 0));
        card.setMaximumSize(new Dimension(900, 130));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (selected) {
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIStyle.PRIMARY, 2, true),
                    new EmptyBorder(14, 16, 14, 16)));
        }

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIStyle.CARD_BG);
        JLabel name = new JLabel(p.getPackageName() + (selected ? "  ✓ Selected" : ""));
        name.setFont(UIStyle.FONT_SECTION);
        info.add(name);
        info.add(Box.createVerticalStrut(4));
        info.add(new JLabel("Photographer: " + p.getPhotographerName()));
        info.add(new JLabel("Duration: " + p.getHours() + " Hours"));
        info.add(new JLabel("Price: \u09F3" + String.format("%,.0f", p.getPrice())));
        info.add(new JLabel("Contact: " + p.getContactInfo()));
        card.add(info, BorderLayout.CENTER);

        JButton selectBtn = UIStyle.primaryButton("SELECT");
        selectBtn.setEnabled(p.isAvailable());
        selectBtn.addActionListener(e -> {
            frame.setSelectedPhotography(p);
            frame.showScreen(MainFrame.CATERING);
        });
        JPanel wrap = new JPanel(new GridBagLayout());
        wrap.setBackground(UIStyle.CARD_BG);
        wrap.add(selectBtn);
        card.add(wrap, BorderLayout.EAST);
        return card;
    }
}
