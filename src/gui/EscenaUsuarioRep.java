package gui;

import java.io.InputStream;
import java.util.ArrayList;
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
	 * Alerta de campo usuario vacío.
	 */
	private static Alert alert = CreadorElementos.createAlertaError("", "", "");

	/**
	 * Alerta de repositorio vacío.
	 */
	private static Alert alert2 = CreadorElementos.createAlertaError("", "", "");

	/**
	 * Label con el título.
	 */
	private static Label usuarioRep = CreadorElementos.createLabel("", 32, "#0076a3", 0, 30);

	/**
	 * Label con la selección de usuario.
	 */
	private static Label usuario = CreadorElementos.createLabel("", 20, "#505050", 0, -100);

	/**
	 * Label con la selección de repositorio.
	 */
	private static Label repositorio = CreadorElementos.createLabel("", 20, "#505050", 0, 40);

	/**
	 * Labl con el título de repositorios.
	 */
	private static Label repos = CreadorElementos.createLabel("", 16, "#505050", -50, 90);

	/**
	 * Label con el titulo de forks.
	 */
	private static Label fork = CreadorElementos.createLabel("", 16, "#505050", -50, 180);

	/**
	 * Textfield con el nombre de usuario.
	 */
	private static TextField tfUsuario = CreadorElementos.createTextField("", 20, "", -50, -30, 250);

	/**
	 * Botón para buscar el repositorio.
	 */
	private static Button repoB = CreadorElementos.createButton("", 20, "", -5, -5, 100);

	/**
	 * Botón para eliminar la selección del repositorio.
	 */
	private static Button borraRepo = CreadorElementos.createButton("", 1, "", 100, 135, -1);

	/**
	 * Botón para buscar los repositorios del usuario.
	 */
	private static Button buscarB = CreadorElementos.createButton("", 20, "", 175, -30, 100);

	/**
	 * Botón para eliminar la selección del fork.
	 */
	private static Button borraForks = CreadorElementos.createButton("", 1, "", 100, 225, -1);

	/**
	 * Botón para volver a la escena anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("", 16, "", 5, -5, 100);

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

		borraRepo.setOnAction(e -> desplegableRepo.getSelectionModel().clearSelection());
		borraRepo.setGraphic(imagenV);
		borraRepo.setMaxSize(32, 32);

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

		borraForks.setOnAction(e -> desplegableForks.getSelectionModel().clearSelection());
		borraForks.setGraphic(imagenV2);
		borraForks.setMaxSize(32, 32);

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

		reloadIdioma(aplicacion, 0);

		this.getChildren().addAll(usuarioRep, usuario, repos, fork, repositorio, tfUsuario, buscarB, desplegableRepo,
				desplegableForks, repoB, borraRepo, borraForks, conex, atrasB);
		EscenaUsuarioRep.setAlignment(usuarioRep, Pos.TOP_CENTER);
		EscenaUsuarioRep.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaUsuarioRep.setAlignment(repoB, Pos.BOTTOM_RIGHT);
		EscenaUsuarioRep.setAlignment(conex, Pos.TOP_RIGHT);
	}

	/**
	 * Carga el tipo de conexión
	 * 
	 * @param valor texto a mostrar.
	 */
	public static void loadNombreConex(String valor) {
		conex.setText(valor);
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
			urlArchivo = "/config/repositorio_es.config";
		} else {
			urlArchivo = "/config/repositorio_en.config";
		}

		InputStream is = EscenaAbout.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		for (int i = 0; i < valores.size(); i++) {
			valores.set(i, valores.get(i).replace("%%n", "\n"));
		}

		alert = CreadorElementos.createAlertaError("", "", "");
		alert2 = CreadorElementos.createAlertaError("", "", "");
		alert.setHeaderText(valores.get(0));
		alert.setContentText(valores.get(1));
		alert.setTitle(valores.get(2));
		alert2.setHeaderText(valores.get(0));
		alert2.setContentText(valores.get(3));
		alert2.setTitle(valores.get(2));
		usuarioRep.setText(valores.get(4));
		usuario.setText(valores.get(5));
		repositorio.setText(valores.get(6));
		repos.setText(valores.get(7));
		fork.setText(valores.get(8));
		tfUsuario.setPromptText(valores.get(9));
		tfUsuario.setTooltip(CreadorElementos.createTooltip(valores.get(10)));
		repoB.setText(valores.get(11));
		repoB.setTooltip(CreadorElementos.createTooltip(valores.get(12)));
		borraRepo.setTooltip(CreadorElementos.createTooltip(valores.get(13)));
		buscarB.setText(valores.get(14));
		buscarB.setTooltip(CreadorElementos.createTooltip(valores.get(15)));
		borraForks.setTooltip(CreadorElementos.createTooltip(valores.get(16)));
		atrasB.setText(valores.get(17));
		atrasB.setTooltip(CreadorElementos.createTooltip(valores.get(18)));
	}
}
