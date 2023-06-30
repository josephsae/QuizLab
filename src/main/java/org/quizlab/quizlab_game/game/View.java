package org.quizlab.quizlab_game.game;

import org.quizlab.quizlab_game.game.Model.CellValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * Vista del juego
 *
 */
public class View extends Group {
	public final static double CELL_WIDTH = 30.0;

	@FXML
	private int rowCount;
	@FXML
	private int columnCount;
	
	private ImageView[][] cellViews;
	
	private Image playerRightImage;
	private Image playerUpImage;
	private Image playerDownImage;
	private Image playerLeftImage;
	private Image enemy1Image;
	private Image enemy2Image;
	private Image wallImage;
	private Image fish;

	/**
	 * Inicializa la vista cargando las imagenes
	 */
	public View() {
		loadImages();
	}

	/**
	 * Construye una cuadrícula vacía de ImageViews
	 */
	private void initializeGrid() {
		if (this.rowCount <= 0 || this.columnCount <= 0) {
			return;
		}

		cellViews = new ImageView[this.rowCount][this.columnCount];

		for (int row = 0; row < this.rowCount; row++) {
			for (int column = 0; column < this.columnCount; column++) {
				ImageView imageView = createImageView(row, column);
				this.cellViews[row][column] = imageView;
				getChildren().add(imageView);
			}
		}
	}

	/**
	 * Crea una imagen para la vista
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private ImageView createImageView(int row, int column) {
		ImageView imageView = new ImageView();
		imageView.setX(column * CELL_WIDTH);
		imageView.setY(row * CELL_WIDTH);
		imageView.setFitWidth(CELL_WIDTH);
		imageView.setFitHeight(CELL_WIDTH);
		return imageView;
	}

	/**
	 * Actualiza la vista para reflejar el estado del modelo
	 *
	 * @param model
	 */
	public void update(Model model) {
		assert model.getRowCount() == this.rowCount && model.getColumnCount() == this.columnCount;

		for (int row = 0; row < this.rowCount; row++) {
			for (int column = 0; column < this.columnCount; column++) {
				updateCellViews(model, row, column);
				updatePlayerImage(model, row, column);
				updateEnemiesImages(model, row, column);
			}
		}
	}

	/**
	 * Actualiza las celdas
	 * 
	 * @param model
	 * @param row
	 * @param column
	 */
	private void updateCellViews(Model model, Integer row, Integer column) {
		CellValue value = model.getCellValue(row, column);
		if (value == CellValue.WALL) {
			this.cellViews[row][column].setImage(this.wallImage);
		} else if (value == CellValue.FISH) {
			this.cellViews[row][column].setImage(this.fish);
		} else {
			this.cellViews[row][column].setImage(null);
		}
	}

	/**
	 * Actualiza las imagenes del jugador
	 * 
	 * @param model
	 * @param row
	 * @param column
	 */
	private void updatePlayerImage(Model model, Integer row, Integer column) {
		int playerRow = (int) model.getPlayerLocation().getX();
		int playerColumn = (int) model.getPlayerLocation().getY();
		Model.Direction lastDirection = Model.getLastDirection();

		if (row == playerRow && column == playerColumn) {
			switch (lastDirection) {
			case UP:
				this.cellViews[playerRow][playerColumn].setImage(this.playerUpImage);
				break;
			case RIGHT:
				this.cellViews[playerRow][playerColumn].setImage(this.playerRightImage);
				break;
			case LEFT:
				this.cellViews[playerRow][playerColumn].setImage(this.playerLeftImage);
				break;
			case DOWN:
			case NONE:
				this.cellViews[playerRow][playerColumn].setImage(this.playerDownImage);
				break;
			}
		}
	}

	/**
	 * Actualiza las imagenes de los enemigos en el escenario
	 * 
	 * @param model
	 * @param row
	 * @param column
	 */
	private void updateEnemiesImages(Model model, Integer row, Integer column) {
		int enemy1Row = (int) model.getEnemy1Location().getX();
		int enemy1Column = (int) model.getEnemy1Location().getY();
		int enemy2Row = (int) model.getEnemy2Location().getX();
		int enemy2Column = (int) model.getEnemy2Location().getY();

		if (row == enemy1Row && column == enemy1Column) {
			this.cellViews[row][column].setImage(this.enemy1Image);
		}
		if (row == enemy2Row && column == enemy2Column) {
			this.cellViews[row][column].setImage(this.enemy2Image);
		}
	}

	/**
	 * Inicializa las variables con las imagenes de los archivos
	 */
	private void loadImages() {
		this.playerRightImage = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/player-right.gif"));
		this.playerUpImage = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/player-up.gif"));
		this.playerDownImage = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/player-down.gif"));
		this.playerLeftImage = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/player-left.gif"));
		this.enemy1Image = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/enemy-red.gif"));
		this.enemy2Image = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/enemy-yellow.gif"));
		this.wallImage = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/wall.png"));
		this.fish = new Image(getClass().getResourceAsStream("/org/quizlab/quizlab_game/fish.png"));
	}

	/**
	 * Obtiene número de filas del escenario 
	 * 
	 * @return Número de filas del escenario 
	 */
	public int getRowCount() {
		return this.rowCount;
	}

	/**
	 * Obtiene número de columnas del escenario 
	 * 
	 * @return Número de columnas del escenario 
	 */
	public int getColumnCount() {
		return this.columnCount;
	}

	/**
	 * Establece número de filas del escenario 
	 * 
	 * @param rowCount Número de filas del escenario 
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		this.initializeGrid();
	}

	/**
	 * Obtiene número de columnas del escenario 
	 * 
	 * @param columnCount Número de columnas del escenario 
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
		this.initializeGrid();
	}
}
