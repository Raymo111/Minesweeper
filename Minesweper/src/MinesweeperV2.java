/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-18
 * Description: Minesweeper game
 */

//Imports java GUI classes
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperV2 {

	// Class variables
	private JButton[][] buttons = new JButton[5][5];
	public static int roundCount = 0;
	static Scanner input = new Scanner(System.in);
	public static int mapSizeX = 6;
	public static int mapSizeY = 6;

	public static void main(String[] args) {

		do {// start of a game
			Mine myMine[][] = new Mine[mapSizeX][mapSizeY];
			for (int i = 0; i < mapSizeX; i++) {
				for (int j = 0; j < mapSizeY; j++) {
					myMine[i][j] = new Mine(false);// adds objects to myMine
				}
			}

			genMines(5, myMine, mapSizeX + 2, mapSizeY + 2);// these variable must be determined before hand

		} while (true);

	}

	/**
	 * Set's a given number of mines randomly thought out the mine field
	 * 
	 * @param numOffMines
	 * @param myMine
	 */
	public static void genMines(int numOffMines, Mine[][] myMine, int notX, int notY) {
		int tempX;
		int tempY;
		for (int i = 0; i < numOffMines; i++) {
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
	 */
	public static void fillWithEmpty(Mine[][] myMine) {
		for (int i = 0; i < myMine.length; i++) {
			for (int j = 0; j < myMine[0].length; j++) {
				myMine[i][j].changeType(MinesweeperTypes.EMPTY);
				;
			}
		}
	}

	/**
	 * checks to see if a mine is at the users guess location
	 * 
	 * @param guessX
	 * @param guessY
	 * @param myMine
	 * @return
	 */
	public static boolean checkForMine(int guessX, int guessY, Mine[][] myMine) {
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
	 * @param guessY
	 * @param myMine
	 * @return
	 */
	public static int genNumOfMines(int guessX, int guessY, Mine[][] myMine) {
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
}
