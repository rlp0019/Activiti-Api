package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.filechooser.FileSystemView;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
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
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(PrincipalFX.class.getName());

	/**
	 * Label con el título.
	 */
	private static Label selecciona = CreadorElementos.createLabel("", 32, "#0076a3", 0, 30);

	/**
	 * Label con el título de la primera búsqueda.
	 */
	private static Label selecciona1 = CreadorElementos.createLabel("", 20, "#505050", 0, -125);

	/**
	 * Label con el título de la segunda búsqueda.
	 */
	private static Label selecciona2 = CreadorElementos.createLabel("", 20, "#505050", 0, 10);

	/**
	 * Textfield con el path del primer archivo.
	 */
	private static TextField archivo1 = CreadorElementos.createTextField("", 16, "", -50, -70, 350);

	/**
	 * Textfield con el path del segundo archivo.
	 */
	private static TextField archivo2 = CreadorElementos.createTextField("", 16, "", -50, 65, 350);

	/**
	 * Primer botón de búsqueda.
	 */
	private static Button s1B = CreadorElementos.createButton("", 16, "", 200, -70, 100);

	/**
	 * Segundo botón de búsqueda.
	 */
	private static Button s2B = CreadorElementos.createButton("", 16, "", 200, 65, 100);

	/**
	 * Botón para comparar.
	 */
	private static Button comparar = CreadorElementos.createButton("", 16, "", -5, -5, 100);

	/**
	 * Botón para volver a la escena anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("", 16, "", 5, -5, 100);

	/**
	 * Alerta de necesarios 2 informes.
	 */
	private static Alert alerta = CreadorElementos.createAlertaError("", "", "");

	/**
	 * Alerta de informes iguales.
	 */
	private static Alert alerta2 = CreadorElementos.createAlertaError("", "", "");

	/**
	 * Alerta de error de apertura.
	 */
	private static Alert alerta3 = CreadorElementos.createAlertaError("", "", "");

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaComparacion(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		archivo1.setDisable(true);
		archivo1.setStyle("-fx-opacity: 1;");

		archivo2.setDisable(true);
		archivo2.setStyle("-fx-opacity: 1;");

		s1B.setOnAction(e -> seleccionarFichero(aplicacion, archivo1));

		s2B.setOnAction(e -> seleccionarFichero(aplicacion, archivo2));

		comparar.setOnAction(e -> leerFicheros(aplicacion, archivo1, archivo2));

		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		reloadIdioma(aplicacion, 0);

		this.getChildren().addAll(selecciona, selecciona1, selecciona2, archivo1, archivo2, s1B, s2B, comparar, atrasB);
		EscenaComparacion.setAlignment(selecciona, Pos.TOP_CENTER);
		EscenaComparacion.setAlignment(selecciona1, Pos.CENTER);
		EscenaComparacion.setAlignment(selecciona2, Pos.CENTER);
		EscenaComparacion.setAlignment(archivo1, Pos.CENTER);
		EscenaComparacion.setAlignment(archivo2, Pos.CENTER);
		EscenaComparacion.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaComparacion.setAlignment(comparar, Pos.BOTTOM_RIGHT);
	}

	/**
	 * Selecciona un fichero y muestra en el campo de texto su path absoluto.
	 * 
	 * @param aplicacion aplicación principal.
	 * @param txtDestino campo de texto que mostrará el resultado.
	 */
	private void seleccionarFichero(PrincipalFX aplicacion, TextField txtDestino) {
		FileChooser selector = new FileChooser();
		FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Text File", "*.TXT", "*.txt");
		selector.getExtensionFilters().add(filtro);

		File inicial = FileSystemView.getFileSystemView().getDefaultDirectory();
		if (inicial.exists()) {
			selector.setInitialDirectory(inicial);
		}

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
		if (tf1.getText().equals("") || tf2.getText().equals("")) {
			LOGGER.log(Level.WARNING, alerta.getContentText());
			alerta.showAndWait();
		} else if (tf1.getText().equals(tf2.getText())) {
			LOGGER.log(Level.WARNING, alerta2.getContentText());
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

					EscenaResultadoComparacion.setCSV(false);
					EscenaResultadoComparacion.setTabla(compararInformes(conexion1, conexion2));
					EscenaResultadoComparacion.enableNota(false);

					tf1.clear();
					tf2.clear();

					aplicacion.cambiaEscena(5);
				} else {
					alerta3.showAndWait();
				}
				contenido1.close();
				lee1.close();
				contenido2.close();
				lee2.close();
			} catch (IOException | NullPointerException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
				alerta3.showAndWait();
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
		String texto = "<html><head><style> table {border: 2px solid black; font-family: Sans-Serif; font-size: 16px; margin: 0 auto; text-align: center}"
				+ "table .titulo {background-color: #a3d3eb;  padding: 10px;}"
				+ "table td {background-color: #e9e9e9; padding: 10px;}" + "table .rojo {background-color: #ffa0a0;}"
				+ "table .verde {background-color: #b2e5b2;} </style></head> <body bgcolor='#e6f2ff'>";
		texto += "<table>";
		texto += "<tr>";
		String header = "<th style=\"background-color: #60b3dc; padding: 10px;\">";
		texto += header + "Métrica</th>";
		texto += header + conexion1.getNombreRepositorio() + "</th>";
		texto += header + conexion2.getNombreRepositorio() + "</th>";
		texto += "</tr>";

		texto += conexion1.comparar(conexion2);

		texto += "</table></body></html>";

		return texto;
	}

	/**
	 * Vuelve a cargar el archivo con el idioma y establece de nuevo los textos de
	 * la escena.
	 * 
	 * @param aplicacion aplicacion principal.
	 * @param id         id del idioma.
	 */
	public static void reloadIdioma(PrincipalFX aplicacion, int id) {
		String urlArchivo = null;

		if (id == 0) {
			urlArchivo = "/config/comparacion_es.config";
		} else {
			urlArchivo = "/config/comparacion_en.config";
		}

		InputStream is = EscenaAbout.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		for (int i = 0; i < valores.size(); i++) {
			valores.set(i, valores.get(i).replace("%%n", "\n"));
		}

		alerta = CreadorElementos.createAlertaError("", "", "");
		alerta2 = CreadorElementos.createAlertaError("", "", "");
		alerta3 = CreadorElementos.createAlertaError("", "", "");
		selecciona.setText(valores.get(0));
		selecciona1.setText(valores.get(1));
		selecciona2.setText(valores.get(2));
		archivo1.setPromptText(valores.get(3));
		archivo1.setTooltip(CreadorElementos.createTooltip(valores.get(4)));
		archivo2.setPromptText(valores.get(5));
		archivo2.setTooltip(CreadorElementos.createTooltip(valores.get(6)));
		s1B.setText(valores.get(7));
		s1B.setTooltip(CreadorElementos.createTooltip(valores.get(8)));
		s2B.setText(valores.get(7));
		s2B.setTooltip(CreadorElementos.createTooltip(valores.get(9)));
		comparar.setText(valores.get(10));
		comparar.setTooltip(CreadorElementos.createTooltip(valores.get(11)));
		atrasB.setText(valores.get(12));
		atrasB.setTooltip(CreadorElementos.createTooltip(valores.get(13)));
		alerta.setHeaderText(valores.get(14));
		alerta.setContentText(valores.get(15));
		alerta.setTitle(valores.get(16));
		alerta2.setHeaderText(valores.get(17));
		alerta2.setContentText(valores.get(18));
		alerta2.setTitle(valores.get(16));
		alerta3.setHeaderText(valores.get(19));
		alerta3.setContentText(valores.get(20));
		alerta3.setTitle(valores.get(16));
	}
}
