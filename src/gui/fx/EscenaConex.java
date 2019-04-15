package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class EscenaConex extends StackPane {
	public EscenaConex(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Alert alerta = new Alert(AlertType.NONE, "Ambos campos son obligatorios.", ButtonType.OK);
		alerta.setTitle("Error");

		Label introduce = CreadorElementos.createLabel("Introduce los datos:", 32, "#0076a3", 0, -175);

		Label usuario = CreadorElementos.createLabel("Introduce el nombre de usuario:", 20, "#505050", 0, -110);

		Label contra = CreadorElementos.createLabel("Introduce la contraseña:", 20, "#505050", 0, -10);

		TextField tfUsuario = CreadorElementos.createTextField("Nombre de usuario.", 20,
				"Introduce el nombre de usuario.", 0, -60, 250);

		PasswordField pfContra = CreadorElementos.createPasswordField("Contraseña.", 20, "Introduce la contraseña.", 0,
				40, 250);

		Button ident = CreadorElementos.createButton("Conectarse", 24,
				"Crear conexión con el usuario y la contraseña introducidos.", 0, 140, 150);
		ident.setOnAction(e -> {
			if (tfUsuario.getText().isEmpty() || pfContra.getText().isEmpty()) {
				alerta.showAndWait();
			} else {
				if (aplicacion.createModoUsuario(tfUsuario.getText(), pfContra.getText())) {
					tfUsuario.clear();
					pfContra.clear();
					aplicacion.cambiaEscena(4);
				}
			}
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> {
			tfUsuario.clear();
			pfContra.clear();
			aplicacion.cambiaEscena(2);
		});

		this.getChildren().addAll(introduce, usuario, contra, tfUsuario, pfContra, ident, atrasB);
	}
}
