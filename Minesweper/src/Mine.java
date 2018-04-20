public class Mine {
	private int visibleValue = 0;
	private MinesweeperTypes contains;
	private boolean isAMine = false;

	public Mine(boolean mine) {
		isAMine = mine;
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
