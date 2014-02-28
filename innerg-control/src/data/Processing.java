package data;
/**
 * Cette classe determinera � partir de quand un mouvement commence ou s'arrete
 * @author Nicolas
 *
 */
public class Processing implements ProcessingInterface {
	
	private Move move;
	private boolean isOn; //D�termine si un processus (appretissage, detection est en cour
	private boolean block; //Bloque l'execution de update
	private int offCount; //Compte le nombre d'it�ration sans mouvement (a un delta pres)
	private int mode; //Le mode en cour, aprentissage(0), analyse statique (1)
	private StaticComparator sc;
	private LearnMove lm;
	private final int[] params; //Parametres a etudier pour l'amplitude
	
	public static final float AMP_MIN = 3; //AU PIF !
	public static final int MAX_OFFCOUNT = 4; //AU PIF !
	
	
	public Processing() {
		super();
		this.move = new Move();
		this.isOn = false;
		this.block = false;
		this.offCount = 0;
		this.sc=new StaticComparator();
		this.lm = new LearnMove("innerg/moves/movelearn.txt");
		this.mode = 1;
		this.params = new int[3];
		
		//On ne calcule les distance/amplitude qu'en fonction du gyro
		params[0]=1;
		params[1]=2;
		params[2]=3;

		
	}
	
	
	public void setMode(int mode) {
		this.mode = mode;
	}



	public void update(Float[] acc, Float[] ori) {
		
		if(!block)
		{
			/*
			 * On verifie d'abort "l'amplitude" des donn�es recues. Si cette amplitude est trop faible,
			 * on verifie tout de meme que ce point n'est pas une singularit� d'un mouvement.Autrement dit,
			 * il est possible qu'au cours d'un mvt, un point est une amplitude faible ce qui ne signifie pas
			 * que le mouvement est termin�. On incremente donc une varible offCount qui mesure le nombre de fois
			 * que l'entree est "nulle" consecutivement.
			 */
			
			Data data = new Data(acc[0],acc[1],acc[2],ori[0],ori[1],ori[2]);
			
			float amp = data.getAmp(params);
			
			
			//Si l'entree est faible
			if(amp < AMP_MIN)
			{	
				//Si l'analyse est en cour
				if(isOn)
				{
					//L'entree est faible depuis trop longtemps on arrete le process
					if(offCount > MAX_OFFCOUNT)
					{	
						//Si on etait en apprentissage
						if(mode == 0)
						{
							lm.recalcMeanData();
							if(!lm.rebootCur()){
								//Si l'apprentissage est termin� on bloque la fonction
								block=true;
							}
								
						}
						//Si on etait en analyse
						else if(mode == 1)
						{
							//On compare ce mouvement 
							int id = sc.getBestMove(move);
							
							if(true) System.out.println("Le mouvement est le num�ro "+id);
							
							/**
							 * ET LA ON FAIT UN TRUC EN FONCTION DE LA REPONSE
							 */
							
							//On reinitialise move;
							move = new Move();
						}
						
						isOn =false;
					}
					else //On continue d'enregistrer les donn�es
					{
						//Si on etait en apprentissage
						if(mode == 0)
						{
							lm.pushNewData(data);
						}
						else if(mode == 1)
						{
							move.getMove().add(data);
						}
					}
				}
				//Sinon on laisse passer ce point
				offCount++;
					
			}
			else //L'amplitude est suffisante
			{
				offCount = 0;
				//Si c'est pas deja fait on lance le process
				if(!isOn)
				{
					isOn= true;
					System.out.println("Debut d'analyse");
				}
				
				//Si on etait en apprentissage
				if(mode == 0)
				{
					lm.pushNewData(data);
				}
				else if(mode == 1)
				{
					move.getMove().add(data);
				}
			}
				
		}
	}

}
