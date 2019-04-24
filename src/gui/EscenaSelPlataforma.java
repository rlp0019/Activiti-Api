package gui;

import gui.herramientas.CreadorElementos;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Escena que contiene la selección de plataforma.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaSelPlataforma extends StackPane {

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaSelPlataforma(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Label selecciona = CreadorElementos.createLabel("Selecciona la plataforma:", 32, "#0076a3", 0, -175);

		Button githubB = CreadorElementos.createButton("GitHub", 22, "Trabajar con la plataforma GitHub.", 0, 0, 200);
		githubB.setOnAction(e -> aplicacion.cambiaEscena(2));

		SwingNode nodoS = CreadorElementos.createBotonAyuda(aplicacion);

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", -315, 227, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		this.getChildren().addAll(selecciona, githubB, nodoS, atrasB);
	}
}
