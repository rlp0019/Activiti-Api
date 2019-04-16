package gui.fx;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;

public class PrincipalFX extends Application {

	private Stage ventana;
	private Scene[] escenas;
	private FabricaConexion fabConexion;

	private Alert alert;
	private Alert alert2;
	private Alert alert3;

	private FachadaConexion lector;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage pStage) throws Exception {
		fabConexion = FabricaConexionGitHub.getInstance();

		alert = new Alert(AlertType.NONE, "Los datos de la conexión no son correctos.", ButtonType.CLOSE);
		alert.setTitle("Error de conexión");

		alert2 = new Alert(AlertType.NONE, "El usuario introducido no es correcto.", ButtonType.CLOSE);
		alert2.setTitle("Error de usuario");

		alert3 = new Alert(AlertType.NONE, "La combinación de usuario-repositorio introducida no es correcta.",
				ButtonType.CLOSE);
		alert3.setTitle("Error de cálculo de métricas");

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

	public void cambiaEscena(int id) {
		ventana.setScene(escenas[id]);
	}

	private void iniciaEscenas() {
		escenas = new Scene[6];

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
	}

	public String[] buscaRepositorios(String usuario) {
		String[] repositorios = null;
		try {
			repositorios = lector.getNombresRepositorio(usuario);
		} catch (IOException e) {
			alert2.showAndWait();
			e.printStackTrace();
		}
		return repositorios;
	}

	public void calculaMetricasRepositorio(String usuario, String repositorio) {
		try {
			lector.obtenerMetricas(usuario, repositorio);
		} catch (IOException e) {
			alert3.showAndWait();
			e.printStackTrace();
		}
	}

	public Object[] getMetricasRepositorio() {
		return lector.getResultados();
	}

	public void createModoDesconectado() {
		lector = fabConexion.crearFachadaConexion();
	}

	public boolean createModoUsuario(String usuario, String contrasena) {
		boolean resultado = false;

		try {
			lector = fabConexion.crearFachadaConexion(usuario, contrasena);
			resultado = true;
		} catch (IOException e) {
			alert.showAndWait();
			e.printStackTrace();
		}

		return resultado;
	}
}
