package gui.fx;

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
	private FachadaConexion conexion;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage pStage) throws Exception {
		fabConexion = FabricaConexionGitHub.getInstance();

		ventana = pStage;
		ventana.setTitle("Activiti-Api");
		ventana.getIcons().add(new Image("/imagenes/Ubu.png"));
		ventana.setWidth(720);
		ventana.setHeight(530);

		iniciaEscenas();

		ventana.setScene(escenas[0]);
		ventana.show();
	}

	public void cambiaEscena(int id) {
		ventana.setScene(escenas[id]);
	}

	private void iniciaEscenas() {
		escenas = new Scene[4];

		EscenaInicio eInicio = new EscenaInicio(this);
		escenas[0] = new Scene(eInicio);

		EscenaSelPlataforma eSelPlat = new EscenaSelPlataforma(this);
		escenas[1] = new Scene(eSelPlat);

		EscenaSelConex eSelCon = new EscenaSelConex(this);
		escenas[2] = new Scene(eSelCon);

		EscenaConex eConex = new EscenaConex(this);
		escenas[3] = new Scene(eConex);
	}

	public void createModoDesconectado() {
		conexion = fabConexion.crearFachadaConexion();
	}

	public void createModoUsuario(String usuario, String contrasena) {
		try {
			conexion = fabConexion.crearFachadaConexion(usuario, contrasena);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.NONE, "Ha ocurrido un error durante la conexión.", ButtonType.CLOSE);
			alert.setTitle("Error");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
}
