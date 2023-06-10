package application.game;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {
	final private static double FRAMES_PER_SECOND = 5.0;

	private static final String[] levelFiles = { "src/levels/level_1.txt", "src/levels/level-2.txt",
			"src/levels/level-3.txt" };

	private String gameOverStyle = "-fx-font-size: 250%; -fx-font-family: 'Iceland'; -fx-text-fill: ";
	@FXML
	private Label scoreLabel;
	@FXML
	private Label levelLabel;
	@FXML
	private Label gameOverLabel;
	@FXML
	private Label newGameLabel;
	@FXML
	private View view;
	private Model model;

	private Timer timer;
	private boolean isPaused;

	/**
	 * Inicializa el controlador sin pausa
	 */
	public Controller() {
		this.isPaused = false;
	}

	/**
	 * Inicializa y actualice el modelo y la vista desde el primer archivo txt e
	 * inicie el temporizador.
	 */
	public void initialize() {
		this.model = new Model();
		this.update(Model.Direction.NONE);
		this.startTimer();
	}

	/**
	 * Programa el modelo para que se actualice según el temporizador.
	 */
	private void startTimer() {
		this.timer = new java.util.Timer();
		long frameTimeInMilliseconds = (long) (1000.0 / FRAMES_PER_SECOND);

	    TimerTask timerTask = new TimerTask() {
	        @Override
	        public void run() {
	            Platform.runLater(() -> update(model.getCurrentDirection()));
	        }
	    };

		this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
	}

	/**
	 * Controla la ejecución del juego y actualiza la vista
	 * 
	 * @param direction La dirección ingresada para que se mueva el personaje
	 */
	private void update(Model.Direction direction) {
		this.model.step(direction);
		this.view.update(model);
		this.scoreLabel.setText(String.format("Puntaje: %d", this.model.getScore()));
		this.levelLabel.setText(String.format("Nivel: %d", this.model.getLevel()));
		checkGameOver();
	}

	/**
	 * Toma la entrada del teclado del usuario para controlar el juego
	 * 
	 * @param keyEvent Entrada del usuario por teclado
	 */
	@Override
	public void handle(KeyEvent keyEvent) {
		boolean keyRecognized = true;
		KeyCode code = keyEvent.getCode();
		Model.Direction direction = Model.Direction.NONE;
		if (code == KeyCode.LEFT) {
			direction = Model.Direction.LEFT;
		} else if (code == KeyCode.RIGHT) {
			direction = Model.Direction.RIGHT;
		} else if (code == KeyCode.UP) {
			direction = Model.Direction.UP;
		} else if (code == KeyCode.DOWN) {
			direction = Model.Direction.DOWN;
		} else if (code == KeyCode.N) {
			startNewGame();
		} else {
			keyRecognized = false;
		}
		if (keyRecognized) {
			keyEvent.consume();
			this.model.setCurrentDirection(direction);
		}
	}
	/**
	 * Inicia un nuevo juego
	 */
	private void startNewGame() {
		pause();
		this.model.startNewGame();
		setGameOverLabel("", "");
		this.isPaused = false;
		this.startTimer();
	}
	
	/**
	 * Valida si se terminó el juego para mostrar el mensaje apropiado
	 */
	private void checkGameOver() {
		if (model.hasLostGame()) {
			setGameOverLabel("Perdiste", "red");
			pause();
		}
		if (model.hasWonGame()) {
			setGameOverLabel("¡Ganaste!", "green");
		}
	}

	/**
	 * Pausar el temporizador
	 */
	public void pause() {
		this.timer.cancel();
		this.isPaused = true;
	}

	public double getBoardWidth() {
		return View.CELL_WIDTH * this.view.getColumnCount();
	}

	public double getBoardHeight() {
		return View.CELL_WIDTH * this.view.getRowCount();
	}

	public static String getLevelFile(int x) {
		return levelFiles[x];
	}

	/**
	 * Obtiene estado para saber si el juego está pausado o no
	 */
	public boolean getPaused() {
		return isPaused;
	}

	/**
	 * Establece el texto y estilo para mostrar de un label
	 */
	public void setGameOverLabel(String text, String color) {
		this.gameOverLabel.setText(String.format(text));
		if (color != "") {
			this.gameOverLabel.setStyle(gameOverStyle + color + ";");
		}
	}
}
