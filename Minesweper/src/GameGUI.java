
/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-18
 * Date Finished: 2018-04-
 * Description: Shows a window with a menuBar containing a Game menu with buttons to load
 * 				or save a game and a submenu containing different new game difficulties
 * 				and another Help button containing instructions on how to play the game.
 * 				The window also contains a clock and a count of the number of mines left.
 * 				Below that is the actual Minesweeper grid, which the user can left press
 * 				to show the number of mines around the square pressed on, or right press
 * 				to flag the square as containing a mine. Flagging a square lowers the
 * 				mine count. If the user presss on a square containing a mine, the user’s
 * 				game will see ‘Game over’ and be prompted to start a new game or quit.
 */

// Imports required packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

//Main class that extends JFrame and implements ActionListener
public class GameGUI extends JFrame implements ActionListener {

	// Serial version UID
	private static final long serialVersionUID = -3978640272114053636L;

	// Private class Variables
	// 2D JButton array for the Minesweeper grid
	private JButton[][] buttons;

	// Clock to display the amount of time elapsed for the user
	private JTextPane clock = new JTextPane();

	// MinesLeft to display the amount of mines left to be flagged
	private JTextPane minesLeft = new JTextPane();

	// GamePanel to hold all the buttons
	private JPanel gamePanel = new JPanel();

	// InfoPanel to hold clock and minesLeft
	private JPanel infoPanel = new JPanel();

	/**
	 * Constructor
	 * 
	 * @param mapSizeX
	 *            The horizontal size of the map
	 * @param mapSizeY
	 *            The vertical size of the map
	 */
	public GameGUI(int mapSizeX, int mapSizeY) {

		// Initializes panels and buttons array
		gamePanel.setLayout(new GridLayout(mapSizeX, mapSizeY));
		infoPanel.setLayout(new FlowLayout());
		buttons = new JButton[mapSizeX][mapSizeY];

		/*
		 * Initializes buttons with raised-bevel border, Comic Sans font and a fixed
		 * size of 30 pixels by 30 pixels
		 */
		for (int i = 0; i < mapSizeX; i++)
			for (int j = 0; j < mapSizeY; j++) {
				final int m = i, n = j;
				buttons[i][j] = new JButton();
				buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				buttons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
				buttons[i][j].setPreferredSize(new Dimension(30, 30));
				buttons[i][j].setMaximumSize(new Dimension(30, 30));
				buttons[i][j].setMinimumSize(new Dimension(30, 30));

				// Adds mouse listener to each button
				buttons[i][j].addMouseListener(new MouseAdapter() {

					// Declares boolean value for whether a button was pressed
					boolean pressed;

					/**
					 * MousePressed method sets the button as armed and pressed
					 */
					public void mousePressed(MouseEvent event) {
						buttons[m][n].getModel().setArmed(true);
						buttons[m][n].getModel().setPressed(true);
						pressed = true;
					}

					/**
					 * MouseReleased method changes left-clicked button to number of mines,
					 * left-clicked button with a mine to game over, and right-clicked button to a
					 * picture of a flag.
					 */
					public void mouseReleased(MouseEvent event) {

						// Sets button as not armed and not pressed
						buttons[m][n].getModel().setArmed(false);
						buttons[m][n].getModel().setPressed(false);

						// Checks if the same button was pressed
						if (pressed) {

							// Checks if the mouse click was a right-click
							if (SwingUtilities.isRightMouseButton(event))

								// Sets the button to an image (flag.png)
								buttons[m][n] = new SizedImageButton("flag.png");

							// If the mouse click was not a left-click
							else {

								buttons[m][n].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

								// Checks if a mine exists at the clicked square
								if (Minesweeper.checkForMine(m, n)) {

									// Sets appropriate color for each number of mines
									// Blue for 1
									if (Minesweeper.genNumOfMines(m, n) == 1) {
										buttons[m][n].setForeground(Color.BLUE);
										buttons[m][n].setText("1");
									}

									// Green for 2
									else if (Minesweeper.genNumOfMines(m, n) == 2) {
										buttons[m][n].setForeground(Color.GREEN);
										buttons[m][n].setText("2");
									}

									// Orange for 3
									else if (Minesweeper.genNumOfMines(m, n) == 3) {
										buttons[m][n].setForeground(Color.ORANGE);
										buttons[m][n].setText("3");
									}

									// Magenta for 4
									else if (Minesweeper.genNumOfMines(m, n) == 2) {
										buttons[m][n].setForeground(Color.MAGENTA);
										buttons[m][n].setText("4");
									}

									// Red for 5
									else if (Minesweeper.genNumOfMines(m, n) == 2) {
										buttons[m][n].setForeground(Color.RED);
										buttons[m][n].setText("5");
									}

									// Cyan for 6
									else if (Minesweeper.genNumOfMines(m, n) == 2) {
										buttons[m][n].setForeground(Color.CYAN);
										buttons[m][n].setText("6");
									}

									// Dark grey for 7
									else if (Minesweeper.genNumOfMines(m, n) == 2) {
										buttons[m][n].setForeground(Color.DARK_GRAY);
										buttons[m][n].setText("7");
									}

									// Gray for 8
									else if (Minesweeper.genNumOfMines(m, n) == 2) {
										buttons[m][n].setForeground(Color.GRAY);
										buttons[m][n].setText("8");
									}

									// Recursive function to auto-click all connecting blank squares
									else {
										// TODO recursive function here
									}
								} else
									for (int k = 0; k < mapSizeX; k++)
										for (int l = 0; l < mapSizeY; l++) {
											if (Minesweeper.checkForMine(k, l)) {
												// TODO Show Mine image here
											}
											// TODO End game
										}
							}
						}
						pressed = false;

					}

					public void mouseExited(MouseEvent event) {
						pressed = false;
					}

					public void mouseEntered(MouseEvent event) {
						pressed = true;
					}
				});
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
		setSize(mapSizeY * 30 + 30, mapSizeX * 30 + 126);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);

		/*
		 * Makes the program terminate on press of close window, and sets window to
		 * visible
		 */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu newGameSubmenu = new JMenu("New game");
		JMenuItem saveGame = new JMenuItem("Save game");
		JMenuItem loadGame = new JMenuItem("Load Game");
		JMenuItem beginner = new JMenuItem("Beginner (9x9)");
		JMenuItem intermediate = new JMenuItem("Intermediate (16x16)");
		JMenuItem expert = new JMenuItem("Expert (16x30)");
		JMenuItem helpButton = new JMenu("Help");

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

	public void actionPerformed(ActionEvent event) {
		// TODO Control menuBar
	}
}