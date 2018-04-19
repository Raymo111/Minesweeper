
public class mineSweeperTester {
	public static int mapSizeX = 5;
	public static int mapSizeY = 5;

	public static void main(String[] args) {
		Mine myMine[][] = new Mine[mapSizeX][mapSizeY];

		for (int i = 0; i < mapSizeX; i++) {
			for (int j = 0; j < mapSizeY; j++) {
				myMine[i][j] = new Mine(false);
			}
		}
		genMines(5, myMine);
	}

	public static void genMines(int numOffMines, Mine[][] myMine) {
		int tempX;
		int tempY;
		for (int i = 0; i < numOffMines; i++) {
			do {
				tempX = (int) (Math.random() * mapSizeX);
				tempY = (int) (Math.random() * mapSizeY);
			} while (myMine[tempX][tempY].checkMine());
			myMine[tempX][tempY].setToMine();
		}
	}

}
