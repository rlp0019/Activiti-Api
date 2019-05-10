package gui;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
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
	 * Array de HBox de Tab con los gráficos.
	 */
	private final static HBox[] pGraf = new HBox[] { new HBox(), new HBox(), new HBox(), new HBox(), new HBox() };

	/**
	 * Botón para volver a la página anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5,
			100);

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaResultados(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		ScrollPane panelContenido = new ScrollPane();
		panelContenido.setMaxSize(970, 590);
		panelContenido.setHbarPolicy(ScrollBarPolicy.NEVER);
		panelContenido.getStylesheets().add(getClass().getResource("/css/scrollpanel.css").toExternalForm());

		TabPane grupo = iniciaTabPane(panelContenido);

		Button guardarB = CreadorElementos.createButton("Guardar informe", 16, "Guardar el informe de resultados.",
				-200, -5, 100);
		guardarB.setOnAction(e -> {
			aplicacion.saveArchivo();
		});

		Button comparacionB = CreadorElementos.createButton("Evaluar con BD", 16,
				"Abrir la BD en formato .csv para realizar la evaluación.", 0, -5, 100);
		comparacionB.setOnAction(e -> {
			EscenaResultadoComparacion.setCSV(true);
			aplicacion.startManager();
			EscenaResultadoComparacion.setTabla(aplicacion.creaTablaCSV());
			EscenaResultadoComparacion.enableNota(true);
			EscenaResultadoComparacion.setNota(aplicacion.getNota(false));
			aplicacion.cambiaEscena(5);
		});

		Button guardarCSVB = CreadorElementos.createButton("Guardar en BD", 16,
				"Guardar el resultado de las métricas en la base de datos elegida.", 200, -5, 100);
		guardarCSVB.setOnAction(e -> {
			aplicacion.startManager();
			aplicacion.guardarEnCSV();
		});

		atrasB.setOnAction(e -> aplicacion.cambiaEscena(2));

		Button inicioB = CreadorElementos.createButton("Al inicio", 16, "Volver a la pantalla inicial.", -5, -5, 100);
		inicioB.setOnAction(e -> aplicacion.cambiaEscena(0));

		panelContenido.setContent(contenido);
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
		contenido.setText((String) resultado[0]);

		atrasB.setDisable(archivo);
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
	private TabPane iniciaTabPane(ScrollPane panelContenido) {
		TabPane tb = new TabPane();

		Tab resultado = CreadorElementos.createTab("Métricas", "Mostrar panel con los resultados de las métricas.");
		resultado.setContent(panelContenido);

		Tab graf1 = CreadorElementos.createTab("Gráfico Issues", "Mostrar gráfico con los issues.");
		graf1.setContent(EscenaResultados.pGraf[0]);
		EscenaResultados.pGraf[0].setAlignment(Pos.TOP_CENTER);

		Tab graf2 = CreadorElementos.createTab("Gráfico Commits / Mes",
				"Mostrar gráfico con los commits por cada mes.");
		graf2.setContent(EscenaResultados.pGraf[1]);
		EscenaResultados.pGraf[1].setAlignment(Pos.TOP_CENTER);

		Tab graf3 = CreadorElementos.createTab("Gráfico Commits / Día",
				"Mostrar gráfico con los commits por cada día.");
		graf3.setContent(EscenaResultados.pGraf[2]);
		EscenaResultados.pGraf[2].setAlignment(Pos.TOP_CENTER);

		Tab graf4 = CreadorElementos.createTab("Gráfico Cambios / Autor", "Mostrar gráfico con los cambios por autor.");
		graf4.setContent(EscenaResultados.pGraf[3]);
		EscenaResultados.pGraf[3].setAlignment(Pos.TOP_CENTER);

		Tab graf5 = CreadorElementos.createTab("Gráfico Issues / Autor", "Mostrar gráfico con los issues por autor.");
		graf5.setContent(EscenaResultados.pGraf[4]);
		EscenaResultados.pGraf[4].setAlignment(Pos.TOP_CENTER);

		tb.getTabs().addAll(resultado, graf1, graf2, graf3, graf4, graf5);

		return tb;
	}
}
