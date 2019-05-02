package gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaConex extends StackPane {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(PrincipalFX.class.getName());

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaConex(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Alert alerta = CreadorElementos.createAlertaError("Campo vacío.", "Ambos campos son obligatorios.",
				"Error de datos.");

		Label introduce = CreadorElementos.createLabel("Introduce los datos:", 32, "#0076a3", 0, -175);

		Label usuario = CreadorElementos.createLabel("Introduce el nombre de usuario:", 20, "#505050", 0, -110);

		Label contra = CreadorElementos.createLabel("Introduce la contraseña:", 20, "#505050", 0, -10);

		TextField tfUsuario = CreadorElementos.createTextField("Nombre de usuario.", 20,
				"Introduce el nombre de usuario.", 0, -60, 250);

		PasswordField pfContra = CreadorElementos.createPasswordField("Contraseña.", 20, "Introduce la contraseña.", 0,
				40, 250);

		Button ident = CreadorElementos.createButton("Conectarse", 20,
				"Crear conexión con el usuario y la contraseña introducidos.", 0, 140, 150);
		ident.setOnAction(e -> {
			if (tfUsuario.getText().isEmpty() || pfContra.getText().isEmpty()) {
				LOGGER.log(Level.SEVERE, alerta.getContentText());
				alerta.showAndWait();
			} else {
				if (aplicacion.createModoUsuario(tfUsuario.getText(), pfContra.getText())) {
					tfUsuario.clear();
					pfContra.clear();
					aplicacion.cambiaEscena(3);
				}
			}
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> {
			tfUsuario.clear();
			pfContra.clear();
			aplicacion.cambiaEscena(1);
		});

		this.getChildren().addAll(introduce, usuario, contra, tfUsuario, pfContra, ident, atrasB);
		EscenaConex.setAlignment(atrasB, Pos.BOTTOM_LEFT);
	}
}
