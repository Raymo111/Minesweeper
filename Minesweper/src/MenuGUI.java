
/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-19
 * Date Finished: 2018-04-
 * Description: User first starts with a welcome message and options to start a new game
 * 				and	to load a game. New game brings out 3 choices - Beginner, Intermediate
 * 				and Expert. A game based on the selected difficulty is then created. Load
 * 				game brings out a file explorer to load a game previously saved to a .mssg
 * 				(MineSweeper Save Game) file.
 */

// Imports required packages
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// MenuGUI class extends JFrame and implements ActionListener
public class MenuGUI extends JFrame implements ActionListener {

	// SerialVersionUID
	private static final long serialVersionUID = 4449469728308361534L;

	// Private class variables
	// Message to display on start of game
	private JLabel message = new JLabel(
			"<html><div style='text-align: center;'>"
					+ "Welcome to Minesweeper!<br>Created by: Raymond Li and David Tuck" + "</div></html>",
			JLabel.CENTER);

	// Button for user to start a new game of Minesweeper
	private JButton newGame = new JButton("New Game");

	// Button for user to load an existing game
	private JButton loadGame = new JButton("Load Game");

	// Buttons for different difficulty categories
	private JButton beginner = new JButton("Beginner (9x9)");
	private JButton intermediate = new JButton("Intermediate (16x16)");
	private JButton expert = new JButton("Expert (16x30)");
	private JButton custom = new JButton("Custom");

	// Back button for user to go back to initial view
	private JButton back = new JButton("Back");

	// Panels to hold message and buttons
	private JPanel messagePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	// File chooser to allow user to load a saved game
	private JFileChooser fc;

	/** Constructor */
	public MenuGUI() {

		// Sets font of message and adds it to messagePanel
		message.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		messagePanel.add(message);

		// Adds action listeners and fonts to buttons
		newGame.addActionListener(this);
		newGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		loadGame.addActionListener(this);
		loadGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		beginner.addActionListener(this);
		beginner.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		intermediate.addActionListener(this);
		intermediate.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		expert.addActionListener(this);
		expert.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		custom.addActionListener(this);
		custom.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		back.addActionListener(this);
		back.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

		// Sets title, size, layout and location of GUI window
		setTitle("Start Game");
		setSize(640, 300);
		setLayout(new GridLayout(2, 1));
		setLocationRelativeTo(null);

		// Sets layout of and adds buttons to buttonPanel
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(newGame);
		buttonPanel.add(loadGame);
		getContentPane().add(messagePanel);
		getContentPane().add(buttonPanel);
		setVisible(true);
	}

	/** Action performed method handles button clicks */
	public void actionPerformed(ActionEvent event) {

		/*
		 * If the newGame button is clicked, removes all buttons and adds difficulty
		 * level buttons and back button, and refreshes the panel and screen
		 */
		if (newGame == event.getSource()) {
			buttonPanel.removeAll();
			buttonPanel.add(beginner);
			buttonPanel.add(intermediate);
			buttonPanel.add(expert);
			buttonPanel.add(back);
			buttonPanel.revalidate();
			buttonPanel.repaint();
			revalidate();
			repaint();
		}

		/*
		 * Sets up file chooser and loads game from file if the loadGame button is
		 * clicked
		 */
		else if (loadGame == event.getSource()) {
			if (fc == null) {
				fc = new JFileChooser();

				// Adds a custom file filter and disables the default (Accept All) file filter
				fc.addChoosableFileFilter(new MSSGFilter());
				fc.setAcceptAllFileFilterUsed(false);
			}

			// Processes the results of getting the user to load a game
			if (fc.showDialog(MenuGUI.this, "Load Game") == JFileChooser.APPROVE_OPTION) {
				File saveGame = fc.getSelectedFile();

				// Resets the file chooser for the next time it's shown
				fc.setSelectedFile(null);

				// Try-catch to handle exceptions
				try {
					// Calls the actual game
					Minesweeper.menufinished();

					// Reads saved game from file
					Minesweeper.readFromFile(saveGame.getName());
				} catch (Exception e) {

					// Prints stack trace if any errors occur
					e.printStackTrace();
				}

				// Shows a popup telling the user that the saved game has been loaded
				JOptionPane.showMessageDialog(rootPane, new JLabel("Savegame loaded!", JLabel.CENTER), "FileLoader",
						JOptionPane.DEFAULT_OPTION);
			}

			/*
			 * Shows a popup telling the user that the saved game has not been loaded and
			 * restarts the MenuGUI window by disposing and recreating it
			 */
			else {
				JOptionPane.showMessageDialog(rootPane, new JLabel("Savegame not loaded. Bad File."), "FileLoader",
						JLabel.CENTER);
				dispose();
				new MenuGUI();
			}
		}

		/*
		 * If the beginner button is clicked, sets size of map to 9x9 and the number of
		 * mines to 10, and proceed to initialize the map with mines and empty squares,
		 * disposing of the menu window when done
		 */
		else if (beginner == event.getSource()) {
			Minesweeper.mapSizeX = 9;
			Minesweeper.mapSizeY = 9;
			Minesweeper.mineCount = 10;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		}

		/*
		 * If the intermediate button is clicked, sets size of map to 16x16 and the
		 * number of mines to 40, and proceed to initialize the map with mines and empty
		 * squares, disposing of the Menu window when done
		 */
		else if (intermediate == event.getSource()) {
			Minesweeper.mapSizeX = 16;
			Minesweeper.mapSizeY = 16;
			Minesweeper.mineCount = 40;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		}

		/*
		 * If the expect button is clicked, sets size of map to 16x30 and the number of
		 * mines to 99, and proceed to initialize the map with mines and empty squares,
		 * disposing of the Menu window when done
		 */
		else if (expert == event.getSource()) {
			Minesweeper.mapSizeX = 16;
			Minesweeper.mapSizeY = 30;
			Minesweeper.mineCount = 99;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		}

		/*
		 * If the custom button is clicked, sets size of map and number of mines to
		 * user-entered values, and proceed to initialize the map with mines and empty
		 * squares, disposing of the Menu window when done
		 */
		else if (custom == event.getSource()) {
			Minesweeper.mapSizeX = 16;
			Minesweeper.mapSizeY = 30;
			Minesweeper.mineCount = 99;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		}

		// If the back button is clicked, restart the MenuGUI
		else if (back == event.getSource()) {
			dispose();
			new MenuGUI();
		}
	}

}