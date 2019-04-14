package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class EscenaSelPlataforma extends StackPane {

	public EscenaSelPlataforma(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Label selecciona = CreadorElementos.createLabel("Selecciona la plataforma:", 32, "#0076a3", 0, -150);

		Button githubB = CreadorElementos.createButton("GitHub", 24, "Trabajar con la plataforma GitHub.", 0, 0, 200);
		githubB.setOnAction(e -> aplicacion.cambiaEscena(2));

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		this.getChildren().addAll(selecciona, githubB, atrasB);
	}
}
