package gui;

import model.Customer;

import javax.swing.*;
import java.awt.*;

/**
 * Simple in-memory login for the academic prototype (no database).
 * Demonstrates: JFrame/JPanel composition, ActionListener, JOptionPane.
 */
public class LoginPanel extends JPanel {

    private final MainFrame frame;
    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(UIStyle.PRIMARY_DARK);

        JPanel card = UIStyle.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(380, 340));

        JLabel heading = UIStyle.title("EVENT MANAGEMENT SYSTEM");
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = UIStyle.subtitle("Welcome back");
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(24));

        card.add(fieldLabel("Username"));
        usernameField.setMaximumSize(new Dimension(300, 32));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(14));

        card.add(fieldLabel("Password"));
        passwordField.setMaximumSize(new Dimension(300, 32));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(22));

        JButton loginBtn = UIStyle.primaryButton("LOGIN");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());
        card.add(loginBtn);

        card.add(Box.createVerticalStrut(10));
        JLabel hint = UIStyle.subtitle("Demo credentials: admin / admin123");
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(hint);

        add(card);

        // Allow pressing Enter in the password field to log in
        passwordField.addActionListener(e -> doLogin());
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UIStyle.FONT_BOLD_BODY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Login Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.equals("admin") && password.equals("admin123")) {
            frame.setCurrentCustomer(new Customer("CUST-1", "Arafat Hossain", "01700-000000", "arafat@example.com"));
            usernameField.setText("");
            passwordField.setText("");
            frame.showScreen(MainFrame.DASHBOARD);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
