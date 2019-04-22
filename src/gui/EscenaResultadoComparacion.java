package gui;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * Escena que muestra los resultados de comparar dos informes.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaResultadoComparacion extends StackPane {
	private static WebView wv = new WebView();

	public EscenaResultadoComparacion(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		StackPane panelContenido = new StackPane();
		panelContenido.setBackground(CreadorElementos.createBackground());

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -315, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		panelContenido.getChildren().add(wv);
		this.getChildren().addAll(panelContenido, atrasB);
		EscenaResultadoComparacion.setAlignment(panelContenido, Pos.TOP_LEFT);
	}

	/**
	 * Set que establece el resultado de la comparación en la cadena y da forma a la
	 * tabla mediante html.
	 * 
	 * @param resultado resultado de las métricas.
	 */
	public static void setComparacion(String resultado) {
		wv.getEngine().loadContent(
				"<html>" + "<head><style>" + "table {font-weight: bold; margin: 0 auto; text-align: center}"
						+ "table td {background-color: #C0C0C0;}" + "table .rojo {background-color: #ff4646;}"
						+ "table .verde {background-color: #62ff79;}" + "</style></head>" + "<body bgcolor='#e6f2ff'>"
						+ resultado + "</body></html>");
	}
}
