package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * Escena de búsqueda de repositorio.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaUsuarioRep extends StackPane {

	/**
	 * Usuario que contiene los repositorios.
	 */
	private String nombreUsuario;

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaUsuarioRep(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		ComboBox<String> desplegableRepo = new ComboBox<String>();
		desplegableRepo.setTranslateX(-30);
		desplegableRepo.setTranslateY(100);
		desplegableRepo.setMinWidth(250);
		desplegableRepo.setMinHeight(40);

		Alert alert = new Alert(AlertType.NONE, "El campo usuario no puede estar vacío.", ButtonType.CLOSE);
		alert.setTitle("Error");

		Alert alert2 = new Alert(AlertType.NONE, "La selección de repositorio no puede estar vacía.", ButtonType.CLOSE);
		alert.setTitle("Error");

		Label usuarioRep = CreadorElementos.createLabel("Seleccionar repositorio:", 32, "#0076a3", 0, -175);

		Label usuario = CreadorElementos.createLabel("Introduce el nombre del usuario a buscar:", 20, "#505050", 0,
				-100);

		Label repositorio = CreadorElementos.createLabel("Selecciona un repositorio tras introducir el usuario:", 20,
				"#505050", 0, 40);

		TextField tfUsuario = CreadorElementos.createTextField("Nombre del usuario.", 20,
				"Introduce el nombre del usuario a buscar.", -30, -30, 250);

		Button repoB = CreadorElementos.createButton("Analizar", 20, "Selecciona el repositorio.", 150, 100, 100);
		repoB.setOnAction(e -> {
			if (desplegableRepo.getSelectionModel().isEmpty()) {
				alert2.showAndWait();
			} else {
				aplicacion.calculaMetricasRepositorio(nombreUsuario, desplegableRepo.getValue());
				EscenaResultados.setResultadoMetricas((String) aplicacion.getMetricasRepositorio()[0]);
				aplicacion.cambiaEscena(5);
			}
		});
		repoB.setDisable(true);

		Button buscarB = CreadorElementos.createButton("Buscar", 20, "Busca los repositorios del usuario introducido.",
				150, -30, 100);
		buscarB.setOnAction(e -> {
			if (tfUsuario.getText().isEmpty()) {
				alert.showAndWait();
			} else {
				String[] repositorios = aplicacion.buscaRepositorios(tfUsuario.getText());
				if (repositorios != null) {
					nombreUsuario = tfUsuario.getText();
					tfUsuario.clear();
					desplegableRepo.getItems().addAll(repositorios);
					repoB.setDisable(false);
				}
			}
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> {
			tfUsuario.clear();
			aplicacion.cambiaEscena(2);
		});

		this.getChildren().addAll(usuarioRep, usuario, repositorio, tfUsuario, buscarB, desplegableRepo, repoB, atrasB);
	}
}
