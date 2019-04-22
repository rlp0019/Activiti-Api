package gui;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class EscenaGraficos extends StackPane {
	private static StackPane grafico;

	public EscenaGraficos(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		grafico = new StackPane();
		grafico.setMaxHeight(450);

		Button graficoIssuesB = CreadorElementos.createButton("Issues", 16, "Mostrar gráfico con los issues.", -217,
				227, 75);
		graficoIssuesB.setOnAction(e -> loadGrafico(aplicacion, 1));

		Button graficoCommitsB = CreadorElementos.createButton("Commits / mes", 16,
				"Mostrar gráfico con los commits por cada mes.", -105, 227, 75);
		graficoCommitsB.setOnAction(e -> loadGrafico(aplicacion, 2));

		Button graficoCommits2B = CreadorElementos.createButton("Commits / día", 16,
				"Mostrar gráfico con los commits por cada día.", 30, 227, 75);
		graficoCommits2B.setOnAction(e -> loadGrafico(aplicacion, 3));

		Button graficoCambioB = CreadorElementos.createButton("Cambios / autor", 16,
				"Mostrar gráfico con los cambios por autor.", 168, 227, 100);
		graficoCambioB.setOnAction(e -> loadGrafico(aplicacion, 4));

		Button graficoIssues2B = CreadorElementos.createButton("Issues / autor", 16,
				"Mostrar gráfico con los issues por autor.", 305, 227, 100);
		graficoIssues2B.setOnAction(e -> loadGrafico(aplicacion, 5));

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -315, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(5));

		this.getChildren().addAll(grafico, graficoIssuesB, graficoCommitsB, graficoCommits2B, graficoCambioB,
				graficoIssues2B, atrasB);
		EscenaGraficos.setAlignment(grafico, Pos.TOP_LEFT);
	}

	public static void loadGrafico(PrincipalFX aplicacion, int id) {
		grafico.getChildren().clear();
		grafico.getChildren().add((Node) aplicacion.getMetricasRepositorio()[id]);
	}
}
