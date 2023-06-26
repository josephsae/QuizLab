package org.quizlab.quizlab_game.menu;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Esta clase define un item (opción) del menú
 * 
 * @see Pane
 */
public class MenuItem extends Pane {

    private Effect shadow = new DropShadow(5, Color.WHITE);
    private Effect blur = new BoxBlur(1, 1, 3);
    
    
	private Font itemFont = Font.loadFont(getClass().getResourceAsStream("/org/quizlab/quizlab_game/Iceland-Regular.ttf"), 24);

	/**
	 * Crea un objeto item para el menú con el texto y fondo
	 * 
	 * @param name Nombre del item
	 */
	public MenuItem(String name) {
		Polygon itemBackGround = new Polygon(0, 0, 200, 0, 215, 15, 200, 30, 0, 30);
		Text itemText = new Text(name);

		setBackGround(itemBackGround);
		setItemText(itemText);

		getChildren().addAll(itemBackGround, itemText);
	}

	/**
	 * Configura el fondo del item
	 * 
	 * @param itemBackGroud Estructura del fondo para el item
	 */
	private void setBackGround(Polygon itemBackGround) {
		itemBackGround.setStroke(Color.color(1, 1, 1, 0.75));
		itemBackGround.setEffect(new GaussianBlur());
        
		itemBackGround.fillProperty().bind(Bindings.when(pressedProperty()).then(Color.color(0, 0, 0, 0.75))
				.otherwise(Color.color(0, 0, 0, 0.25)));
	}

	/**
	 * Configura el texto del item
	 * 
	 * @param itemText Texto para el item
	 */
	private void setItemText(Text itemText) {
		itemText.setTranslateX(5);
		itemText.setTranslateY(20);

		itemText.setFont(itemFont);
		itemText.setFill(Color.WHITE);
		
		itemText.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );
	}

	/**
	 * Configura la acción del item
	 * 
	 * @param action Acción del item
	 */
	public void setOnAction(Runnable action) {
		setOnMouseClicked(e -> action.run());
	}
}