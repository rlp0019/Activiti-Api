package gui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	 * Label con el nombre de la conexión actual.
	 */
	private static Label conex = CreadorElementos.createLabel("", 16, "#0076a3", -5, 5);

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
		desplegableRepo.setTranslateY(135);
		desplegableRepo.setMinWidth(200);
		desplegableRepo.setMinHeight(40);

		ComboBox<String> desplegableForks = new ComboBox<String>();
		desplegableForks.setTranslateX(-50);
		desplegableForks.setTranslateY(225);
		desplegableForks.setMinWidth(200);
		desplegableForks.setMinHeight(40);

		Alert alert = CreadorElementos.createAlertaError("Campo vacío.", "El campo usuario no puede estar vacío.",
				"Error de datos.");

		Alert alert2 = CreadorElementos.createAlertaError("Campo vacío.",
				"La selección de repositorio no puede estar vacía.", "Error de datos.");

		Label usuarioRep = CreadorElementos.createLabel("Seleccionar repositorio", 32, "#0076a3", 0, 30);

		Label usuario = CreadorElementos.createLabel("Introduce el nombre del usuario a buscar", 20, "#505050", 0,
				-100);

		Label repositorio = CreadorElementos.createLabel(
				"Selecciona un repositorio o alguno de sus forks tras introducir el usuario", 20, "#505050", 0, 40);

		Label repos = CreadorElementos.createLabel("Repositorios", 16, "#505050", -50, 90);

		Label fork = CreadorElementos.createLabel("Forks", 16, "#505050", -50, 180);

		TextField tfUsuario = CreadorElementos.createTextField("Nombre del usuario", 20,
				"Introduce el nombre del usuario a buscar.", -50, -30, 250);

		Button repoB = CreadorElementos.createButton("Siguiente", 20, "Selecciona el repositorio.", -5, -5, 100);
		repoB.setOnAction(e -> {
			if (desplegableRepo.getSelectionModel().isEmpty() && desplegableForks.getSelectionModel().isEmpty()) {
				LOGGER.log(Level.SEVERE, alert2.getContentText());
				alert2.showAndWait();
			} else {
				if (!desplegableRepo.getSelectionModel().isEmpty()) {
					aplicacion.calculaMetricasRepositorio(nombreUsuario, desplegableRepo.getValue());
				} else {
					aplicacion.calculaMetricasRepositorio(nombreUsuario, desplegableForks.getValue());
				}
				EscenaResultados.setResultadoMetricas(aplicacion.getMetricasRepositorio(), false);
				EscenaResultados.loadGraficos(aplicacion);
				desplegableRepo.getItems().clear();
				desplegableForks.getItems().clear();
				aplicacion.cambiaEscena(3);
			}
		});
		repoB.setDisable(true);

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/Clear.png").toExternalForm());
		ImageView imagenV = new ImageView(imagen);
		imagenV.setFitWidth(32);
		imagenV.setFitHeight(32);

		ImageView imagenV2 = new ImageView(imagen);
		imagenV2.setFitWidth(32);
		imagenV2.setFitHeight(32);

		Button borraRepo = CreadorElementos.createButton("", 1, "Elimina la selección de repositorio.", 100, 135, -1);
		borraRepo.setOnAction(e -> desplegableRepo.getSelectionModel().clearSelection());
		borraRepo.setGraphic(imagenV);
		borraRepo.setMaxSize(32, 32);

		Button buscarB = CreadorElementos.createButton("Buscar", 20, "Busca los repositorios del usuario introducido.",
				175, -30, 100);
		buscarB.setOnAction(e -> {
			if (tfUsuario.getText().isEmpty()) {
				LOGGER.log(Level.SEVERE, alert.getContentText());
				alert.showAndWait();
			} else {
				List<String> repositorios = aplicacion.buscaRepositorios(tfUsuario.getText());
				if (repositorios != null) {
					nombreUsuario = tfUsuario.getText();
					tfUsuario.clear();
					desplegableRepo.getItems().clear();
					desplegableRepo.getItems().addAll(repositorios);
					repoB.setDisable(false);
				}

				List<String> forks = aplicacion.buscaForks(nombreUsuario);
				if (forks != null) {
					desplegableForks.getItems().clear();
					desplegableForks.getItems().addAll(forks);
				}
			}
		});

		Button borraForks = CreadorElementos.createButton("", 1, "Elimina la selección de repositorio.", 100, 225, -1);
		borraForks.setOnAction(e -> desplegableForks.getSelectionModel().clearSelection());
		borraForks.setGraphic(imagenV2);
		borraForks.setMaxSize(32, 32);

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> {
			tfUsuario.clear();
			desplegableRepo.getItems().clear();
			desplegableForks.getItems().clear();
			repoB.setDisable(true);

			if (aplicacion.isLectorNull()) {
				aplicacion.cambiaEscena(1);
			} else {
				aplicacion.cambiaEscena(0);
			}
		});

		this.getChildren().addAll(usuarioRep, usuario, repos, fork, repositorio, tfUsuario, buscarB, desplegableRepo,
				desplegableForks, repoB, borraRepo, borraForks, conex, atrasB);
		EscenaUsuarioRep.setAlignment(usuarioRep, Pos.TOP_CENTER);
		EscenaUsuarioRep.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaUsuarioRep.setAlignment(repoB, Pos.BOTTOM_RIGHT);
		EscenaUsuarioRep.setAlignment(conex, Pos.TOP_RIGHT);
	}

	public static void loadNombreConex(String valor) {
		conex.setText(valor);
	}
}
