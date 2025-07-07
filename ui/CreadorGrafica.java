package ui;

import java.sql.*;
import java.sql.SQLException;

import org.jfree.data.category.DefaultCategoryDataset;
import database.ConexionBD;
public class CreadorGrafica {
	
	public DefaultCategoryDataset datasetGrafica() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		String statement= "SELECT " +
	            "SUM(enero) AS enero, SUM(febrero) AS febrero, SUM(marzo) AS marzo, " +
	            "SUM(abril) AS abril, SUM(mayo) AS mayo, SUM(junio) AS junio, " +
	            "SUM(julio) AS julio, SUM(agosto) AS agosto, SUM(septiembre) AS septiembre, " +
	            "SUM(octubre) AS octubre, SUM(noviembre) AS noviembre, SUM(diciembre) AS diciembre " +
	            "FROM ventas_comerciales";
		int ventas;
		
		try(Connection conexion = ConexionBD.getConexion();
			PreparedStatement pstm = conexion.prepareStatement(statement);
			ResultSet rs = pstm.executeQuery()){
			while(rs.next()) {
				for(int i=0;i<meses.length;i++) {
					ventas= rs.getInt(i+1);
					dataset.addValue(ventas, "ventas", meses[i]);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dataset;
	}
	public DefaultCategoryDataset datasetGraficaPorComercial(String nombreComercial) {
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
	            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	    
	    String statement = "SELECT enero, febrero, marzo, abril, mayo, junio, " +
	            "julio, agosto, septiembre, octubre, noviembre, diciembre " +
	            "FROM ventas_comerciales WHERE nombre = ?";
	    
	    try (Connection conexion = ConexionBD.getConexion();
	         PreparedStatement pstm = conexion.prepareStatement(statement)) {
	        
	        pstm.setString(1, nombreComercial);
	        try (ResultSet rs = pstm.executeQuery()) {
	            if (rs.next()) {
	                for (int i = 0; i < meses.length; i++) {
	                    int ventas = rs.getInt(i + 1);
	                    dataset.addValue(ventas, "ventas", meses[i]);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dataset;
	}

	
	
}
