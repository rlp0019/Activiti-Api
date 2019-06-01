package gui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * Escena que contiene el método de conexión, mediante un log in o como
 * desconectado.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaSelConex extends StackPane {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(EscenaSelConex.class.getName());

	/**
	 * Label con el título.
	 */
	private static Label selecciona = CreadorElementos.createLabel("", 32, "#0076a3", 0, 30);

	/**
	 * Alerta información del modo desconectado.
	 */
	private static Alert alerta = new Alert(AlertType.WARNING, "", ButtonType.NO, ButtonType.YES);

	private static Alert alerta2 = CreadorElementos.createAlertaError("", "", "");

	/**
	 * RadioButton de conexión modo usuario.
	 */
	private static RadioButton radioUsuario = new RadioButton("");

	/**
	 * RadioButton de conexión modo desconectado.
	 */
	private static RadioButton radioDesconectado = new RadioButton("");

	/**
	 * Label de introducir usuario.
	 */
	private static Label usuario = CreadorElementos.createLabel("", 20, "#505050", 0, -110);

	/**
	 * Label de introducir contraseña.
	 */
	private static Label contra = CreadorElementos.createLabel("", 20, "#505050", 0, -10);

	/**
	 * TextField para introducir el nombre de usuario.
	 */
	private static TextField tfUsuario = CreadorElementos.createTextField("", 20, "", 0, -60, 250);

	/**
	 * PasswordField para introducir la contraseña asociada al usuario.
	 */
	private static PasswordField pfContra = CreadorElementos.createPasswordField("", 20, "", 0, 40, 250);

	/**
	 * Botón para pasar a la siguiente escena.
	 */
	private static Button siguienteB = CreadorElementos.createButton("", 16, "", -5, -5, 100);

	/**
	 * Botón para volver a la escena anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("", 16, "", 5, -5, 100);

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaSelConex(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		ToggleGroup grupo = new ToggleGroup();

		radioUsuario.setFont(new Font("SansSerif", 16));
		radioUsuario.setToggleGroup(grupo);
		radioUsuario.setOnAction(e -> this.positionLeft());

		radioDesconectado.setFont(new Font("SansSerif", 16));
		radioDesconectado.setToggleGroup(grupo);
		radioDesconectado.setSelected(true);
		radioDesconectado.setOnAction(e -> this.positionCenter());

		usuario.setVisible(false);

		contra.setVisible(false);

		tfUsuario.setVisible(false);

		pfContra.setVisible(false);

		siguienteB.setOnAction(e -> {
			if (grupo.getSelectedToggle().equals(radioDesconectado)) {
				alerta.showAndWait();
				if (alerta.getResult() == ButtonType.YES) {
					aplicacion.createModoDesconectado();
					aplicacion.cambiaEscena(2);
				}
			} else {
				if (tfUsuario.getText().isEmpty() || pfContra.getText().isEmpty()) {
					LOGGER.log(Level.SEVERE, alerta2.getContentText());
					alerta2.showAndWait();
				} else {
					if (aplicacion.createModoUsuario(tfUsuario.getText(), pfContra.getText())) {
						tfUsuario.clear();
						pfContra.clear();
						radioDesconectado.setSelected(true);
						positionCenter();
						aplicacion.cambiaEscena(2);
					}
				}
			}
		});

		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		reloadIdioma(aplicacion, 0);

		this.positionCenter();
		this.getChildren().addAll(selecciona, radioUsuario, radioDesconectado, usuario, contra, tfUsuario, pfContra,
				siguienteB, atrasB);
		EscenaSelConex.setAlignment(selecciona, Pos.TOP_CENTER);
		EscenaSelConex.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaSelConex.setAlignment(siguienteB, Pos.BOTTOM_RIGHT);
		EscenaSelConex.setAlignment(usuario, Pos.CENTER);
		EscenaSelConex.setAlignment(contra, Pos.CENTER);
		EscenaSelConex.setAlignment(tfUsuario, Pos.CENTER);
		EscenaSelConex.setAlignment(pfContra, Pos.CENTER);
	}

	/**
	 * Posiciona los RadioButton en el centro.
	 */
	private void positionCenter() {
		radioUsuario.setTranslateX(0);
		radioUsuario.setTranslateY(-50);

		radioDesconectado.setTranslateX(22);
		radioDesconectado.setTranslateY(50);

		EscenaSelConex.setAlignment(radioUsuario, Pos.CENTER);
		EscenaSelConex.setAlignment(radioDesconectado, Pos.CENTER);

		usuario.setVisible(false);
		contra.setVisible(false);
		tfUsuario.setVisible(false);
		pfContra.setVisible(false);
	}

	/**
	 * Posiciona los RadioButton en la izquierda y habilita los campos.
	 */
	private void positionLeft() {
		radioUsuario.setTranslateX(20);
		radioUsuario.setTranslateY(-50);

		radioDesconectado.setTranslateX(20);
		radioDesconectado.setTranslateY(50);

		EscenaSelConex.setAlignment(radioUsuario, Pos.CENTER_LEFT);
		EscenaSelConex.setAlignment(radioDesconectado, Pos.CENTER_LEFT);

		usuario.setVisible(true);
		contra.setVisible(true);
		tfUsuario.setVisible(true);
		pfContra.setVisible(true);
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
			urlArchivo = "/config/conexion_es.config";
		} else {
			urlArchivo = "/config/conexion_en.config";
		}

		InputStream is = EscenaAbout.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		for (int i = 0; i < valores.size(); i++) {
			valores.set(i, valores.get(i).replace("%%n", "\n"));
		}

		alerta = new Alert(AlertType.WARNING, "", ButtonType.NO, ButtonType.YES);
		alerta2 = CreadorElementos.createAlertaError("", "", "");
		selecciona.setText(valores.get(0));
		alerta.setContentText(valores.get(1));
		alerta.setHeaderText(valores.get(2));
		alerta.setTitle(valores.get(3));
		alerta2.setContentText(valores.get(4));
		alerta2.setHeaderText(valores.get(5));
		alerta2.setTitle(valores.get(6));
		radioUsuario.setText(valores.get(7));
		radioUsuario.setTooltip(CreadorElementos.createTooltip(valores.get(8)));
		radioDesconectado.setText(valores.get(9));
		radioDesconectado.setTooltip(CreadorElementos.createTooltip(valores.get(10)));
		usuario.setText(valores.get(11));
		contra.setText(valores.get(12));
		tfUsuario.setPromptText(valores.get(13));
		tfUsuario.setTooltip(CreadorElementos.createTooltip(valores.get(14)));
		pfContra.setPromptText(valores.get(15));
		pfContra.setTooltip(CreadorElementos.createTooltip(valores.get(16)));
		siguienteB.setText(valores.get(17));
		siguienteB.setTooltip(CreadorElementos.createTooltip(valores.get(18)));
		atrasB.setText(valores.get(19));
		atrasB.setTooltip(CreadorElementos.createTooltip(valores.get(20)));
	}
}
