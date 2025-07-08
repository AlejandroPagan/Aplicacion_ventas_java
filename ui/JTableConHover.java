package ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

public class JTableConHover extends JTable {
    private int hoveredRow = -1;
    private int hoveredColumn = -1;

    public JTableConHover() {
        super();

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                if (row != hoveredRow || col != hoveredColumn) {
                    hoveredRow = row;
                    hoveredColumn = col;
                    repaint();
                }

                // Si el ratón se mueve fuera del área visible de la tabla
                Rectangle bounds = getVisibleRect();
                if (!bounds.contains(e.getPoint())) {
                    cancelarEdicionSiEsNecesario();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                hoveredColumn = -1;
                repaint();

                // También al salir del componente: detener edición
                cancelarEdicionSiEsNecesario();
            }
        });
    }

    private void cancelarEdicionSiEsNecesario() {
        if (isEditing()) {
            TableCellEditor editor = getCellEditor();
            if (editor != null) {
                editor.stopCellEditing(); // Esto llama a getCellEditorValue()
            }
        }
    }

    public int getHoveredRow() {
        return hoveredRow;
    }

    public int getHoveredColumn() {
        return hoveredColumn;
    }
}
