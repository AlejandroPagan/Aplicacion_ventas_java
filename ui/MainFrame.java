package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
/*Cosas que faltan: 
 * 4.Añadir una forma de eliminar los comerciales
 * 5.Resaltar una columna de comerciales concreta
 * 6.Que al seleccionar un comercial se modifique la gráfica y se muestren sus datos en la gráfica.
 * 7.Forma de mostrar los comerciales especiales o mejor dicho las 
 * */
public class MainFrame extends JFrame{
	private JPanel toolbar;
	private RoundedButton botonCrear;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JPanel cajaGrafica;
	private JTable tabla;
	
	public MainFrame() {
		setTitle("Ventas Asesoramiento Laboral");
		setSize(1000,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		iniToolbar();
		iniCuerpo();
		
		setVisible(true);
	}
	public void iniToolbar() {
		toolbar= new JPanel(new BorderLayout());
		toolbar.setBackground(new Color(0, 102, 204));
		toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); 
		
		JLabel titulo = new JLabel("Gestor Ventas Asesoramiento Laboral");		
		titulo.setForeground(Color.WHITE);
		titulo.setHorizontalAlignment(SwingConstants.LEFT);
		titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
		titulo.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		
		toolbar.add(titulo,BorderLayout.CENTER);

		add(toolbar, BorderLayout.NORTH);
		
	}
	public void iniCuerpo() {           		
        //creo la gráfica con la clase externa
        CreadorGrafica grafica= new CreadorGrafica();
        DefaultCategoryDataset dataset= grafica.datasetGrafica();
        chart = ChartFactory.createBarChart("Ventas por mes", "VENTAS", "MES", dataset, PlotOrientation.VERTICAL, true,true, false);

		chart.setBackgroundPaint(Color.DARK_GRAY);
	    chart.getTitle().setPaint(Color.WHITE);
	    
		 CategoryPlot plot = chart.getCategoryPlot();
		 plot.setBackgroundPaint(new Color(30,30,30));
		 plot.setDomainGridlinePaint(Color.white);		    
		 plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
		 plot.getRangeAxis().setTickLabelPaint(Color.white);
		 plot.getDomainAxis().setLabelPaint(Color.white);
		 plot.getRangeAxis().setLabelPaint(Color.white);	    
	        
		 //Aspecto de las barras
		 BarRenderer renderer = (BarRenderer) plot.getRenderer();

		 renderer.setSeriesPaint(0, new Color(255, 153, 0));
		 renderer.setDrawBarOutline(false);
		 renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		 renderer.setBaseItemLabelsVisible(true);
		 renderer.setBaseItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		 renderer.setBaseItemLabelPaint(Color.WHITE);
        
        //caja de la grafica
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 350));

		chartPanel.setBackground(Color.WHITE);
		chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		cajaGrafica = new JPanel();
		cajaGrafica.setBackground(Color.white);
		cajaGrafica.setLayout(new FlowLayout(FlowLayout.CENTER));
		cajaGrafica.setPreferredSize(new Dimension(620, 330));
		cajaGrafica.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		cajaGrafica.add(chartPanel);

		// ==========Botón añadir comercial==========//
		RoundedButton boton = botonCrear();
		boton.setPreferredSize(new Dimension(600, 40));
		boton.setBackground(new Color(255, 153, 0));
		boton.setForeground(Color.WHITE);
		boton.setFont(new Font("SansSerif", Font.BOLD, 12));
		boton.setFocusPainted(false);

		JPanel cajaBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cajaBoton.add(boton);
		cajaBoton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		// =========Crear Tabla===========//
		tabla = new JTableConHover();

		CreadorTabla creador = new CreadorTabla();
		DefaultTableModel modelo = creador.modeloTabla();
		tabla.setModel(modelo);
		tabla.setShowGrid(true); // Muestra líneas entre celdas
		tabla.setGridColor(Color.GRAY);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.getTableHeader().setBackground(new Color(0, 102, 204));
		tabla.getTableHeader().setForeground(Color.WHITE);
		tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		tabla.setRowHeight(30); 

		EditorCeldasVentas editor = new EditorCeldasVentas(tabla, MainFrame.this);
		RenderCeldasTabla renderCelda = new RenderCeldasTabla();
		for (int i = 2; i < (tabla.getColumnCount()-1); i++) {
			tabla.getColumnModel().getColumn(i).setCellEditor(editor);
			tabla.getColumnModel().getColumn(i).setCellRenderer(renderCelda);		
			}

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(1000, 400));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

		Box cajaVertical = Box.createVerticalBox();
		cajaVertical.add(cajaGrafica);
		cajaVertical.add(Box.createVerticalStrut(10));
		cajaVertical.add(cajaBoton);
		cajaVertical.add(Box.createVerticalStrut(10));
		cajaVertical.add(scroll);

		JPanel cuerpo = new JPanel(new BorderLayout());
		cuerpo.add(cajaVertical, BorderLayout.CENTER);
		add(cuerpo, BorderLayout.CENTER);
	}
	public RoundedButton botonCrear() {
		botonCrear= new RoundedButton("Añadir Comercial",20);
		
		botonCrear.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				botonCrear.setBackground(Color.BLUE);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				botonCrear.setBackground(Color.orange);
			}
		});
		JFrame vCrear = new PantallaCrearComercial(MainFrame.this);
		vCrear.setVisible(false);
		botonCrear.addActionListener(e -> {	
					vCrear.setVisible(true);
		});	
		
		return botonCrear;
	}
	public void actualizarTablaComerciales() {
		CreadorTabla creador = new CreadorTabla();
		DefaultTableModel modelo = creador.modeloTabla();
		tabla.setModel(modelo);
		tabla.setShowGrid(true); 
		tabla.setGridColor(Color.GRAY);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.getTableHeader().setBackground(new Color(0, 102, 204));
		tabla.getTableHeader().setForeground(Color.WHITE);
		tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		tabla.setRowHeight(30); 

		EditorCeldasVentas editor = new EditorCeldasVentas(tabla,MainFrame.this);
		RenderCeldasTabla renderCelda = new RenderCeldasTabla();
		for (int i = 2; i < tabla.getColumnCount(); i++) {
			tabla.getColumnModel().getColumn(i).setCellEditor(editor);
			tabla.getColumnModel().getColumn(i).setCellRenderer(renderCelda);		
			}			
	}
	public void actualizarGrafica() {
	    CreadorGrafica grafica = new CreadorGrafica();
	    DefaultCategoryDataset dataset = grafica.datasetGrafica();
	    chart = ChartFactory.createBarChart("Ventas por mes", "VENTAS", "MES", dataset, PlotOrientation.VERTICAL, true, true, false);

	    chart.setBackgroundPaint(Color.DARK_GRAY);
	    chart.getTitle().setPaint(Color.WHITE);

	    CategoryPlot plot = chart.getCategoryPlot();
	    plot.setBackgroundPaint(new Color(30,30,30));
	    plot.setDomainGridlinePaint(Color.white);		    
	    plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
	    plot.getRangeAxis().setTickLabelPaint(Color.white);
	    plot.getDomainAxis().setLabelPaint(Color.white);
	    plot.getRangeAxis().setLabelPaint(Color.white);	    

	    BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    renderer.setSeriesPaint(0, new Color(255, 153, 0));
	    renderer.setDrawBarOutline(false);
	    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    renderer.setBaseItemLabelsVisible(true);
	    renderer.setBaseItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));
	    renderer.setBaseItemLabelPaint(Color.WHITE);

	    // Elimina el antiguo chartPanel del contenedor
	    cajaGrafica.remove(chartPanel);

	    // Crea y añade el nuevo chartPanel
	    chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new Dimension(800, 350));
	    chartPanel.setBackground(Color.WHITE);
	    chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

	    cajaGrafica.add(chartPanel);
	    cajaGrafica.revalidate(); // Actualiza el layout
	    cajaGrafica.repaint();    // Repinta el panel
	}

}
