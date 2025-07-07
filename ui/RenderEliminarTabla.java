package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class RenderEliminarTabla extends JPanel implements TableCellRenderer {
    private final RoundedButton btnEliminar;

    public RenderEliminarTabla() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(true);

        btnEliminar = new RoundedButton("Eliminar", 10);
        btnEliminar.setPreferredSize(new Dimension(80, 25));
        btnEliminar.setMargin(new Insets(0, 0, 0, 0));
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        removeAll();

        Object valorEspecial = table.getValueAt(row, column);
        boolean mostrarBoton = valorEspecial != null && valorEspecial.toString().equals("0");

        if (mostrarBoton) {
            JPanel panelCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelCentrado.setOpaque(true);
            panelCentrado.setBackground(table.getBackground());
            panelCentrado.add(btnEliminar);
            return panelCentrado;
        } else {
            add(Box.createHorizontalGlue());
            setBackground(table.getBackground());
            return this;
        }
    }


}
