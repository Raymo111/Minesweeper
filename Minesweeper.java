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

// Main class with JFrame and ActionListener enabled
public class Minesweeper extends JFrame implements ActionListener {

	private static final long serialVersionUID = 580779134344104016L;

	// Class variables
	private JButton[][] buttons = new JButton[5][5];
	private static int[][] nums = new int[5][5];
	private static Random random = new Random();

	// Constructor
	public Minesweeper() {

		/*
		 * Initializes buttons with randomly generated numbers, sets font of and adds
		 * action listeners to buttons, and adds them to the frame
		 */
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				nums[i][j] = (random.nextInt(6) + 1);
				buttons[i][j] = new JButton(Integer.toString(nums[i][j]));
				buttons[i][j].addActionListener(this);
				buttons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
				getContentPane().add(buttons[i][j]);
			}

		// Sets title, size, layout and location of GUI window
		setTitle("2D Arrays GUI 1");
		setSize(840, 840);
		setLayout(new GridLayout(5, 5));
		setLocationRelativeTo(null);

		/*
		 * Makes the program terminate on click of close window, and sets window to
		 * visible
		 */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Main method
	public static void main(String[] args) {
		new Minesweeper();
	}

	public void actionPerformed(ActionEvent event) {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (buttons[i][j] == event.getSource() && nums[i][j] > 0) {
					nums[i][j] -= 1;
					buttons[i][j].setText(Integer.toString(nums[i][j]));

					// Check left
					if (j >= 1 && (nums[i][j] + 1) == nums[i][j - 1]) {
						nums[i][j - 1] -= 1;
						buttons[i][j - 1].setText(Integer.toString(nums[i][j - 1]));
					}

					// Check right
					if (j <= 3 && (nums[i][j] + 1) == nums[i][j + 1]) {
						nums[i][j + 1] -= 1;
						buttons[i][j + 1].setText(Integer.toString(nums[i][j + 1]));
					}

					// Check bottom
					if (i <= 3 && (nums[i][j] + 1) == nums[i + 1][j]) {
						nums[i + 1][j] -= 1;
						buttons[i + 1][j].setText(Integer.toString(nums[i + 1][j]));
					}

					// Check top
					if (i >= 1 && (nums[i][j] + 1) == nums[i - 1][j]) {
						nums[i - 1][j] -= 1;
						buttons[i - 1][j].setText(Integer.toString(nums[i - 1][j]));
					}

					// Check top left
					if (i >= 1 && j >= 1 && (nums[i][j] + 1) == nums[i - 1][j - 1]) {
						nums[i - 1][j - 1] -= 1;
						buttons[i - 1][j - 1].setText(Integer.toString(nums[i - 1][j - 1]));
					}

					// Check top right
					if (i >= 1 && j <= 3 && (nums[i][j] + 1) == nums[i - 1][j + 1]) {
						nums[i - 1][j + 1] -= 1;
						buttons[i - 1][j + 1].setText(Integer.toString(nums[i - 1][j + 1]));
					}

					// Check bottom left
					if (i <= 3 && j >= 1 && (nums[i][j] + 1) == nums[i + 1][j - 1]) {
						nums[i + 1][j - 1] -= 1;
						buttons[i + 1][j - 1].setText(Integer.toString(nums[i + 1][j - 1]));
					}

					// Check bottom right
					if (i <= 3 && j <= 3 && (nums[i][j] + 1) == nums[i + 1][j + 1]) {
						nums[i + 1][j + 1] -= 1;
						buttons[i + 1][j + 1].setText(Integer.toString(nums[i + 1][j + 1]));
					}
				}
			}
		}
	}

}
