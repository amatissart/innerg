package data;
import gui.Window;


public class Main {
	
	public static final boolean GuiTest = true; // Si vrai, le programme lance l'interface graphique de test
	public static final boolean Switch = true;  // Commande ou non des interrupteurs via RaspberryPi

	public static void main(String[] args) {
		
		if (GuiTest){
			new Window();
		}
		else {
			
		// Squelette de base permettant de traiter les données
			
			// La classe ProcessingExample décrit le traitement des données, via la méthode "update" qu'elle implemente
			ProcessingInterface proc = new ProcessingExample();
			
			// Initialise la connection Bluetooth
			Stream stream = new Stream();  
			
			// Le thread prend en paramètre le stream, et la classe responsable du traitement
			StreamThread thread = new StreamThread(stream, proc);  
			
			// Lance la lecture des données
			thread.start(); 		
			
			// Pour le test, on arrête au bout de 10 secondes.
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			thread.interrupt();
			stream.close();
			
		}

	}

}
