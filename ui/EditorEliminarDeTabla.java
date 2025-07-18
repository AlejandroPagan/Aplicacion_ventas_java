package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import database.ComercialesDAO;

public class EditorEliminarDeTabla extends AbstractCellEditor implements TableCellEditor{
	private final JPanel panel;
	private final RoundedButton btnEliminar;
	private JTable tabla;
    private MainFrame mainFrame;
    private int fila, id_comercial;
    private ComercialesDAO comercialDAO;
    
    public EditorEliminarDeTabla (JTable tabla, MainFrame mainFrame) {
    	this.tabla = tabla; 
    	this.mainFrame = mainFrame;
    	this.comercialDAO = new ComercialesDAO();
    	
    	panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(true);
        panel.setBackground(new Color(30, 30, 30));
        
        btnEliminar = new RoundedButton("Eliminar",10);
    }
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    this.fila = row;

	    Object valorCelda = table.getValueAt(row, 0);
	    try {
	        id_comercial = Integer.parseInt(valorCelda.toString());
	    } catch (NumberFormatException e) {
	        id_comercial = -1;
	    }

	    // Limpia cualquier ActionListener anterior para evitar duplicaciones
	    for (java.awt.event.ActionListener al : btnEliminar.getActionListeners()) {
	        btnEliminar.removeActionListener(al);
	    }

	    btnEliminar.addActionListener(e -> {
	        int opcion = JOptionPane.showConfirmDialog(
	            table,
	            "¿Estás seguro de que quieres eliminar este comercial?\nSus ventas serán transferidas a 'AUSENTES'.",
	            "Confirmar eliminación",
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.WARNING_MESSAGE
	        );

	        if (opcion == JOptionPane.YES_OPTION) {
	            eliminarComercialYTransferirVentas();
	        }

	        fireEditingStopped(); // Cierra el editor después de pulsar
	    });

	    // Devuelve el botón dentro de un panel centrado
	    JPanel panelCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelCentrado.setOpaque(true);
	    panelCentrado.setBackground(new Color(30, 30, 30));
	    panelCentrado.add(btnEliminar);

	    return panelCentrado;
	}



	 @Override
	public boolean isCellEditable(EventObject e) {
        return true;
    }
	 private void eliminarComercialYTransferirVentas() {
	        if (id_comercial != -1) {
	            boolean exito = comercialDAO.eliminarComercialYTransferirVentas(id_comercial);
	            if (exito) {
	                JOptionPane.showMessageDialog(null, "Comercial eliminado correctamente.");
	                mainFrame.actualizarTablaComerciales();
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo eliminar el comercial.");
	            }
	        }
	    }
}
