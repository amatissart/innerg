package data;

/**
 * Cette classe est la structure d'un paquet de données (accélération+orienation)
 * Une méthode permet aussi de calculer la distance entre deux paquets, selon un ou plusieurs parametres
 **/

public class Data {
	
	private float aX;
	private float aY;
	private float aZ;
	private float oX;
	private float oY;
	private float oZ;
	
	
	public Data(float aX, float aY, float aZ, float oX, float oY, float oZ) {
		super();
		this.aX = aX;
		this.aY = aY;
		this.aZ = aZ;
		this.oX = oX;
		this.oY = oY;
		this.oZ = oZ;
	}
	
	public Data() {
		super();
		this.aX = 0;
		this.aY = 0;
		this.aZ = 0;
		this.oX = 0;
		this.oY = 0;
		this.oZ = 0;
	}
	
	//Methode qui actualise la valeur des variables en fonction d'autres valeur et de poids.
	public void actualiseMean(Data data, int weightExt, int weightLoc)
	{
		int totWeight = weightLoc + weightExt;
		
		aX = (data.getaX()*weightExt + aX*weightLoc)/totWeight;
		aY = (data.getaY()*weightExt + aY*weightLoc)/totWeight;
		aZ = (data.getaZ()*weightExt + aZ*weightLoc)/totWeight;
		
		oX = (data.getoX()*weightExt + oX*weightLoc)/totWeight;
		oY = (data.getoY()*weightExt + oY*weightLoc)/totWeight;
		oZ = (data.getoZ()*weightExt + oZ*weightLoc)/totWeight;
		
	}
	
	public float getaX() {
		return aX;
	}
	public void setaX(float aX) {
		this.aX = aX;
	}
	public float getaY() {
		return aY;
	}
	public void setaY(float aY) {
		this.aY = aY;
	}
	public float getaZ() {
		return aZ;
	}
	public void setaZ(float aZ) {
		this.aZ = aZ;
	}
	public float getoX() {
		return oX;
	}
	public void setoX(float oX) {
		this.oX = oX;
	}
	public float getoY() {
		return oY;
	}
	public void setoY(float oY) {
		this.oY = oY;
	}
	public float getoZ() {
		return oZ;
	}
	public void setoZ(float oZ) {
		this.oZ = oZ;
	}
	
	//Calcule la distance entre deux datas suivant un nombre defini de parametres
	//params =[1,2,3,5] signifie qu'on étudiera aX,aY,aZ,oY
	
	public float calcDist(Data studiedData,int[] params)
	{
		float dist =0;
	
		int nbParam = params.length;
		
		float distPoint=0;
		
		for(int i =0; i<nbParam;i++)
		{

			switch(params[i])
			{
			case 1:
				distPoint=Math.abs(aX - studiedData.getaX());
				break;
			case 2:
				distPoint=Math.abs(aY - studiedData.getaY());
				break;
			case 3:
				distPoint=Math.abs(aZ - studiedData.getaZ());
				break;
			case 4:
				distPoint=Math.abs(oX - studiedData.getoX());
				break;
			case 5:
				distPoint=Math.abs(oY - studiedData.getoY());
				break;
			case 6:
				distPoint=Math.abs(oZ - studiedData.getoZ());
				break;
			default:
				break;
			}
			
			dist+=distPoint;
			distPoint=0;
		}
		
		return dist;
		
	}
	
	//Si le tableau n'est pas passé en parametre on calcule avec toutes les composantes
	public float calcDist(Data studiedData)
	{
		int[] tab = new int[6];
		for(int i=0;i<6;i++) tab[i] = i+1;
		
		return calcDist(studiedData,tab);
		
	}
	
	//Calcul l'amplitude d'un point (si le portable bouge suffisement selon certain parametres
	public float getAmp(int[] params)
	{
		int size = params.length;
		float amp = 0;
		
		for(int i=0;i<size;i++)
		{
			switch(params[i])
			{
			case 1:
				amp +=Math.abs(aX);
				break;
			case 2:
				amp +=Math.abs(aY);
				break;
			case 3:
				amp +=Math.abs(aZ);
				break;
			case 4:
				amp +=Math.abs(oX);
				break;
			case 5:
				amp +=Math.abs(oY);
				break;
			case 6:
				amp +=Math.abs(oZ);
				break;
			default:
				amp+=0;
			
			}
			
		}
		System.out.println(" : amp = "+amp);
		return amp;
	}
	
	//Si rien en entrée on prend tout en compte
	public float getAmp()
	{
		int[] tab = new int[6];
		for(int i=0;i<6;i++) tab[i] = i+1;
		return getAmp(tab);
	}

}
