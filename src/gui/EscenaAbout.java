package gui;

import java.io.InputStream;
import java.util.ArrayList;

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
	 * Label con el titulo.
	 */
	private static Label about = CreadorElementos.createLabel("", 32, "#0076a3", 0, 10);

	/**
	 * Label con la información de la universidad.
	 */
	private static Label infoUni = CreadorElementos.createLabel("", 20, "#000000", -35, -55);

	/**
	 * Label con la información sobre los autores.
	 */
	private static Label infoAutor = CreadorElementos.createLabel("", 20, "#000000", 0, 65);

	/**
	 * Hyperlink con el repositorio del proyecto.
	 */
	private static Hyperlink repos = new Hyperlink("");

	/**
	 * Hyperlink con el repositorio de Activiti-Api.
	 */
	private static Hyperlink reposActiviti = new Hyperlink("");

	/**
	 * Botón para volver a la pantalla anterior.
	 */
	private static Button atrasB = CreadorElementos.createButton("", 16, "", 5, -5, 100);

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaAbout(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		repos.setTranslateX(-2);
		repos.setTranslateY(145);
		repos.setFont(new Font("SansSerif", 20));
		repos.setOnAction(e -> aplicacion.getHostServices().showDocument(repos.getText()));

		reposActiviti.setTranslateX(5);
		reposActiviti.setTranslateY(175);
		reposActiviti.setFont(new Font("SansSerif", 20));
		reposActiviti.setOnAction(e -> aplicacion.getHostServices().showDocument(reposActiviti.getText()));

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/Ubu.png").toExternalForm());
		ImageView imagenV = new ImageView(imagen);
		imagenV.setScaleX(0.10);
		imagenV.setScaleY(0.10);
		imagenV.setTranslateY(-200);

		atrasB.setOnAction(e -> aplicacion.cambiaEscena(0));

		reloadIdioma(aplicacion, 0);

		this.getChildren().addAll(about, infoUni, infoAutor, repos, reposActiviti, imagenV, atrasB);
		EscenaAbout.setAlignment(atrasB, Pos.BOTTOM_CENTER);
		EscenaAbout.setAlignment(about, Pos.TOP_CENTER);
		EscenaAbout.setAlignment(infoUni, Pos.CENTER);
		EscenaAbout.setAlignment(imagenV, Pos.CENTER);
	}

	/**
	 * Vuelve a cargar el archivo con el idioma y establece de nuevo los textos de
	 * la escena.
	 * 
	 * @param aplicacion aplicacion principal.
	 * @param id         id del idioma.
	 */
	public static void reloadIdioma(PrincipalFX aplicacion, int id) {
		String urlArchivo = null;

		if (id == 0) {
			urlArchivo = "/config/about_es.config";
		} else {
			urlArchivo = "/config/about_en.config";
		}

		InputStream is = EscenaAbout.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		for (int i = 0; i < valores.size(); i++) {
			valores.set(i, valores.get(i).replace("%%n", "\n"));
		}

		about.setText(valores.get(0));
		infoUni.setText(valores.get(1));
		infoAutor.setText(valores.get(2));
		repos.setText(valores.get(3));
		reposActiviti.setText(valores.get(4));
		atrasB.setText(valores.get(5));
		atrasB.setTooltip(CreadorElementos.createTooltip(valores.get(6)));
	}
}
