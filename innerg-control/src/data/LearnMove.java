package data;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Cet objet est cr�e quand on veut apprendre un nouveau mouvement. Il faut lui communiquer chaque 
 * nouveau paquet (Data) et lui indiquer quand le mouveau est termin� afin qu'il mette � jour le mouvement
 * moyen.
 **/

//A FAIRE : verifier la synchro

public class LearnMove {
	
	private Move currentMove; //Contient un mouvement en cour d'apprentissage
	private Move meanMove; //Contient les donn�es moyennes
	private int nbDatas; //Le nombre de mouvements utilis�s dans la moyenne

	public LearnMove() {
		super();
		this.currentMove = new Move();
		this.nbDatas=0;
	}
	
	public void rebootCur(){
		currentMove = new Move();
	}
	
	// Ajouter un point � la currentdatas
	public void pushNewData(Data data)
	{
		currentMove.getMove().add(data);
	}
	
	//Une fois que le nouveau mouv. d'appren. est termin� on actualise la moyenne
	public void recalcMeanData()
	{
		int curSize = currentMove.getMove().size();
		int meanSize = meanMove.getMove().size();
		
		//Si c'est le premier mouvement 
		if(nbDatas == 0) meanMove = new Move(currentMove.getMove(),currentMove.getMoveID(),currentMove.getMoveName());
		else
		{
			boolean toolong = false;
			int max = Math.max(meanSize, curSize);
			int min = Math.min(meanSize, curSize);
			
			//Si le nouveau mouvement d'appren. est plus long que le moyen
			if(meanSize==min) toolong = true;
			
			for(int i=0; i<min;i++)
			{
				meanMove.getMove().get(i).actualiseMean(currentMove.getMove().get(i), 1, nbDatas);
			}
			
			Data nulData = new Data();

			//Si toolong = true on ajoute les valeurs (pond�r�es avec des zeros);
			if(toolong)
			{
				for(int j = min;j<max;j++)
				{
					currentMove.getMove().get(j).actualiseMean(nulData, nbDatas, 1);
					meanMove.getMove().add(currentMove.getMove().get(j));
					
				}
			}
			//Sinon on ajoute on moyenne avec zero meandatas
			else
			{
				for(int j = min;j<max;j++)
				{
					meanMove.getMove().get(j).actualiseMean(nulData, 1, nbDatas);				
				}
			}		
			
		}
		
		nbDatas++;
			
			//On a ajout� une nouvelle donn�e a notre mouvement moyen
			
	}
		
	//Pour enregistrer le mouvement dans un fichier texte
	public void saveMove(String filepath)
	{
		meanMove.saveMove(filepath);
		
	}
		
	
	
	

}
