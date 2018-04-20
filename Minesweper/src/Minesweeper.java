/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-18
 * Date Finished: 2018-04-
 * Description: Minesweeper game
 */

//Imports java io and Random classes
import java.io.*;
import java.util.Random;

public class Minesweeper {

	// Class variables
	private static Square map[][];
	public static int roundCount = 0, mapSizeX, mapSizeY, mineCount;
	public static final int mapSizeYLimit = 24;// limit on the custom dimension Y
	public static final int mapSizeXLimit = 30;// limit on the custom dimension X
	public static final int numOfMinesLimit = 99;// limit on the number of mines
	public static long additionalTime;
	public static boolean newGame = false;
	public static Integer numOfMinesLeft;
	private static Random random = new Random();

	public static void main(String[] args) {

		// Start menu instance
		new MenuGUI();
	}

	public static void menufinished() throws IOException, ClassNotFoundException {
		map = new Square[mapSizeX][mapSizeY];
		for (int i = 0; i < mapSizeX; i++) {
			for (int j = 0; j < mapSizeY; j++) {
				map[i][j] = new Square();
			}
		}
	}

	/**
	 * Sets a given number of mines randomly thought out the mine field Set's a
	 * given number of mines randomly thought out the mine field
	 *
	 * @param numOffMines
	 *            the number of total mines that should be in the whole mine field
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 */
	public static void genMines() {
		int newX, newY;
		for (int i = 0; i < mineCount; i++) {
			newX = random.nextInt(mapSizeX);
			newY = random.nextInt(mapSizeY);
			map[newX][newY].setToMine();
		}
	}

	/**
	 * Sets the array of myMine to all empty
	 *
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 */
	public static void fillWithEmpty() {
		for (int i = 0; i < mapSizeX; i++) {
			for (int j = 0; j < mapSizeY; j++)
				map[i][j].changeType(MinesweeperTypes.EMPTY);
		}
	}

	/**
	 * Checks to see if a mine is at the user's clicked location.
	 *
	 * @param clickX
	 *            The X coordinate of the users current click.
	 * @param clickY
	 *            The Y coordinate of the users current click.
	 * @param map
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * @return true if the users current guess location IS in the location of a mine
	 *         false if the users current guess location is NOT the location of a
	 *         mine
	 */
	public static boolean checkForMine(int clickX, int clickY) {
		if (roundCount == 0 && map[clickX][clickY].checkMine()) {// moves the mine
			map[clickX][clickY].removeMine();// Removes the mine
			// generates one move mine in a different location
			int newX, newY;
			do {
				newX = random.nextInt(mapSizeX);
				newY = random.nextInt(mapSizeY);
			} while (map[newX][newY].checkMine() || newX == clickX & newY == clickY);
			map[newX][newY].setToMine();
			return true;
		} else {
			if (map[clickX][clickY].checkMine()) {// checks to see if position has a mine
				return false;// returns true if user has clicked on a mine
			} else {// returns false if there is no mine
				return true;
			}
		}
	}

	/**
	 * finds the number of mines arrowed a guess location (x,y)
	 *
	 * @param guessX
	 *            The X coordinate of the users current click.
	 * @param guessY
	 *            The Y coordinate of the users current click.
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * @return the number of mines that are in the surrounding area of guessX and
	 *         GuessY coordinates
	 */
	public static int genNumOfMines(int clickX, int clickY) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				try {
					if (map[clickX + i][clickY + j].checkMine()) {
						count++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}

	/**
	 * Saves a current game to a file
	 *
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeToFile(String fileName) throws FileNotFoundException, IOException {
		try (FileOutputStream f = new FileOutputStream(fileName + ".mssg");
				ObjectOutput s = new ObjectOutputStream(f)) {
			s.writeObject(map);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file.");// GUI needs to display error
		} catch (IOException e) {
			System.err.println(e);// GUI needs to display error
		}
	}

	/**
	 * read a saved game from a file and outputs this to myMine array
	 *
	 * @param myMine
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void readFromFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		try (FileInputStream in = new FileInputStream(fileName); ObjectInputStream s = new ObjectInputStream(in)) {
			map = (Square[][]) s.readObject();
		} catch (FileNotFoundException e) {
			System.err.println(e);// GUI needs to display error
		}
	}

	/**
	 * Updates the number of mines that are left. This method should be run right
	 * after every time the user right click a cell
	 * 
	 * 
	 */
	public static void updateScore() {
		int counter = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j].getMineType() == MinesweeperTypes.FLAG) {
					counter++;
				}
			}
		}
		numOfMinesLeft = mineCount - counter;
	}
}
