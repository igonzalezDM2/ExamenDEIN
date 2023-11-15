package controller;

import static utilities.Utilidades.checkCampoDoubleStr;
import static utilities.Utilidades.checkCampoStrMaxLengthStr;
import static utilities.Utilidades.checkCampoStrMinLengthStr;
import static utilities.Utilidades.checkCampoStrNotNullStr;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarImagen;
import static utilities.Utilidades.mostrarInfo;
import static utilities.Utilidades.num2str;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAOProducto;
import excepciones.ProductosException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import model.Producto;
import utilities.StringUtils;
import utilities.Utilidades;

/**
 * Controlador para la gestión de productos.
 */
public class ProductosController implements Initializable {
	
	byte[] imgSeleccionada;

    /**
     * Botón para actualizar un producto.
     */
    @FXML
    private Button btnActualizar;

    /**
     * Botón para crear un nuevo producto.
     */
    @FXML
    private Button btnCrear;

    /**
     * Botón para limpiar el formulario de producto.
     */
    @FXML
    private Button btnLimpiar;
    
    /**
     * Botón para subir una imagen para el producto.
     */
    @FXML
    private Button btnSubirImagen;

    /**
     * Checkbox para indicar si el producto está disponible.
     */
    @FXML
    private CheckBox cbDisponible;

    /**
     * Vista de imagen para mostrar la imagen del producto.
     */
    @FXML
    private ImageView ivImagen;
    
    /**
     * Menú de información.
     */
    @FXML
    private MenuItem miInfo;

    /**
     * Columna de código de producto en la tabla de productos.
     */
    @FXML
    private TableColumn<Producto, String> tcCodigo;

    /**
     * Columna de disponibilidad de producto en la tabla de productos.
     */
    @FXML
    private TableColumn<Producto, Boolean> tcDisponinbe;

    /**
     * Columna de nombre de producto en la tabla de productos.
     */
    @FXML
    private TableColumn<Producto, String> tcNombre;

    /**
     * Columna de precio de producto en la tabla de productos.
     */
    @FXML
    private TableColumn<Producto, Double> tcPrecio;

    /**
     * Campo de texto para el código de producto.
     */
    @FXML
    private TextField tfCodigo;

    /**
     * Campo de texto para el nombre de producto.
     */
    @FXML
    private TextField tfNombre;

    /**
     * Campo de texto para el precio de producto.
     */
    @FXML
    private TextField tfPrecio;

    /**
     * Tabla de vista de productos.
     */
    @FXML
    private TableView<Producto> tvProductos;
    
    /**
     * Método para actualizar un producto.
     * @param event Evento de acción.
     */
    @FXML
    void actualizar(ActionEvent event) {
    	Producto seleccionado = tvProductos.getSelectionModel().getSelectedItem();
    	if (seleccionado.getImagen() != null && this.imgSeleccionada == null) {
    		lanzarError(new ProductosException("Si tenía una imagen, no puede dejarla en blanco"));
    		return;
    	}
    	if (validarFormulario()) {
    		try {
    			Producto producto = construirProducto();
    			DAOProducto.modificarProducto(producto);
    			limpiarFormulario();
    			actualizarTabla();
    		} catch (ProductosException | SQLException e) {
    			lanzarError(e);
    		}
    	}
    }

    /**
     * Método para crear un nuevo producto.
     * @param event Evento de acción.
     */
    @FXML
    void crear(ActionEvent event) {
    	if (validarFormulario()) {    		
    		try {
    			Producto producto = construirProducto();
    			DAOProducto.anadirProducto(producto);
    			limpiarFormulario();
    			actualizarTabla();
    		} catch (ProductosException | SQLException e) {
    			lanzarError(e);
    		}
    	}
    }

    /**
     * Método para limpiar el formulario de producto.
     * @param event Evento de acción.
     */
    @FXML
    void limpiar(ActionEvent event) {
    	limpiarFormulario();
    }
    
    /**
     * Método para subir una imagen para el producto.
     * @param event Evento de acción.
     */
    @FXML
    void subirImagen(ActionEvent event) {
    	try {
    		Window window = ((Node)event.getSource()).getScene().getWindow();
    		this.imgSeleccionada = Utilidades.abrirFileChooserImagen(window);
    		if (this.imgSeleccionada != null) {    			
    			ivImagen.setImage(Utilidades.byte2Image(imgSeleccionada));
    		}
		} catch (ProductosException e) {
			lanzarError(e);
		}
    }
    
    /**
     * Método para mostrar información sobre la aplicación.
     * @param event Evento de acción.
     */
    @FXML
    void acercade(ActionEvent event) {
    	mostrarInfo("Gestión de productos 1.0\nAutor: don Iker González Díaz");
    }
    
    /**
     * Método para actualizar la tabla de productos.
     */
    private void actualizarTabla() {
    	try {
    		tvProductos.getItems().clear();
			tvProductos.getItems().addAll(DAOProducto.getProductos());
			tvProductos.refresh();
		} catch (ProductosException e) {
			lanzarError(e);
		}
    }
    
