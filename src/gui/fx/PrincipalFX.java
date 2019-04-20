package gui.fx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	 * Alerta de opción de sobreescripción.
	 */
	private Alert alertaSobreescribir;

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

		alertaConexion = new Alert(AlertType.NONE, "Los datos de la conexión no son correctos.", ButtonType.CLOSE);
		alertaConexion.setTitle("Error de conexión");

		alertaUsuario = new Alert(AlertType.NONE, "El usuario introducido no es correcto.", ButtonType.CLOSE);
		alertaUsuario.setTitle("Error de usuario");

		alertaRepositorio = new Alert(AlertType.NONE,
				"La combinación de usuario-repositorio introducida no es correcta.", ButtonType.CLOSE);
		alertaRepositorio.setTitle("Error de cálculo de métricas");

		alertaArchivo = new Alert(AlertType.NONE, "El archivo seleccionado no se ha podido abrir correctamente.",
				ButtonType.CLOSE);
		alertaArchivo.setTitle("Error al abrir el archivo");

		alertaSobreescribir = new Alert(AlertType.NONE, "¿Quiére sobreescribir el archivo?", ButtonType.YES,
				ButtonType.NO);
		alertaSobreescribir.setTitle("Sobreescribir el archivo");

		alertaGuardado = new Alert(AlertType.NONE, "Informe guardado correctamente.", ButtonType.OK);
		alertaGuardado.setTitle("Archivo guardado");

		alertaEGuardar = new Alert(AlertType.NONE, "Error al guardar el informe.", ButtonType.CLOSE);
		alertaEGuardar.setTitle("Error de guardado");

		ventana = pStage;
		ventana.setTitle("Activiti-Api");
		ventana.getIcons().add(new Image(new FileInputStream("rsc/imagenes/Ubu.png")));
		ventana.setWidth(720);
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
		escenas = new Scene[8];

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

				EscenaResultados.setResultadoMetricas((String) lector.getResultados()[0]);
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
			boolean respuesta = false;

			if (archivo.exists() && archivo.isFile()) {
				alertaSobreescribir.showAndWait();

				if (alertaSobreescribir.getResult() == ButtonType.YES) {
					respuesta = true;
				}
			} else {
				respuesta = true;
			}

			if (respuesta) {
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
}
