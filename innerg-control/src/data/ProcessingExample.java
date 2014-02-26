package data;

import gui.Window;

public class ProcessingExample implements ProcessingInterface {

	// Cette implementation du traitement ne fait qu'afficher dans la console les valeurs reçues
	
	public void update(Float[] acc, Float[] ori) {
		System.out.println(" *** Données actualisées *** ");
		System.out.println("Valeurs d'acceleration : x= "+acc[0]+" | y= "+acc[1]+ "| z = "+acc[2]);
		System.out.println("Valeurs du gyroscope  : x= "+ori[0]+" | y= "+ori[1]+ "| z = "+ori[2]);
	}

	public void setWindow(Window w) {}

}
