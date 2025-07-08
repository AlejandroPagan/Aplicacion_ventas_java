package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

import org.jfree.chart.JFreeChart;

import database.VentasDAO;
import model.Venta;

public class EditorCeldasVentas extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JLabel labelNumero;
    private final RoundedButton btnMas, btnMenos, btnElimComercial;
    private int valor, intFila, intColumn;
    private JTable tabla;
    private MainFrame mainFrame;
    
    private final String[] meses = {
            "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
        };

    public EditorCeldasVentas(JTable tabla, MainFrame mainFrame) {
    	this.tabla = tabla;
    	this.mainFrame=mainFrame;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(true);
        panel.setBackground(new Color(30, 30, 30));

        labelNumero = new JLabel("0", SwingConstants.CENTER);
        labelNumero.setPreferredSize(new Dimension(30, 15));
        labelNumero.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelNumero.setForeground(Color.WHITE);
        labelNumero.setHorizontalAlignment(SwingConstants.CENTER);

        btnMas = new RoundedButton("+", 10);
        btnMenos = new RoundedButton("-", 10);

        btnMas.setPreferredSize(new Dimension(22, 22));
        btnMas.setMargin(new Insets(0, 0, 0, 0));

        btnMenos.setPreferredSize(new Dimension(22, 22));
        btnMenos.setMargin(new Insets(0, 0, 0, 0));        

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
        
        btnElimComercial = new RoundedButton("",10);
        
        configurarBotonConRepeticion(btnMas, () -> {
            valor++;
            labelNumero.setText(String.valueOf(valor));
        });

        configurarBotonConRepeticion(btnMenos, () -> {
            if (valor > 0) {
                valor--;
                labelNumero.setText(String.valueOf(valor));
            }
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
        if (intFila < 0 || intFila >= tabla.getRowCount() || intColumn < 0 || intColumn >= tabla.getColumnCount()) {
            return valor;
        }
        int idComercial = Integer.parseInt(tabla.getValueAt(intFila, 0).toString());
        if (intColumn >= 2 && intColumn < 14) {
            String mes = meses[intColumn - 2];
            Venta venta = new Venta(idComercial, mes, valor);
            
            // Usar SwingWorker para no bloquear EDT
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    VentasDAO comercial = new VentasDAO();
                    return comercial.actualizarVentas(venta);
                }

                @Override
                protected void done() {
                    try {
                        boolean exito = get();
                        if (exito) {
                            mainFrame.actualizarTablaComerciales();
                            mainFrame.actualizarGrafica();
                            mostrarTooltip("Ventas actualizadas", intFila, intColumn);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            worker.execute();
        }
        return valor;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
       this.intFila = row;
       this.intColumn = column;   	
    	
    	valor = (value != null) ? Integer.parseInt(value.toString()) : 0;
        labelNumero.setText(String.valueOf(valor));
        return panel;
    }
    private void mostrarTooltip(String mensaje, int fila, int columna) {
        Rectangle rect = tabla.getCellRect(fila, columna, true);
        Point punto = rect.getLocation();
        SwingUtilities.convertPointToScreen(punto, tabla);
        
        JWindow tooltipWindow = new JWindow();
        tooltipWindow.setLayout(new BorderLayout());
        
        JLabel label = new JLabel(mensaje);
        label.setOpaque(true);
        label.setBackground(new Color(60, 60, 60));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(140, 25));

        tooltipWindow.add(label);
        tooltipWindow.pack();
        
        // Posición del tooltip justo arriba de la celda
        tooltipWindow.setLocation(punto.x + 20, punto.y - 30);
        tooltipWindow.setVisible(true);
        
        // Ocultarlo tras 2 segundos
        new Timer(2000, e -> tooltipWindow.setVisible(false)).start();
    }
 // Timer y lógica común
    private void configurarBotonConRepeticion(RoundedButton boton, Runnable accion) {
        Timer timer = new Timer(100, e -> accion.run());
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                accion.run(); // Ejecutar inmediatamente
                timer.start();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                timer.stop();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                timer.stop(); // Detener si se sale del botón
            }
        });
    }

}
