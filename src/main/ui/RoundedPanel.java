package ui;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that creates a panel with rounded corners.
 */
public class RoundedPanel extends JPanel {
    private int cornerRadius;


    /**
     * Modifies: this
     * Effects: Initializes the cornerRadius to the specified value.
     *          Sets the panel to be non-opaque.
     */
    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color
        g2.setColor(getBackground());

        // Draw the rounded rectangle
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Optional: Add a border for better visibility
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
    }
}
