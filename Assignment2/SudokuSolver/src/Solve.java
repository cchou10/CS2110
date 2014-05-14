import java.util.*;

/**
 * this class is the method for solving the given board
 * @author hs
 *
 */
public class Solve {
	private int stattotal;
	private int statunique;
	private int total;
	
	/**
	 * count all the possible number of solutions to the input board in
	 * @param in
	 * @return number of solutions
	 * @throws NullBoardException
	 */
	public int countSol(Board in) throws NullBoardException{
		total = 0;
		Board[] children = in.getChildren();
		if (in.ifGoal())
			total++;
		if (in.validBoardState())
			stattotal++;
		for (int i = 0; i < children.length; i++){	
			countSol(children[i]);
		}
		return total;
	}
	
	/**
	 * Solves the puzzle and simply prints solution
	 * @param in
	 * @throws NullBoardException
	 */
	
	public void printSolve(Board in) throws NullBoardException{
		Board[] children = in.getChildren();
		if (in.ifGoal())
			in.printBoard();
		if (countSol(in) == 0){
			System.out.println("there are no solutions");
		}
		for (int i = 0; i < children.length; i++){
			printSolve(children[i]);
		}
	}
	
	/**
	 * Solve with statistics
	 * @param in
	 * @return one valid solution
	 * @throws NullBoardException
	 */
	public ArrayList<Board> solveWithStats(Board in) throws NullBoardException{
		Board[] children = in.getChildren();
		ArrayList<Board> solve = new ArrayList<Board>();
		if (in.ifGoal())
			solve.add(in);
			in.printBoard();
		if (countSol(in) == 0){
			System.out.println("there are no solutions");
		}
		for (int i = 0; i < children.length; i++){
			printSolve(children[i]);
		}
		int j = 0;
		while (j < solve.size()){
			Board one = solve.get(j);
			int k = 0;
			while (k < solve.size()){
				if (k != j && one == solve.get(k)){
					solve.remove(k);
				}
			}
		}
		statunique = solve.size();
		return solve;
	}
	
	public Board solveSmart(Board in) throws NullBoardException{
		Board[] children = in.getIntelligentChildren();
		if (in.ifGoal())
			return in;
		for (int i = 0; i < children.length; i++){
			printSolve(children[i]);
		}
		System.out.println("There are no solutions");
		return null;
	}

	public int getStattotal() {
		return stattotal;
	}

	public int getStatunique() {
		return statunique;
	}

	public int getTotal() {
		return total;
	}	
	

}