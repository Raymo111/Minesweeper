
/*
 * Authors: Raymond Li, David Tuck
 * Date started: 2018-04-19
 * Date Finished: 2018-04-
 * Description: Minesweeper game
 */

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 4449469728308361534L;

	// Private class variables
	private JLabel message = new JLabel(
			"<html><div style='text-align: center;'>"
					+ "Welcome to Minesweeper!<br>Created by: Raymond Li and David Tuck" + "</div></html>",
			JLabel.CENTER);
	private JButton newGame = new JButton("New Game"), loadGame = new JButton("Load Game"),
			beginner = new JButton("Beginner (6x6)"), intermediate = new JButton("Intermediate (8x8)"),
			Expert = new JButton("Expert (10x10)"), back = new JButton("Back");
	private JPanel messagePanel = new JPanel(), buttonPanel = new JPanel();
	private JFileChooser fc;

	// Constructor
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
		Expert.addActionListener(this);
		Expert.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
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

	public void actionPerformed(ActionEvent event) {
		if (newGame == event.getSource()) {
			buttonPanel.remove(newGame);
			buttonPanel.remove(loadGame);
			buttonPanel.add(beginner);
			buttonPanel.add(intermediate);
			buttonPanel.add(Expert);
			buttonPanel.add(back);
			buttonPanel.revalidate();
			buttonPanel.repaint();
			revalidate();
			repaint();
		} else if (loadGame == event.getSource()) {
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
		} else if (beginner == event.getSource()) {
			Minesweeper.mapSizeX = 6;
			Minesweeper.mapSizeY = 6;
			Minesweeper.mineCount = 10;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		} else if (intermediate == event.getSource()) {
			Minesweeper.mapSizeX = 8;
			Minesweeper.mapSizeY = 8;
			Minesweeper.mineCount = 40;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		} else if (Expert == event.getSource()) {
			Minesweeper.mapSizeX = 10;
			Minesweeper.mapSizeY = 10;
			Minesweeper.mineCount = 99;
			try {
				Minesweeper.menufinished();
				Minesweeper.fillWithEmpty();
				Minesweeper.genMines();
				new GameGUI(Minesweeper.mapSizeX, Minesweeper.mapSizeY);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		} else if (back == event.getSource()) {
			dispose();
			new MenuGUI();
		}
	}

}