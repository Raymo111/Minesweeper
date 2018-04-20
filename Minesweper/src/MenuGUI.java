import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-19
 * Date Finished: 2018-04-
 * Description: Minesweeper game
 */
public class MenuGUI extends JFrame implements ActionListener {

	// Private class variables
	private JLabel message = new JLabel(
			"<html><div style='text-align: center;'>"
					+ "Welcome to Minesweeper!<br>Created by: Raymond Li and David Tuck" + "</div></html>",
			JLabel.CENTER);
	private JButton newGame = new JButton("New Game"), loadGame = new JButton("Load Game"), small = new JButton("6x6"),
			medium = new JButton("8x8"), large = new JButton("10x10"), back = new JButton("Back");
	private JPanel messagePanel = new JPanel(), buttonPanel = new JPanel();
	private JFileChooser fc;

	// Constructor
	public MenuGUI() {

		// Sets font of message and adds it to messagePanel
		message.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		messagePanel.add(message);

		// Adds action listeners and fonts to buttons
		newGame.addActionListener(this);
		newGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		loadGame.addActionListener(this);
		loadGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		small.addActionListener(this);
		small.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		medium.addActionListener(this);
		medium.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		large.addActionListener(this);
		large.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));

		// Sets title, size, layout and location of GUI window
		setTitle("Start Game");
		setSize(420, 420);
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

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == newGame) {
			buttonPanel.remove(newGame);
			buttonPanel.remove(loadGame);
			buttonPanel.add(small);
			buttonPanel.add(medium);
			buttonPanel.add(large);
			buttonPanel.add(back);
			buttonPanel.revalidate();
			buttonPanel.repaint();
			revalidate();
			repaint();
		} else if (event.getSource() == loadGame) {
			// Load game from file
			// Set up the file chooser
			if (fc == null) {
				fc = new JFileChooser();

				// Add a custom file filter and disable the default (Accept All) file filter
				fc.addChoosableFileFilter(new MSSGFilter());
				fc.setAcceptAllFileFilterUsed(false);
			}

			// Show it
			int returnVal = fc.showDialog(MenuGUI.this, "Load Game");

			// Process the results
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File saveGame = fc.getSelectedFile();

				// Reset the file chooser for the next time it's shown and read file
				fc.setSelectedFile(null);
				try {
					Minesweeper.menufinished();
					Minesweeper.readFromFile(saveGame.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(rootPane, new JLabel("File loaded!", JLabel.CENTER), "FileLoader",
						JOptionPane.DEFAULT_OPTION);
			} else {
				JOptionPane.showMessageDialog(rootPane, new JLabel("File not loaded. Bad File."), "FileLoader",
						JLabel.CENTER);
				dispose();
				new MenuGUI();
			}
		} else if (event.getSource() == small) {
			Minesweeper.mapSizeX = 6;
			Minesweeper.mapSizeY = 6;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		} else if (event.getSource() == medium) {
			Minesweeper.mapSizeX = 8;
			Minesweeper.mapSizeY = 8;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		} else if (event.getSource() == large) {
			Minesweeper.mapSizeX = 10;
			Minesweeper.mapSizeY = 10;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
<<<<<<< HEAD
=======
				new GameGUI(Minesweeper.mapSizeX, Minesweeper.mapSizeY);
>>>>>>> 34d1c8ff900edf1c8256b699e6faf9f3f4573250
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		} else if (event.getSource() == back) {
			dispose();
			new MenuGUI();
		}
	}

}