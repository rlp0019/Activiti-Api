package gui;

import gui.herramientas.CreadorElementos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Escena de selección de función a realizar.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaInicio extends StackPane {

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaInicio(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Label activiti = CreadorElementos.createLabel("Bienvenido a Activiti-Api.", 32, "#383838", 0, -200);

		Label selecciona = CreadorElementos.createLabel("Selecciona la función que desees:", 26, "#0076a3", 0, -130);

		Button buscarRepoB = CreadorElementos.createButton("Buscar repositorio", 22,
				"Buscar un repositorio para calcular sus métricas.", 0, -50, 250);
		buscarRepoB.setOnAction(e -> aplicacion.cambiaEscena(2));

		Button cargarInformeB = CreadorElementos.createButton("Cargar informe", 22,
				"Cargar un informe para importar sus métricas.", 0, 30, 250);
		cargarInformeB.setOnAction(e -> aplicacion.loadArchivo());

		Button compararInformesB = CreadorElementos.createButton("Comparar informes", 22,
				"Cargar dos informes para comparar sus métricas.", 0, 110, 250);
		compararInformesB.setOnAction(e -> aplicacion.cambiaEscena(6));

		this.getChildren().addAll(activiti, selecciona, buscarRepoB, cargarInformeB, compararInformesB);
	}
}
