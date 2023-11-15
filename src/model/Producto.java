package model;

import java.util.Objects;

/**
 * Clase que representa un producto.
 */
public class Producto {

    private String codigo;
    private String nombre;
    private byte[] imagen;
    private double precio;
    private boolean disponible;
    
    /**
     * Constructor por defecto.
     */
    public Producto() {	
    }

    /**
     * Obtiene el código del producto.
     * 
     * @return el código del producto
     */
	public String getCodigo() {
		return codigo;
	}

    /**
     * Establece el código del producto.
     * 
     * @param codigo el código del producto
     * @return la instancia actual del producto
     */
	public Producto setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

    /**
     * Obtiene el nombre del producto.
     * 
     * @return el nombre del producto
     */
	public String getNombre() {
		return nombre;
	}

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre el nombre del producto
     * @return la instancia actual del producto
     */
	public Producto setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

    /**
     * Obtiene la imagen del producto.
     * 
     * @return la imagen del producto
     */
	public byte[] getImagen() {
		return imagen;
	}

    /**
     * Establece la imagen del producto.
     * 
     * @param imagen la imagen del producto
     * @return la instancia actual del producto
     */
	public Producto setImagen(byte[] imagen) {
		this.imagen = imagen;
		return this;
	}

    /**
     * Obtiene el precio del producto.
     * 
     * @return el precio del producto
     */
	public double getPrecio() {
		return precio;
	}

    /**
     * Establece el precio del producto.
     * 
     * @param precio el precio del producto
     * @return la instancia actual del producto
     */
	public Producto setPrecio(double precio) {
		this.precio = precio;
		return this;
	}

    /**
     * Verifica si el producto está disponible.
     * 
     * @return true si el producto está disponible, false de lo contrario
     */
	public boolean isDisponible() {
		return disponible;
	}

    /**
     * Establece la disponibilidad del producto.
     * 
     * @param disponible true si el producto está disponible, false de lo contrario
     * @return la instancia actual del producto
     */
	public Producto setDisponible(boolean disponible) {
		this.disponible = disponible;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return nombre;
	}
    
    
    
}
