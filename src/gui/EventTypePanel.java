package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Shows Wedding/Birthday/Anniversary for Social, or Conference/Alumni for
 * Corporate, depending on the category chosen on the previous screen.
 */
public class EventTypePanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JLabel titleLabel;
    private final JPanel buttonPanel = new JPanel(new GridBagLayout());

    public EventTypePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        titleLabel = UIStyle.title("SELECT EVENT TYPE");
        top.add(titleLabel);
        add(top, BorderLayout.NORTH);

        buttonPanel.setBackground(UIStyle.BG);
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void onShow() {
        buttonPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        int row = 0;

        boolean social = "SOCIAL".equals(frame.getSelectedCategory());
        titleLabel.setText(social ? "SOCIAL EVENTS" : "CORPORATE EVENTS");

        String[] types = social
                ? new String[]{"WEDDING", "BIRTHDAY", "ANNIVERSARY"}
                : new String[]{"CONFERENCE", "ALUMNI ASSOCIATION EVENT"};

        for (String type : types) {
            JButton btn = UIStyle.primaryButton(type);
            btn.setPreferredSize(new Dimension(280, 48));
            btn.addActionListener(e -> {
                frame.setCurrentEvent(null); // event object built on Details screen
                EventDetailsPanel.pendingTypeLabel = type;
                frame.showScreen(MainFrame.EVENT_DETAILS);
            });
            gbc.gridy = row++;
            buttonPanel.add(btn, gbc);
        }

        JButton back = UIStyle.secondaryButton("BACK");
        back.setPreferredSize(new Dimension(280, 42));
        back.addActionListener(e -> frame.showScreen(MainFrame.EVENT_CATEGORY));
        gbc.gridy = row;
        buttonPanel.add(back, gbc);

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
}
