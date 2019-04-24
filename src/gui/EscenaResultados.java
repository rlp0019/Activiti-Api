package gui;

import gui.herramientas.CreadorElementos;
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

		Button guardarB = CreadorElementos.createButton("Guardar informe", 16, "Guardar el informe de resultados.",
				-175, 227, 100);
		guardarB.setOnAction(e -> {
			aplicacion.saveArchivo();
		});

		Button graficosB = CreadorElementos.createButton("Ver gráficos", 16, "Cambiar a la pantalla de gráficos.", -30,
				227, 100);
		graficosB.setOnAction(e -> {
			EscenaGraficos.loadGrafico(aplicacion, 1);
			aplicacion.cambiaEscena(8);
		});

		Button comparacionB = CreadorElementos.createButton("Evaluar", 16,
				"Cambiar a la pantalla de comparación con la base de datos en formato .csv.", 95, 227, 100);
		comparacionB.setOnAction(e -> {
			EscenaResultadoComparacion.setCSV(true);
			EscenaResultadoComparacion.setTabla(aplicacion.creaTablaCSV());
			EscenaResultadoComparacion.setNota(Double.toString(aplicacion.getNota(false)));
			aplicacion.cambiaEscena(7);
		});

		Button guardarCSVB = CreadorElementos.createButton("Guardar en BD", 16,
				"Guardar el resultado de las métricas en la base de datos.", 233, 227, 100);
		guardarCSVB.setOnAction(e -> {
			aplicacion.creaTablaCSV();
			aplicacion.guardarEnCSV();
		});

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -315, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		panelContenido.setContent(contenido);
		this.getChildren().addAll(panelContenido, guardarB, graficosB, comparacionB, guardarCSVB, atrasB);
		EscenaResultados.setAlignment(panelContenido, Pos.TOP_CENTER);
	}

	/**
	 * Set que establece las métricas en la label.
	 * 
	 * @param resultado resultado de las métricas.
	 */
	public static void setResultadoMetricas(Object[] resultado) {
		contenido.setText((String) resultado[0]);
	}
}
