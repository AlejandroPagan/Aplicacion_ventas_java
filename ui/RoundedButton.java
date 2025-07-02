package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.Shape;
import javax.swing.JButton;

class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(160, 36));
        setBackground(new Color(33, 150, 243));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cambia color si está en hover
        if (getModel().isRollover()) {
            g2.setColor(new Color(30, 136, 229)); // hover
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Sin borde visible
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        return shape.contains(x, y);
    }
}

