package gui;

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
import javafx.scene.control.Tooltip;
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
	 * RadioButton de conexión modo usuario.
	 */
	private RadioButton radioUsuario;

	/**
	 * RadioButton de conexión modo desconectado.
	 */
	private RadioButton radioDesconectado;

	/**
	 * Label de introducir usuario.
	 */
	private Label usuario;

	/**
	 * Label de introducir contraseña.
	 */
	private Label contra;

	/**
	 * TextField para introducir el nombre de usuario.
	 */
	private TextField tfUsuario;

	/**
	 * PasswordField para introducir la contraseña asociada al usuario.
	 */
	private PasswordField pfContra;

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaSelConex(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		Label selecciona = CreadorElementos.createLabel("Selecciona el tipo de conexión", 32, "#0076a3", 0, 30);

		Alert alerta = new Alert(AlertType.WARNING,
				"Usando este modo pueden surgir problemas por el número de peticiones. ¿Quiéres continuar de todas formas?",
				ButtonType.NO, ButtonType.YES);
		alerta.setHeaderText("Confirmación de conexión.");
		alerta.setTitle("Advertencia");

		Alert alerta2 = CreadorElementos.createAlertaError("Los campos no pueden estar vacíos.", "Campos vacíos.",
				"Error de datos.");

		ToggleGroup grupo = new ToggleGroup();

		radioUsuario = new RadioButton("Modo usuario");
		radioUsuario.setFont(new Font("SansSerif", 16));
		Tooltip bTooltip = new Tooltip(
				"Conectarse para poder tener un mayor número de peticiones (5000 por hora). Se necesita una cuenta en la plataforma.");
		bTooltip.setFont(new Font("SansSerif", 12));
		radioUsuario.setTooltip(bTooltip);
		radioUsuario.setToggleGroup(grupo);
		radioUsuario.setOnAction(e -> this.positionLeft());

		radioDesconectado = new RadioButton("Modo desconectado");
		radioDesconectado.setFont(new Font("SansSerif", 16));
		Tooltip b2Tooltip = new Tooltip("No conectarse, aunque se tendrá un menor número de peticiones (60 por hora).");
		bTooltip.setFont(new Font("SansSerif", 12));
		radioDesconectado.setTooltip(b2Tooltip);
		radioDesconectado.setToggleGroup(grupo);
		radioDesconectado.setSelected(true);
		radioDesconectado.setOnAction(e -> this.positionCenter());

		usuario = CreadorElementos.createLabel("Introduce el nombre de usuario", 20, "#505050", 0, -110);
		usuario.setVisible(false);

		contra = CreadorElementos.createLabel("Introduce la contraseña", 20, "#505050", 0, -10);
		contra.setVisible(false);

		tfUsuario = CreadorElementos.createTextField("Nombre de usuario", 20, "Introduce el nombre de usuario.", 0, -60,
				250);
		tfUsuario.setVisible(false);

		pfContra = CreadorElementos.createPasswordField("Contraseña", 20, "Introduce la contraseña.", 0, 40, 250);
		pfContra.setVisible(false);

		Button siguienteB = CreadorElementos.createButton("Siguiente", 16, "Ir a la pantalla siguiente.", -5, -5, 100);
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
						aplicacion.cambiaEscena(2);
					}
				}
			}
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

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
}
