/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-18
 * Date Finished: 2018-04-
 * Description: Shows a window with a menuBar containing a Game menu with buttons to load
 * 				or save a game and a submenu containing different new game difficulties
 * 				and another Help button containing instructions on how to play the game.
 * 				The window also contains a clock and a count of the number of mines left.
 * 				Below that is the actual Minesweeper map, which the user can left press
 * 				to show the number of mines around the square pressed on, or right press
 * 				to flag the square as containing a mine. Flagging a square lowers the
 * 				mine count. If the user press on a square containing a mine, the user’s
 * 				game will see ‘Game over’ and be prompted to start a new game or quit.
 */

// Imports required packages
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.Serializable;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

//Main class that extends JFrame and implements ActionListener
public class GameGUI implements ActionListener, MouseListener, Serializable {

	private static final long serialVersionUID = 9140551278445058487L;

	// Private class Variables
	// Main frame that contains everything
	public JFrame mainFrame = new JFrame();

	// 2D JButton array for the Minesweeper map
	public JButton[][] buttons;

	// Clock to display the amount of time elapsed for the user
	public JTextPane clockPane = new JTextPane();
	private Clock clock;

	// Check to see if click is the first
	public boolean firstClick = true;

	// MinesLeft to display the amount of mines left to be flagged
	private JTextPane minesLeft = new JTextPane();

	// GamePanel to hold all the buttons
	private JPanel gamePanel = new JPanel();

	// InfoPanel to hold clock and minesLeft
	private JPanel infoPanel = new JPanel();

	/*
	 * Colors for each number of mines: 1-Blue 2-Green 3-Red 4-Dark_Blue 5-Dark_Red
	 * 6-Turquoise 7-Black 8-Grey
	 */
	private final static Color[] mycolors = { new Color(0, 0, 255), new Color(0, 129, 0), new Color(255, 19, 0),
			new Color(0, 0, 131), new Color(129, 5, 0), new Color(42, 148, 148), new Color(0, 0, 0),
			new Color(128, 128, 128) };

	// MenuBar variables
	// The menuBar itself
	private JMenuBar menuBar = new JMenuBar();

	// The first menu
	private JMenu gameMenu = new JMenu("Game");

	// New game with F2 as keyboard shortcut - maintains current settings
	private JMenuItem newGame = new JMenuItem("New game", KeyEvent.VK_F2);

	// The submenu under gameMenu
	private JMenu newSubmenu = new JMenu("New");

	// Menu item to save game
	private JMenuItem saveGame = new JMenuItem("Save game");

	// Menu item to load game
	private JMenuItem loadGame = new JMenuItem("Load Game");

	// Menu items for difficulty (under the newGame submenu)
	private JMenuItem beginner = new JMenuItem("Beginner (9x9)");
	private JMenuItem intermediate = new JMenuItem("Intermediate (16x16)");
	private JMenuItem expert = new JMenuItem("Expert (16x30)");
	private JMenuItem custom = new JMenuItem("Custom");

	// Help menu for general information
	private JMenu helpMenu = new JMenu("Help");

	// About button displays game's creators
	private JMenuItem about = new JMenuItem("About");

	// How to play button links to a webpage on how to play minesweeper
	private JMenuItem howToPlay = new JMenuItem("How to Play");

	// Declares boolean value for whether a button was pressed
	private boolean pressed, finished = false;

