/*
 * Authors: Raymond Li
 * Date: 06/05/2018
 * Description: Custom mode dialog used by user to create a custom game. Dimensions are limited in Minesweeper.java
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomModeDialog extends JDialog implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = -7536805503015591331L;
	private JFrame mainFrame;
	private JTextField heightField = new JTextField(4);
	private JTextField widthField = new JTextField(4);
	private JTextField minesField = new JTextField(4);

	private JOptionPane optionPane;

	private String ok = "Ok";
	private String cancel = "Cancel";

	/** Creates the reusable dialog. */
	public CustomModeDialog(JFrame frame) {
		super();
		mainFrame = frame;
		setTitle("Custom Map");

		// Create an array of the text and components to be displayed
		JLabel heightPrompt = new JLabel("Height:");
		JLabel widthPrompt = new JLabel("Width: ");
		JLabel minesPrompt = new JLabel("Mines: ");
		JPanel heightPanel = new JPanel(new FlowLayout());
		JPanel widthPanel = new JPanel(new FlowLayout());
		JPanel minesPanel = new JPanel(new FlowLayout());
		heightPanel.add(heightPrompt);
		heightPanel.add(heightField);
		widthPanel.add(widthPrompt);
		widthPanel.add(widthField);
		minesPanel.add(minesPrompt);
		minesPanel.add(minesField);
		Object[] array = { heightPanel, widthPanel, minesPanel };

		// Create an array specifying the number of dialog buttons
		// and their text
		Object[] options = { ok, cancel };

		// Create the JOptionPane
		optionPane = new JOptionPane(array, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options,
				options[0]);

		// Make this dialog display it
		setContentPane(optionPane);

		// Ensure the option pane always gets the first focus
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				optionPane.requestFocusInWindow();
			}
		});

		// Register an event handler that puts the text into the option pane
		heightField.addActionListener(this);
		widthField.addActionListener(this);
		minesField.addActionListener(this);

		// Register an event handler that reacts to option pane state changes
		optionPane.addPropertyChangeListener(this);
	}

	/** This method handles events for the text field. */
	public void actionPerformed(ActionEvent event) {
		optionPane.setValue(ok);
	}

	/** This method reacts to state changes in the option pane. */
	public void propertyChange(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();

		if (isVisible() && (event.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(propertyName)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(propertyName))) {
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				// ignore reset
				return;
			}

			// Reset the JOptionPane's value.
			// If you don't do this, then if the user
			// presses the same button next time, no
			// property change event will be fired.
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (ok.equals(value))
				try {
					if (Integer.parseInt(heightField.getText()) < Minesweeper.minX
							|| Integer.parseInt(heightField.getText()) > Minesweeper.maxX)
						JOptionPane.showMessageDialog(CustomModeDialog.this, "Invalid height.",
								"Stop trying to crash my game! :)", JOptionPane.ERROR_MESSAGE);
					else if (Integer.parseInt(widthField.getText()) < Minesweeper.minY
							|| Integer.parseInt(widthField.getText()) > Minesweeper.maxY)
						JOptionPane.showMessageDialog(CustomModeDialog.this, "Invalid width.",
								"Stop trying to crash my game! :)", JOptionPane.ERROR_MESSAGE);
					else if (Integer.parseInt(minesField.getText()) < Minesweeper.minMines
							|| Integer.parseInt(minesField.getText()) > (Integer.parseInt(heightField.getText()) - 1)
									* (Integer.parseInt(widthField.getText()) - 1))
						JOptionPane.showMessageDialog(CustomModeDialog.this, "Invalid number of mines.",
								"Stop trying to crash my game! :)", JOptionPane.ERROR_MESSAGE);
					else {
						Minesweeper.mapSizeX = Integer.parseInt(heightField.getText());
						Minesweeper.mapSizeY = Integer.parseInt(widthField.getText());
						Minesweeper.mineCount = Integer.parseInt(minesField.getText());

						// Dismiss the dialog
						try {
							mainFrame.dispose();
							mainFrame.dispose();
							dispose();
							Minesweeper.menufinished();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(CustomModeDialog.this,
							"Please enter a valid integer for all 3 fields.", "Stop trying to crash my game! :)",
							JOptionPane.ERROR_MESSAGE);
				}
			else if (cancel.equals(value)) {
				dispose();
			}
		}
	}
}