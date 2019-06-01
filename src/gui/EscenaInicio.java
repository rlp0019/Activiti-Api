package gui;

import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JButton;

import gui.herramientas.CreadorElementos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
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
	 * Label con el título.
	 */
	private static Label titulo = CreadorElementos.createLabel("", 32, "#0076a3", 0, 30);

	/**
	 * Label con la info.
	 */
	private static Label info = CreadorElementos.createLabel("", 20, "#0076a3", 0, 75);

	/**
	 * Menú superior.
	 */
	private static MenuBar menu = new MenuBar();

	/**
	 * Opción del menú, operaciones.
	 */
	private static Menu operaciones = new Menu();

	/**
	 * Opción de operaciones, analizar.
	 */
	private static Menu analizar = new Menu();

	/**
	 * Opción de analizar, github.
	 */
	private static MenuItem github = new MenuItem();

	/**
	 * Opción de operaciones, importar.
	 */
	private static MenuItem importar = new MenuItem();

	/**
	 * Opción de operaciones, comparar.
	 */
	private static MenuItem comparar = new MenuItem();

	/**
	 * Opción del menú, opciones.
	 */
	private static Menu opciones = new Menu();

	/**
	 * Opción de opciones, idioma.
	 */
	private static Menu idioma = new Menu();

	/**
	 * Opción de idioma, español.
	 */
	private static RadioMenuItem esp = new RadioMenuItem();

	/**
	 * Opción de idioma, inglés.
	 */
	private static RadioMenuItem eng = new RadioMenuItem();

	/**
	 * Opción del menú, ayuda
	 */
	private static Menu ayuda = new Menu();

	/**
	 * Opción de ayuda, mostrar.
	 */
	private static MenuItem ver = new MenuItem();

	/**
	 * Opción del menú, about.
	 */
	private static Menu about = new Menu();

	/**
	 * Opción de about, mostrar.
	 */
	private static MenuItem verA = new MenuItem();

	/**
	 * Opción del menú, conexión.
	 */
	private static Menu conexion = new Menu();

	/**
	 * Opción de conexión, eliminar conexión.
	 */
	private static MenuItem desconectar = new MenuItem();

	/**
	 * Constructor de la escena.
	 * 
	 * @param aplicacion aplicación principal.
	 */
	public EscenaInicio(PrincipalFX aplicacion) {
		this.setMinSize(1000, 700);
		this.setBackground(CreadorElementos.createBackground());

		reloadIdioma(aplicacion, 0);

		createMenu(aplicacion);
		aplicacion.cargarAyuda(ayudaB);

		Image imagen = new Image(getClass().getClassLoader().getResource("imagenes/FachadaUbu.jpg").toExternalForm());
		ImageView imagenV = new ImageView(imagen);

		imagenV.setScaleX(0.75);
		imagenV.setScaleY(0.75);

		this.getChildren().addAll(titulo, info, imagenV, menu);
		EscenaInicio.setAlignment(titulo, Pos.TOP_CENTER);
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
			urlArchivo = "/config/inicio_es.config";
		} else {
			urlArchivo = "/config/inicio_en.config";
		}

		InputStream is = EscenaInicio.class.getResourceAsStream(urlArchivo);

		ArrayList<String> valores = aplicacion.loadArchivoIdioma(is);

		titulo.setText(valores.get(0));
		info.setText(valores.get(1));
		operaciones.setText(valores.get(2));
		analizar.setText(valores.get(3));
		github.setText(valores.get(4));
		importar.setText(valores.get(5));
		comparar.setText(valores.get(6));
		opciones.setText(valores.get(7));
		idioma.setText(valores.get(8));
		esp.setText(valores.get(9));
		eng.setText(valores.get(10));
		ayuda.setText(valores.get(11));
		ver.setText(valores.get(12));
		about.setText(valores.get(13));
		verA.setText(valores.get(14));
		conexion.setText(valores.get(15));
		desconectar.setText(valores.get(16));
	}

	/**
	 * Crea el menú superior de la aplicación.
	 * 
	 * @param aplicacion aplicación principal.
	 * @return barra del menú.
	 */
	private static void createMenu(PrincipalFX aplicacion) {
		github.setOnAction(e -> {
			if (aplicacion.isLectorNull()) {
				aplicacion.cambiaEscena(1);
			} else {
				aplicacion.cambiaEscena(2);
			}
		});
		analizar.getItems().add(github);

		importar.setOnAction(e -> aplicacion.loadArchivo());

		comparar.setOnAction(e -> aplicacion.cambiaEscena(4));
		operaciones.getItems().addAll(analizar, importar, comparar);

		ToggleGroup grupo = new ToggleGroup();
		esp.setOnAction(e -> PrincipalFX.reloadIdioma(aplicacion, 0));
		eng.setOnAction(e -> PrincipalFX.reloadIdioma(aplicacion, 1));
		grupo.getToggles().add(esp);
		grupo.getToggles().add(eng);
		esp.setSelected(true);

		idioma.getItems().add(esp);
		idioma.getItems().add(eng);
		opciones.getItems().add(idioma);

		ayuda.getItems().add(ver);
		ver.setOnAction(e -> EscenaInicio.click());

		about.getItems().add(verA);
		verA.setOnAction(e -> aplicacion.cambiaEscena(6));

		conexion.getItems().add(desconectar);
		desconectar.setOnAction(e -> aplicacion.disconnect());

		menu.getMenus().addAll(conexion, operaciones, opciones, ayuda, about);
	}
}
