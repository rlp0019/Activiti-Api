package gui.herramientas;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Clase que contiene la creación de los nodos usados en las escenas.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class CreadorElementos {
	/**
	 * Crea un background y lo devuelve.
	 * 
	 * @return background.
	 */
	public static Background createBackground() {
		return new Background(new BackgroundFill(Color.web("#e6f2ff"), null, null));
	}

	/**
	 * Crea una label y la devuelve.
	 * 
	 * @param texto texto que muestra la label.
	 * @param tamF  tamaño de la fuente.
	 * @param color color de la fuente.
	 * @param posX  posición x.
	 * @param posY  posición y.
	 * @return label.
	 */
	public static Label createLabel(String texto, double tamF, String color, int posX, int posY) {
		Label label = new Label(texto);
		label.setFont(new Font("SansSerif", tamF));
		label.setTextFill(Color.web(color));
		label.setTranslateX(posX);
		label.setTranslateY(posY);

		return label;
	}

	/**
	 * Crea un botón y lo devuelve.
	 * 
	 * @param texto   texto que muestra el botón.
	 * @param tamF    tamaño de la fuente.
	 * @param tooltip texto del tooltip.
	 * @param posX    posición x.
	 * @param posY    posición y.
	 * @param minW    tamaño mínimo del botón.
	 * @return botón.
	 */
	public static Button createButton(String texto, double tamF, String tooltip, int posX, int posY, int minW) {
		Button button = new Button(texto);
		button.setFont(new Font("SansSerif", tamF));
		Tooltip bTooltip = new Tooltip(tooltip);
		bTooltip.setFont(new Font("SansSerif", 12));
		button.setTooltip(bTooltip);
		button.setTranslateX(posX);
		button.setTranslateY(posY);
		button.setMinWidth(minW);

		return button;
	}

	/**
	 * Crea un campo de texto y lo devuelve.
	 * 
	 * @param textoP  texto que contiene cuando está vacío.
	 * @param tamF    tamaño de la fuente.
	 * @param tooltip texto del tooltip.
	 * @param posX    posición x.
	 * @param posY    posición y.
	 * @param maxW    tamaño máximo del campo.
	 * @return textfield.
	 */
	public static TextField createTextField(String textoP, double tamF, String tooltip, int posX, int posY, int maxW) {
		TextField tf = new TextField();
		tf.setPromptText(textoP);
		tf.setFont(new Font("SansSerif", tamF));
		Tooltip tfTooltip = new Tooltip(tooltip);
		tfTooltip.setFont(new Font("SansSerif", 12));
		tf.setTooltip(tfTooltip);
		tf.setTranslateX(posX);
		tf.setTranslateY(posY);
		tf.setMaxWidth(maxW);

		return tf;
	}

	/**
	 * Crea un campo de contraseña y lo devuelve.
	 * 
	 * @param textoP  texto que contiene cuando está vacío.
	 * @param tamF    tamaño de la fuente.
	 * @param tooltip texto del tooltip.
	 * @param posX    posición x.
	 * @param posY    posición y.
	 * @param maxW    tamaño máximo del campo.
	 * @return passwordfield.
	 */
	public static PasswordField createPasswordField(String textoP, double tamF, String tooltip, int posX, int posY,
			int maxW) {
		PasswordField pf = new PasswordField();
		pf.setPromptText(textoP);
		pf.setFont(new Font("SansSerif", tamF));
		Tooltip tfTooltip = new Tooltip(tooltip);
		tfTooltip.setFont(new Font("SansSerif", 12));
		pf.setTooltip(tfTooltip);
		pf.setTranslateX(posX);
		pf.setTranslateY(posY);
		pf.setMaxWidth(maxW);

		return pf;
	}

	/**
	 * Crea una alera y la devuelve.
	 * 
	 * @param cabecera texto de la cabecera.
	 * @param contexto texto del contexto.
	 * @param titulo   título de la alerta.
	 * @return la alerta.
	 */
	public static Alert createAlertaError(String cabecera, String contexto, String titulo) {
		Alert alerta = new Alert(AlertType.ERROR, contexto, ButtonType.CANCEL);
		alerta.setHeaderText(cabecera);
		alerta.setTitle(titulo);

		return alerta;
	}

	/**
	 * Crea una tab para el menú y la devuelve.
	 * 
	 * @param titulo  texto de la tab.
	 * @param tooltip tooltip de la tab.
	 * @return la tab.
	 */
	public static Tab createTab(String titulo, String tooltip) {
		Tab tab = new Tab(titulo);
		Tooltip t1 = new Tooltip(tooltip);
		t1.setFont(new Font("SansSerif", 12));
		tab.setTooltip(t1);

		return tab;
	}

	/**
	 * Crea un tooltip.
	 * 
	 * @param tooltip texto del tooltip.
	 * @return tooltip creado.
	 */
	public static Tooltip createTooltip(String tooltip) {
		Tooltip bTooltip = new Tooltip(tooltip);
		bTooltip.setFont(new Font("SansSerif", 12));

		return bTooltip;
	}
}
