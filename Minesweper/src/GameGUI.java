
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
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.BevelBorder;

//Main class that extends JFrame and implements ActionListener
public class GameGUI extends JFrame implements ActionListener, MouseListener {

	// Serial version UID
	private static final long serialVersionUID = -3978640272114053636L;

	// Private class Variables
	// 2D JButton array for the Minesweeper map
	private static JButton[][] buttons;

	// Clock to display the amount of time elapsed for the user
	private JTextPane clock = new JTextPane();

	// MinesLeft to display the amount of mines left to be flagged
	private JTextPane minesLeft = new JTextPane();

	// GamePanel to hold all the buttons
	private JPanel gamePanel = new JPanel();

	// InfoPanel to hold clock and minesLeft
	private JPanel infoPanel = new JPanel();

	// Colors for each number of mines
	static Color[] mycolors = { Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY };

	// MenuBar variables
	// The menuBar itself
	private JMenuBar menuBar = new JMenuBar();

	// The first menu
	private JMenu gameMenu = new JMenu("Game");

	// The submenu under gameMenu
	private JMenu newGameSubmenu = new JMenu("New game");

	// Menu item to save game
	private JMenuItem saveGame = new JMenuItem("Save game");

	// Menu item to load game
	private JMenuItem loadGame = new JMenuItem("Load Game");

	// Menu items for difficulty (under the newGame submenu)
	private JMenuItem beginner = new JMenuItem("Beginner (9x9)");
	private JMenuItem intermediate = new JMenuItem("Intermediate (16x16)");
	private JMenuItem expert = new JMenuItem("Expert (16x30)");
	private JMenuItem helpButton = new JMenu("Help");

	// Declares boolean value for whether a button was pressed
	boolean pressed;

	/**
	 * Constructor
	 * 
	 * @param Minesweeper.mapSizeX
	 *            The horizontal size of the map
	 * @param Minesweeper.mapSizeY
	 *            The vertical size of the map
	 */
	public GameGUI() {

		// Initializes panels and buttons array
		gamePanel.setLayout(new GridLayout(Minesweeper.mapSizeX, Minesweeper.mapSizeY));
		infoPanel.setLayout(new FlowLayout());
		buttons = new JButton[Minesweeper.mapSizeX][Minesweeper.mapSizeY];

		/*
		 * Initializes buttons with raised-bevel border, Comic Sans font and a fixed
		 * size of 30 pixels by 30 pixels
		 */
		for (int i = 0; i < Minesweeper.mapSizeX; i++)
			for (int j = 0; j < Minesweeper.mapSizeY; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				buttons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
				buttons[i][j].setPreferredSize(new Dimension(30, 30));
				buttons[i][j].setMaximumSize(new Dimension(30, 30));
				buttons[i][j].setMinimumSize(new Dimension(30, 30));

				// Adds mouse listener to each button
				buttons[i][j].addMouseListener(this);

				// Adds buttons to game panel
				gamePanel.add(buttons[i][j]);
			}

		// Initializes and adds textPanes to infoPanel
		clock.setEditable(false);
		minesLeft.setEditable(false);
		clock.setFont(new Font("Consolas", Font.BOLD, 20));
		minesLeft.setFont(new Font("Consolas", Font.BOLD, 20));
		clock.setForeground(Color.RED);
		minesLeft.setForeground(Color.RED);
		clock.setBackground(Color.BLACK);
		minesLeft.setBackground(Color.BLACK);
		infoPanel.add(clock);
		infoPanel.add(minesLeft);

		// Adds panels to frame
		getContentPane().add(infoPanel);
		getContentPane().add(gamePanel);

		// Adds menuBar to frame
		setJMenuBar(createMenuBar());

		// Sets title, size, layout and location of GUI window
		setTitle("Minesweeper");
		setSize(Minesweeper.mapSizeY * 30 + 30, Minesweeper.mapSizeX * 30 + 126);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);

		// Makes the program terminate on press of close window
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Sets window to visible
		setVisible(true);
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
		saveGame.addActionListener(this);
		gameMenu.add(saveGame);
		loadGame.addActionListener(this);
		gameMenu.add(loadGame);

