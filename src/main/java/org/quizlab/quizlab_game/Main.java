package org.quizlab.quizlab_game;
	
import org.quizlab.quizlab_game.menu.Menu;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Clase principal
 * 
 * @author
 * 		Luis Munevar 
 * 		Joseymi Pajaro 
 * 		Joseph Caicedo
 *  @see Application
 */
public class Main extends Application {
/**
 * {@inheritDoc}
 * 
 * Punto de entrada para la aplicación. Se llama al método start 
 * después de que el sistema esté listo para que la aplicación 
 * comience a ejecutarse.
 * 
 * @param primaryStage Descripción del primer parámetro.
 * @see Application#start
 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Menu menu = new Menu();
			menu.start(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Llama al método launch para iniciar una aplicación independiente. 
 * 
 * @param args
 * @see Application#launch
 */
	public static void main(String[] args) {
		launch(args);
	}
}