	/**
	 * Constructor
	 * 
	 * @param Minesweeper.mapSizeX
	 *            The horizontal size of the map
	 * @param Minesweeper.mapSizeY
	 *            The vertical size of the map
	 */
	public GameGUI(boolean isLoadGame) {

		// Initializes panels and buttons array
		gamePanel.setLayout(new GridLayout(Minesweeper.mapSizeX, Minesweeper.mapSizeY));
		infoPanel.setLayout(new FlowLayout());
		buttons = new JButton[Minesweeper.mapSizeX][Minesweeper.mapSizeY];

		// Sets minesLeft to mineCount
		Minesweeper.numOfMinesLeft = Minesweeper.mineCount;

		/*
		 * Initializes buttons with raised-bevel border, Consolas font and a fixed size
		 * of 30 pixels by 30 pixels
		 */
		for (int i = 0; i < Minesweeper.mapSizeX; i++)
			for (int j = 0; j < Minesweeper.mapSizeY; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				buttons[i][j].setFont(new Font("Consolas", Font.BOLD, 20));
				buttons[i][j].setPreferredSize(new Dimension(30, 30));
				buttons[i][j].setMaximumSize(new Dimension(30, 30));
				buttons[i][j].setMinimumSize(new Dimension(30, 30));

				// Adds mouse listener to each button
				buttons[i][j].addMouseListener(this);

				// Adds buttons to game panel
				gamePanel.add(buttons[i][j]);
			}

		if (!isLoadGame)

			// Resets clock
			Minesweeper.clockSeconds = 0;

		// Initializes and adds textPanes to infoPanel
		clockPane.setEditable(false);
		minesLeft.setEditable(false);
		clockPane.setFont(new Font("Consolas", Font.BOLD, 20));
		minesLeft.setFont(new Font("Consolas", Font.BOLD, 20));
		clockPane.setForeground(Color.RED);
		minesLeft.setForeground(Color.RED);
		clockPane.setBackground(Color.BLACK);
		minesLeft.setBackground(Color.BLACK);
		clockPane.setText(Integer.toString(Minesweeper.clockSeconds));
		minesLeft.setText(Integer.toString(Minesweeper.numOfMinesLeft));
		infoPanel.add(clockPane);
		infoPanel.add(minesLeft);

		// Adds panels to frame
		mainFrame.getContentPane().add(infoPanel);
		mainFrame.getContentPane().add(gamePanel);

		// Adds menuBar to frame
		mainFrame.setJMenuBar(createMenuBar());

		// Sets title, size, layout and location of GUI window
		mainFrame.setTitle("Minesweeper");
		mainFrame.setSize(Minesweeper.mapSizeY * 30 + 30, Minesweeper.mapSizeX * 30 + 126);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setLocationRelativeTo(null);

		// Makes the program terminate on press of close window
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets window to visible and disable resizing
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		if (isLoadGame) {

			// Refreshes GUI according to save data
			for (int i = 0; i < Minesweeper.mapSizeX; i++)
				for (int j = 0; j < Minesweeper.mapSizeY; j++) {

					// Adds buttons to game panel
					if (Minesweeper.map[i][j].getMineType() == SquareTypes.FLAG)
						flagSquare(i, j);
					else if (Minesweeper.map[i][j].getMineType() == SquareTypes.EMPTY)
						showValue(i, j);
				}

			// Shows a popup telling the user that the saved game has been loaded
			JOptionPane.showMessageDialog(mainFrame.getContentPane(), new JLabel("Savegame loaded!", JLabel.CENTER),
					"FileLoader", JOptionPane.INFORMATION_MESSAGE);
		}

		// Play new game sound
		AudioInputStream newGameSound;
		try {
			newGameSound = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("NewGame.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(newGameSound);
			clip.start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame.getContentPane(),
					new JLabel("You did not download the music file!", JLabel.CENTER), "Minesweeper Sound Player",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Creates the menuBar
	 * 
	 * @return The finished JMenuBar
	 */
	public JMenuBar createMenuBar() {

		// Adds gameMenu to menuBar
		menuBar.add(gameMenu);

		// Adds actionListeners to menuItems and adds menuItems to gameMenu
		newGame.addActionListener(this);
		gameMenu.add(newGame);
		saveGame.addActionListener(this);
		gameMenu.add(saveGame);
		loadGame.addActionListener(this);
		gameMenu.add(loadGame);

		// Adds newGame submenu
		gameMenu.addSeparator();

		// Adds actionListeners to submenuItems and adds menuItems to gameMenu
		beginner.addActionListener(this);
		newSubmenu.add(beginner);
		intermediate.addActionListener(this);
		newSubmenu.add(intermediate);
		expert.addActionListener(this);
		newSubmenu.add(expert);
		custom.addActionListener(this);
		newSubmenu.add(custom);
		gameMenu.add(newSubmenu);

		// Adds help menu to menubar
		menuBar.add(helpMenu);
		about.addActionListener(this);
		helpMenu.add(about);
		howToPlay.addActionListener(this);
		helpMenu.add(howToPlay);

		// Returns the finished menuBar
		return menuBar;
	}

	/**
	 * MousePressed method sets the button as pressed
	 */
	public void mousePressed(MouseEvent event) {
		for (int i = 0; i < buttons.length; i++)
			for (int j = 0; j < buttons[i].length; j++)
				if (buttons[i][j] == event.getSource()) {
					buttons[i][j].getModel().setPressed(true);
					pressed = true;
				}
	}

	/**
	 * MouseReleased method changes left-clicked button to number of mines,
	 * left-clicked button with a mine to game over, and right-clicked button to a
	 * picture of a flag.
	 */
	public void mouseReleased(MouseEvent event) {

		for (int i = 0; i < buttons.length; i++)
			for (int j = 0; j < buttons[i].length; j++)
				if (buttons[i][j] == event.getSource()) {

					// Sets button as not pressed
					buttons[i][j].getModel().setPressed(false);

					// Checks if the same button was pressed
					if (pressed) {

						// Checks if the mouse click was a right-click
						if (event.getButton() == MouseEvent.BUTTON3
								&& (Minesweeper.map[i][j].getMineType() == SquareTypes.UNKNOWN
										|| Minesweeper.map[i][j].getMineType() == SquareTypes.FLAG)) {

							// Plays the flag sound
							AudioInputStream flagSound;
							try {
								flagSound = AudioSystem
										.getAudioInputStream(this.getClass().getClassLoader().getResource("Flag.wav"));
								Clip clip = AudioSystem.getClip();
								clip.open(flagSound);
								clip.start();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(mainFrame.getContentPane(),
										new JLabel("You did not download the music file!", JLabel.CENTER),
										"Minesweeper Sound Player", JOptionPane.ERROR_MESSAGE);
							}

							// If there is no flag at the square
							if (Minesweeper.map[i][j].getMineType() != SquareTypes.FLAG) {

								// Flags the square
								flagSquare(i, j);
							}

							// If there is a flag at the square
							else {

								// Reset button
								buttons[i][j].setIcon(null);
								Minesweeper.map[i][j].changeType(SquareTypes.UNKNOWN);
								Minesweeper.numOfMinesLeft++;
								minesLeft.setText(Integer.toString(Minesweeper.numOfMinesLeft));
							}
						}
						// If the mouse click was a left-click
						else if (event.getButton() == MouseEvent.BUTTON1
								&& Minesweeper.map[i][j].getMineType() == SquareTypes.UNKNOWN) {

							// Starts timer on the first click
							if (firstClick) {
								clock = new Clock(clockPane);
								firstClick = false;
							}

							// Checks if a mine exists at the clicked square
							if (!Minesweeper.checkForMine(i, j)
									&& Minesweeper.map[i][j].getMineType() != SquareTypes.FLAG) {
								if (Minesweeper.genNumOfMines(i, j) != 0) {
									showValue(i, j);

									// Play click sound
									AudioInputStream clickSound;
									try {
										clickSound = AudioSystem.getAudioInputStream(
												this.getClass().getClassLoader().getResource("Click.wav"));
										Clip clip = AudioSystem.getClip();
										clip.open(clickSound);
										clip.start();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(mainFrame.getContentPane(),
												new JLabel("You did not download the music file!", JLabel.CENTER),
												"Minesweeper Sound Player", JOptionPane.ERROR_MESSAGE);
									}
								}

								/*
								 * Calls recursive function to auto-click all connecting blank squares and
								 * surrounding numbered squares
								 */
								else
									recursion(i, j);

								boolean won = true;

								// Checks if all empty squares were clicked
								for (int k = 0; k < Minesweeper.map.length; k++)
									for (int l = 0; l < Minesweeper.map[k].length; l++)

										// Exit loops and set won to false if any non-mine square has not been clicked
										if (!Minesweeper.map[k][l].checkMine()
												&& Minesweeper.map[k][l].getMineType() == SquareTypes.UNKNOWN) {
											won = false;
											break;
										}

								if (won) {

									// Plays the win sound
									AudioInputStream winSound;
									try {
										winSound = AudioSystem.getAudioInputStream(
												this.getClass().getClassLoader().getResource("Win.wav"));
										Clip clip = AudioSystem.getClip();
										clip.open(winSound);
										clip.start();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(mainFrame.getContentPane(),
												new JLabel("You did not download the music file!", JLabel.CENTER),
												"Minesweeper Sound Player", JOptionPane.ERROR_MESSAGE);
									}

									// For all squares on map, disables clicking
									for (int k = 0; k < Minesweeper.mapSizeX; k++)
										for (int l = 0; l < Minesweeper.mapSizeY; l++) {
											buttons[k][l].removeMouseListener(this);

											// If a square has a mine, autoflag it
											if (Minesweeper.map[k][l].getMineType() == SquareTypes.UNKNOWN) {
												Minesweeper.map[k][l].changeType(SquareTypes.FLAG);

												// Flags the square
												flagSquare(k, l);
											}
										}

									// Shows a win dialog and stops timer
									try {
										clock.cancel();
									} catch (Exception e) {
									}
									finished = true;
									JOptionPane.showMessageDialog(mainFrame.getContentPane(), new JLabel(
											"<html><div style='text-align: center;'>Congratulations!<br>You've won the game!<br>Game created by:<br>Raymond Li and David Tuck</div></html>"),
											"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
								}
							}

							// If user clicks on a mine
							else {

								// Plays the explosion sound
								AudioInputStream explosionSound;
								try {
									explosionSound = AudioSystem.getAudioInputStream(
											this.getClass().getClassLoader().getResource("Explosions.wav"));
									Clip clip = AudioSystem.getClip();
									clip.open(explosionSound);
									clip.start();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(mainFrame.getContentPane(),
											new JLabel("You did not download the music file!", JLabel.CENTER),
											"Minesweeper Sound Player", JOptionPane.ERROR_MESSAGE);
								}

								// Stops timer
								try {
									clock.cancel();
								} catch (Exception e) {
								}
								finished = true;

								for (int k = 0; k < Minesweeper.mapSizeX; k++)
									for (int l = 0; l < Minesweeper.mapSizeY; l++) {

										// For all squares on map, disables clicking
										buttons[k][l].removeMouseListener(this);

										// For squares with mine, shows mine image
										if (Minesweeper.checkForMine(k, l)) {

											if (!(Minesweeper.map[k][l].getMineType() == SquareTypes.FLAG))

												// Sets the buttons to an image (Mine.png)
												buttons[k][l].setIcon(new ImageIcon(
														this.getClass().getClassLoader().getResource("Mine.png")));
										}

										// Sets the buttons to Wrong.png if the flag was not a mine
										if (Minesweeper.map[k][l].getMineType() == SquareTypes.FLAG
												&& !Minesweeper.checkForMine(k, l))
											buttons[k][l].setIcon(new ImageIcon(
													this.getClass().getClassLoader().getResource("Wrong.png")));

									}

								// Sets the button to an image (Explode.png)
								buttons[i][j].setIcon(
										new ImageIcon(this.getClass().getClassLoader().getResource("Explode.png")));

								// Ends the game with a game over
								JOptionPane.showMessageDialog(mainFrame.getContentPane(), new JLabel(
										"<html><div style='text-align: center;'>Game Over!<br>Better luck next time!<br>Game created by:<br>Raymond Li and David Tuck</div></html>"),
										"Game Over!", JOptionPane.ERROR_MESSAGE);
							}
						}
					}

					// Resets pressed variable to false
					pressed = false;
				}
	}

	// Sets pressed to false if cursor leaves a button
	public void mouseExited(MouseEvent event) {
		pressed = false;
	}

	// Sets pressed to true if cursor enters a button that is not the help
	public void mouseEntered(MouseEvent event) {
		pressed = true;
	}

	/**
	 * Action performed method to control the menuBar
	 */
	public void actionPerformed(ActionEvent event) {

		/*
		 * If the beginner button is clicked, sets size of map to 9x9 and the number of
		 * mines to 10, and proceed to initialize the map with mines and empty squares,
		 * disposing of the menu window when done
		 */
		if (newGame == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}
			try {
				Minesweeper.menufinished();
			} catch (Exception e) {
			}
			mainFrame.dispose();
		}

		/*
		 * Sets up file chooser and loads game from file if the loadGame button is
		 * clicked
		 */
		else if (saveGame == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}

			if (!finished) {

				// Setup file saver
				JFileChooser saveFile = new JFileChooser();
				saveFile.setCurrentDirectory(new File("."));
				saveFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				saveFile.addChoosableFileFilter(new MSSGFilter());
				saveFile.setAcceptAllFileFilterUsed(false);

				// Processes the results of getting the user to load a game
				if (saveFile.showDialog(mainFrame, "Save Game") == JFileChooser.APPROVE_OPTION) {
					File game = saveFile.getSelectedFile();

					// Resets the file chooser for the next time it's shown
					saveFile.setSelectedFile(null);

					// Try-catch to handle exceptions
					try {

						// Saves game to file
						Minesweeper.writeToFile(game.getName());
					} catch (Exception e) {

						JOptionPane.showMessageDialog(mainFrame.getContentPane(),
								new JLabel("Oops, something happened and the game was not saved.", JLabel.CENTER),
								"FileSaver", JOptionPane.ERROR_MESSAGE);
					}

					// Shows a popup telling the user that the saved game has been loaded
					JOptionPane.showMessageDialog(mainFrame.getContentPane(), new JLabel("Game Saved!", JLabel.CENTER),
							"FileSaver", JOptionPane.INFORMATION_MESSAGE);

					// Starts clock
					clock = new Clock(clockPane);
				}
			} else
				JOptionPane.showMessageDialog(mainFrame.getContentPane(),
						new JLabel("You can not save a finished game.", JLabel.CENTER), "FileSaver",
						JOptionPane.ERROR_MESSAGE);
		}

		/*
		 * Sets up file chooser and loads game from file if the loadGame button is
		 * clicked
		 */
		else if (loadGame == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}

			JFileChooser loadFile = new JFileChooser();

			// Sets the default directory to wherever the Minesweeper game is
			loadFile.setCurrentDirectory(new File("."));

			// Adds a custom file filter and disables the default (Accept All) file filter
			loadFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			loadFile.addChoosableFileFilter(new MSSGFilter());
			loadFile.setAcceptAllFileFilterUsed(false);

			// Try-catch to handle exceptions
			try {

				// Processes the results of getting the user to load a game
				if (loadFile.showDialog(mainFrame, "Load Game") == JFileChooser.APPROVE_OPTION) {
					File game = loadFile.getSelectedFile();

					// Resets the file chooser for the next time it's shown
					loadFile.setSelectedFile(null);

					// Reads saved game from file
					Minesweeper.readFromFile(game.getName());
					mainFrame.dispose();
				}

				/*
				 * Shows a popup telling the user that the saved game has not been loaded
				 */
				else {
					JOptionPane.showMessageDialog(mainFrame.getContentPane(),
							new JLabel("Savegame not loaded. Bad File.", JLabel.CENTER), "FileLoader",
							JOptionPane.ERROR_MESSAGE);

					// Starts clock
					clock = new Clock(clockPane);
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(mainFrame.getContentPane(),
						new JLabel("Savegame not loaded. Bad File.", JLabel.CENTER), "FileLoader",
						JOptionPane.ERROR_MESSAGE);
				if (!finished)

					// Starts clock
					clock = new Clock(clockPane);
			}
		}

		else if (beginner == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}
			Minesweeper.mapSizeX = 9;
			Minesweeper.mapSizeY = 9;
			Minesweeper.mineCount = 10;
			try {
				Minesweeper.menufinished();
			} catch (Exception e) {
			}
			mainFrame.dispose();
		}

		/*
		 * If the intermediate button is clicked, sets size of map to 16x16 and the
		 * number of mines to 40, and proceed to initialize the map with mines and empty
		 * squares, disposing of the Menu window when done
		 */
		else if (intermediate == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}
			Minesweeper.mapSizeX = 16;
			Minesweeper.mapSizeY = 16;
			Minesweeper.mineCount = 40;
			try {
				Minesweeper.menufinished();
			} catch (Exception e) {
			}
			mainFrame.dispose();
		}

		/*
		 * If the expect button is clicked, sets size of map to 16x30 and the number of
		 * mines to 99, and proceed to initialize the map with mines and empty squares,
		 * disposing of the Menu window when done
		 */
		else if (expert == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}
			Minesweeper.mapSizeX = 16;
			Minesweeper.mapSizeY = 30;
			Minesweeper.mineCount = 99;
			try {
				Minesweeper.menufinished();
			} catch (Exception e) {
			}
			mainFrame.dispose();
		}

