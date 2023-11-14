package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import excepciones.OlimpiadasException;
import model.Producto;
import utilities.StringUtils;


public class DAOProducto extends DAOBase {
	
	private static final String TABLA = "productos";
	
	public static Producto mapProducto(ResultSet rs) throws SQLException {
		return new Producto()
				.setCodigo(rs.getString("codigo"))
				.setDisponible(rs.getBoolean("disponible"))
				.setImagen(rs.getBytes("imagen"))
				.setNombre(rs.getString("nombre"))
				.setPrecio(rs.getDouble("precio"));
	}
	
	public static List<Producto> getProductos() throws OlimpiadasException {
		List<Producto> productos = new LinkedList<>();
		try (Connection con = getConexion()) {
			Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM " + TABLA);
				while (rs.next()) {
					productos.add(mapProducto(rs));
				}
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
		return productos;
	}
	
	public static Producto getProducto(String codigo) throws OlimpiadasException {
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
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	public static void anadirProducto(Producto producto) throws OlimpiadasException, SQLException {
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
				throw new OlimpiadasException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new OlimpiadasException("Los datos introducidos están incompletos");
		}
	}
	
	public static void modificarProducto(Producto producto) throws OlimpiadasException, SQLException {
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
				throw new OlimpiadasException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new OlimpiadasException("Los datos introducidos están incompletos");
		}
	}
	
	public static void borrarProducto(Producto producto) throws SQLException, OlimpiadasException {
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
				throw new OlimpiadasException(e);
			} finally {
				con.close();
			}
		}
		
	}
	
	
}