		// Adds newGame submenu
		gameMenu.addSeparator();

		// Adds actionListeners to submenuItems and adds menuItems to gameMenu
		beginner.addActionListener(this);
		newGameSubmenu.add(beginner);
		intermediate.addActionListener(this);
		newGameSubmenu.add(intermediate);
		expert.addActionListener(this);
		newGameSubmenu.add(expert);
		gameMenu.add(newGameSubmenu);

		// Adds help button to menu
		helpButton.addActionListener(this);
		menuBar.add(helpButton);

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
								&& (Minesweeper.map[i][j].getMineType() == MinesweeperTypes.UNKNOWN
										|| Minesweeper.map[i][j].getMineType() == MinesweeperTypes.FLAG)) {

							// If there is no flag at the square
							if (Minesweeper.map[i][j].getMineType() != MinesweeperTypes.FLAG) {

								// Sets the button to an image (flag.png)
								buttons[i][j].setIcon(
										new ImageIcon(this.getClass().getClassLoader().getResource("flag.png")));
								Minesweeper.map[i][j].changeType(MinesweeperTypes.FLAG);
							}

							// If there is a flag at the square
							else {

								// Reset button
								buttons[i][j].setIcon(null);
								Minesweeper.map[i][j].changeType(MinesweeperTypes.UNKNOWN);
							}
						}
						// If the mouse click was a left-click
						else if (event.getButton() == MouseEvent.BUTTON1
								&& Minesweeper.map[i][j].getMineType() == MinesweeperTypes.UNKNOWN) {

							// Checks if a mine exists at the clicked square
							if (Minesweeper.checkForMine(i, j)) {
								if (Minesweeper.map[i][j].getMineType() != MinesweeperTypes.FLAG) {

									int mineCount = Minesweeper.genNumOfMines(i, j);
									if (mineCount != 0) {
										showValue(i, j);
									}

									/*
									 * Calls recursive function to auto-click all connecting blank squares and
									 * surrounding numbered squares
									 */
									else {
										recursion(i, j);
									}
								}
								// Checks if all empty squares were clicked
								// TODO check for all empty squares
							}

							// If user clicks on a mine
							else {

								// For all squares on map with a mine, shows the mine image
								for (int k = 0; k < Minesweeper.mapSizeX; k++)
									for (int l = 0; l < Minesweeper.mapSizeY; l++) {
										if (Minesweeper.checkForMine(k, l)) {
											// TODO Show Mine image here
										}
										// Ends the game with a game over and prompts for name with High Score
										// TODO End game
									}
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

	// Sets pressed to true if cursor enters a button
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
		if (beginner == event.getSource()) {
			Minesweeper.mapSizeX = 9;
			Minesweeper.mapSizeY = 9;
			Minesweeper.mineCount = 10;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithUnknown();
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
				Minesweeper.fillWithUnknown();
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
				Minesweeper.fillWithUnknown();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		}
	}

	public static void recursion(int m, int n) {

		if (Minesweeper.genNumOfMines(m, n) == 0 && Minesweeper.map[m][n].getMineType() == MinesweeperTypes.UNKNOWN) {

			Minesweeper.map[m][n].changeType(MinesweeperTypes.EMPTY);
			buttons[m][n].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					try {
						recursion(i + m, j + n);
					} catch (IndexOutOfBoundsException e) {

					}
				}
			}
		} else {
			showValue(m, n);
		}
	}

	public static void showValue(int i, int j) {
		int mineCount = Minesweeper.genNumOfMines(i, j);
		if (mineCount != 0 && Minesweeper.map[i][j].getMineType() != MinesweeperTypes.FLAG) {
			Minesweeper.map[i][j].changeType(MinesweeperTypes.NEXTTOMINE);
			buttons[i][j].setText(Integer.toString(mineCount));
			buttons[i][j].setForeground(mycolors[mineCount]);
			buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		}
	}

	public void mouseClicked(MouseEvent event) {
		mouseReleased(event);
	}
}