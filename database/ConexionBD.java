package database;
import java.awt.*;
import java.sql.*;

public class ConexionBD {//he he, of course this is censored, i cant give it to u like that ^^
	private static final String URL = "*******************************************";
	private static final String USER = "********************************";
	private static final String PASSWORD= "*******************************";
	
	public static Connection getConexion() throws SQLException{
		return DriverManager.getConnection(URL,USER,PASSWORD);
	}
}
