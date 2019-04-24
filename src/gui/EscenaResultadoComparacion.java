package gui;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * Escena que muestra los resultados de comparar dos informes o un informe con
 * la base de datos .csv.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaResultadoComparacion extends StackPane {

	/**
	 * WebView que contiene las tablas html.
	 */
	private static WebView wv = new WebView();

	/**
	 * Booleano con el tipo de tabla a mostrar.
	 */
	private static boolean csv = false;

	/**
	 * Label con la nota a mostrar.
	 */
	private static Label nota = CreadorElementos.createLabel("", 24, "#000000", 0, 50);

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaResultadoComparacion(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		StackPane panelContenido = new StackPane();
		panelContenido.setBackground(CreadorElementos.createBackground());

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -315, 227, 100);
		atrasB.setOnAction(e -> {
			if (!csv) {
				aplicacion.cambiaEscena(0);
			} else {
				aplicacion.cambiaEscena(5);
			}
		});

		panelContenido.getChildren().add(wv);
		this.getChildren().addAll(panelContenido, nota, atrasB);
		EscenaResultadoComparacion.setAlignment(panelContenido, Pos.TOP_LEFT);
	}

	/**
	 * Set que establece la tabla html.
	 * 
	 * @param tabla tabla con los resultado de la comparación.
	 */
	public static void setTabla(String tabla) {
		wv.getEngine().loadContent(tabla);
	}

	/**
	 * Set que establece si está comparando dos informes o un informe con la base de
	 * datos.
	 * 
	 * @param valor true si compara con la base de datos o false si compara dos
	 *              informes.
	 */
	public static void setCSV(boolean valor) {
		csv = valor;
	}

	public static void setNota(String valor) {
		if (!("".contentEquals(valor))) {
			nota.setText("La nota del desarrollo del proyecto es " + valor + " sobre 8.");
		} else {
			nota.setText(valor);
		}
	}
}
