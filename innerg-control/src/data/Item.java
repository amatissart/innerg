package data;

public class Item {
	//décrit un objet commandable par le bracelet, et sa position
	
	public final int plug;  // identifiant de la prise éléctrique associée à l'objet
	public final double x;  // orientation nord/sud correspondant à l'objet
	public final double y;	// orientation haut/bas correspondant à l'objet
	
	public Item(int i,double x,double y){
		this.plug = i;
		this.x = x;
		this.y = y;
	}

}
