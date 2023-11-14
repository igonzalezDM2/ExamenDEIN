package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import model.Producto;
import utilities.Utilidades;

public class ProductosController implements Initializable {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;
    
    @FXML
    private Button btnSubirImagen;

    @FXML
    private CheckBox cbDisponible;

    @FXML
    private ImageView ivImagen;

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
    void subirImagen(ActionEvent event) {
    	Window window = ((Node)event.getSource()).getScene().getWindow();
    	byte[] imagen = Utilidades.abrirFileChooserImagen(window);
    	//TODO: HAZ LO QUE TE SALGA DE LA PINGA CON LA IMAGEN
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//        tcCodigo.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
        
//        tcCodigo.setCellValueFactory(param -> {
//            Producto prod = param.getValue();
//            if (prod != null) {
//                return new SimpleStringProperty(prod.getCodigo());
//            }
//            return new SimpleStringProperty();
//        });
        
        
        ivImagen.setOnMouseClicked(e -> {
        	Producto producto = tvProductos.getSelectionModel().getSelectedItem();
        	
        	if (producto != null && producto.getImagen() != null) {
        			Utilidades.mostrarImagen(producto.getImagen(), 0, 0);
        		
        	}
        });
		
	}

}
