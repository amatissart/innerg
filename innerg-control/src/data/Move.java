package data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Cette classe est la structure d'un mouvement, soit une liste de Data.
 * Elle a en attribut un id et un nom qui caractérise le mouvement.
 * Une methode permet de calculer l'ecart avec un autre mouvement
 * Une methode permet d'initialiser le mouvement à partir d'un fichier texte
 * Une autre methode permet d'enregistrer le mouvement dans un fichier texte
 **/

public class Move {
	
	private final boolean DEV_MODE = false;
	
	private ArrayList<Data> move;
	private int moveID;
	private String moveName;
	private int[] params; //Parametres a etudier pour calculer la distance


	public Move(ArrayList<Data> move, int moveID, String moveName) {
		this.move = new ArrayList<Data>(move);
		this.moveID = moveID;
		this.moveName = moveName;
		initParams();
	}
	
	public Move() {
		this.move = new ArrayList<Data>();
		this.moveID = 0;
		this.moveName = "Untitled";
		initParams();
	}
	
	private void initParams(){
		this.params=new int[3];
	
		//On etudie que le gyroscope
		params[0]=1;
		params[1]=2;
		params[2]=3;
		
	}

	public ArrayList<Data> getMove() {
		return move;
	}

	public void setMove(ArrayList<Data> move) {
		this.move = move;
	}

	public int getMoveID() {
		return moveID;
	}

	public void setMoveID(int moveID) {
		this.moveID = moveID;
	}

	public String getMoveName() {
		return moveName;
	}

	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}
	
	//Initialise un mouvement à partir d'un fichier
	//!!!! Cette fonction contient le nombre de parametres (6) a changer si nouvelles données (giro, gps etc...)
	
	public void initialise(String filepath)
	{
		try{
			
			InputStream ips=new FileInputStream(filepath); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				String[] datas = ligne.split(";");
				float[] datasFloat = new float[6];
				int size = datas.length;
				size=Math.min(size,6);
				
				for(int i=0;i<size;i++)
				{
					datasFloat[i] = Float.parseFloat(datas[i]);
				}
				
				Data data = new Data(datasFloat[0],datasFloat[1],datasFloat[2],datasFloat[3],datasFloat[4],datasFloat[5]);
				
				
				move.add(data);
			}
		br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}		
	}
	
	//Pour enregistrer le mouvement dans un fichier texte
	public void saveMove(String filepath)
	{
		int meanSize = move.size();
	    try{
	      PrintWriter out  = new PrintWriter(new FileWriter(filepath));
	      for (int i = 0; i < meanSize; i++){		    	  
	        out.print(move.get(i).getaX() + ";");
	        out.print(move.get(i).getaY() + ";");
	        out.print(move.get(i).getaZ() + ";");
	        out.print(move.get(i).getoX() + ";");
	        out.print(move.get(i).getoY() + ";");
	        out.println(move.get(i).getoZ() + ";");
	      }
	      if(true) System.out.println("Move : Fichier sauvegardé !");
	      out.close();
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
		
	}
	
	//Retourne la distance entre ce mouvement et un autre
	//Cette distance est point a point (a amliorer en fonction des tests)
	public float calcDist(Move studiedMove)
	{
		int moveSize = move.size();
		int studySize = studiedMove.getMove().size();
		
		int min = Math.min(moveSize, studySize);
		int max = Math.max(moveSize, studySize);
		
		float dist = 0;
		
		if(DEV_MODE) System.out.println("Move : Mouvement num "+studiedMove.getMoveID());
		
		for(int i = 0;i<min;i++)
		{
			dist+=move.get(i).calcDist(studiedMove.getMove().get(i),params);
			if(DEV_MODE) System.out.println("Move : distance a l'etape "+i+" = "+dist);
			
		}
		
		Data nuldata = new Data();
		
		//Si un des move est plus long on fait la difference avec zero
		if(moveSize == min){
			
			for(int i=min;i<max;i++){
				dist+=studiedMove.getMove().get(i).calcDist(nuldata,params);
				if(DEV_MODE) System.out.println("Move : distance a l'etape "+i+" = "+dist);
			}
			
		}
		else
		{
			
			for(int i=min;i<max;i++){
				dist+=move.get(i).calcDist(nuldata,params);
				if(DEV_MODE) System.out.println("Move : distance a l'etape "+i+" = "+dist);
			}
			
		}
		

		
		dist = dist/min;
		
//		if(DEV_MODE) 
			System.out.println("Move : distance = "+dist);
		
		return dist;
		
	}
	
	
	
	
	

}
