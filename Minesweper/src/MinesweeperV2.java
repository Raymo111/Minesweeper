/*
 * Authors: David Tuck, Raymond Li
 * Date started: 2018-04-18
 * Date Finished: 2018-04-
 * Description: Minesweeper game
 */

//Imports java GUI classes
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class MinesweeperV2 implements Serializable {

	// Class variables
	private JButton[][] buttons = new JButton[5][5];
	public static int roundCount = 0;
	static Scanner input = new Scanner(System.in);
	public static int mapSizeX = 6;
	public static int mapSizeY = 6;
	public static int mapSizeYLimit = 24;//limit on the custom dimension Y
	public static int mapSizeXLimit = 30;//limit on the custom dimension X
	public static int numOffMines = 0;// get this from menu gui
	public static long additionalTime;
	public static Integer numOfMinesLeft;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {

		do {// start of a game
			// run menu GUI here
			// can we run the GUI in a different class so that the main method is less
			// crowded

			boolean newGame = false;// get from gui

			Square myMine[][] = new Square[mapSizeX][mapSizeY];
			for (int i = 0; i < mapSizeX; i++) {
				for (int j = 0; j < mapSizeY; j++) {
					myMine[i][j] = new Square();// adds objects to myMine
				}
			}

			if (newGame) {//
				fillWithEmpty(myMine);
				genMines(numOffMines, myMine, mapSizeX + 2, mapSizeY + 2);// these variable must be determined before
																			// hand
			} else {//
				readFromFile(myMine, "Get name of file from user");
			}
		} while (true);

	}

	/**
	 * Set's a given number of mines randomly thought out the mine field
	 * 
	 * @param countOf
	 *            the number of total mines that should be in the whole mine field
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 */
	public static void genMines(int countOf, Square[][] myMine, int notX, int notY) {
		int tempX;
		int tempY;
		for (int i = 0; i < countOf; i++) {
			do {
				tempX = (int) (Math.random() * mapSizeX);
				tempY = (int) (Math.random() * mapSizeY);
			} while (myMine[tempX][tempY].checkMine() || tempX == notX & tempY == notY);
			myMine[tempX][tempY].setToMine();
		}
	}

	/**
	 * Sets the array of myMine to all empty
	 * 
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 */
	public static void fillWithEmpty(Square[][] myMine) {
		for (int i = 0; i < myMine.length; i++) {
			for (int j = 0; j < myMine[0].length; j++) {
				myMine[i][j].changeType(MinesweeperTypes.EMPTY);
				;
			}
		}
	}

	/**
	 * Checks to see if a mine is at the users guess location.
	 * 
	 * @param guessX
	 *            The X coordinate of the users current click.
	 * @param guessY
	 *            The Y coordinate of the users current click.
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * @return true if the users current guess location IS in the location of a mine
	 *         false if the users current guess location is NOT the location of a
	 *         mine
	 */
	public static boolean checkForMine(int guessX, int guessY, Square[][] myMine) {
		if (roundCount == 0 && myMine[guessX][guessY].checkMine()) {// moves the mine
			myMine[guessX][guessY].removeMine();// Removes the mine
			genMines(1, myMine, guessX, guessY);// generates one move mine in a different location
			return true;
		} else {
			if (myMine[guessX][guessY].checkMine()) {// checks to see if position has a mine
				return false;// returns false if user has clicked on a mine
			} else {// returns true if there is no mine
				return true;
			}
		}
	}

	/**
	 * finds the number of mines arrowed a guess location z-
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
	public static int genNumOfMines(int guessX, int guessY, Square[][] myMine) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				try {
					if (myMine[guessX + i][guessY + j].checkMine()) {
						count++;
					}
				} catch (Exception e) {
				}
			}
		}
		return count;
	}

	/**
	 * Saves a current game to a file.
	 * 
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * @param fileName
	 *            The name the user fishes to save the file as.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeToFile(Square[][] myMine, String fileName) throws FileNotFoundException, IOException {
		try (FileOutputStream f = new FileOutputStream(fileName + ".txt"); ObjectOutput s = new ObjectOutputStream(f)) {
			s.writeObject(myMine);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file.");// GUI needs to display error
		} catch (IOException e) {
			System.err.println(e);// GUI needs to display error
		}
	}

	/**
	 * Read a saved game from a file and outputs this to myMine array.
	 * 
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * @param fileName
	 *            The name the user has the games saved as.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void readFromFile(Square[][] myMine, String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		try (FileInputStream in = new FileInputStream(fileName + ".txt");
				ObjectInputStream s = new ObjectInputStream(in)) {
			myMine = (Square[][]) s.readObject();
		} catch (FileNotFoundException e) {
			System.err.println(e);// GUI needs to display error
		}
	}

	/**
	 * Updates the number of mines that are left. This method should be run right
	 * after every time the user right click a cell
	 * 
	 * @param currentScore
	 *            The number of mines left.
	 * @param myMine
	 *            2D array for objects that holds all of the information about the
	 *            game board. Each object is is a different square on the board.
	 * 
	 */
	public static void updateScore(Integer currentScore, Square[][] myMine) {
		int counter = 0;
		for (int i = 0; i < myMine.length; i++) {
			for (int j = 0; j < myMine.length; j++) {
				if (myMine[i][j].getMineType() == MinesweeperTypes.FLAG) {
					counter++;
				}
			}
		}
		currentScore = numOffMines - counter;

	}
}