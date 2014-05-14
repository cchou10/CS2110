import java.util.ArrayList;

public class Board {

	int[][] holes;

	/*
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * the board is represented as a 2d array empty spots are occupied by -1's
	 * and non-empty spots are occupied by the respective numbers
	 */

	/**
	 * 
	 * @param b
	 * @throws NullBoardException
	 */
	public Board(int[][] b) throws NullBoardException {
		if (checkBoard(b))
			holes = b;
		else
			throw new NullBoardException("invalid");

	}

	public Board() throws NullBoardException {
		this(new int[9][9]);
	}

	/**
	 * Generates next board states the simple way outlined in the assignment
	 * 
	 * @return
	 * @throws NullBoardException
	 */
	public Board[] getChildren() throws NullBoardException {
		int i = 0;
		int j = 0;
		int I = 0;
		int J = 0;
		Board[] allboards = null;
		ArrayList<Integer> values = new ArrayList<Integer>();
		while (i < 9) {
			while (j < 9) {
				if (holes[i][j] == -1) {
					I = i;
					J = j;
					for (int num = 0; num <= 9; num++) {
						if (checkSubsquare(I, J, num) && checkRow(I, J, num)
								&& checkColumn(I, J, num))
							values.add(num);
					}
					allboards = new Board[values.size()];
					for (int fill = 0; fill < values.size(); fill++) {
						Board possible = this.cloneBoard();
						possible.fill(I, J, values.get(fill));
						allboards[fill] = possible;
					}
					return allboards;
				}
				j++;
			}
			i++;
		}
		return allboards;
	}

	/**
	 * Generates next board states in a more intelligent way.
	 * 
	 * @return
	 * @throws NullBoardException
	 */
	public Board[] getIntelligentChildren() throws NullBoardException {
		
		return null;
	}

	/**
	 * fill the hole at (x,y) with the value n
	 * 
	 * @param x
	 * @param y
	 * @param n
	 */
	public void fill(int row, int col, int n) {
		holes[row][col] = n;
	}

	/**
	 * check if the 3*3 sub-square contains the specified val
	 * 
	 * @param col
	 * @param row
	 * @param val
	 * @return
	 */
	public boolean checkSubsquare(int col, int row, int val) {
		int crem = col % 3;
		int rrem = row % 3;
		if (crem == 1)
			crem = 0;
		if (crem == 2)
			crem = 3;
		if (crem == 3)
			crem = 6;
		if (rrem == 1)
			rrem = 0;
		if (rrem == 2)
			rrem = 3;
		if (rrem == 3)
			rrem = 6;
		while (rrem < rrem + 3) {
			while (crem < crem + 3) {
				if (holes[rrem][crem] == val)
					return false;
				crem++;
			}
			rrem++;
		}
		return true;
	}

	/**
	 * to check if the same row has already contained the number n
	 * 
	 * @param row
	 * @param c
	 * @param n
	 * @return
	 */
	public boolean checkRow(int row, int c, int n) {
		for (int i = 0; i < 9; i++) {
			if (i != c && holes[row][i] == n)
				return false;
		}
		return true;
	}

	/**
	 * to check if the same column has already contained the number n
	 * 
	 * @param col
	 * @param r
	 * @param n
	 * @return
	 */
	public boolean checkColumn(int col, int r, int n) {
		for (int j = 0; j < 9; j++) {
			if (j != r && holes[col][j] == n)
				return false;
		}
		return true;
	}

	/**
	 * check if the game board holes are all filled with numbers
	 * 
	 * @return
	 */
	public boolean ifFull() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (holes[i][j] == -1)
					return false;
			}
		}
		return true;
	}

	/**
	 * Checks if board state is valid, for whatever degree of filled it is.
	 * 
	 * @return
	 */
	public boolean validBoardState() {
		for (int num = 1; num <= 9; num++) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (checkSubsquare(i, j, num) && checkRow(i, j, num)
							&& checkColumn(i, j, num) && checkBoard(holes))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * check if the board is in its goal position
	 * 
	 * @return
	 */
	public boolean ifGoal() {
		if (validBoardState() && ifFull())
			return true;
		return false;
	}

	/**
	 * make a deep copy of the current board
	 * 
	 * @return
	 * @throws NullBoardException
	 */
	public Board cloneBoard() throws NullBoardException {
		Board clone = new Board();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				clone.fill(i, j, holes[i][j]);
			}
		}
		return clone;
	}

	/**
	 * print the array representation of this board
	 */
	public void printBoard() {
		String out = "";
		for (int r = 0; r < 9; r++) {
			String row = "";
			for (int c = 0; c < 9; c++) {
				row = row + this.holes[r][c];
			}
			out = out + (row + "\n");
		}
		System.out.println(out);
	}

	/**
	 * Check if the input board b is a valid board
	 * 
	 * @param b
	 * @return
	 */
	boolean checkBoard(int[][] b) {
		if (b.length == 9 && b[0].length == 9)
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if ((b[i][j] <= 9 && b[i][j] >= 0) || b[i][j] == -1)
						return true;
				}
			}
		return false;
	}

}
