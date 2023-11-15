package utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import excepciones.ProductosException;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Clase de utilidades para el proyecto.
 */
public class Utilidades {
	
	private Utilidades() throws IllegalAccessException {
		throw new IllegalAccessException("Clase de utilidad");
	}
	
	/**
	 * Convierte una cadena en formato decimal a un valor double.
	 * 
	 * @param str cadena a convertir
	 * @return valor double convertido
	 * @throws ProductosException si la cadena no tiene un formato decimal válido
	 */
	public static double parseDouble(String str) throws ProductosException {
		if (str != null && !str.isBlank()) {
			try {
				return Double.parseDouble(str.replace(',', '.'));
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new ProductosException("Formato de número decimal incorrecto");
	}
	
	/**
	 * Convierte una cadena en formato entero a un valor int.
	 * 
	 * @param str cadena a convertir
	 * @return valor int convertido
	 * @throws ProductosException si la cadena no tiene un formato entero válido
	 */
	public static int parseInt(String str) throws ProductosException {
		if (str != null && !str.isBlank()) {
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new ProductosException("Formato de número entero incorrecto");
	}
	
	/**
	 * Devuelve una cadena vacía si la cadena de entrada es nula.
	 * 
	 * @param str cadena de entrada
	 * @return cadena vacía si la cadena de entrada es nula, de lo contrario, la cadena de entrada sin cambios
	 */
	public static String emptyIfNull(String str) {
		if (str != null) {
			return str;
		}
		return "";
	}
	
	/**
	 * Convierte un número en una cadena.
	 * 
	 * @param num número a convertir
	 * @return cadena que representa el número
	 */
	public static <T extends Number> String num2str(T num) {
		if (num != null) {
			if (num instanceof Double || num instanceof Float) {
				return String.format("%,.2f", (Double)num);
			}
			return "" + num;
		}
		return "";
	}
	
	/**
	 * Convierte una fecha LocalDate a un objeto Date.
	 * 
	 * @param ld fecha LocalDate a convertir
	 * @return objeto Date convertido
	 */
	public static Date local2Date(LocalDate ld) {
		if (ld != null) {			
			return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}
	
	/**
	 * Convierte un objeto Date a una fecha LocalDate.
	 * 
	 * @param date objeto Date a convertir
	 * @return fecha LocalDate convertida
	 */
	public static LocalDate date2Local(Date date) {
		if (date != null) {			
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}
	
	/**
	 * Convierte un array de bytes en una imagen.
	 * 
	 * @param bytes array de bytes a convertir
	 * @return imagen convertida
	 * @throws ProductosException si ocurre un error al convertir el array de bytes en imagen
	 */
	public static Image byte2Image(byte[] bytes) throws ProductosException {
		if (bytes != null) {
			try (InputStream is = new ByteArrayInputStream(bytes)) {				
				return new Image(is);
			} catch (IOException e) {
				throw new ProductosException(e);
			}
		}
		return null;
	}
	
	/**
	 * Verifica que el contenido de un TextField sea un número decimal válido.
	 * 
	 * @param tf TextField a verificar
	 * @throws ProductosException si el contenido del TextField no es un número decimal válido
	 */
	public static void checkCampoDouble(TextField tf) throws ProductosException {
		String strNum = tf.getText();
		Pattern doublePattern = Pattern.compile("\\d+([\\.,]\\d+)?");
		Matcher matcher = doublePattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new ProductosException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}

	public static String checkCampoDoubleStr(TextField tf) {
		String strNum = tf.getText();
		Pattern doublePattern = Pattern.compile("\\d+([\\.,]\\d+)?");
		Matcher matcher = doublePattern.matcher(strNum);
		if (!matcher.matches()) {
			return "El campo " + tf.getId() + " contiene un formato incorrecto o está vacío";
		}
		return "";
	}

	/**
	 * Verifica que el contenido de un TextField sea un número entero válido.
	 * 
	 * @param tf TextField a verificar
	 * @throws ProductosException si el contenido del TextField no es un número entero válido
	 */
	public static void checkCampoInt(TextField tf) throws ProductosException {
		String strNum = tf.getText();
		Pattern intPattern = Pattern.compile("\\d+");
		Matcher matcher = intPattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new ProductosException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}

	/**
	 * Verifica que el contenido de un TextField no sea nulo ni vacío.
	 * 
	 * @param tf TextField a verificar
	 * @throws ProductosException si el contenido del TextField es nulo o vacío
	 */
	public static void checkCampoStrNotNull(TextField tf) throws ProductosException {
		String str = tf.getText();
		if (str == null || str.isBlank()) {
			throw new ProductosException("El campo" + tf.getId() + " está vacío");
		}
	}
	
	public static String checkCampoStrNotNullStr(TextField tf) {
		String str = tf.getText();
		if (str == null || str.isBlank()) {
			return "El campo" + tf.getId() + " está vacío";
		}
		return "";
	}
	
	
	/**
	 * Comprueba que no sobrepase un máximo de caracteres; no salta excepción si es nulo o vacío
	 * @param tf
	 * @param maxLength
	 * @throws ProductosException
	 */
	public static void checkCampoStrMaxLength(TextField tf, int maxLength) throws ProductosException {
		String str = tf.getText();
		if (str != null && !str.isBlank()) {
			if (tf.getText().length() > maxLength) {
				throw new ProductosException("El campo" + tf.getId() + " está tiene más de " + maxLength + " caracteres");
			}
		}
	}

	public static String checkCampoStrMaxLengthStr(TextField tf, int maxLength) {
		String str = StringUtils.trimToEmpty(tf.getText());
		if (str != null && !str.isBlank()) {
			if (tf.getText().length() > maxLength) {
				return "El campo" + tf.getId() + " tiene más de " + maxLength + " caracteres";
			}
		}
		return "";
	}
	
	/**
	 * Comprueba que alcanza un mínimo de caracteres; no salta excepción si es nulo o vacío
	 * @param tf
	 * @param maxLength
	 * @throws ProductosException
	 */
	public static void checkCampoStrMinLength(TextField tf, int minLength) throws ProductosException {
		String str = tf.getText();
		if (str != null && !str.isBlank()) {
			if (tf.getText().length() < minLength) {
				throw new ProductosException("El campo" + tf.getId() + " está tiene más de " + minLength + " caracteres");
			}
		}
	}

	public static String checkCampoStrMinLengthStr(TextField tf, int minLength) {
		String str = StringUtils.trimToEmpty(tf.getText());
		if (str != null && !str.isBlank()) {
			if (tf.getText().length() < minLength) {
				return "El campo" + tf.getId() + " está tiene más de " + minLength + " caracteres";
			}
		}
		return "";
	}
	
	/**
	 * Lanza un diálogo de error con el mensaje de error y muestra la traza de la excepción en la consola.
	 * 
	 * @param e excepción a lanzar
	 */
	public static void lanzarError(Throwable e) {
		Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
		alert.showAndWait();
		e.printStackTrace();
	}
	
	/**
	 * Muestra un diálogo de información con el mensaje de información.
	 * 
	 * @param info mensaje de información a mostrar
	 */
	public static void mostrarInfo(String info) {
		Alert alert = new Alert(AlertType.INFORMATION, info, ButtonType.OK);
		alert.setTitle("INFO");
		alert.setHeaderText("");
		alert.show();
	}
	
	/**
	 * Interfaz funcional para el callback de confirmación.
	 */
	@FunctionalInterface
	public static interface ConfirmCallback {
		/**
		 * Realiza las acciones necesarias.
		 */
		void hacerCosas();
	}
	
	/**
	 * Muestra un diálogo de confirmación con el mensaje especificado y ejecuta el callback si se selecciona "Sí".
	 * 
	 * @param mensaje el mensaje a mostrar en el diálogo de confirmación
	 * @param callback el callback a ejecutar si se selecciona "Sí"
	 */
	public static void confirmarSiNo(String mensaje, ConfirmCallback callback) {
		Alert alert = new Alert(AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> confirm = alert.showAndWait();
		if (ButtonType.YES.equals(confirm.orElse(null))) {
			callback.hacerCosas();
		}
	}
	
	/**
	 * Devuelve el elemento seleccionado en una tabla.
	 * 
	 * @param tabla tabla de la que se obtendrá el elemento seleccionado
	 * @return elemento seleccionado en la tabla
	 */
	public static <T> T getSeleccionTabla(TableView<T> tabla) {
		if (tabla != null) {			
			return tabla.getSelectionModel().getSelectedItem();
		}
		return null;
	}
	
	/**
	 * Convierte un objeto Date a un objeto java.sql.Date.
	 * 
	 * @param date objeto Date a convertir
	 * @return objeto java.sql.Date convertido
	 */
	public static java.sql.Date sqlDate(Date date) {
		if (date != null) {			
			return new java.sql.Date(date.getTime());
		}
		return null;
	}
	
	/**
	 * Cierra la ventana a partir de un evento.
	 * 
	 * @param evento el evento que desencadena el cierre de la ventana
	 */
	public static void cerrarVentanaDesdeEvento(Event evento) {
		((Stage)((Node)evento.getSource()).getScene().getWindow()).close();
	}
	
	
	
	
    /**
     * Abre un cuadro de diálogo para seleccionar una imagen.
     *
     * @param ventana la ventana padre del cuadro de diálogo
     * @return un arreglo de bytes que representa la imagen seleccionada, o null si no se seleccionó ninguna imagen
     */
    public static byte[] abrirFileChooserImagen(Window ventana) {
    	FileChooser fc = new FileChooser();
    	fc.setInitialDirectory(new File(System.getProperty("user.home")));
    	fc.setSelectedExtensionFilter(new ExtensionFilter("Imágenes JPG o PNG", "*.jpg", "*.png"));
    	File fichero = fc.showOpenDialog(ventana);
    	if (fichero != null) {    		
    		try (FileInputStream fis = new FileInputStream(fichero)) {
    			return fis.readAllBytes();
    		} catch (IOException e) {
    			lanzarError(e);
    		}
    	}
    	return null;
    }
	
    
    
    /**
     * Muestra una imagen con un ancho y alto personalizados.
     *
     * @param imagen el arreglo de bytes que representa la imagen
     * @param width el ancho deseado para la imagen
     * @param height el alto deseado para la imagen
     */
    public static void mostrarImagen(byte[] imagen, int width, int height) {
    	mostrarImagen(imagen, width, height, null);
    }
    
    /**
     * Muestra una imagen con un ancho y alto personalizados.
     *
     * @param imagen el arreglo de bytes que representa la imagen
     * @param width el ancho deseado para la imagen
     * @param height el alto deseado para la imagen
     * @param titulo el título de la ventana
     */
    public static void mostrarImagen(byte[] imagen, int width, int height, String titulo) {
    	
    	int defaultWidth = 400;
    	int defaultHeight = 400;
    	
    	int defWidth = width > 0 ? width : defaultWidth;
    	int defHeight = height > 0 ? height : defaultHeight;
    	
    	if (imagen != null) {    		
    		
    		try {
    			BorderPane imgRoot = new BorderPane();
				ImageView iv = new ImageView(Utilidades.byte2Image(imagen));
				iv.setPreserveRatio(true);
				iv.setFitWidth(defWidth);
				iv.setFitHeight(defHeight);
				iv.maxWidth(defWidth);
				iv.maxHeight(defHeight);
				iv.prefWidth(defWidth);
				iv.prefHeight(defHeight);
				imgRoot.setCenter(iv);
				Stage stage = new Stage();
				if (!StringUtils.isBlank(titulo)) {
					stage.setTitle(titulo);
				}
				stage.setResizable(false);
				stage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(imgRoot,defWidth,defHeight);
				stage.setScene(scene);
				stage.show();
			} catch (ProductosException e) {
				lanzarError(e);
			}
    				
    	}
    }
	
	
}
