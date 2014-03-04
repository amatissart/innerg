package data;
import gui.Window;


public class Main {
	
 	public static final String MOVES_DIR = "innerg/moves";  
	
	public static final boolean GuiTest = true; // Si vrai, le programme lance l'interface graphique de test
	public static final boolean ElectricSwitch = false;  // Commande ou non des interrupteurs via RaspberryPi

	public static void main(String[] args) {
	
		if (GuiTest){
			Processing proc = new Processing();
			proc.setMode(1);
			new Window(proc);
		}
		else {
			
		// Squelette de base permettant de traiter les données
			
			// La classe ProcessingExample décrit le traitement des données, via la méthode "update" qu'elle implemente
			Processing proc = new Processing();
			proc.setMode(1);
			
			// Initialise la connection Bluetooth
			Stream stream = new Stream();  
			
			// Le thread prend en paramètre le stream, et la classe responsable du traitement
			StreamThread thread = new StreamThread(stream, proc);  
			
			// Lance la lecture des données
			thread.start(); 		
			
			// Pour le test, on arrête au bout de 10 secondes.
			try {
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			thread.interrupt();
			stream.close();	
		}

	}

}
