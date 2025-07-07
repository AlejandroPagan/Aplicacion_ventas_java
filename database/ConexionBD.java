package database;
import java.awt.*;
import java.sql.*;

public class ConexionBD {//censurado por motivos obvios, modificar al gusto del usuario
	private static final String URL = "*****************************************************";
	private static final String USER = "*****************************************************";
	private static final String PASSWORD= "*****************************************************";
	
	public static Connection getConexion() throws SQLException{
		return DriverManager.getConnection(URL,USER,PASSWORD);
	}
}
