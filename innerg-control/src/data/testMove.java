package data;


public class testMove {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StaticComparator sc = new StaticComparator();
		
		Move move = new Move();
		move.initialise("innerg/moves/movetest.txt");
		
		System.out.println(sc.getBestMove(move, 10));

	}

}
