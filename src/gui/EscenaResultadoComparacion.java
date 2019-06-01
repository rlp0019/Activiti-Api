package gui;

import java.io.InputStream;
import java.util.ArrayList;

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
	 * Label con el título.
	 */
	private static Label titulo = CreadorElementos.createLabel("", 32, "#0076a3", 0, 10);

	/**
	 * Label con la nota a mostrar.
	 */
	private static Label nota = CreadorElementos.createLabel("", 24, "#0076a3", 0, 230);

	/**
	 * Botón para obtener la nota poco estricta.
	 */
	private static Button notaB = CreadorElementos.createButton("", 16, "", -200, -5, 100);

	/**
	 * Botón para obtener la nota estricta.
	 */
	private static Button notaSB = CreadorElementos.createButton("", 16, "", 200, -5, 100);

	/**
	 * Botón para volver a la escena anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("", 16, "", 5, -5, 100);

	/**
	 * Botón para volver al inicio.
	 */
	private static Button inicioB = CreadorElementos.createButton("", 16, "", -5, -5, 100);

	/**
	 * Texto que acompaña a la nota.
	 */
	private static String notaTexto = "";

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaResultadoComparacion(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		StackPane panelContenido = new StackPane();
		panelContenido.setBackground(CreadorElementos.createBackground());
		panelContenido.setTranslateY(70);

		notaB.setOnAction(e -> EscenaResultadoComparacion.setNota(aplicacion.getNota(false)));

		notaSB.setOnAction(e -> EscenaResultadoComparacion.setNota(aplicacion.getNota(true)));

		atrasB.setOnAction(e -> {
			if (!csv) {
				aplicacion.cambiaEscena(4);
			} else {
				aplicacion.cambiaEscena(3);
			}
		});

		inicioB.setOnAction(e -> aplicacion.cambiaEscena(0));

		reloadIdioma(aplicacion, 0);

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
		EscenaResultadoComparacion.setAlignment(wv, Pos.TOP_CENTER);
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
			nota.setText(notaTexto + valor + "/8.0");
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

	/**
	 * Cambia la altura del visualizador web con la tabla.
	 * 
	 * @param altura altura de la ventana.
	 */
	public static void setAlturaWebView(double altura) {
		wv.setMaxHeight(altura - 153);
	}

	/**
	 * Vuelve a cargar el archivo con el idioma y establece de nuevo los textos de
	 * la escena.
	 * 
	 * @param aplicacion aplicacion principal.
	 * @param id         id del idioma.
	 */
	public static void reloadIdioma(PrincipalFX aplicacion, int id) {
		String urlArchivo = null;

		if (id == 0) {
			urlArchivo = "/config/resultado_comparacion_es.config";
		} else {
			urlArchivo = "/config/resultado_comparacion_en.config";
		}

		InputStream is = EscenaAbout.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		for (int i = 0; i < valores.size(); i++) {
			valores.set(i, valores.get(i).replace("%%n", "\n"));
		}

		titulo.setText(valores.get(0));
		notaTexto = valores.get(1);
		notaB.setText(valores.get(2));
		notaB.setTooltip(CreadorElementos.createTooltip(valores.get(3)));
		notaSB.setText(valores.get(4));
		notaSB.setTooltip(CreadorElementos.createTooltip(valores.get(5)));
		atrasB.setText(valores.get(6));
		atrasB.setTooltip(CreadorElementos.createTooltip(valores.get(7)));
		inicioB.setText(valores.get(8));
		inicioB.setTooltip(CreadorElementos.createTooltip(valores.get(8)));
	}
}
