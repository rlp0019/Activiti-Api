package gui;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class EscenaAbout extends StackPane {

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaAbout(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		Label about = CreadorElementos.createLabel("Sobre la universidad", 32, "#0076a3", 0, 10);

		Label infoUni = CreadorElementos.createLabel(
				"Universidad de Burgos\nEscuela Politécnica Superior\nGrado en Ingeniería Informática", 20, "#000000",
				-35, -55);
		Label infoAutor = CreadorElementos.createLabel(
				"Creado por: Roberto Luquero\nTutor: Carlos López\nBasado en: Activiti-Api de David Alonso", 20,
				"#000000", 0, 65);

		Hyperlink repos = new Hyperlink("https://github.com/rlp0019/Activiti-Api");
		repos.setTranslateX(-2);
		repos.setTranslateY(145);
		repos.setFont(new Font("SansSerif", 20));
		repos.setOnAction(e -> aplicacion.getHostServices().showDocument(repos.getText()));

		Hyperlink reposActiviti = new Hyperlink("https://github.com/dba0010/Activiti-Api");
		reposActiviti.setTranslateX(5);
		reposActiviti.setTranslateY(175);
		reposActiviti.setFont(new Font("SansSerif", 20));
		reposActiviti.setOnAction(e -> aplicacion.getHostServices().showDocument(reposActiviti.getText()));

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/Ubu.png").toExternalForm());
		ImageView imagenV = new ImageView(imagen);
		imagenV.setScaleX(0.10);
		imagenV.setScaleY(0.10);
		imagenV.setTranslateY(-200);

		Button atrasB = CreadorElementos.createButton("Cerrar", 16, "Volver a la pantalla anterior.", 5, -5, 100);
		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		this.getChildren().addAll(about, infoUni, infoAutor, repos, reposActiviti, imagenV, atrasB);
		EscenaAbout.setAlignment(atrasB, Pos.BOTTOM_CENTER);
		EscenaAbout.setAlignment(about, Pos.TOP_CENTER);
		EscenaAbout.setAlignment(infoUni, Pos.CENTER);
		EscenaAbout.setAlignment(imagenV, Pos.CENTER);
	}
}
