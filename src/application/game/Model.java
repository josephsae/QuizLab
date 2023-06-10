package application.game;

import javafx.geometry.Point2D;
import javafx.fxml.FXML;
import java.io.*;

import java.util.*;

public class Model {
	@FXML
	private int rowCount;
	@FXML
	private int columnCount;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT, NONE
	};

	public enum CellValue {
		EMPTY, FISH, WALL, ENEMY1HOME, ENEMY2HOME, PLAYERHOME
	};

	private CellValue[][] grid;

	private int score;
	private int level;
	private int fishCount;

	private boolean hasLost;
	private boolean hasWon;

	private Point2D playerLocation;
	private Point2D playerVelocity;

	private Point2D enemy1Location;
	private Point2D enemy1Velocity;

	private Point2D enemy2Location;
	private Point2D enemy2Velocity;

	private static Direction lastDirection;
	private static Direction currentDirection;

	/**
	 * Inicia un nuevo juego
	 */
	public Model() {
		this.startNewGame();
	}

	/**
	 * Configura la cuadrícula según el archivo txt, coloca el personaje y los
	 * enemigos en sus ubicaciones iniciales. "W" indica un muro, "E" indica un
	 * cuadrado vacío, "A" indica una manzana, "1" o "2" indica el punto de inicio
	 * de los enemigos "P" indica el punto de inicio del personaje
	 *
	 * @param fileName Nombre del archivo de texto que tiene la configuración del
	 *                 escenario
	 */
	public void initializeLevel(String fileName) {
		File file = new File(fileName);
		try {
			rowCount = 0;
			columnCount = 0;
			grid = null;

			calculateGridSize(file);
			createGrid(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		currentDirection = Direction.NONE;
		lastDirection = Direction.NONE;
	}

	private void calculateGridSize(File file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				try (Scanner lineScanner = new Scanner(line)) {
					while (lineScanner.hasNext()) {
						lineScanner.next();
						this.columnCount++;
					}
				}
				this.rowCount++;
			}
		}
		this.columnCount = columnCount / rowCount;
	}

	private void createGrid(File file) throws FileNotFoundException {
		this.grid = new CellValue[this.rowCount][this.columnCount];
		try (Scanner scanner = new Scanner(file)) {
			for (int row = 0; row < this.rowCount; row++) {
				String line = scanner.nextLine();
				try (Scanner lineScanner = new Scanner(line)) {
					for (int column = 0; column < this.columnCount && lineScanner.hasNext(); column++) {
						String value = lineScanner.next();
						CellValue cellValue = parseCellValue(value, row, column);
						this.grid[row][column] = cellValue;
					}
				}
			}
		}
	}

	private CellValue parseCellValue(String value, int row, int column) {
		CellValue cellValue;
		switch (value) {
		case "W":
			cellValue = CellValue.WALL;
			break;
		case "F":
			cellValue = CellValue.FISH;
			this.fishCount++;
			break;
		case "1":
			cellValue = CellValue.ENEMY1HOME;
			this.enemy1Location = new Point2D(row, column);
			this.enemy1Velocity = new Point2D(-1, 0);
			break;
		case "2":
			cellValue = CellValue.ENEMY2HOME;
			this.enemy2Location = new Point2D(row, column);
			this.enemy2Velocity = new Point2D(-1, 0);
			break;
		case "P":
			cellValue = CellValue.PLAYERHOME;
			this.playerLocation = new Point2D(row, column);
			this.playerVelocity = new Point2D(0, 0);
			break;
		default:
			cellValue = CellValue.EMPTY;
			break;
		}
		return cellValue;
	}

	/**
	 * Establece los valores del inico e inicia el escenario del nivel
	 */
	public void startNewGame() {
		this.hasLost = false;
		this.hasWon = false;
		this.fishCount = 0;
		this.rowCount = 0;
		this.columnCount = 0;
		this.score = 0;
		this.level = 1;
		this.initializeLevel(Controller.getLevelFile(0));
	}

	/**
	 * Inicia el escenario del nivel para el siguiente nivel
	 *
	 */
	public void startNextLevel() {
		if (this.isLevelComplete()) {
			this.level++;
			this.rowCount = 0;
			this.columnCount = 0;
			this.hasWon = false;
			try {
				this.initializeLevel(Controller.getLevelFile(level - 1));
			} catch (ArrayIndexOutOfBoundsException e) {
				// Si no hay más niveles que leer, el juego termina
				this.hasWon = true;
				this.hasLost = true;
				this.level--;
			}
		}
	}

	/**
	 * Mueva el personaje según la dirección indicada por el jugador
	 * 
	 * @param direction La dirección ingresada para que se mueva el personaje
	 */
	public void movePlayer(Direction direction) {
		Point2D potentialPlayerVelocity = changeVelocity(direction);
		Point2D potentialPlayerLocation = playerLocation.add(potentialPlayerVelocity);

		if (direction.equals(lastDirection)) {
			handleSameDirectionMovement(potentialPlayerVelocity, potentialPlayerLocation);
		} else {
			handleDifferentDirectionMovement(direction, potentialPlayerVelocity, potentialPlayerLocation);
		}
	}

	private void handleSameDirectionMovement(Point2D potentialPlayerVelocity, Point2D potentialPlayerLocation) {
		if (gridContainsWall(potentialPlayerLocation)) {
			stopPlayerMovement();
		} else {
			movePlayerToLocation(potentialPlayerVelocity, potentialPlayerLocation);
		}
	}

	private void handleDifferentDirectionMovement(Direction direction, Point2D potentialPlayerVelocity,
			Point2D potentialPlayerLocation) {
		if (gridContainsWall(potentialPlayerLocation)) {
			handleWallCollisionInDifferentDirection(direction);
		} else {
			movePlayerToLocation(potentialPlayerVelocity, potentialPlayerLocation);
			setLastDirection(direction);
		}
	}

	private void handleWallCollisionInDifferentDirection(Direction direction) {
		Point2D potentialPlayerVelocity = changeVelocity(lastDirection);
		Point2D potentialPlayerLocation = playerLocation.add(potentialPlayerVelocity);

		if (gridContainsWall(potentialPlayerLocation)) {
			stopPlayerMovement();
		} else {
			movePlayerToLocation(potentialPlayerVelocity, potentialPlayerLocation);
		}
	}

	private boolean gridContainsWall(Point2D location) {
		return grid[(int) location.getX()][(int) location.getY()] == CellValue.WALL;
	}

	private void stopPlayerMovement() {
		this.playerVelocity = changeVelocity(Direction.NONE);
		setLastDirection(Direction.NONE);
	}

	private void movePlayerToLocation(Point2D velocity, Point2D location) {
		this.playerVelocity = velocity;
		this.playerLocation = location;
	}

	/**
	 * Mueve los enemigos para seguir al personaje
	 * 
	 * @see moveEnemy
	 */
	public void moveEnemies() {
		Point2D[] enemy1Data = moveEnemy(enemy1Velocity, enemy1Location);
		Point2D[] enemy2Data = moveEnemy(enemy2Velocity, enemy2Location);
		enemy1Velocity = enemy1Data[0];
		enemy1Location = enemy1Data[1];
		enemy2Velocity = enemy2Data[0];
		enemy2Location = enemy2Data[1];

	}

	/**
	 * Establece el movimiento del enemigo para buscar al personaje o sino moverse
	 * aleatoriamente hasta colisionar con una pared.
	 * 
	 * @param velocity la velocidad actual del enemigo
	 * @param location la ubicación actual del enemigo
	 * @return Un vector que contiene una nueva velocidad y ubicación para el
	 *         enemigo
	 */
	public Point2D[] moveEnemy(Point2D velocity, Point2D location) {
		Random generator = new Random();

		if (isSameColumnAsPlayer(location)) {
			velocity = moveTowardsPlayerInColumn(location, velocity);
		} else if (isSameRowAsPlayer(location)) {
			velocity = moveTowardsPlayerInRow(location, velocity);
		} else {
			velocity = moveRandomlyUntilWallCollision(location, velocity, generator);
		}

		location = location.add(velocity);

		Point2D[] data = { velocity, location };
		return data;
	}

	private boolean isSameColumnAsPlayer(Point2D location) {
		return location.getY() == playerLocation.getY();
	}

	private boolean isSameRowAsPlayer(Point2D location) {
		return location.getX() == playerLocation.getX();
	}

	private Point2D moveTowardsPlayerInColumn(Point2D location, Point2D velocity) {
		if (location.getX() > playerLocation.getX()) {
			return changeVelocity(Direction.UP);
		} else {
			return changeVelocity(Direction.DOWN);
		}
	}

	private Point2D moveTowardsPlayerInRow(Point2D location, Point2D velocity) {
		if (location.getY() > playerLocation.getY()) {
			return changeVelocity(Direction.LEFT);
		} else {
			return changeVelocity(Direction.RIGHT);
		}
	}

	private Point2D moveRandomlyUntilWallCollision(Point2D location, Point2D velocity, Random generator) {
		Point2D potentialLocation = location.add(velocity);

		while (gridContainsWall(potentialLocation)) {
			Direction randomDirection = getRandomDirection(generator);
			velocity = changeVelocity(randomDirection);
			potentialLocation = location.add(velocity);
		}

		return velocity;
	}

	private Direction getRandomDirection(Random generator) {
		int randomNum = generator.nextInt(4);
		return intToDirection(randomNum);
	}

	/**
	 * Convierte un número entero entre 0 y 3 a una dirección
	 * 
	 * @param x Número entero entre 0 y 3
	 * @return La dirección correspondiente
	 */
	public Direction intToDirection(int x) {
		switch (x) {
		case 0:
			return Direction.LEFT;
		case 1:
			return Direction.RIGHT;
		case 2:
			return Direction.UP;
		default:
			return Direction.DOWN;
		}
	}

	/**
	 * Restablece la ubicación y la velocidad del enemigo a su estado de origen
	 */
	private void sendEnemyHome(CellValue enemyHome, Point2D enemyLocation, Point2D enemyVelocity) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (grid[row][column] == enemyHome) {
					enemyLocation = new Point2D(row, column);
				}
			}
		}
		enemyVelocity = new Point2D(-1, 0);
	}

	public void sendEnemy1Home() {
		sendEnemyHome(CellValue.ENEMY1HOME, enemy1Location, enemy1Velocity);
	}

	public void sendEnemy2Home() {
		sendEnemyHome(CellValue.ENEMY2HOME, enemy2Location, enemy2Velocity);
	}

	/**
	 * Actualiza el modelo para reflejar el movimiento del personaje, los enemigos y
	 * el cambio de estado de las manzanas
	 * 
	 * @param direction La dirección ingresada más recientemente para que el
	 *                  personaje se mueva
	 */
	public void step(Direction direction) {
		movePlayer(direction);
		updateFish();
		checkEnemyCollision();
		moveEnemies();
		checkEnemyCollision();
		checkLevelCompletion();
	}

	private void updateFish() {
		CellValue playerLocationCellValue = grid[(int) playerLocation.getX()][(int) playerLocation.getY()];
		if (playerLocationCellValue == CellValue.FISH) {
			grid[(int) playerLocation.getX()][(int) playerLocation.getY()] = CellValue.EMPTY;
			fishCount--;
			score += 10;
		}
	}

	private void checkEnemyCollision() {
		if (playerLocation.equals(enemy1Location) || playerLocation.equals(enemy2Location)) {
			hasLost = true;
			playerVelocity = new Point2D(0, 0);
		}
	}

	private void checkLevelCompletion() {
		if (isLevelComplete()) {
			playerVelocity = new Point2D(0, 0);
			startNextLevel();
		}
	}

	/**
	 * Conecta cada dirección al vector de velocidad correspondiente (Izquierda =
	 * (-1,0), Derecha = (1,0), Arriba = (0,-1), Abajo = (0,1))
	 * 
	 * @param direction dirección de la entidad
	 * @return Vector de velocidad
	 */
	public Point2D changeVelocity(Direction direction) {
		switch (direction) {
		case LEFT:
			return new Point2D(0, -1);
		case RIGHT:
			return new Point2D(0, 1);
		case UP:
			return new Point2D(-1, 0);
		case DOWN:
			return new Point2D(1, 0);
		default:
			return new Point2D(0, 0);
		}
	}

	/**
	 * Determia si se ganó
	 * 
	 * @return boolean
	 */
	public boolean hasWonGame() {
		return hasWon;
	}

	/**
	 * Determia si se perdió
	 * 
	 * @return boolean
	 */
	public boolean hasLostGame() {
		return hasLost;
	}

	/**
	 * Determia si el nivel está completo cuando hay cero manzanas
	 * 
	 * @return boolean
	 */
	public boolean isLevelComplete() {
		return this.fishCount == 0;
	}

	/**
	 * Obtiene la cuadrícula
	 * 
	 * @return
	 */
	public CellValue[][] getGrid() {
		return grid;
	}

	/**
	 * Obtiene valor de la celda
	 * 
	 * @param fila
	 * @param columna
	 * @return Valor de la celda (row, column)
	 */
	public CellValue getCellValue(int row, int column) {
		assert row >= 0 && row < this.grid.length && column >= 0 && column < this.grid[0].length;
		return this.grid[row][column];
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public static Direction getLastDirection() {
		return lastDirection;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	/**
	 * @return Obtiene el número de manzanas restantes
	 */
	public int getFishCount() {
		return fishCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Point2D getPlayerLocation() {
		return playerLocation;
	}

	public Point2D getEnemy1Location() {
		return enemy1Location;
	}

	public Point2D getEnemy2Location() {
		return enemy2Location;
	}

	public Point2D getPlayerVelocity() {
		return playerVelocity;
	}

	public Point2D getEnemy1Velocity() {
		return enemy1Velocity;
	}

	public Point2D getEnemy2Velocity() {
		return enemy2Velocity;
	}

	public void setCurrentDirection(Direction direction) {
		currentDirection = direction;
	}

	public void setLastDirection(Direction direction) {
		lastDirection = direction;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Añade a la puntuación
	 *
	 * @param puntos
	 */
	public void addToScore(int points) {
		this.score += points;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return Establece el número de manzanas restantes
	 */
	public void setFishCount(int fishCount) {
		this.fishCount = fishCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public void setPlayerLocation(Point2D playerLocation) {
		this.playerLocation = playerLocation;
	}

	public void setEnemy1Location(Point2D enemy1Location) {
		this.enemy1Location = enemy1Location;
	}

	public void setEnemy2Location(Point2D enemy2Location) {
		this.enemy2Location = enemy2Location;
	}

	public void setPlayerVelocity(Point2D velocity) {
		this.playerVelocity = velocity;
	}

	public void setEnemy1Velocity(Point2D enemy1Velocity) {
		this.enemy1Velocity = enemy1Velocity;
	}

	public void setEnemy2Velocity(Point2D enemy2Velocity) {
		this.enemy2Velocity = enemy2Velocity;
	}
}