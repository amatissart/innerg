package data;
/**
 * Cette classe determinera à partir de quand un mouvement commence ou s'arrete
 * @author Nicolas
 *
 */
public class Processing implements ProcessingInterface {
	
	private Move move;
	private boolean isOn;
	private int offCount; //Compte le nombre d'itération sans mouveùent (a un delta pres)
	private final float[] deltas; //delta pour chaque parametre
	private int mode; //Le mode en cour, aprentissage(0), analyse statique (1)
	private StaticComparator sc;
	private LearnMove lm;
	
	public static final float AMP_MIN = 10; //AU PIF !
	public static final int MAX_OFFCOUNT = 4; //AU PIF !
	public static final int _THRESHOLD = 10; //AU PIF !
	
	
	public Processing() {
		super();
		this.move = new Move();
		this.isOn = false;
		this.offCount = 0;
		this.deltas = new float[6];
		this.sc=new StaticComparator();
		this.lm = new LearnMove();
		this.mode = 1;
		
		//Au pif : A REGLER
		deltas[1]=10; //aX
		deltas[2]=10;
		deltas[3]=10;
		deltas[4]=10; //oX
		deltas[5]=10;
		deltas[6]=10;
		
	}
	
	public void update(Float[] acc, Float[] ori) {
		
		/*
		 * On verifie d'abort "l'amplitude" des données recues. Si cette amplitude est trop faible,
		 * on verifie tout de meme que ce point n'est pas une singularité d'un mouvement.Autrement dit,
		 * il est possible qu'au cours d'un mvt, un point est une amplitude faible ce qui ne signifie pas
		 * que le mouvement est terminé. On incremente donc une varible offCount qui mesure le nombre de fois
		 * que l'entree est "nulle" consecutivement.
		 */
		
		Data data = new Data();
		
		float amp = data.getAmp();
		
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
						lm.rebootCur();
					}
					//Si on etait en analyse
					else if(mode == 0)
					{
						//On compare ce mouvement 
						int id = sc.getBestMove(move,_THRESHOLD);
						
						/**
						 * ET LA ON FAIT UN TRUC EN FONCTION DE LA REPONSE
						 */
						
						//On reinitialise move;
						move = new Move();
					}
					
					isOn =false;
				}
				else //On continue d'enregistrer les données
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
				
		}
		else //L'amplitude est suffisante
		{
			offCount = 0;
			//Si c'est pas deja fait on lance le process
			isOn = true;
			
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
