package ui;
import java.sql.*;
import database.ConexionBD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/*Esta clase se encarga de devolver el modelo de la tabla a utilizar en el MainFrame
 * la consulta se realiza solicitando únicamente los datos a mostrar en la tabla
 */
public class CreadorTabla {
	
	public DefaultTableModel modeloTabla() {
		String columnas[]= {"ID", "Nombre", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
	            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "Especial"
	        };
		DefaultTableModel modelo = new DefaultTableModel(columnas,0);
		
		String sql = "SELECT id_comercial, nombre, enero, febrero, marzo, abril, mayo, junio, " +
	             "julio, agosto, septiembre, octubre, noviembre, diciembre, es_especial FROM ventas_comerciales";
		
		try(Connection conexion = ConexionBD.getConexion();
			PreparedStatement pstm= conexion.prepareStatement(sql);
			ResultSet rs= pstm.executeQuery()){
			
			while(rs.next()) {
				Object[] fila= new Object[columnas.length];	
				for(int i =0; i<columnas.length;i++) {
					fila[i]=rs.getObject(i+1);
				}
				modelo.addRow(fila);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return modelo;	
	}
}
