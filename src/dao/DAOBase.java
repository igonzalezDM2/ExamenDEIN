package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Clase base para los DAO.
 * 
 * <p>
 * Esta clase proporciona una conexión a la base de datos y métodos comunes para los DAO.
 * </p>
 */
public abstract class DAOBase {
	private static final ResourceBundle BUNDLE; 
	private static final String USUARIO;
	private static final String CONTRASENA;
	private static final String BASE_DE_DATOS;
	
	static {
		BUNDLE = ResourceBundle.getBundle("properties.configuration");
		USUARIO = BUNDLE.getString("user");
		CONTRASENA = BUNDLE.getString("password");
		BASE_DE_DATOS = BUNDLE.getString("db");
	}
	
	/**
	 * Obtiene una conexión a la base de datos.
	 * 
	 * @return la conexión a la base de datos
	 * @throws SQLException si ocurre un error al obtener la conexión
	 */
	public static Connection getConexion() throws SQLException {
		return DriverManager.getConnection(String.format("jdbc:mariadb://localhost:3306/%s?user=%s&password=%s", BASE_DE_DATOS, USUARIO, CONTRASENA));
	}
}
