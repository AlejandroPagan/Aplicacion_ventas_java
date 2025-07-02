package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class RenderCeldasTabla extends JPanel implements TableCellRenderer {
    private final JLabel labelNumero;
    private final RoundedButton btnMas, btnMenos;

    public RenderCeldasTabla() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(true);

        labelNumero = new JLabel("0", SwingConstants.CENTER);
        labelNumero.setPreferredSize(new Dimension(40, 25));
        labelNumero.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelNumero.setHorizontalAlignment(SwingConstants.CENTER);

        btnMas = new RoundedButton("+", 10);
        btnMenos = new RoundedButton("-", 10);

        Dimension btnSize = new Dimension(10, 10);  // Unificado con editor
        btnMas.setPreferredSize(btnSize);
        btnMenos.setPreferredSize(btnSize);

        Color btnColor = new Color(255, 153, 0);
        btnMas.setBackground(btnColor);
        btnMenos.setBackground(btnColor);
        btnMas.setForeground(Color.WHITE);
        btnMenos.setForeground(Color.WHITE);
        btnMas.setFocusPainted(false);
        btnMenos.setFocusPainted(false);
        btnMas.setFont(new Font("SansSerif", Font.BOLD, 8));
        btnMenos.setFont(new Font("SansSerif", Font.BOLD, 8));

        add(Box.createHorizontalStrut(3));
        add(btnMenos);
        add(Box.createHorizontalStrut(5));
        add(labelNumero);
        add(Box.createHorizontalStrut(5));
        add(btnMas);
        add(Box.createHorizontalStrut(3));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        labelNumero.setText(value != null ? value.toString() : "0");

        boolean hovered = false;
        if (table instanceof JTableConHover) {
            JTableConHover t = (JTableConHover) table;
            hovered = (row == t.getHoveredRow() && column == t.getHoveredColumn());
        }

        if (hovered) {
            setBackground(new Color(30, 30, 30));
            labelNumero.setForeground(Color.WHITE);
            btnMas.setVisible(true);
            btnMenos.setVisible(true);
        } else {
            setBackground(table.getBackground());
            labelNumero.setForeground(table.getForeground());
            btnMas.setVisible(false);
            btnMenos.setVisible(false);
        }

        return this;
    }
}

