package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Shared visual constants and small factory helpers so every panel
 * looks consistent without duplicating styling code.
 */
public final class UIStyle {
    public static final Color PRIMARY = new Color(0x2D, 0x6A, 0x4F);       // deep green
    public static final Color PRIMARY_DARK = new Color(0x21, 0x4D, 0x3A);
    public static final Color ACCENT = new Color(0xD9, 0xA6, 0x3A);        // warm gold
    public static final Color BG = new Color(0xF6, 0xF7, 0xF5);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color BORDER = new Color(0xDD, 0xE2, 0xDE);
    public static final Color TEXT_DARK = new Color(0x22, 0x28, 0x25);
    public static final Color TEXT_MUTED = new Color(0x6B, 0x74, 0x6F);
    public static final Color DANGER = new Color(0xB3, 0x2B, 0x2B);
    public static final Color SUCCESS = new Color(0x2D, 0x6A, 0x4F);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_SECTION = new Font("SansSerif", Font.BOLD, 17);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BOLD_BODY = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_PRICE = new Font("SansSerif", Font.BOLD, 16);

    private UIStyle() {}

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD_BODY);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 22, 10, 22));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setBorderPainted(false);
        return b;
    }

    public static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD_BODY);
        b.setBackground(Color.WHITE);
        b.setForeground(PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(PRIMARY, 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD_BODY);
        b.setBackground(Color.WHITE);
        b.setForeground(DANGER);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(DANGER, 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(TEXT_DARK);
        return l;
    }

    public static JLabel subtitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SUBTITLE);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(16, 18, 16, 18)));
        return p;
    }

    public static JPanel screen() {
        JPanel p = new JPanel();
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(28, 40, 28, 40));
        return p;
    }
}
