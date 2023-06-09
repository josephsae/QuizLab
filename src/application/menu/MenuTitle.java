package application.menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Esta clase representa el título de un menú
 * 
 *  @see Pane
 */
public class MenuTitle extends Pane {
	
	private Font titleFont = Font.loadFont(getClass().getResourceAsStream("/application/res/Iceland-Regular.ttf"), 150);
	private Text titleText;
/**
 * Crea un objeto título para el menú con el nombre
 * @param name Nombre del título
 */
	public MenuTitle(String name) {
		titleText = new Text(name);
		
		titleText.setFont(titleFont);
		titleText.setFill(Color.WHITE);

		getChildren().addAll(titleText);
	}
/**
 * Obtiene el ancho del título de un menú
 * @return El ancho del título
 */
	public double getTitleWidth() {
		return titleText.getLayoutBounds().getWidth();
	}
/**
 * Obtiene la altura del título de un menú
 * @return La altura del título
 */
	public double getTitleHeight() {
		return titleText.getLayoutBounds().getHeight();
	}
}
