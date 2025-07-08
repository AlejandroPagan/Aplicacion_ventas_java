package database;
import java.awt.*;
import java.sql.*;

public class ConexionBD {
	private static final String URL = "jdbc:mysql://bk3bl7th1fjcuoyxedxw-mysql.services.clever-cloud.com:3306/bk3bl7th1fjcuoyxedxw";
	private static final String USER = "uci31plazmzce4dv";
	private static final String PASSWORD= "mHKKbJZ6v71IdaO0qaJD";
	
	public static Connection getConexion() throws SQLException{
		return DriverManager.getConnection(URL,USER,PASSWORD);
	}
}
