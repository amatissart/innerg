package data;

/**
 * Cet objet est crée quand on veut apprendre un nouveau mouvement. Il faut lui communiquer chaque 
 * nouveau paquet (Data) et lui indiquer quand le nouveau est terminé afin qu'il mette à jour le mouvement
 * moyen.
 **/

//A FAIRE : verifier la synchro

public class LearnMove {
	
	private Move currentMove; //Contient un mouvement en cour d'apprentissage
	private Move meanMove; //Contient les données moyennes
	private int nbDatas; //Le nombre de mouvements utilisés dans la moyenne
	private String filepath; //Chemin d'enregistrement
	
	private final int nbLearning = 5; //Nombre de mouvement avant arret appren.
	
	private final float THRESHOLD_VALID = 4; //AU PIF, seuil a partir duquel un point d'appren est considérer comme une singularité non valide

	public LearnMove(String filepath) {
		super();
		this.currentMove = new Move();
		this.nbDatas=0;
		this.filepath = filepath;
		
	}
	
	public boolean rebootCur(){
		
		if(nbDatas >nbLearning)
		{
			this.saveMove();
			
			return false;
			//On arrete l'apprentissage
		}
		else
			currentMove = new Move();
			return true;
	}
	
	// Ajouter un point à la currentdatas
	public void pushNewData(Data data)
	{
		currentMove.getMove().add(data);
	}
	
	//Une fois que le nouveau mouv. d'appren. est terminé on actualise la moyenne
	public void recalcMeanData()
	{
		if(true) System.out.println("On recalcule le mouvement moyen - ndata="+nbDatas);
		int curSize = currentMove.getMove().size();

		
		//Si c'est le premier mouvement 
		if(nbDatas == 0){
			meanMove = new Move(currentMove.getMove(),currentMove.getMoveID(),currentMove.getMoveName());
			nbDatas++;
		}
		else
		{
			int meanSize = meanMove.getMove().size();
			//On vérifie que ce mouvement n'est pas une singularité
			if(currentMove.calcDist(meanMove)<THRESHOLD_VALID)
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
	
				//Si toolong = true on ajoute les valeurs (pondérées avec des zeros);
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
				
				nbDatas++;
				//On a ajout� une nouvelle donn�e a notre mouvement moyen
				
			}
			
		}
			
			
			
	}
		
	//Pour enregistrer le mouvement dans un fichier texte
	public void saveMove()
	{
		meanMove.saveMove(filepath);
		
	}
		
	
	
	

}
