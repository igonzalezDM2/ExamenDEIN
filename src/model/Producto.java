package model;

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
    
    
    
}
