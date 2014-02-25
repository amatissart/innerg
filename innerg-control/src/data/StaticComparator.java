package data;

import java.util.ArrayList;

/**
 * Cette objet contient a sa creation la base de donn�es des mouvements. Une methode retourne
 * l'id du mouvement le plus proche pass� en entr�e (ou -1 si le mouvement est "trop loin")
 * @author Nicolas
 *
 */

public class StaticComparator {
	
	private final boolean DEV_MODE = true;
	private final int _THRESHOLD = 2; // Seuil d'acceptation d'un mouvement, a regler
	
	private ArrayList<Move> movesDatabase;

	public StaticComparator() {
		
		
		super();
		
		movesDatabase = new ArrayList<Move>();
		Move myMove1 = new Move();
		myMove1.initialise(Main.MOVES_DIR+"/movelearn.txt");
		myMove1.setMoveID(1);
		myMove1.setMoveName("Activation");

		
		movesDatabase.add(myMove1);
	}
	
	//Fonction retournant l'id du mouvement le plus proche ou -1 s l'�cart est > seuil
	
	public int getBestMove(Move moveStudied)
	{
		float minDist = Integer.MAX_VALUE;
		int nbMoves = movesDatabase.size();
		int id = -1;
		
		for(int i=0;i<nbMoves;i++)
		{
			float newDist = moveStudied.calcDist(movesDatabase.get(i));
			
			if(DEV_MODE) System.out.println("La distance au mvt "+movesDatabase.get(i).getMoveID()+" est de : "+newDist);
			
			//Si la distance est plus petite que le seuil et minimale on actualise les donn�es
			if(newDist<_THRESHOLD && newDist<minDist)
			{
				id = movesDatabase.get(i).getMoveID();
				minDist = newDist;
			}
		}
		
		return id;
	}
	
	

}
