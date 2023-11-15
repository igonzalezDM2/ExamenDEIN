package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import excepciones.ProductosException;
import model.Producto;
import utilities.StringUtils;


/**
 * Esta clase proporciona métodos para interactuar con la tabla de productos en la base de datos.
 */
public class DAOProducto extends DAOBase {
	
	private static final String TABLA = "productos";
	
	/**
	 * Este método mapea un ResultSet a un objeto Producto.
	 * @param rs el ResultSet a mapear
	 * @return un objeto Producto
	 * @throws SQLException si ocurre un error al acceder a los datos
	 */
	public static Producto mapProducto(ResultSet rs) throws SQLException {
		return new Producto()
				.setCodigo(rs.getString("codigo"))
				.setDisponible(rs.getBoolean("disponible"))
				.setImagen(rs.getBytes("imagen"))
				.setNombre(rs.getString("nombre"))
				.setPrecio(rs.getDouble("precio"));
	}
	
	/**
	 * Este método devuelve una lista de todos los productos en la base de datos.
	 * @return una lista de objetos Producto
	 * @throws ProductosException si ocurre un error al recuperar los productos
	 */
	public static List<Producto> getProductos() throws ProductosException {
		List<Producto> productos = new LinkedList<>();
		try (Connection con = getConexion()) {
			Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM " + TABLA);
				while (rs.next()) {
					productos.add(mapProducto(rs));
				}
		} catch (SQLException e) {
			throw new ProductosException(e);
		}
		return productos;
	}
	
	/**
	 * Este método devuelve un producto específico de la base de datos.
	 * @param codigo el código del producto a recuperar
	 * @return un objeto Producto si se encuentra el producto, null en caso contrario
	 * @throws ProductosException si ocurre un error al recuperar el producto
	 */
	public static Producto getProducto(String codigo) throws ProductosException {
		if (codigo != null && !StringUtils.isBlank(codigo)) {
			String sql = "SELECT * FROM " + TABLA + " WHERE codigo = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, codigo);
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return mapProducto(rs);
				}
			} catch (SQLException e) {
				throw new ProductosException(e);
			}
		}
		return null;
	}
	
	/**
	 * Este método añade un nuevo producto a la base de datos.
	 * @param producto el producto a añadir
	 * @throws ProductosException si ocurre un error al añadir el producto
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 */
	public static void anadirProducto(Producto producto) throws ProductosException, SQLException {
		if (producto != null) {
			
			String sql = "INSERT INTO " + TABLA + " ("
					+ "nombre, "
					+ "precio, "
					+ "imagen, "
					+ "disponible, "
					+ "codigo) "
					+ "VALUES (?,?,?,?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, producto.getNombre());
					ps.setDouble(2, producto.getPrecio());
					ps.setBytes(3, producto.getImagen());
					ps.setBoolean(4, producto.isDisponible());
					ps.setString(5, producto.getCodigo());
					
					ps.executeUpdate();
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new ProductosException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new ProductosException("Los datos introducidos están incompletos");
		}
	}
	
	/**
	 * Este método modifica un producto existente en la base de datos.
	 * @param producto el producto a modificar
	 * @throws ProductosException si ocurre un error al modificar el producto
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 */
	public static void modificarProducto(Producto producto) throws ProductosException, SQLException {
		if (producto != null && !StringUtils.isBlank(producto.getCodigo())) {
			
			String sql = "UPDATE " + TABLA +" SET "
					+ "nombre = ?, "
					+ "precio= ?, "
					+ "imagen = ?, "
					+ "disponible = ? "
					+ "WHERE codigo = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, producto.getNombre());
					ps.setDouble(2, producto.getPrecio());
					ps.setBytes(3, producto.getImagen());
					ps.setBoolean(4, producto.isDisponible());
					ps.setString(5, producto.getCodigo());
					
					ps.executeUpdate();
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new ProductosException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new ProductosException("Los datos introducidos están incompletos");
		}
	}
	
	/**
	 * Este método elimina un producto de la base de datos.
	 * @param producto el producto a eliminar
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 * @throws ProductosException si ocurre un error al eliminar el producto
	 */
	public static void borrarProducto(Producto producto) throws SQLException, ProductosException {
		if (producto != null && !StringUtils.isBlank(producto.getCodigo())) {			
			String sql = "DELETE FROM " + TABLA +" WHERE codigo = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, producto.getCodigo());
				ps.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				con.rollback();
				throw new ProductosException(e);
			} finally {
				con.close();
			}
		}
		
	}
	
	
}
