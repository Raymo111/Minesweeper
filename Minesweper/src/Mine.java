
public class Mine {
	private int visableValue = 0;
	private mineTypes contains;
	private boolean isAMine = false;

	public Mine(boolean mine) {
		isAMine = mine;
		contains = mineTypes.empty;
	}

	public static enum mineTypes {
		empty, flaged, unknown, quetionMark
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

	public mineTypes getMineType() {
		return contains;
	}

	public void changeType(int change) {
		if (change == 0) {
			contains = mineTypes.empty;
		} else if (change == 1) {
			contains = mineTypes.flaged;
		} else if (change == 2) {
			contains = mineTypes.unknown;
		} else if (change == 3) {
			contains = mineTypes.quetionMark;
		} else {
		}
	}
}
