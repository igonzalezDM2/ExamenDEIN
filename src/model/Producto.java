package model;

import java.util.Objects;

public class Producto {

    private String codigo;
    private String nombre;
    private byte[] imagen;
    private double precio;
    private boolean disponible;
    
    public Producto() {	
    }

	public String getCodigo() {
		return codigo;
	}

	public Producto setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public Producto setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public Producto setImagen(byte[] imagen) {
		this.imagen = imagen;
		return this;
	}

	public double getPrecio() {
		return precio;
	}

	public Producto setPrecio(double precio) {
		this.precio = precio;
		return this;
	}

	public boolean isDisponible() {
		return disponible;
	}

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
