# minesweeper-gui
A Java implementation of the classic game Minesweeper using Model-View-Controller. One user graphical user interface is implemented with Java Swing).

Minesweeper Implementation Rules and Play:
- This implementation of Minesweeper allows a Beginner (9x9 grid with 10 mines),
      Intermediate (16x16 grid with 40 mines),
      Expert (16x30 grid with 99 mines), and Custom (user
      specified rows, columns, and number of mines) modes.
- This implementation also allows for extra lives (0, 1, 2, or 3).
- In Minesweeper, you are given a board of tiles which
      may contain mines (\"M\") or numbers,
			or be empty (\" \").
- If you click on a tile with a mine, and you do not
			have any more lives, you lose.
- Tiles with numbers (1-8) indicate how many bombs are
			immediately adjacent to that tile (touching a side
			or a corner of that tile).
- If you click on a tile and an empty square appears,
			it indicates that no bombs are immediately adjacent
			to that tile, and so all surrounding tiles
			will be displayed.
- The objective is to fill in all non-mine tiles by
			clicking them (and optionally flagging the mine tiles).
- To flag a tile you think is a mine, point and right-click.
- To unflag a tile you previously flagged, point and right-click again.
- You can click a numbered tile after it is initially opened
			to display all adjacent tiles if the correct number of
			flags have been placed (and/or mines hit in extra-lives
			mode) on surrounding tiles.
- Tiles on the edge of the board have fewer adjacent tiles
			(the board does not wrap around the edges).
- The number of mines minus the number of flags used, the number
			of lives left (if applicable), and the time passed are
			displayed at the top of the game.

Controller.java (Controller)
- the main *Controller* (not really needed)
- **subclass** of ViewGUIToController interface using ```implements```
- mainly just mediates communication between the Model and ViewGUI components

ControllerToModel.java
- **interface** for the Controller to communicate with the Model

ControllerToViewGUI.java
- **interface** for the Controller to communicate with the various Views

Model.java (Model)
- the main *Model* object
- **subclass** of ControllerToModel using ```implements```
- allows the Controller component to store and access data
- contains and mediates access to the number of rows, mines, columns, games played, games won, best times, difficulty,
      extra lives left, actual placements of mines/empties/numbers in the grid of tiles, which tiles are visible to the 
      user, which tiles are flagged, etc.

PlayMinesweeper.java (Controller)
- starts a Minesweeper Game by making and running a Controller object in the main method

ViewButtonClickListener.java (View)
- **subclass** of ActionListener using ```implements```
- contains the View logic to respond to button clicks in the GUI 
      (not including the grid tiles)

ViewCheckBoxListener.java (View)
- **subclass** of ItemListener using ```implements```
- contains the View logic to respond to changes in the check boxes in the GUI 
      
ViewEndFrame.java (View)
- **subclass** of JFrame using ```extends```
- frame showing the end-of-game data to the user

ViewGUI.java (View)
- the main *View* object
- **subclass** of ControllerToViewGUI using ```implements```
- used by the Controller component

ViewGUIToController.java
- **interface** for the ViewGUI to communicate with the Controller

ViewGameTilesFrame.java (View)
- **subclass** of JFrame using ```extends```
- frame showing the game grid and data to the user for them to interact with

ViewMenuListener.java (View)
- **subclass** of ActionListener using ```implements```
- contains the View logic to respond to clicks in the menu of the game frame 

ViewMouseListener.java (View)
- **subclass** of MouseAdapter using ```extends```
- contains the View logic to respond to mouse clicks in the game grid to place flags 
      or click buttons

ViewPopupHelp.java (View)
- **subclass** of JFrame using ```extends```
- frame showing the helpful information to the user

ViewRadioButtonListener.java (View)
- **subclass** of ActionListener using ```implements```
- contains the View logic to respond to radio button changes in the start frame
      (extra lives or changes in difficulty)
      
ViewSpinnerListener.java (View)
- **subclass** of ChangeListener using ```implements```
- contains the View logic to respond to spinner changes in the start frame's custom settings 

ViewStartFrame.java (View)
- **subclass** of JFrame using ```extends```
- frame showing the start-of-game settings to the user for them to change

ViewTimerActionListener(View)
- **subclass** of ActionListener using ```implements```
- contains the View logic to increment the timer in the game frame
