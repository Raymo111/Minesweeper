/*
 * Authors: Raymond Li, David Tuck
 * Date: 06/05/2018
 * Description: User first starts with a welcome message and options to start a new game
 * 				and	to load a game. New game brings out 3 choices - Beginner, Intermediate
 * 				and Expert. A game based on the selected difficulty is then created. Load
 * 				game brings out a file explorer to load a game previously saved to a .mssg
 * 				(MineSweeper Save Game) file.
 */

// Imports required packages
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// MenuGUI class extends JFrame and implements ActionListener
public class MenuGUI implements ActionListener {

	// Private class variables
	// Main frame that contains everything
	public JFrame mainFrame = new JFrame();

	// Message to display on start of game
	private JLabel message = new JLabel(
			"<html><div style='text-align: center;'>Welcome to Minesweeper!<br>Created by: Raymond Li and David Tuck</div></html>",
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
		mainFrame.setTitle("Start Game");
		mainFrame.setSize(640, 300);
		mainFrame.setLayout(new GridLayout(2, 1));
		mainFrame.setLocationRelativeTo(null);

		// Sets layout of and adds buttons to buttonPanel
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(newGame);
		buttonPanel.add(loadGame);
		mainFrame.getContentPane().add(messagePanel);
		mainFrame.getContentPane().add(buttonPanel);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			buttonPanel.add(custom);
			buttonPanel.add(back);
			buttonPanel.revalidate();
			buttonPanel.repaint();
			mainFrame.revalidate();
			mainFrame.repaint();
		}

		/*
		 * Sets up file chooser and loads game from file if the loadGame button is
		 * clicked
		 */
		else if (loadGame == event.getSource()) {

			JFileChooser loadFile = new JFileChooser();

			// Sets the default directory to wherever the Minesweeper game is
			loadFile.setCurrentDirectory(new File("."));

			// Adds a custom file filter and disables the default (Accept All) file filter
			loadFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			loadFile.addChoosableFileFilter(new MSSGFilter());
			loadFile.setAcceptAllFileFilterUsed(false);

			// Processes the results of getting the user to load a game
			if (loadFile.showDialog(mainFrame, "Load Game") == JFileChooser.APPROVE_OPTION) {
				File game = loadFile.getSelectedFile();

				// Resets the file chooser for the next time it's shown
				loadFile.setSelectedFile(null);

				// Try-catch to handle exceptions
				try {

					// Reads saved game from file
					Minesweeper.readFromFile(game.getName());
					mainFrame.dispose();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(mainFrame.getContentPane(),
							new JLabel("Savegame not loaded. Bad File.", JLabel.CENTER), "FileLoader",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			/*
			 * Shows a popup telling the user that the saved game has not been loaded and
			 * restarts the MenuGUI window by disposing and recreating it
			 */
			else {
				JOptionPane.showMessageDialog(mainFrame.getContentPane(),
						new JLabel("Savegame not loaded. Bad File.", JLabel.CENTER), "FileLoader",
						JOptionPane.ERROR_MESSAGE);
				mainFrame.dispose();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainFrame.dispose();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainFrame.dispose();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainFrame.dispose();
		}

		// If the custom button is clicked, opens a CustomMode dialog
		else if (custom == event.getSource()) {

			// Calls a custom mode dialog
			CustomModeDialog customMode = new CustomModeDialog(mainFrame);

			// Packs the customMode dialog
			customMode.pack();

			// Sets the location of the customMode dialog
			customMode.setLocationRelativeTo(mainFrame.getContentPane());

			// Shows the customMode dialog
			customMode.setResizable(false);
			customMode.setVisible(true);
		}

		// If the back button is clicked, restart the MenuGUI
		else if (back == event.getSource()) {
			mainFrame.dispose();
			new MenuGUI();
		}
	}

}