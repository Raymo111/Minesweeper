public class Mine {
	private int visableValue = 0;
	public MinesweeperTypes contains;
	private boolean isAMine = false;

	public Mine(boolean mine) {
		isAMine = mine;
		contains = MinesweeperTypes.EMPTY;
	}

	/*
	 * public static enum mineTypes { empty, flaged, unknown, quetionMark }
	 */
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
}
