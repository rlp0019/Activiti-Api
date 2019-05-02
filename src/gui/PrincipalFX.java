package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JButton;
import javax.swing.filechooser.FileSystemView;

import gui.herramientas.CreadorElementos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;
import motormetricas.csv.ManagerCSV;

/**
 * Clase principal que ejecuta la interfaz gráfica.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class PrincipalFX extends Application {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(PrincipalFX.class.getName());

	/**
	 * Stage de la aplicación.
	 */
	private Stage ventana;

	/**
	 * Array de Scene de la aplicación.
	 */
	private Scene[] escenas;

	/**
	 * Fábrica de conexiones.
	 */
	private FabricaConexion fabConexion;

	/**
	 * Alerta de guardado correcto.
	 */
	private Alert alertaGuardado;

	/**
	 * Alerta de error de guardado.
	 */
	private Alert alertaEGuardar;

	/**
	 * Lector de métricas.
	 */
	private FachadaConexion lector;

	/**
	 * Manager de los datos del .csv.
	 */
	private ManagerCSV manager;

	/**
	 * Hilo de ejecución principal.
	 * 
	 * @param args nada.
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Método de inicio de la aplicación.
	 */
	@Override
	public void start(Stage pStage) throws Exception {
		fabConexion = FabricaConexionGitHub.getInstance();

		alertaGuardado = new Alert(AlertType.CONFIRMATION, "Guardado correctamente.", ButtonType.OK);
		alertaGuardado.setHeaderText("Confirmación de guardado correcto.");
		alertaGuardado.setTitle("Guardado satisfactorio");

		alertaEGuardar = CreadorElementos.createAlertaError("Error de guardado del informe.",
				"Ha ocurrido un error al guardar el informe.", "Error de guardado");

		ventana = pStage;
		ventana.setTitle("Activiti-Api");
		ventana.getIcons().add(new Image(getClass().getClassLoader().getResource("imagenes/Ubu.png").toExternalForm()));
		ventana.setWidth(1000);
		ventana.setHeight(700);
		ventana.setResizable(false);

		iniciaEscenas();

		ventana.setScene(escenas[0]);
		ventana.show();
	}

	/**
	 * Cambia la escena de la ventana.
	 * 
	 * @param id escena a la que cambiar.
	 */
	public void cambiaEscena(int id) {
		ventana.setScene(escenas[id]);
	}

	/**
	 * Inicialización de las escenas.
	 */
	private void iniciaEscenas() {
		escenas = new Scene[9];

		EscenaInicio eInicio = new EscenaInicio(this);
		escenas[0] = new Scene(eInicio);

		EscenaSelConex eSelCon = new EscenaSelConex(this);
		escenas[1] = new Scene(eSelCon);

		EscenaConex eConex = new EscenaConex(this);
		escenas[2] = new Scene(eConex);

		EscenaUsuarioRep eUsuario = new EscenaUsuarioRep(this);
		escenas[3] = new Scene(eUsuario);

		EscenaResultados eResultados = new EscenaResultados(this);
		escenas[4] = new Scene(eResultados);

		EscenaComparacion eComparacion = new EscenaComparacion(this);
		escenas[5] = new Scene(eComparacion);

		EscenaResultadoComparacion eResComp = new EscenaResultadoComparacion(this);
		escenas[6] = new Scene(eResComp);

		EscenaGraficos eGraficos = new EscenaGraficos(this);
		escenas[7] = new Scene(eGraficos);

		EscenaAbout eAbout = new EscenaAbout(this);
		escenas[8] = new Scene(eAbout);
	}

	/**
	 * Devuelve los repositorios cuyo usuario es el dado.
	 * 
	 * @param usuario usuario que contiene los repositorios.
	 * @return array de String con los nombres de los repositorios.
	 */
	public String[] buscaRepositorios(String usuario) {
		String[] repositorios = null;
		try {
			repositorios = lector.getNombresRepositorio(usuario);
		} catch (IOException e) {
			Alert alertaUsuario = CreadorElementos.createAlertaError("Datos erróneos.",
					"El usuario introducido no es correcto.", "Error de usuario");
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaUsuario.showAndWait();
		}
		return repositorios;
	}

	/**
	 * Calcula las métricas de un repositorio utilizando el lector.
	 * 
	 * @param usuario     usuario que contiene el repositorio.
	 * @param repositorio nombre del repositorio.
	 */
	public void calculaMetricasRepositorio(String usuario, String repositorio) {
		try {
			lector.obtenerMetricas(usuario, repositorio);
		} catch (IOException e) {
			Alert alertaRepositorio = CreadorElementos.createAlertaError("Datos erróneos.",
					"La combinación de usuario-repositorio introducida no es correcta.",
					"Error de cálculo de métricas");
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaRepositorio.showAndWait();
		}
	}

	/**
	 * Devuelve las métricas de un repositorio.
	 * 
	 * @return array de objetos que contiene las métricas y gráficos.
	 */
	public Object[] getMetricasRepositorio() {
		return lector.getResultados();
	}

	/**
	 * Crea una conexión sin usuario.
	 */
	public void createModoDesconectado() {
		lector = fabConexion.crearFachadaConexion();
	}

	/**
	 * Crea una conexión logueándose con usuario y contraseña.
	 * 
	 * @param usuario    usuario al que loguearse.
	 * @param contrasena contraseña de ese usuario.
	 * @return true si se ha logueado, false si ha ocurrido un error.
	 */
	public boolean createModoUsuario(String usuario, String contrasena) {
		boolean resultado = false;

		try {
			lector = fabConexion.crearFachadaConexion(usuario, contrasena);
			resultado = true;
		} catch (IOException e) {
			Alert alertaConexion = CreadorElementos.createAlertaError("Datos erróneos.",
					"Los datos de la conexión no son correctos.", "Error de conexión");
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaConexion.showAndWait();
		}

		return resultado;
	}

	/**
	 * Carga los resultados de métricas de un archivo.
	 */
	public void loadArchivo() {
		try {
			FileChooser selector = new FileChooser();
			ExtensionFilter filtro = new ExtensionFilter("*.txt", "txt");
			selector.setSelectedExtensionFilter(filtro);

			File inicial = FileSystemView.getFileSystemView().getDefaultDirectory();
			if (inicial.exists()) {
				selector.setInitialDirectory(inicial);
			}

			File archivo = selector.showOpenDialog(ventana);

			if (archivo != null && archivo.exists() && archivo.isFile()) {
				FileReader lee = new FileReader(archivo);
				BufferedReader contenido = new BufferedReader(lee);
				String linea = "";
				if ((linea = contenido.readLine()) != null && "GitHub".equals(linea)) {
					this.createModoDesconectado();
					lector.leerArchivo(contenido);
				}

				contenido.close();
				lee.close();

				EscenaResultados.setResultadoMetricas(this.getMetricasRepositorio());
				this.cambiaEscena(4);
			}
		} catch (IOException | NullPointerException e) {
			Alert alertaArchivo = CreadorElementos.createAlertaError("Error de apertura del archivo.",
					"El archivo seleccionado no se ha podido abrir correctamente.", "Error de apertura");
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaArchivo.showAndWait();
		}
	}

	/**
	 * Guardar los resultados de métricas en un archivo.
	 */
	public void saveArchivo() {
		try {
			FileChooser selector = new FileChooser();
			ExtensionFilter filtro = new ExtensionFilter("*.txt", "txt");
			selector.setSelectedExtensionFilter(filtro);
			File archivo = selector.showSaveDialog(ventana);

			if (archivo != null) {
				FileWriter save = new FileWriter(archivo);
				save.write(lector.generarArchivo());
				save.close();

				if (!(archivo.getAbsolutePath().endsWith(".txt"))) {
					File temp = new File(archivo.getAbsolutePath() + ".txt");
					archivo.renameTo(temp);
				}
				alertaGuardado.showAndWait();
			}
			/* } */
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaEGuardar.showAndWait();
		}
	}

	/**
	 * Devuelve la stage.
	 * 
	 * @return la stage.
	 */
	public Stage getVentana() {
		return ventana;
	}

	/**
	 * Devuelve el manager de .csv.
	 * 
	 * @return manager de .csv.
	 */
	public String creaTablaCSV() {
		manager = new ManagerCSV(Paths.get("rsc/datoscsv/DataSet_EvolutionSoftwareMetrics_FYP.csv"),
				lector.getResultados()[0]);

		return manager.creaTabla(lector.getNombreRepositorio());
	}

	/**
	 * Devuelve la nota del proyecto analizado.
	 * 
	 * @param estricto indica si la obtención de la nota toma en mayor o menor
	 *                 medida el valor de resultados intermedios.
	 * @return nota del proyecto.
	 */
	public double getNota(boolean estricto) {
		return manager.calculaNota(estricto);
	}

	/**
	 * Guarda los resultados en el .csv si no están guardados ya.
	 * 
	 * @return 0 si ha dado error, 1 si ha guardado correctamente, 2 si el archivo
	 *         ya se encuentra en la base de datos.
	 */
	public boolean guardarEnCSV() {
		boolean resultado = manager.addMetricasProyecto(lector.getNombreRepositorio());

		if (resultado) {
			alertaGuardado.showAndWait();
		} else {
			LOGGER.log(Level.SEVERE, alertaEGuardar.getContentText());
			alertaEGuardar.showAndWait();
		}

		return resultado;
	}

	public void cargarAyuda(JButton boton) {
		Alert alertaArchivo = CreadorElementos.createAlertaError("Error de apertura de la ayuda.",
				"La ayuda no se ha podido abrir correctamente.", "Error de apertura");

		try {
			URL hsURL = getClass().getResource("/gui/ayuda/ayuda.hs");
			if (hsURL == null) {
				alertaArchivo.showAndWait();
			}
			HelpSet helpset = new HelpSet(null, hsURL);
			HelpBroker helpbroker = helpset.createHelpBroker();
			helpbroker.enableHelpOnButton(boton, "Principal", helpset);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaArchivo.showAndWait();
		}
	}
}
