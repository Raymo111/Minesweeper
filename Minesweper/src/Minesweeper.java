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

public class Minesweeper {

	// Class variables
	private JButton[][] buttons = new JButton[5][5];

	private static Random random = new Random();
	public static int roundCount = 0;
	static Scanner input = new Scanner(System.in);
	public static int mapSizeX = 5;
	public static int mapSizeY = 5;

	public static void main(String[] args) { 

		int numOffMines = 5;

		mineSweeperTypes[][] masterMap = new mineSweeperTypes[mapSizeX][mapSizeY];// underlying map of mines
		fillWithEmpty(masterMap);
		genMines(numOffMines, masterMap);

		mineSweeperTypes[][] userMap = new mineSweeperTypes[mapSizeX][mapSizeY];// map that is visible to user
		fillWithEmpty(userMap);
		do {
			System.out.println("Enter X");
			int guessX = input.nextInt();// temp method of input
			System.out.println("Enter Y");
			int guessY = input.nextInt();// temp method of input
			if (checkForMine(guessX, guessY, masterMap)) {
				int numOfMines = genNumOfMines(guessX, guessY, userMap);

			} else {

			}

		} while (true);
	}

	/**
	 * Set's a given number of mines randomly thoughtout the master map
	 * 
	 * @param numOffMines
	 * @param masterMap
	 */
	public static void genMines(int numOffMines, mineSweeperTypes[][] masterMap) {
		int tempX;
		int tempY;
		for (int i = 0; i < numOffMines; i++) {
			do {
				tempX = (int) (Math.random() * mapSizeX);
				tempY = (int) (Math.random() * mapSizeY);
			} while (masterMap[tempX][tempY] != mineSweeperTypes.mine);
			masterMap[tempX][tempY] = mineSweeperTypes.mine;
		}
	}

	/**
	 * 
	 * @param map
	 */
	public static void fillWithEmpty(mineSweeperTypes[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = mineSweeperTypes.empty;
			}
		}
	}

	public enum mineSweeperTypes {
		mine, empty, flaged, unknown, quetionMark
	}

	/**
	 * 
	 * @param guessX
	 * @param guessY
	 * @param masterMap
	 * @return
	 */
	public static boolean checkForMine(int guessX, int guessY, mineSweeperTypes[][] masterMap) {
		if (roundCount == 0 && masterMap[guessX][guessY] == mineSweeperTypes.mine) {// moves the mine

			int tempX;
			int tempY;
			masterMap[guessX][guessY] = mineSweeperTypes.empty;
			do {
				tempX = (int) (Math.random() * mapSizeX);
				tempY = (int) (Math.random() * mapSizeY);
			} while (masterMap[tempX][tempY] != mineSweeperTypes.mine);
			masterMap[tempX][tempY] = mineSweeperTypes.mine;
			return true;

		} else {
			if (masterMap[guessX][guessY] == mineSweeperTypes.mine) {// checks to see if position has a mine
				return false;
			} else {// returns true if there is no mine
				return true;
			}
		}
	}

	/**
	 * 
	 * @param guessX
	 * @param guessY
	 * @param userMap
	 * @return
	 */
	public static int genNumOfMines(int guessX, int guessY, mineSweeperTypes[][] userMap) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				try {
					if (userMap[guessX + i][guessY + j] == mineSweeperTypes.mine) {
						count++;
					}
				} catch (Exception e) {

				}
			}
		}
		return count;
	}
}
