package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Comercial;

public class ComercialesDAO {
	
	public Boolean insertarComercial(Comercial comercial) {
		String sqlInsertar="INSERT INTO ventas_comerciales (id_comercial, nombre,media_2024, enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre, es_especial)"+
							" VALUES(?,?, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,?)";		
		
		
		if(comercialExiste(comercial.getId())) {
			return false;
		}
		else {
			try(Connection conexion = ConexionBD.getConexion();
					PreparedStatement stmt= conexion.prepareStatement(sqlInsertar);
					){
				stmt.setInt(1,comercial.getId());
				stmt.setString(2, comercial.getNombre());
				stmt.setBoolean(3, comercial.isEspecial());
			
				stmt.executeUpdate();
				return true;
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	public Boolean eliminarComercial(Comercial comercial) {
		String sqlEliminar="DELETE FROM ventas_comerciales WHERE id_comercial = ?";
		
		try(Connection conexion = ConexionBD.getConexion();
				PreparedStatement stmt= conexion.prepareStatement(sqlEliminar)){
			
			stmt.setInt(1, comercial.getId());	
			stmt.executeUpdate();
			
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public void consultarVentasComercial(Comercial comercial) {
		String nombre = comercial.getNombre();
		String sqlConsultar="SELECT enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre FROM ventas_comerciales WHERE nombre = ?";

		try(Connection conexion= ConexionBD.getConexion();
			PreparedStatement stmt= conexion.prepareStatement(sqlConsultar)){
			stmt.setString(1,nombre);
			ResultSet rs= stmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	public Boolean comercialExiste(int idComercial) {
		String conseguirIDs = "SELECT id_comercial FROM ventas_comerciales WHERE id_comercial = ?";
		
		try(Connection conexion = ConexionBD.getConexion();
			PreparedStatement pst = conexion.prepareStatement(conseguirIDs);) {
			
			pst.setInt(1,idComercial);
			ResultSet rs= pst.executeQuery();
			
			if(rs.next()) {
				return true;
			}else {
				return false;
						}	
			}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
