package gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(EscenaUsuarioRep.class.getName());

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
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		ComboBox<String> desplegableRepo = new ComboBox<String>();
		desplegableRepo.setTranslateX(-50);
		desplegableRepo.setTranslateY(100);
		desplegableRepo.setMinWidth(250);
		desplegableRepo.setMinHeight(40);

		Alert alert = CreadorElementos.createAlertaError("Campo vacío.", "El campo usuario no puede estar vacío.",
				"Error de datos.");

		Alert alert2 = CreadorElementos.createAlertaError("Campo vacío.",
				"La selección de repositorio no puede estar vacía.", "Error de datos.");

		Label usuarioRep = CreadorElementos.createLabel("Seleccionar repositorio:", 32, "#0076a3", 0, 30);

		Label usuario = CreadorElementos.createLabel("Introduce el nombre del usuario a buscar:", 20, "#505050", 0,
				-100);

		Label repositorio = CreadorElementos.createLabel("Selecciona un repositorio tras introducir el usuario:", 20,
				"#505050", 0, 40);

		TextField tfUsuario = CreadorElementos.createTextField("Nombre del usuario.", 20,
				"Introduce el nombre del usuario a buscar.", -50, -30, 250);

		Button repoB = CreadorElementos.createButton("Siguiente", 20, "Selecciona el repositorio.", -5, -5, 100);
		repoB.setOnAction(e -> {
			if (desplegableRepo.getSelectionModel().isEmpty()) {
				LOGGER.log(Level.SEVERE, alert2.getContentText());
				alert2.showAndWait();
			} else {
				aplicacion.calculaMetricasRepositorio(nombreUsuario, desplegableRepo.getValue());
				EscenaResultados.setResultadoMetricas(aplicacion.getMetricasRepositorio(), false);
				EscenaResultados.loadGraficos(aplicacion);
				desplegableRepo.getItems().clear();
				aplicacion.cambiaEscena(3);
			}
		});
		repoB.setDisable(true);

		Button buscarB = CreadorElementos.createButton("Buscar", 20, "Busca los repositorios del usuario introducido.",
				175, -30, 100);
		buscarB.setOnAction(e -> {
			if (tfUsuario.getText().isEmpty()) {
				LOGGER.log(Level.SEVERE, alert.getContentText());
				alert.showAndWait();
			} else {
				String[] repositorios = aplicacion.buscaRepositorios(tfUsuario.getText());
				if (repositorios != null) {
					nombreUsuario = tfUsuario.getText();
					tfUsuario.clear();
					desplegableRepo.getItems().clear();
					desplegableRepo.getItems().addAll(repositorios);
					repoB.setDisable(false);
				}
			}
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> {
			tfUsuario.clear();
			desplegableRepo.getItems().clear();
			repoB.setDisable(true);

			if (aplicacion.isLectorNull()) {
				aplicacion.cambiaEscena(1);
			} else {
				aplicacion.cambiaEscena(0);
			}
		});

		this.getChildren().addAll(usuarioRep, usuario, repositorio, tfUsuario, buscarB, desplegableRepo, repoB, atrasB);
		EscenaUsuarioRep.setAlignment(usuarioRep, Pos.TOP_CENTER);
		EscenaUsuarioRep.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaUsuarioRep.setAlignment(repoB, Pos.BOTTOM_RIGHT);
	}
}
