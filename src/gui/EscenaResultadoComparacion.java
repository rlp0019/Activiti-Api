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
	private static Label nota = CreadorElementos.createLabel("", 24, "#0076a3", 0, 200);

	/**
	 * Botón para obtener la nota poco estricta.
	 */
	private static Button notaB = CreadorElementos.createButton("Obtener nota", 16, "Muestra la nota poco estricta.",
			-200, -5, 100);

	/**
	 * Botón para obtener la nota estricta.
	 */
	private static Button notaSB = CreadorElementos.createButton("Obtener nota estricta", 16,
			"Muestra la nota estricta.", 200, -5, 100);

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaResultadoComparacion(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		Label titulo = CreadorElementos.createLabel("Comparación", 32, "#0076a3", 0, 10);

		StackPane panelContenido = new StackPane();
		panelContenido.setBackground(CreadorElementos.createBackground());
		panelContenido.setTranslateY(70);

		notaB.setOnAction(e -> EscenaResultadoComparacion.setNota(aplicacion.getNota(false)));

		notaSB.setOnAction(e -> EscenaResultadoComparacion.setNota(aplicacion.getNota(true)));

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> {
			if (!csv) {
				aplicacion.cambiaEscena(4);
			} else {
				aplicacion.cambiaEscena(3);
			}
		});

		Button inicioB = CreadorElementos.createButton("Al inicio", 16, "Volver a la pantalla inicial.", -5, -5, 100);
		inicioB.setOnAction(e -> aplicacion.cambiaEscena(0));

		panelContenido.getChildren().addAll(wv, nota);
		StackPane.setAlignment(nota, Pos.CENTER);
		this.getChildren().addAll(titulo, panelContenido, nota, notaB, notaSB, atrasB, inicioB);
		EscenaResultadoComparacion.setAlignment(panelContenido, Pos.CENTER);
		EscenaResultadoComparacion.setAlignment(nota, Pos.CENTER);
		EscenaResultadoComparacion.setAlignment(notaB, Pos.BOTTOM_CENTER);
		EscenaResultadoComparacion.setAlignment(notaSB, Pos.BOTTOM_CENTER);
		EscenaResultadoComparacion.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaResultadoComparacion.setAlignment(inicioB, Pos.BOTTOM_RIGHT);
		EscenaResultadoComparacion.setAlignment(titulo, Pos.TOP_CENTER);
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

	/**
	 * Establece el texto a mostrar en la nota.
	 * 
	 * @param valor valor de la nota.
	 */
	public static void setNota(double valor) {
		String valorS = Double.toString(valor);
		if (!("".contentEquals(valorS))) {
			nota.setText("Calificación del desarrollo del proyecto: " + valor + "/8.0");
		}
	}

	/**
	 * Establece la visualización de la nota y los botones.
	 * 
	 * @param activar booleano para desactivar o activar la nota.
	 */
	public static void enableNota(boolean activar) {
		nota.setVisible(activar);
		notaB.setVisible(activar);
		notaSB.setVisible(activar);
	}
}
