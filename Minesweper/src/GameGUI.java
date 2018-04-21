/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-18
 * Date Finished: 2018-04-
 * Description: Minesweeper game's main GUI
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -3978640272114053636L;

	// Private class Variables
	private JButton[][] buttons;
	private JButton newGame = new JButton("New Game");
	private JTextPane clock = new JTextPane();
	private JTextPane minesLeft = new JTextPane();
	private JPanel gamePanel = new JPanel();
	private JPanel controlPanel = new JPanel();

	public GameGUI(int mapSizeX, int mapSizeY) {

		// Initializes panels and
		gamePanel.setLayout(new GridLayout(mapSizeX, mapSizeY));
		controlPanel.setLayout(new FlowLayout());
		buttons = new JButton[mapSizeX][mapSizeY];

		// Initializes buttons, adds action listeners and adds buttons to the gamePanel
		for (int i = 0; i < mapSizeX; i++)
			for (int j = 0; j < mapSizeY; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(this);
				gamePanel.add(buttons[i][j]);
			}

		// Adds button and textPanes to controlPanel
		controlPanel.add(clock);
		controlPanel.add(newGame);
		controlPanel.add(minesLeft);

		// Adds panels to frame
		getContentPane().add(controlPanel);
		getContentPane().add(gamePanel);

		// Sets title, size, layout and location of GUI window
		setTitle("Minesweeper");
		setSize(840, 840);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);

		/*
		 * Makes the program terminate on click of close window, and sets window to
		 * visible
		 */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		for (int i = 0; i < buttons.length; i++)
			for (int j = 0; j < buttons[i].length; j++)
				if (buttons[i][j] == event.getSource()) {
					Minesweeper.checkForMine(i, j);
					Minesweeper.genNumOfMines(i, j);
					Minesweeper.updateScore();
				}
	}
}