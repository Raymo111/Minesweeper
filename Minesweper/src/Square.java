import java.io.Serializable;

/*
 * Authors: Raymond Li, David Tuck
 * Date created: 2018-04-18
 * Description: Minesweeper squares
 */
public class Square implements Serializable {

	private static final long serialVersionUID = -4896377842037028342L;

	private SquareTypes contains;
	private boolean isAMine = false;

	public Square() {
		contains = SquareTypes.EMPTY;
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

	public SquareTypes getMineType() {
		return contains;
	}

	public void changeType(SquareTypes type) {
		contains = type;
	}
}