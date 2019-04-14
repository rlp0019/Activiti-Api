package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class EscenaSelConex extends StackPane {
	public EscenaSelConex(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Label selecciona = CreadorElementos.createLabel("Selecciona el tipo de conexión:", 32, "#0076a3", 0, -150);

		Alert alerta = new Alert(AlertType.NONE,
				"Usando este modo pueden surgir problemas por el número de peticiones. ¿Quiéres continuar de todas formas?",
				ButtonType.YES, ButtonType.NO);
		alerta.setTitle("Advertencia");

		Button usuarioB = CreadorElementos.createButton("Modo usuario", 22,
				"Conectarse para poder tener un mayor número de peticiones (5000 por hora). Se necesita una cuenta en la plataforma.",
				0, -50, 250);
		usuarioB.setOnAction(e -> {
			;
			aplicacion.cambiaEscena(3);
		});

		Button desconectadoB = CreadorElementos.createButton("Modo desconectado", 22,
				"No conectarse, aunque se tendrá un menor número de peticiones (60 por hora).", 0, 30, 250);
		desconectadoB.setOnAction(e -> {
			alerta.showAndWait();

			if (alerta.getResult() == ButtonType.YES) {
				aplicacion.createModoDesconectado();
				aplicacion.cambiaEscena(4);
			}
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(1));

		this.getChildren().addAll(selecciona, usuarioB, desconectadoB, atrasB);
	}
}
