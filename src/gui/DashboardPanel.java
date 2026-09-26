package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JLabel welcomeLabel;

    public DashboardPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.screen();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel appTitle = UIStyle.title("EVENT MANAGEMENT SYSTEM");
        welcomeLabel = UIStyle.subtitle("Welcome!");
        top.add(appTitle);
        top.add(Box.createVerticalStrut(4));
        top.add(welcomeLabel);
        top.add(Box.createVerticalStrut(6));
        JLabel prompt = UIStyle.subtitle("What would you like to do?");
        top.add(prompt);

        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(UIStyle.BG);

        JButton newEventBtn = UIStyle.primaryButton("CREATE NEW EVENT");
        newEventBtn.setPreferredSize(new Dimension(260, 48));
        newEventBtn.addActionListener(e -> {
            frame.resetWizardState();
            frame.showScreen(MainFrame.EVENT_CATEGORY);
        });

        JButton myBookingsBtn = UIStyle.secondaryButton("MY BOOKINGS");
        myBookingsBtn.setPreferredSize(new Dimension(260, 48));
        myBookingsBtn.addActionListener(e -> frame.showScreen(MainFrame.MY_BOOKINGS));

        JButton exitBtn = UIStyle.dangerButton("EXIT");
        exitBtn.setPreferredSize(new Dimension(260, 48));
        exitBtn.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 0;
        center.add(newEventBtn, gbc);
        gbc.gridy = 1;
        center.add(myBookingsBtn, gbc);
        gbc.gridy = 2;
        center.add(exitBtn, gbc);

        add(center, BorderLayout.CENTER);
    }

    @Override
    public void onShow() {
        if (frame.getCurrentCustomer() != null) {
            welcomeLabel.setText("Welcome, " + frame.getCurrentCustomer().getName());
        }
    }
}
