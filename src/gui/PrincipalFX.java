package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
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
import javafx.stage.Stage;
import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;
import motormetricas.csv.ManagerCSV;
import motormetricas.csv.SeparadorMetricas;

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
	 * Alerta de apertura de archivo.
	 */
	private Alert alertaArchivo;

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

		alertaArchivo = CreadorElementos.createAlertaError("Error de apertura del archivo.",
				"El archivo seleccionado no se ha podido abrir correctamente.", "Error de apertura");

		ventana = pStage;
		ventana.setTitle("Evalua y compara la actividad del repositorio en GitHub");
		ventana.getIcons().add(new Image(getClass().getClassLoader().getResource("imagenes/Ubu.png").toExternalForm()));
		ventana.setMinWidth(1000);
		ventana.setMinHeight(700);

		iniciaEscenas();

		ventana.setScene(escenas[0]);

		ventana.heightProperty().addListener((obs, valorAntiguo, nuevoValor) -> {
			EscenaResultados.setAlturaMetricas(nuevoValor.doubleValue());
		});

		ventana.show();
	}

	/**
	 * Cambia la escena de la ventana.
	 * 
	 * @param id escena a la que cambiar.
	 */
	public void cambiaEscena(int id) {
		double w = ventana.getWidth();
		double h = ventana.getHeight();

		ventana.setScene(escenas[id]);

		ventana.setWidth(w);
		ventana.setHeight(h);
	}

	/**
	 * Inicialización de las escenas.
	 */
	private void iniciaEscenas() {
		escenas = new Scene[7];

		EscenaInicio eInicio = new EscenaInicio(this);
		escenas[0] = new Scene(eInicio);

		EscenaSelConex eSelCon = new EscenaSelConex(this);
		escenas[1] = new Scene(eSelCon);

		EscenaUsuarioRep eUsuario = new EscenaUsuarioRep(this);
		escenas[2] = new Scene(eUsuario);

		EscenaResultados eResultados = new EscenaResultados(this);
		escenas[3] = new Scene(eResultados);

		EscenaComparacion eComparacion = new EscenaComparacion(this);
		escenas[4] = new Scene(eComparacion);

		EscenaResultadoComparacion eResComp = new EscenaResultadoComparacion(this);
		escenas[5] = new Scene(eResComp);

		EscenaAbout eAbout = new EscenaAbout(this);
		escenas[6] = new Scene(eAbout);
	}

	/**
	 * Devuelve los repositorios cuyo usuario es el dado.
	 * 
	 * @param usuario usuario que contiene los repositorios.
	 * @return array de String con los nombres de los repositorios.
	 */
	public List<String> buscaRepositorios(String usuario) {
		List<String> repositorios = null;
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

	public List<String> buscaForks(String usuario) {
		List<String> forks = null;
		try {
			forks = lector.getNombresForks(usuario);
		} catch (IOException e) {
			Alert alertaUsuario = CreadorElementos.createAlertaError("Datos erróneos.",
					"El usuario introducido no es correcto.", "Error de usuario");
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaUsuario.showAndWait();
		}

		return forks;
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
	 * Devuelve las métricas de un repositorio en html.
	 * 
	 * @return array de objetos que contiene las métricas y gráficos.
	 */
	public Object[] getMetricasRepositorio() {
		Object[] metricas = lector.getResultados();
		metricas[0] = getMetricasRepositorioHtml(metricas);

		return metricas;
	}

	/**
	 * Convierte las métricas de un repositorio en formato String a html.
	 * 
	 * @return array de objetos que contiene las métricas y gráficos.
	 */
	private String getMetricasRepositorioHtml(Object[] metricas) {
		SeparadorMetricas sm = new SeparadorMetricas(metricas[0]);

		String metricasHtml = "<html>";
		metricasHtml += "<body bgcolor=\"#e6f2ff\"; font-family: Sans-Serif;>";
		metricasHtml += "<h1 style=\"color: #0076a3\">Métricas</h1>";
		String espacio = "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;";
		metricasHtml += this.fillParrafo("NumeroIssues:<br>" + espacio + sm.getTotalIssues(), false);
		metricasHtml += this.fillParrafo("ContadorTareas:<br>" + espacio + sm.getContadorCambios(), true);
		metricasHtml += this.fillParrafo("NumeroIssuesCerradas:<br>" + espacio + sm.getIssuesCerradas(), false);
		metricasHtml += this.fillParrafo("PorcentajeIssuesCerradas:<br>" + espacio + sm.getPorcentajeCerrados(), true);
		metricasHtml += this.fillParrafo("MediaDiasCierre:<br>" + espacio + sm.getMediaDiasCierre(), false);
		metricasHtml += this.fillParrafo("NumeroCambiosSinMensaje:<br>" + espacio + sm.getNumeroCambiosSinMensaje(),
				true);
		metricasHtml += this.fillParrafo("MediaDiasCambio:<br>" + espacio + sm.getMediaDiasEntreCommit(), false);
		metricasHtml += this.fillParrafo("DiasPrimerUltimoCommit:<br>" + espacio + sm.getTotalDias(), true);
		metricasHtml += this.fillParrafo("UltimaModificacion:<br>" + espacio + sm.getUltimaModificacion(), false);
		metricasHtml += this.fillParrafo("CommitPorMes:<br>" + espacio + sm.getCommitPorMes(), true);
		metricasHtml += this.fillParrafo("RelacionMesPico:<br>" + espacio + sm.getRelacionMesPico(), false);
		metricasHtml += this.fillParrafo("ContadorCambiosPico:<br>" + espacio + sm.getCambioPico(), true);
		metricasHtml += this.fillParrafo("RatioActividadCambio:<br>" + espacio + sm.getActividadCambio(), false);
		metricasHtml += this.fillParrafo("CommitPorDia:<br>" + espacio + sm.getCommitPorDia(), true);
		metricasHtml += this.fillParrafo("CambioPorAutor:<br>" + espacio + sm.getCambioPorAutor(), false);
		metricasHtml += this.fillParrafo("ContadorAutor:<br>" + espacio + sm.getContadorAutor(), true);
		metricasHtml += this.fillParrafo("IssuesPorAutor:<br>" + espacio + sm.getIssuesPorAutor(), false);
		metricasHtml += this.fillParrafo("NumeroFavoritos:<br>" + espacio + sm.getNumeroFavoritos(), true);

		metricasHtml += "</body></html>";

		return metricasHtml;
	}

	private String fillParrafo(String cadena, boolean bg) {
		String resultado = "";

		if (bg) {
			resultado += "<p style=\"background-color: #e9e9e9\">";
		} else {
			resultado += "<p style=\"background-color: #a3d3eb\">";
		}

		resultado += cadena + "</p>";

		return resultado;
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
			File archivo = openArchivo("Text Files", "*.TXT", "*.txt", true);

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

				EscenaResultados.setResultadoMetricas(this.getMetricasRepositorio(), true);
				EscenaResultados.loadGraficos(this);
				this.cambiaEscena(3);
			}
		} catch (IOException | NullPointerException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaArchivo.showAndWait();
		}
	}

	/**
	 * Guardar los resultados de métricas en un archivo.
	 */
	public void saveArchivo() {
		try {
			File archivo = openArchivo("Text Files", "*.TXT", "*.txt", false);

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

	public void startManager() {
		File archivo = openArchivo("CSV Files", "*.CSV", "*.csv", true);

		if (archivo != null) {
			manager = new ManagerCSV(Paths.get(archivo.getAbsolutePath()), lector.getResultados()[0]);
		}
	}

	/**
	 * Devuelve el manager de .csv.
	 * 
	 * @return manager de .csv.
	 */
	public String creaTablaCSV() {
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

	/**
	 * Carga la ayuda en una nueva ventana.
	 * 
	 * @param boton botón que muestra la ayuda.
	 */
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

	/**
	 * Devuelve si el lector se ha iniciado o no.
	 * 
	 * @return true si no se ha iniciado o false de lo contrario.
	 */
	public boolean isLectorNull() {
		boolean resultado = false;

		if (lector == null) {
			resultado = true;
		}

		return resultado;
	}

	/**
	 * Elimina la conexión del lector y cambia la pantalla de inicio.
	 */
	public void disconnect() {
		lector = null;
		this.cambiaEscena(0);
	}

	/**
	 * Inicia un FileChooser con el filtro pasado.
	 * 
	 * @param descr  descripción de la extensión.
	 * @param extM   extensión en mayúsculas.
	 * @param ext    extensión en minúsculas.
	 * @param cargar opción para guardar o cargar.
	 * @return archivo.
	 */
	private File openArchivo(String descr, String extM, String ext, boolean cargar) {
		File archivo = null;

		try {
			FileChooser selector = new FileChooser();
			FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter(descr, extM, ext);
			selector.getExtensionFilters().add(filtro);

			if (cargar) {
				File inicial = FileSystemView.getFileSystemView().getDefaultDirectory();
				if (inicial.exists()) {
					selector.setInitialDirectory(inicial);
				}

				archivo = selector.showOpenDialog(ventana);
			} else {
				archivo = selector.showSaveDialog(ventana);
			}
		} catch (NullPointerException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			alertaArchivo.showAndWait();
		}

		return archivo;
	}
}
