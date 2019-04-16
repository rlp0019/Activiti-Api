package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;

public class EscenaResultados extends StackPane {
	private static Label contenido;

	public EscenaResultados(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		ScrollPane panelContenido = new ScrollPane();
		panelContenido.setMaxSize(715, 450);
		panelContenido.setHbarPolicy(ScrollBarPolicy.NEVER);
		panelContenido.setBackground(CreadorElementos.createBackground());

		contenido = CreadorElementos.createLabel("", 14, "#000000", 0, 0);

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> {
			aplicacion.cambiaEscena(4);
		});

		panelContenido.setContent(contenido);
		this.getChildren().addAll(panelContenido, atrasB);
		EscenaResultados.setAlignment(panelContenido, Pos.TOP_CENTER);
	}

	public static void setResultadoMetricas(PrincipalFX aplicacion) {
		contenido.setText((String) aplicacion.getMetricasRepositorio()[0]);
	}
}
