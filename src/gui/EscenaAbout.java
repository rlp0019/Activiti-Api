package gui;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class EscenaAbout extends StackPane {

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaAbout(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		Label about = CreadorElementos.createLabel("Sobre la universidad", 32, "#383838", 0, 10);

		Label info = CreadorElementos.createLabel("Universidad pública situada en Burgos, creada en el 1994\n"
				+ "como una partición de la Universidad de Valladolid \n" + "a partir del campus existente en Burgos.",
				20, "#000000", 0, 25);

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/Ubu.png").toExternalForm());
		ImageView imagenV = new ImageView(imagen);
		imagenV.setScaleX(2.5);
		imagenV.setScaleY(2.5);
		imagenV.setTranslateY(-150);

		Button atrasB = CreadorElementos.createButton("Cerrar", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		this.getChildren().addAll(about, info, imagenV, atrasB);
		EscenaAbout.setAlignment(atrasB, Pos.BOTTOM_CENTER);
		EscenaAbout.setAlignment(about, Pos.TOP_CENTER);
		EscenaAbout.setAlignment(info, Pos.CENTER);
		EscenaAbout.setAlignment(imagenV, Pos.CENTER);
	}
}
