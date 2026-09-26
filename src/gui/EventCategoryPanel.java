package gui;

import javax.swing.*;
import java.awt.*;

public class EventCategoryPanel extends JPanel {

    public EventCategoryPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(UIStyle.title("SELECT EVENT CATEGORY"));
        top.add(Box.createVerticalStrut(4));
        top.add(UIStyle.subtitle("Choose the type of event you are planning."));
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(UIStyle.BG);

        JButton social = UIStyle.primaryButton("SOCIAL EVENTS");
        social.setPreferredSize(new Dimension(260, 48));
        social.addActionListener(e -> {
            frame.setSelectedCategory("SOCIAL");
            frame.showScreen(MainFrame.EVENT_TYPE);
        });

        JButton corporate = UIStyle.primaryButton("CORPORATE EVENTS");
        corporate.setPreferredSize(new Dimension(260, 48));
        corporate.addActionListener(e -> {
            frame.setSelectedCategory("CORPORATE");
            frame.showScreen(MainFrame.EVENT_TYPE);
        });

        JButton back = UIStyle.secondaryButton("BACK");
        back.setPreferredSize(new Dimension(260, 42));
        back.addActionListener(e -> frame.showScreen(MainFrame.DASHBOARD));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 0;
        center.add(social, gbc);
        gbc.gridy = 1;
        center.add(corporate, gbc);
        gbc.gridy = 2;
        center.add(back, gbc);

        add(center, BorderLayout.CENTER);
    }
}
