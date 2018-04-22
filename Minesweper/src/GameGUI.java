/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-18
 * Date Finished: 2018-04-
 * Description: Minesweeper game's main GUI
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

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

		// Initializes panels and buttons array
		gamePanel.setLayout(new GridLayout(mapSizeX, mapSizeY));
		controlPanel.setLayout(new FlowLayout());
		buttons = new JButton[mapSizeX][mapSizeY];

		// Initializes buttons, adds mouse listeners and adds buttons to the gamePanel
		for (int i = 0; i < mapSizeX; i++)
			for (int j = 0; j < mapSizeY; j++) {
				final int m = i, n = j;
				buttons[i][j] = new JButton();
				buttons[i][j].addMouseListener(new MouseAdapter() {
					boolean pressed;

					public void mousePressed(MouseEvent e) {
						buttons[m][n].getModel().setArmed(true);
						buttons[m][n].getModel().setPressed(true);
						pressed = true;
					}

					public void mouseReleased(MouseEvent e) {
						buttons[m][n].getModel().setArmed(false);
						buttons[m][n].getModel().setPressed(false);

						if (pressed) {
							if (SwingUtilities.isRightMouseButton(e)) {
								// TODO Show flag here
							} else {
								buttons[m][n].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
								if (!Minesweeper.checkForMine(m, n)) {
									if (Minesweeper.genNumOfMines(m, n) != 0)
										buttons[m][n].setText(Integer.toString(Minesweeper.genNumOfMines(m, n)));
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

					public void mouseExited(MouseEvent e) {
						pressed = false;
					}

					public void mouseEntered(MouseEvent e) {
						pressed = true;
					}
				});
				buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				buttons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
				buttons[i][j].setPreferredSize(new Dimension(60, 60));
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
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setLocationRelativeTo(null);

		/*
		 * Makes the program terminate on click of close window, and sets window to
		 * visible
		 */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {

	}
}