package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(EscenaInicio.class.getName());

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaInicio(PrincipalFX aplicacion) {
		this.setBackground(CreadorElementos.createBackground());

		Label activiti = CreadorElementos.createLabel("Bienvenido a Activiti-Api.", 32, "#383838", 0, 0);

		Label info = CreadorElementos.createLabel("TFG desarrollado en la Universidad de Burgos.", 20, "#000000", 0,
				50);

		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream("rsc/imagenes/FachadaUbu.jpg");
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

		Image imagen = new Image(inputstream);
		ImageView imagenV = new ImageView(imagen);

		imagenV.setScaleX(0.75);
		imagenV.setScaleY(0.75);

		this.getChildren().addAll(activiti, info, imagenV);
		EscenaInicio.setAlignment(activiti, Pos.TOP_CENTER);
		EscenaInicio.setAlignment(info, Pos.TOP_CENTER);
		EscenaInicio.setAlignment(imagenV, Pos.CENTER);
	}
}
