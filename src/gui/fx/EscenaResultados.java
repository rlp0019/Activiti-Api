package gui.fx;

import gui.fx.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;

/**
 * Escena que muestra los resultados de las métricas, tanto calculadas como
 * importadas.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaResultados extends StackPane {
	/**
	 * Label que contiene las métricas a mostrar.
	 */
	private static Label contenido = CreadorElementos.createLabel("", 14, "#000000", 0, 0);

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaResultados(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		ScrollPane panelContenido = new ScrollPane();
		panelContenido.setMaxSize(715, 450);
		panelContenido.setHbarPolicy(ScrollBarPolicy.NEVER);
		panelContenido.getStylesheets().add(getClass().getResource("/css/scrollpanel.css").toExternalForm());

		Button guardarB = CreadorElementos.createButton("Guardar", 16, "Guardar el informe de resultados.", 0, 227,
				100);
		guardarB.setOnAction(e -> {
			aplicacion.saveArchivo();
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -300, 227, 100);
		atrasB.setOnAction(e -> {
			aplicacion.cambiaEscena(0);
		});

		panelContenido.setContent(contenido);
		this.getChildren().addAll(panelContenido, guardarB, atrasB);
		EscenaResultados.setAlignment(panelContenido, Pos.TOP_CENTER);
	}

	/**
	 * Set que establece las métricas en la label.
	 * 
	 * @param resultado resultado de las métricas.
	 */
	public static void setResultadoMetricas(String resultado) {
		contenido.setText(resultado);
	}
}
