package application.menu;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import application.game.Controller;

/**
 * Esta clase define un objeto menú
 * 
 * @see Application
 */
public class Menu extends Application {

	private static final int WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
	private static final int HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();
	private Stage primaryStage;

	private List<Pair<String, Runnable>> menuData = Arrays.asList(new Pair<String, Runnable>("Jugar", () -> {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/game/maze.fxml"));
			Parent root = loader.load();
			primaryStage.setTitle("QuizLab");
			Controller controller = loader.getController();
			root.setOnKeyPressed(controller);
	        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT-20));
			primaryStage.show();
			root.requestFocus();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}), new Pair<String, Runnable>("Opciones de Juego", () -> {
	}), new Pair<String, Runnable>("Creditos", () -> {
	}), new Pair<String, Runnable>("Salir al escritorio", Platform::exit));

	private Pane root = new Pane();
	private VBox menuBox = new VBox(-5);
	private Line line;

	/**
	 * Crea el nodo raíz con toda la configuración gráfica del menú
	 * 
	 * @return Nodo raíz para el escenario gráfico
	 */
	private Parent createContent() {
		addBackground();
		addTitle();

		double lineX = WIDTH / 2 - 100;
		double lineY = HEIGHT / 3 + 50;

		addLine(lineX, lineY);
		addMenuOptions(lineX + 5, lineY + 5);

		startAnimation();

		return root;
	}

	/**
	 * Añade el fondo del menú
	 */
	private void addBackground() {
		ImageView imageView = new ImageView(
				new Image(getClass().getResource("/application/res/3d-maze.png").toExternalForm()));
		imageView.setFitWidth(WIDTH);
		imageView.setFitHeight(HEIGHT);

		root.getChildren().add(imageView);
	}

	/**
	 * Añade el título del menú
	 */
	private void addTitle() {
		MenuTitle title = new MenuTitle("QuizLab");
		title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
		title.setTranslateY(HEIGHT / 3);

		root.getChildren().add(title);
	}

	/**
	 * Añade una línea en el menú
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	private void addLine(double x, double y) {
		line = new Line(x, y, x, y + 170);
		line.setStrokeWidth(3);
		line.setStroke(Color.WHITE);
		line.setScaleY(0);

		root.getChildren().add(line);
	}

	/**
	 * Inicia la animación
	 */
	private void startAnimation() {
		ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
		st.setToY(1);
		st.setOnFinished(e -> {

			for (int i = 0; i < menuBox.getChildren().size(); i++) {
				Node n = menuBox.getChildren().get(i);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
				tt.setToX(0);
				tt.setOnFinished(e2 -> n.setClip(null));
				tt.play();
			}
		});
		st.play();
	}

	/**
	 * Añade las opciones del menú
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	private void addMenuOptions(double x, double y) {
		menuBox.setTranslateX(x);
		menuBox.setTranslateY(y);
		menuData.forEach(data -> {
			MenuItem item = new MenuItem(data.getKey());
			item.setOnAction(data.getValue());
			item.setTranslateX(-300);

			Rectangle clip = new Rectangle(300, 30);
			clip.translateXProperty().bind(item.translateXProperty().negate());

			item.setClip(clip);

			menuBox.getChildren().addAll(item);
		});

		root.getChildren().add(menuBox);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Punto de entrada para la aplicación. Se llama al método start después de que
	 * el sistema esté listo para que la aplicación comience a ejecutarse.
	 * 
	 * @throws Exception Si se presenta cualquier excepción
	 * @param primaryStage Descripción del primer parámetro.
	 * @see Application#start
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Scene scene = new Scene(createContent());

		primaryStage.setTitle("QuizLab");
		primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
		primaryStage.show();
	}

}