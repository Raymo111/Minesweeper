/*
 * Authors: Raymond Li, David Tuck
 * Date created: 2018-04-18
 * Description: Minesweeper squares
 */
public class Square {
	private int visibleValue = 0;
	public MinesweeperTypes contains;
	private boolean isAMine = false;

	public Square() {
		contains = MinesweeperTypes.EMPTY;
	}

	public void setToMine() {
		isAMine = true;
	}

	public void removeMine() {
		isAMine = false;
	}

	public boolean checkMine() {
		return isAMine;
	}

	public MinesweeperTypes getMineType() {
		return contains;
	}

	public void changeType(MinesweeperTypes var) {
		contains = var;
	}

	public void setVisibleValue(int newValue) {
		visibleValue = newValue;
	}

	public int getVisibleValue() {
		return visibleValue;
	}
}
