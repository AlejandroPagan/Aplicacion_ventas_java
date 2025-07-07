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
	public boolean eliminarComercialYTransferirVentas(int idComercial) {
	    Connection conn = null;
	    PreparedStatement psBuscarComercial = null;
	    PreparedStatement psBuscarAusentes = null;
	    PreparedStatement psInsertAusentes = null;
	    PreparedStatement psActualizarAusentes = null;
	    PreparedStatement psEliminarComercial = null;
	    ResultSet rsComercial = null;
	    ResultSet rsAusentes = null;

	    String[] meses = {
	        "enero", "febrero", "marzo", "abril", "mayo", "junio",
	        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
	    };

	    try {
	        conn = ConexionBD.getConexion();
	        conn.setAutoCommit(false); // Iniciar transacción

	        // 1. Obtener datos del comercial a eliminar
	        psBuscarComercial = conn.prepareStatement("SELECT * FROM ventas_comerciales WHERE id_comercial = ?");
	        psBuscarComercial.setInt(1, idComercial);
	        rsComercial = psBuscarComercial.executeQuery();

	        if (!rsComercial.next()) {
	            conn.rollback();
	            return false; // Comercial no encontrado
	        }

	        // 2. Buscar comercial "AUSENTES"
	        int idAusentes = -1;
	        psBuscarAusentes = conn.prepareStatement("SELECT * FROM ventas_comerciales WHERE nombre = 'AUSENTES'");
	        rsAusentes = psBuscarAusentes.executeQuery();

	        if (rsAusentes.next()) {
	            idAusentes = rsAusentes.getInt("id_comercial");  
	        } else {
	            // Si no existe, crearlo con ventas en 0
	            String sql = "INSERT INTO ventas_comerciales (nombre " +
	                String.join(", ", meses) + ", especial) VALUES ('AUSENTES', 0, " +
	                "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
	            psInsertAusentes = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            psInsertAusentes.executeUpdate();
	            ResultSet generatedKeys = psInsertAusentes.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                idAusentes = generatedKeys.getInt(1);
	            } else {
	                conn.rollback();
	                return false;
	            }
	        }

	        // 3. Construir UPDATE para sumar las ventas del comercial a AUSENTES
	        StringBuilder sqlUpdate = new StringBuilder("UPDATE ventas_comerciales SET ");
	        for (int i = 0; i < meses.length; i++) {
	            int ventasOriginal = rsComercial.getInt(meses[i]);
	            sqlUpdate.append(meses[i]).append(" = ").append(meses[i]).append(" + ").append(ventasOriginal);
	            if (i < meses.length - 1) sqlUpdate.append(", ");
	        }
	        sqlUpdate.append(" WHERE id_comercial = ?");

	        psActualizarAusentes = conn.prepareStatement(sqlUpdate.toString());
	        psActualizarAusentes.setInt(1, idAusentes);
	        psActualizarAusentes.executeUpdate();

	        // 4. Eliminar el comercial original
	        psEliminarComercial = conn.prepareStatement("DELETE FROM ventas_comerciales WHERE id_comercial = ?");
	        psEliminarComercial.setInt(1, idComercial);
	        psEliminarComercial.executeUpdate();

	        conn.commit();
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    } finally {
	        try {
	            if (rsComercial != null) rsComercial.close();
	            if (rsAusentes != null) rsAusentes.close();
	            if (psBuscarComercial != null) psBuscarComercial.close();
	            if (psBuscarAusentes != null) psBuscarAusentes.close();
	            if (psInsertAusentes != null) psInsertAusentes.close();
	            if (psActualizarAusentes != null) psActualizarAusentes.close();
	            if (psEliminarComercial != null) psEliminarComercial.close();
	            if (conn != null) conn.setAutoCommit(true); conn.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
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
