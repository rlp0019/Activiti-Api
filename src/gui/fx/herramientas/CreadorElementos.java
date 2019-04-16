package gui.fx.herramientas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreadorElementos {
	public static Background createBackground() {
		return new Background(new BackgroundFill(Color.web("#e6f2ff"), null, null));
	}

	public static Label createLabel(String texto, double tamF, String color, int posX, int posY) {
		Label label = new Label(texto);
		label.setFont(new Font("Arial", tamF));
		label.setTextFill(Color.web(color));
		label.setTranslateX(posX);
		label.setTranslateY(posY);

		return label;
	}

	public static Button createButton(String texto, double tamF, String tooltip, int posX, int posY, int minW) {
		Button button = new Button(texto);
		button.setFont(new Font("Arial", tamF));
		Tooltip bTooltip = new Tooltip(tooltip);
		bTooltip.setFont(new Font("Arial", 12));
		button.setTooltip(bTooltip);
		button.setTranslateX(posX);
		button.setTranslateY(posY);
		button.setMinWidth(minW);

		return button;
	}

	public static TextField createTextField(String textoP, double tamF, String tooltip, int posX, int posY, int maxW) {
		TextField tf = new TextField();
		tf.setPromptText(textoP);
		tf.setFont(new Font("Arial", tamF));
		Tooltip tfTooltip = new Tooltip(tooltip);
		tfTooltip.setFont(new Font("Arial", 12));
		tf.setTooltip(tfTooltip);
		tf.setTranslateX(posX);
		tf.setTranslateY(posY);
		tf.setMaxWidth(maxW);

		return tf;
	}

	public static PasswordField createPasswordField(String textoP, double tamF, String tooltip, int posX, int posY,
			int maxW) {
		PasswordField pf = new PasswordField();
		pf.setPromptText(textoP);
		pf.setFont(new Font("Arial", tamF));
		Tooltip tfTooltip = new Tooltip(tooltip);
		tfTooltip.setFont(new Font("Arial", 12));
		pf.setTooltip(tfTooltip);
		pf.setTranslateX(posX);
		pf.setTranslateY(posY);
		pf.setMaxWidth(maxW);

		return pf;
	}
}
