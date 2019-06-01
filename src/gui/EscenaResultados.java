package gui;

import java.io.InputStream;
import java.util.ArrayList;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

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
	private static WebView contenido = new WebView();

	/**
	 * Array de HBox de Tab con los gráficos.
	 */
	private final static VBox[] pGraf = new VBox[] { new VBox(), new VBox(), new VBox(), new VBox(), new VBox() };

	/**
	 * Botón para guardar los resultados en la BD.
	 */
	private static Button guardarB = CreadorElementos.createButton("", 16, "", -200, -5, 100);

	/**
	 * Botón para volver a la página anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("", 16, "", 5, -5, 100);

	/**
	 * Botón para comparar con la BD.
	 */
	private static Button comparacionB = CreadorElementos.createButton("", 16, "", 0, -5, 100);

	/**
	 * Botón para guardar en la BD.
	 */
	private static Button guardarCSVB = CreadorElementos.createButton("", 16, "", 200, -5, 100);

	/**
	 * Botón para volver a la página de inicio.
	 */
	private static Button inicioB = CreadorElementos.createButton("", 16, "", -5, -5, 100);

	/**
	 * TabPane que contiene el resultado de las métricas y los gráficos.
	 */
	private static TabPane grupo = new TabPane();

	/**
	 * Tab con el resultado de las métricas.
	 */
	private static Tab resultado = CreadorElementos.createTab("", "");

	/**
	 * Tab con el primer gráfico.
	 */
	private static Tab graf1 = CreadorElementos.createTab("", "");

	/**
	 * Tab con el segundo gráfico.
	 */
	private static Tab graf2 = CreadorElementos.createTab("", "");

	/**
	 * Tab con el tercer gráfico.
	 */
	private static Tab graf3 = CreadorElementos.createTab("", "");

	/**
	 * Tab con el cuarto gráfico.
	 */
	private static Tab graf4 = CreadorElementos.createTab("", "");

	/**
	 * Tab con el quinto gráfico.
	 */
	private static Tab graf5 = CreadorElementos.createTab("", "");

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaResultados(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		contenido.setMaxHeight(590);

		guardarB.setOnAction(e -> {
			aplicacion.saveArchivo();
		});

		comparacionB.setOnAction(e -> {
			EscenaResultadoComparacion.setCSV(true);
			aplicacion.startManager();
			EscenaResultadoComparacion.setTabla(aplicacion.creaTablaCSV());
			EscenaResultadoComparacion.enableNota(true);
			EscenaResultadoComparacion.setNota(aplicacion.getNota(false));
			aplicacion.cambiaEscena(5);
		});

		guardarCSVB.setOnAction(e -> {
			aplicacion.startManager();
			aplicacion.guardarEnCSV();
		});

		atrasB.setOnAction(e -> aplicacion.cambiaEscena(2));

		inicioB.setOnAction(e -> aplicacion.cambiaEscena(0));

		iniciaTabPane();
		reloadIdioma(aplicacion, 0);

		this.getChildren().addAll(grupo, guardarB, comparacionB, guardarCSVB, atrasB, inicioB);
		EscenaResultados.setAlignment(comparacionB, Pos.BOTTOM_CENTER);
		EscenaResultados.setAlignment(guardarCSVB, Pos.BOTTOM_CENTER);
		EscenaResultados.setAlignment(guardarB, Pos.BOTTOM_CENTER);
		EscenaResultados.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaResultados.setAlignment(inicioB, Pos.BOTTOM_RIGHT);
	}

	/**
	 * Set que establece las métricas en la label y habilita o deshabilita el botón
	 * de atrás.
	 * 
	 * @param resultado resultado de las métricas.
	 * @param archivo   indica si la entrada es por archivo o no.
	 */
	public static void setResultadoMetricas(Object[] resultado, boolean archivo) {
		contenido.getEngine().loadContent((String) resultado[0]);

		atrasB.setDisable(archivo);
	}

	/**
	 * Cambia la altura de la vista de las métricas.
	 * 
	 * @param altura altura de la ventana.
	 */
	public static void setAlturaMetricas(double altura) {
		contenido.setMaxHeight(altura - 112);
	}

	/**
	 * Carga los gráficos en las correspondientes Tab.
	 * 
	 * @param aplicacion aplicacion principal.
	 */
	public static void loadGraficos(PrincipalFX aplicacion) {
		for (int i = 0; i < EscenaResultados.pGraf.length; i++) {
			EscenaResultados.pGraf[i].getChildren().clear();
			Node g = (Node) aplicacion.getMetricasRepositorio()[i + 1];
			EscenaResultados.pGraf[i].getChildren().add(g);
			EscenaResultados.pGraf[i].setMaxHeight(575);
		}
	}

	/**
	 * Crea un TabPane con todas las tab necesarias para los resultados y gráficos.
	 * 
	 * @param panelContenido Panel con el contenido de las métricas.
	 * @return TabPane completo.
	 */
	private static void iniciaTabPane() {
		resultado.setContent(contenido);

		graf1.setContent(EscenaResultados.pGraf[0]);
		EscenaResultados.pGraf[0].setAlignment(Pos.BOTTOM_CENTER);

		graf2.setContent(EscenaResultados.pGraf[1]);
		EscenaResultados.pGraf[1].setAlignment(Pos.BOTTOM_CENTER);

		graf3.setContent(EscenaResultados.pGraf[2]);
		EscenaResultados.pGraf[2].setAlignment(Pos.BOTTOM_CENTER);

		graf4.setContent(EscenaResultados.pGraf[3]);
		EscenaResultados.pGraf[3].setAlignment(Pos.BOTTOM_CENTER);

		graf5.setContent(EscenaResultados.pGraf[4]);
		EscenaResultados.pGraf[4].setAlignment(Pos.BOTTOM_CENTER);

		grupo.getTabs().addAll(resultado, graf1, graf2, graf3, graf4, graf5);
	}

	/**
	 * Vuelve a cargar el archivo con el idioma y establece de nuevo los textos de
	 * la escena.
	 * 
	 * @param id id del idioma.
	 */
	public static void reloadIdioma(PrincipalFX aplicacion, int id) {
		String urlArchivo = null;

		if (id == 0) {
			urlArchivo = "/config/resultados_es.config";
		} else {
			urlArchivo = "/config/resultados_en.config";
		}

		InputStream is = EscenaAbout.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		for (int i = 0; i < valores.size(); i++) {
			valores.set(i, valores.get(i).replace("%%n", "\n"));
		}

		guardarB.setText(valores.get(0));
		guardarB.setTooltip(CreadorElementos.createTooltip(valores.get(1)));
		atrasB.setText(valores.get(2));
		atrasB.setTooltip(CreadorElementos.createTooltip(valores.get(3)));
		comparacionB.setText(valores.get(4));
		comparacionB.setTooltip(CreadorElementos.createTooltip(valores.get(5)));
		guardarCSVB.setText(valores.get(6));
		guardarCSVB.setTooltip(CreadorElementos.createTooltip(valores.get(7)));
		inicioB.setText(valores.get(8));
		inicioB.setTooltip(CreadorElementos.createTooltip(valores.get(9)));
		resultado.setText(valores.get(10));
		resultado.setTooltip(CreadorElementos.createTooltip(valores.get(11)));
		graf1.setText(valores.get(12));
		graf1.setTooltip(CreadorElementos.createTooltip(valores.get(13)));
		graf2.setText(valores.get(14));
		graf2.setTooltip(CreadorElementos.createTooltip(valores.get(15)));
		graf3.setText(valores.get(16));
		graf3.setTooltip(CreadorElementos.createTooltip(valores.get(17)));
		graf4.setText(valores.get(18));
		graf4.setTooltip(CreadorElementos.createTooltip(valores.get(19)));
		graf5.setText(valores.get(20));
		graf5.setTooltip(CreadorElementos.createTooltip(valores.get(21)));
	}
}
