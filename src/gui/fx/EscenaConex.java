package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class EscenaConex extends StackPane {
	public EscenaConex(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Alert alerta = new Alert(AlertType.NONE, "Ambos campos son obligatorios.", ButtonType.OK);
		alerta.setTitle("Campos vacíos");

		Label introduce = CreadorElementos.createLabel("Introduce los datos:", 32, "#0076a3", 0, -175);

		Label usuario = CreadorElementos.createLabel("Introduce el nombre de usuario:", 20, "#505050", 0, -110);

		Label contra = CreadorElementos.createLabel("Introduce la contraseña:", 20, "#505050", 0, -10);

		TextField tfUsuario = CreadorElementos.createTextField("Nombre de usuario.", 20,
				"Introduce el nombre de usuario.", 0, -60, 250);

		TextField tfContra = CreadorElementos.createTextField("Contraseña.", 20, "Introduce la contraseña.", 0, 40,
				250);

		Button ident = CreadorElementos.createButton("Conectarse", 24,
				"Crear conexión con el usuario y la contraseña introducidos.", 0, 140, 150);
		ident.setOnAction(e -> {
			if (tfUsuario.getText() == "" || tfContra.getText() == "") {
				alerta.showAndWait();
			} else {
				aplicacion.createModoUsuario(tfUsuario.getText(), tfContra.getText());
				aplicacion.cambiaEscena(4);
			}
		});

		this.getChildren().addAll(introduce, usuario, contra, tfUsuario, tfContra, ident);
	}
}
