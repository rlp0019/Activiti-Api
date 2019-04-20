package gui.fx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import gui.fx.herramientas.CreadorElementos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;

/**
 * Escena para realizar la comparación de métricas entre dos archivos guardados.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaComparacion extends StackPane {

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaComparacion(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Label selecciona = CreadorElementos.createLabel("Selecciona los informes a comparar:", 32, "#0076a3", 0, -175);

		Label selecciona1 = CreadorElementos.createLabel("Selecciona el primer informe a comparar:", 20, "#505050", 0,
				-125);

		Label selecciona2 = CreadorElementos.createLabel("Selecciona el segundo informe a comparar:", 20, "#505050", 0,
				10);

		TextField archivo1 = CreadorElementos.createTextField("Path del primer archivo.", 16,
				"Aparecerá el nombre del primer archivo a comparar.", -50, -70, 350);
		archivo1.setDisable(true);
		archivo1.setStyle("-fx-opacity: 1;");

		TextField archivo2 = CreadorElementos.createTextField("Path del segundo archivo.", 16,
				"Aparecerá el nombre del segundo archivo a comparar.", -50, 65, 350);
		archivo2.setDisable(true);
		archivo2.setStyle("-fx-opacity: 1;");

		Button s1B = CreadorElementos.createButton("Buscar", 16, "Buscar primer archivo a comparar.", 200, -70, 100);
		s1B.setOnAction(e -> seleccionarFichero(aplicacion, archivo1));

		Button s2B = CreadorElementos.createButton("Buscar", 16, "Buscar segundo archivo a comparar.", 200, 65, 100);
		s2B.setOnAction(e -> seleccionarFichero(aplicacion, archivo2));

		Button comparar = CreadorElementos.createButton("Comparar", 16, "Realizar la comparación.", 0, 125, 100);
		comparar.setOnAction(e -> leerFicheros(aplicacion, archivo1, archivo2));

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		this.getChildren().addAll(selecciona, selecciona1, selecciona2, archivo1, archivo2, s1B, s2B, comparar, atrasB);
	}

	/**
	 * Selecciona un fichero y muestra en el campo de texto su path absoluto.
	 * 
	 * @param aplicacion aplicación principal.
	 * @param txtDestino campo de texto que mostrará el resultado.
	 */
	private void seleccionarFichero(PrincipalFX aplicacion, TextField txtDestino) {
		FileChooser selector = new FileChooser();
		ExtensionFilter filtro = new ExtensionFilter("*.txt", "txt");
		selector.setSelectedExtensionFilter(filtro);
		File archivo = selector.showOpenDialog(aplicacion.getVentana());
		if (archivo != null) {
			txtDestino.setText(archivo.getAbsolutePath());
		}
	}

	/**
	 * Lee dos ficheros, los compara y pasa a la escena en la que muestra los
	 * resultados.
	 * 
	 * @param aplicacion aplicación principal.
	 * @param tf1        campo de texto con el primer informe.
	 * @param tf2        campo de texto con el segundo informe.
	 */
	private void leerFicheros(PrincipalFX aplicacion, TextField tf1, TextField tf2) {
		Alert alerta = new Alert(AlertType.NONE,
				"Deben seleccionarse dos informes distintos para proceder a la comparación.", ButtonType.CANCEL);
		alerta.setTitle("Error");

		Alert alerta2 = new Alert(AlertType.NONE, "Estás intentando comparar el mismo informe.", ButtonType.CANCEL);
		alerta2.setTitle("Error");

		Alert alerta3 = new Alert(AlertType.NONE, "Error al abrir los informes.", ButtonType.CANCEL);
		alerta3.setTitle("Error");

		if (tf1.getText().equals("") || tf2.getText().equals("")) {
			alerta.showAndWait();
		} else if (tf1.getText().equals(tf2.getText())) {
			alerta2.showAndWait();
		} else {
			try {
				FileReader lee1 = new FileReader(tf1.getText());
				FileReader lee2 = new FileReader(tf2.getText());
				BufferedReader contenido1 = new BufferedReader(lee1);
				BufferedReader contenido2 = new BufferedReader(lee2);
				String linea1 = "";
				String linea2 = "";
				if (((linea1 = contenido1.readLine()) != null) && ((linea2 = contenido2.readLine()) != null)
						&& "GitHub".equals(linea1) && "GitHub".equals(linea2)) {
					FabricaConexion fabrica1 = FabricaConexionGitHub.getInstance();
					FabricaConexion fabrica2 = FabricaConexionGitHub.getInstance();
					FachadaConexion conexion1 = fabrica1.crearFachadaConexion();
					FachadaConexion conexion2 = fabrica2.crearFachadaConexion();
					conexion1.leerArchivo(contenido1);
					conexion2.leerArchivo(contenido2);

					EscenaResultadoComparacion.setComparacion(compararInformes(conexion1, conexion2));

					aplicacion.cambiaEscena(7);
				}
				contenido1.close();
				lee1.close();
				contenido2.close();
				lee2.close();
			} catch (IOException e) {
				alerta3.showAndWait();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo que genera el texto en formato HTML con los resultados de la
	 * comparacion.
	 * 
	 * @return String con los resultados de la comparacion en formato HTML.
	 */
	private String compararInformes(FachadaConexion conexion1, FachadaConexion conexion2) {
		String texto = "<table>";
		texto += "<tr>";
		texto += "<th>Métrica</th>";
		texto += "<th>" + conexion1.getNombreRepositorio() + "</th>";
		texto += "<th>" + conexion2.getNombreRepositorio() + "</th>";
		texto += "</tr>";

		texto += conexion1.comparar(conexion2);

		texto += "</table>";

		return texto;
	}
}
