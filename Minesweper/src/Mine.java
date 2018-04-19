
public class Mine {
	private int visableValue = 0;
	public typesOfMines.mineTypes contains;
	private boolean isAMine = false;
	
	
	public Mine(boolean mine) {
		isAMine = mine;
		contains = mineTypes.empty;
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

	public typesOfMines.mineTypes getMineType() {
		return contains;
	}

	public void changeType(typesOfMines.mineTypes var) {
		contains = var;
	}
}
