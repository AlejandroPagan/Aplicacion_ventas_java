package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.*;
import database.ConexionBD;
import model.Comercial;
import database.ComercialesDAO;
/*
 * */
public class PantallaCrearComercial extends JFrame{
	private JTextField campoNombre;
	private JTextField campoId;
	private JCheckBox checkEspecial;
	private RoundedButton botonGuardar;
	private JFrame desplegable;
	private MainFrame mainFrame;
	
	public PantallaCrearComercial(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		setTitle("Crear comercial");
		setSize(200,250);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		desplegable = new JFrame();
		
		campoNombre= new JTextField();
		
		campoId= new JTextField();
		checkEspecial = new JCheckBox("Especial");
		botonGuardar = new RoundedButton("Crear",15);
		
		Box cajaVertical = Box.createVerticalBox();
		cajaVertical.add(new JLabel("Nombre:"));
		cajaVertical.add(campoNombre);
		cajaVertical.add(new JLabel("ID:"));
		cajaVertical.add(campoId);
		cajaVertical.add(checkEspecial);
		cajaVertical.add(botonGuardar);
		
		JPanel contenido = new JPanel();
		contenido.add(cajaVertical);
		
		add(contenido);
		//Hoover boton
		botonGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				botonGuardar.setBackground(Color.BLUE);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				botonGuardar.setBackground(Color.orange);
			}
		});
		//Accion boton	
		botonGuardar.addActionListener(e -> {
			ComercialesDAO comercialesDAO = new ComercialesDAO();

			String nombre = campoNombre.getText().trim();
			String idTexto = campoId.getText().trim();
			boolean especial = checkEspecial.isSelected();
			
			int id;
			try {
				id = Integer.parseInt(idTexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un número entero");
				return;
			}
			
			if(nombre.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe introducir el nombre del comercial", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
				campoNombre.requestFocus();
				return;
			}
			
			Comercial comercial = new Comercial(id, nombre, especial);

			if (comercialesDAO.comercialExiste(id)) {
				JOptionPane.showMessageDialog(null, "El ID de comercial ya está en uso", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
				campoId.setText("");
				campoId.requestFocus();
				return;
			} else {
				if (comercialesDAO.insertarComercial(comercial)) {

					setVisible(false);
					JOptionPane.showMessageDialog(null, "Comercial insertado correctamente", "Advertencia",
							JOptionPane.INFORMATION_MESSAGE);
					mainFrame.actualizarTablaComerciales();
				} else {
					JOptionPane.showMessageDialog(null, "Error al insertar el comercial", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});			
	}
}