		// If the custom button is clicked, opens a CustomMode dialog
		else if (custom == event.getSource()) {

			// Stops the clock
			try {
				clock.cancel();
			} catch (Exception e) {
			}

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

		// If the about button is clicked, opens a dialog
		else if (about == event.getSource()) {

			// Shows a creators dialog
			JOptionPane.showMessageDialog(mainFrame.getContentPane(),
					new JLabel("Game created by Raymond Li and David Tuck.", JLabel.CENTER), "About",
					JOptionPane.INFORMATION_MESSAGE);
		}

		// If the how to play button is clicked, opens a webpage
		else if (howToPlay == event.getSource()) {

			// Opens Minesweeper.info
			try {
				Desktop.getDesktop().browse(new URL("http://minesweeper.info/wiki/Windows_Minesweeper").toURI());
			} catch (Exception e) {
			}
		}
	}

	public void recursion(int m, int n) {

		if (Minesweeper.genNumOfMines(m, n) == 0 && Minesweeper.map[m][n].getMineType() == SquareTypes.UNKNOWN) {

			Minesweeper.map[m][n].changeType(SquareTypes.EMPTY);
			buttons[m][n].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

			// Play click sound
			AudioInputStream clickSound;
			try {
				clickSound = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("Click.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(clickSound);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(mainFrame.getContentPane(),
						new JLabel("You did not download the music file!", JLabel.CENTER), "Minesweeper Sound Player",
						JOptionPane.ERROR_MESSAGE);
			}

			// Disables clicks on square
			buttons[m][n].removeMouseListener(this);

			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					try {
						recursion(i + m, j + n);
					} catch (Exception e) {
					}
				}
			}
		} else {
			showValue(m, n);
		}
	}

	public void showValue(int i, int j) {
		int mineCount = Minesweeper.genNumOfMines(i, j);
		if (Minesweeper.map[i][j].getMineType() != SquareTypes.FLAG) {

			if (mineCount != 0) {
				buttons[i][j].setText(Integer.toString(mineCount));
				buttons[i][j].setForeground(mycolors[mineCount]);
			}
			Minesweeper.map[i][j].changeType(SquareTypes.EMPTY);
			buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

			// Disables clicks on square
			buttons[i][j].removeMouseListener(this);
		}
	}

	// Flags the selected square
	public void flagSquare(int i, int j) {
		// Sets the button to an image (Flag.png)
		buttons[i][j].setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Flag.png")));
		Minesweeper.map[i][j].changeType(SquareTypes.FLAG);
		Minesweeper.numOfMinesLeft--;
		minesLeft.setText(Integer.toString(Minesweeper.numOfMinesLeft));
	}

	public void mouseClicked(MouseEvent event) {
		mouseReleased(event);
	}
}