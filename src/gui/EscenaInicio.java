package gui;

import javax.swing.JButton;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Escena de selección de función a realizar.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class EscenaInicio extends StackPane {
	/**
	 * Botón no visible para la ayuda.
	 */
	private static JButton ayudaB = new JButton();

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaInicio(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		MenuBar menu = CreadorElementos.createMenu(aplicacion);
		aplicacion.cargarAyuda(ayudaB);

		Label activiti = CreadorElementos.createLabel("Evalua y compara la actividad del repositorio en GitHub", 32,
				"#383838", 0, 30);

		Label info = CreadorElementos.createLabel("TFG desarrollado en la Universidad de Burgos", 20, "#000000", 0, 75);

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/FachadaUbu.jpg").toExternalForm());
		ImageView imagenV = new ImageView(imagen);

		imagenV.setScaleX(0.75);
		imagenV.setScaleY(0.75);

		this.getChildren().addAll(activiti, info, imagenV, menu);
		EscenaInicio.setAlignment(activiti, Pos.TOP_CENTER);
		EscenaInicio.setAlignment(info, Pos.TOP_CENTER);
		EscenaInicio.setAlignment(imagenV, Pos.CENTER);
		EscenaInicio.setAlignment(menu, Pos.TOP_LEFT);
	}

	/**
	 * Realiza click en el JButton.
	 */
	public static void click() {
		ayudaB.doClick();
	}
}
