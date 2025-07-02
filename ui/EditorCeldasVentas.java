package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class EditorCeldasVentas extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JLabel labelNumero;
    private final RoundedButton btnMas, btnMenos;
    private int valor;

    public EditorCeldasVentas(JTable tabla) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(true);
        panel.setBackground(new Color(30, 30, 30));  // Fondo oscuro

        labelNumero = new JLabel("0", SwingConstants.CENTER);
        labelNumero.setPreferredSize(new Dimension(40, 25));
        labelNumero.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelNumero.setForeground(Color.WHITE);
        labelNumero.setHorizontalAlignment(SwingConstants.CENTER);

        btnMas = new RoundedButton("+", 10);
        btnMenos = new RoundedButton("-", 10);

        Dimension btnSize = new Dimension(10, 10);
        btnMas.setPreferredSize(btnSize);
        btnMenos.setPreferredSize(btnSize);
        

        // Estilo visual de los botones
        Color btnColor = new Color(255, 153, 0);
        btnMas.setBackground(btnColor);
        btnMenos.setBackground(btnColor);
        btnMas.setForeground(Color.WHITE);
        btnMenos.setForeground(Color.WHITE);
        btnMas.setFocusPainted(false);
        btnMenos.setFocusPainted(false);
        btnMas.setFont(new Font("SansSerif", Font.BOLD, 8));
        btnMenos.setFont(new Font("SansSerif", Font.BOLD, 8));
        
        btnMas.addActionListener(e -> {
            valor++;
            labelNumero.setText(String.valueOf(valor));
        });

        btnMenos.addActionListener(e -> {
            if (valor > 0) valor--;
            labelNumero.setText(String.valueOf(valor));
        });

        panel.add(Box.createHorizontalStrut(3));
        panel.add(btnMenos);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(labelNumero);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(btnMas);
        panel.add(Box.createHorizontalStrut(3));
    }

    @Override
    public Object getCellEditorValue() {
        return valor;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        valor = (value != null) ? Integer.parseInt(value.toString()) : 0;
        labelNumero.setText(String.valueOf(valor));
        return panel;
    }
}
