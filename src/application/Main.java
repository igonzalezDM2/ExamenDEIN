package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Clase principal que extiende de Application.
 */
public class Main extends Application {
	
	/**
	 * Método de inicio de la aplicación.
	 * @param primaryStage Escenario principal de la aplicación.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			/**
			 * Carga la vista de la aplicación.
			 */
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/style/estilos.css").toExternalForm());
			primaryStage.setTitle("PRODUCTOS");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			/**
			 * En caso de error, se cierra la aplicación.
			 */
			Platform.exit();
		}
	}
	
	/**
	 * Método principal de la aplicación.
	 * @param args Argumentos de la línea de comandos.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