    /**
     * Método para validar el formulario de producto.
     * @return Verdadero si el formulario es válido, falso en caso contrario.
     */
    private boolean validarFormulario() {
    	StringBuilder errores = new StringBuilder();
    	errores.append(checkCampoStrNotNullStr(tfCodigo) + "\n");
    	
    	errores.append(checkCampoStrMinLengthStr(tfCodigo, 5) + "\n");
    	errores.append(checkCampoStrMaxLengthStr(tfCodigo, 5) + "\n");
    	
    	errores.append(checkCampoStrNotNullStr(tfNombre) + "\n");
    	
    	errores.append(checkCampoStrNotNullStr(tfPrecio) + "\n");
    	errores.append(checkCampoDoubleStr(tfPrecio) + "\n");
    	
    	if (!StringUtils.isBlank(errores.toString())) {
    		lanzarError(new ProductosException(errores.toString()));
    		return false;
    	}
    	return true;
    }
    
    /**
     * Método para construir un producto a partir de los datos del formulario.
     * @return Producto construido.
     * @throws ProductosException Si hay un error al construir el producto.
     */
    private Producto construirProducto() throws ProductosException {
    	return new Producto()
    			.setCodigo(StringUtils.trimToEmpty(tfCodigo.getText()))
    			.setNombre(StringUtils.trimToEmpty(tfNombre.getText()))
    			.setPrecio(Utilidades.parseDouble(StringUtils.trimToEmpty(tfPrecio.getText())))
    			.setDisponible(cbDisponible.isSelected())
    			.setImagen(imgSeleccionada);
    }
    
    /**
     * Método para rellenar el formulario de producto con los datos de un producto.
     * @param producto Producto a partir del cual se rellenará el formulario.
     */
    private void rellenarEditor(Producto producto) {
    	try {
    		tfCodigo.setText(producto.getCodigo());
    		tfNombre.setText(producto.getNombre());
    		tfPrecio.setText(num2str(producto.getPrecio()));
    		cbDisponible.setSelected(producto.isDisponible());
    		this.imgSeleccionada = producto.getImagen();
			ivImagen.setImage(Utilidades.byte2Image(imgSeleccionada));
		} catch (ProductosException e) {
			lanzarError(e);
		}
    }
    
    /**
     * Método para limpiar el formulario de producto.
     */
    private void limpiarFormulario() {
    	this.imgSeleccionada = null;
    	tfCodigo.clear();
    	tfNombre.clear();
    	tfPrecio.clear();
    	cbDisponible.setSelected(false);
    	ivImagen.setImage(null);
    	btnActualizar.setDisable(true);
    	btnCrear.setDisable(false);
    	tfCodigo.setDisable(false);
    	tvProductos.getSelectionModel().clearSelection();
    }

	/**
	 * Método para inicializar el controlador.
	 * @param location Ubicación utilizada para resolver rutas relativas para el objeto raíz.
	 * @param resources Recursos utilizados para localizar el objeto raíz.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnActualizar.setDisable(true);

        tcCodigo.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
        tcDisponinbe.setCellFactory(tc -> new CheckBoxTableCell<Producto, Boolean>());
        tcDisponinbe.setCellValueFactory(f -> new SimpleBooleanProperty(f.getValue().isDisponible()));
        
        tvProductos.getItems().addListener(new ListChangeListener<Producto>() {

			@Override
			public void onChanged(Change<? extends Producto> p) {
				Producto producto = tvProductos.getSelectionModel().getSelectedItem();
				
				if (producto != null) {
					btnCrear.setDisable(true);
					btnActualizar.setDisable(false);
					tfCodigo.setDisable(true);
					rellenarEditor(producto);
				} else {
					btnCrear.setDisable(false);
					btnActualizar.setDisable(true);
					tfCodigo.setDisable(false);
					limpiarFormulario();
				}				
			}
        	
        });
        
        tvProductos.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Producto>() {

			@Override
			public void onChanged(Change<? extends Producto> c) {
	        	Producto producto = tvProductos.getSelectionModel().getSelectedItem();
	        	if (producto != null) {
	        		btnCrear.setDisable(true);
	        		btnActualizar.setDisable(false);
	        		tfCodigo.setDisable(true);
	        		rellenarEditor(producto);
	        	} else {
	        		btnCrear.setDisable(false);
	        		btnActualizar.setDisable(true);
	        		tfCodigo.setDisable(false);
	        		limpiarFormulario();
	        	}
			}
        	
        });
        
        MenuItem miVerImagen = new MenuItem("Ver Imagen");
        MenuItem miEliminar = new MenuItem("Eliminar");
        
        miVerImagen.setOnAction(e -> {
        	Producto producto = tvProductos.getSelectionModel().getSelectedItem();
        	if (producto != null && producto.getImagen() != null) {        		
        		mostrarImagen(producto.getImagen(), 300, 300, "Imagen");
        	}
        });
        
        miEliminar.setOnAction(e -> {
        	try {
        		Producto producto = tvProductos.getSelectionModel().getSelectedItem();
            	if (producto != null) {
            		DAOProducto.borrarProducto(producto);
            		limpiarFormulario();
            		actualizarTabla();
            	}
        	} catch (ProductosException | SQLException ex) {
        		lanzarError(ex);
        	}
        });
        
        ContextMenu cm = new ContextMenu(miVerImagen, miEliminar);
        
        //HAGO VISIBLES LOS BOTONES SEGÚN DISPONIBILIDAD
        cm.setOnShowing(e -> {
        	Producto producto = tvProductos.getSelectionModel().getSelectedItem();
        	if (producto != null) {
        		miEliminar.setVisible(true);
        		miVerImagen.setVisible(producto.getImagen() != null);
        		
        	} else {
        		miEliminar.setVisible(false);
        		miVerImagen.setVisible(false);
        	}
        	
        });
        
        
        tvProductos.setContextMenu(cm);
        
        
        actualizarTabla();
		
	}

}
