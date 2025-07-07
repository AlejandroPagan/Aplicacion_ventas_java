package database;
import model.Venta;
import database.ConexionBD;
import java.sql.*;

public class VentasDAO {
	public void insertarVentas(Venta venta) {
		String sqlInsert="UPDATE ventas_comerciales SET "+venta.getMes()+" = " +venta.getMes()+"+ ? WHERE id_comercial = ?";
		
		try(Connection conexion = ConexionBD.getConexion(); 
			PreparedStatement pstm= conexion.prepareStatement(sqlInsert)){		

			pstm.setInt(1, venta.getCantidad());	
			pstm.setInt(2, venta.getComercialID());
			pstm.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Boolean actualizarVentas(Venta venta) {
		String sqlActualizar = "UPDATE ventas_comerciales SET "+venta.getMes()+" = ? WHERE id_comercial = ?";
		
		try(Connection conexion = ConexionBD.getConexion();
			PreparedStatement pstm= conexion.prepareStatement(sqlActualizar)){
			
			pstm.setInt(1, venta.getCantidad());
			pstm.setInt(2, venta.getComercialID());
			pstm.executeUpdate();
			
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public void eliminarVentas(Venta venta) {
		String sqlDelete="UPDATE ventas_comerciales SET "+venta.getMes()+"="+venta.getMes()+" - ? WHERE id_comercial = ?";
		
		try(Connection conexion = ConexionBD.getConexion();
				PreparedStatement pstm = conexion.prepareStatement(sqlDelete)){
			
			pstm.setInt(1, venta.getCantidad());
			pstm.setInt(2, venta.getComercialID());
			
			pstm.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
