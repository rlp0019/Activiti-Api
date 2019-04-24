package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

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
	 * Alerta de error de conexión.
	 */
	private Alert alertaConexion;

	/**
	 * Alerta de error de usuario.
	 */
	private Alert alertaUsuario;

	/**
	 * Alerta de error de combinación usuario/repositorio.
	 */
	private Alert alertaRepositorio;

	/**
	 * Alerta de error de apertura de archivo.
	 */
	private Alert alertaArchivo;

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

		alertaConexion = CreadorElementos.createAlertaError("Datos erróneos.",
				"Los datos de la conexión no son correctos.", "Error de conexión");

		alertaUsuario = CreadorElementos.createAlertaError("Datos erróneos.", "El usuario introducido no es correcto.",
				"Error de usuario");

		alertaRepositorio = CreadorElementos.createAlertaError("Datos erróneos.",
				"La combinación de usuario-repositorio introducida no es correcta.", "Error de cálculo de métricas");

		alertaArchivo = CreadorElementos.createAlertaError("Error de apertura del archivo.",
				"El archivo seleccionado no se ha podido abrir correctamente.", "Error de apertura");

		alertaGuardado = new Alert(AlertType.CONFIRMATION, "Guardado correctamente.", ButtonType.OK);
		alertaGuardado.setHeaderText("Confirmación de guardado correcto.");
		alertaGuardado.setTitle("Guardado satisfactorio");

		alertaEGuardar = CreadorElementos.createAlertaError("Error de guardado del informe.",
				"Ha ocurrido un error al guardar el informe.", "Error de guardado");

		ventana = pStage;
		ventana.setTitle("Activiti-Api");
		ventana.getIcons().add(new Image(getClass().getClassLoader().getResource("imagenes/Ubu.png").toExternalForm()));
		ventana.setWidth(750);
		ventana.setHeight(530);
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

		EscenaSelPlataforma eSelPlat = new EscenaSelPlataforma(this);
		escenas[1] = new Scene(eSelPlat);

		EscenaSelConex eSelCon = new EscenaSelConex(this);
		escenas[2] = new Scene(eSelCon);

		EscenaConex eConex = new EscenaConex(this);
		escenas[3] = new Scene(eConex);

		EscenaUsuarioRep eUsuario = new EscenaUsuarioRep(this);
		escenas[4] = new Scene(eUsuario);

		EscenaResultados eResultados = new EscenaResultados(this);
		escenas[5] = new Scene(eResultados);

		EscenaComparacion eComparacion = new EscenaComparacion(this);
		escenas[6] = new Scene(eComparacion);

		EscenaResultadoComparacion eResComp = new EscenaResultadoComparacion(this);
		escenas[7] = new Scene(eResComp);

		EscenaGraficos eGraficos = new EscenaGraficos(this);
		escenas[8] = new Scene(eGraficos);
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
			alertaUsuario.showAndWait();
			e.printStackTrace();
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
			alertaRepositorio.showAndWait();
			e.printStackTrace();
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
			alertaConexion.showAndWait();
			e.printStackTrace();
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
				this.cambiaEscena(5);
			}
		} catch (IOException | NullPointerException e) {
			alertaArchivo.showAndWait();
			e.printStackTrace();
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
			alertaEGuardar.showAndWait();
			e.printStackTrace();
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
			alertaEGuardar.showAndWait();
		}

		return resultado;
	}
}
