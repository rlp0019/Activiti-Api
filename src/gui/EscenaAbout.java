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

		Label about = CreadorElementos.createLabel("Sobre mi", 32, "#383838", 0, 10);

		Label info = CreadorElementos.createLabel("Estudiante de Ingeniería Informática en la universidad de Burgos, \n"
				+ "con especial inclinación hacia el desarrollo de software y videojuegos. \n"
				+ "\nInteresado en el lenguaje Java.", 20, "#000000", 0, 50);

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/Roberto.jpg").toExternalForm());
		ImageView imagenV = new ImageView(imagen);
		imagenV.setScaleX(1.5);
		imagenV.setScaleY(1.5);
		imagenV.setTranslateY(-150);

		Button atrasB = CreadorElementos.createButton("Atrás", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		this.getChildren().addAll(about, info, imagenV, atrasB);
		EscenaAbout.setAlignment(atrasB, Pos.BOTTOM_LEFT);
		EscenaAbout.setAlignment(about, Pos.TOP_CENTER);
		EscenaAbout.setAlignment(info, Pos.CENTER);
		EscenaAbout.setAlignment(imagenV, Pos.CENTER);
	}
}
