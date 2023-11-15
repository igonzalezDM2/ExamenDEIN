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
import excepciones.OlimpiadasException;
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

public class ProductosController implements Initializable {
	
	byte[] imgSeleccionada;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnLimpiar;
    
    @FXML
    private Button btnSubirImagen;

    @FXML
    private CheckBox cbDisponible;

    @FXML
    private ImageView ivImagen;
    
    @FXML
    private MenuItem miInfo;

    @FXML
    private TableColumn<Producto, String> tcCodigo;

    @FXML
    private TableColumn<Producto, Boolean> tcDisponinbe;

    @FXML
    private TableColumn<Producto, String> tcNombre;

    @FXML
    private TableColumn<Producto, Double> tcPrecio;

    @FXML
    private TextField tfCodigo;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPrecio;

    @FXML
    private TableView<Producto> tvProductos;
    
    @FXML
    void actualizar(ActionEvent event) {
    	Producto seleccionado = tvProductos.getSelectionModel().getSelectedItem();
    	if (seleccionado.getImagen() != null && this.imgSeleccionada == null) {
    		lanzarError(new OlimpiadasException("Si tenía una imagen, no puede dejarla en blanco"));
    		return;
    	}
    	if (validarFormulario()) {
    		try {
    			Producto producto = construirProducto();
    			DAOProducto.modificarProducto(producto);
    			limpiarFormulario();
    			actualizarTabla();
    		} catch (OlimpiadasException | SQLException e) {
    			lanzarError(e);
    		}
    	}
    }

    @FXML
    void crear(ActionEvent event) {
    	if (validarFormulario()) {    		
    		try {
    			Producto producto = construirProducto();
    			DAOProducto.anadirProducto(producto);
    			limpiarFormulario();
    			actualizarTabla();
    		} catch (OlimpiadasException | SQLException e) {
    			lanzarError(e);
    		}
    	}
    }

    @FXML
    void limpiar(ActionEvent event) {
    	limpiarFormulario();
    }
    
    @FXML
    void subirImagen(ActionEvent event) {
    	try {
    		Window window = ((Node)event.getSource()).getScene().getWindow();
    		this.imgSeleccionada = Utilidades.abrirFileChooserImagen(window);
    		if (this.imgSeleccionada != null) {    			
    			ivImagen.setImage(Utilidades.byte2Image(imgSeleccionada));
    		}
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
    }
    
    @FXML
    void acercade(ActionEvent event) {
    	mostrarInfo("Gestión de productos 1.0\nAutor: don Iker González Díaz");
    }
    
    private void actualizarTabla() {
    	try {
    		tvProductos.getItems().clear();
			tvProductos.getItems().addAll(DAOProducto.getProductos());
			tvProductos.refresh();
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
    }
    
    private boolean validarFormulario() {
    	StringBuilder errores = new StringBuilder();
    	errores.append(checkCampoStrNotNullStr(tfCodigo) + "\n");
    	
    	errores.append(checkCampoStrMinLengthStr(tfCodigo, 5) + "\n");
    	errores.append(checkCampoStrMaxLengthStr(tfCodigo, 5) + "\n");
    	
    	errores.append(checkCampoStrNotNullStr(tfNombre) + "\n");
    	
    	errores.append(checkCampoStrNotNullStr(tfPrecio) + "\n");
    	errores.append(checkCampoDoubleStr(tfPrecio) + "\n");
    	
    	if (!StringUtils.isBlank(errores.toString())) {
    		lanzarError(new OlimpiadasException(errores.toString()));
    		return false;
    	}
    	return true;
    }
    
    private Producto construirProducto() throws OlimpiadasException {
    	return new Producto()
    			.setCodigo(StringUtils.trimToEmpty(tfCodigo.getText()))
    			.setNombre(StringUtils.trimToEmpty(tfNombre.getText()))
    			.setPrecio(Utilidades.parseDouble(StringUtils.trimToEmpty(tfPrecio.getText())))
    			.setDisponible(cbDisponible.isSelected())
    			.setImagen(imgSeleccionada);
    }
    
    private void rellenarEditor(Producto producto) {
    	try {
    		tfCodigo.setText(producto.getCodigo());
    		tfNombre.setText(producto.getNombre());
    		tfPrecio.setText(num2str(producto.getPrecio()));
    		cbDisponible.setSelected(producto.isDisponible());
    		this.imgSeleccionada = producto.getImagen();
			ivImagen.setImage(Utilidades.byte2Image(imgSeleccionada));
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
    }
    
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
        	} catch (OlimpiadasException | SQLException ex) {
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
