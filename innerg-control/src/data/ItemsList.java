package data;

import gpioClient.GpioControl;

import java.util.ArrayList;

public class ItemsList {
	
	private static final double SEUIL = 50;
	private ArrayList<Item> list;
	private GpioControl gpio;
	
	public ItemsList(GpioControl g){
		gpio = g;
		list = new ArrayList<Item>();
		list.add(new Item(4,40,0)); // Prise 4 à 40°N
		list.add(new Item(3,80,0)); // Prise 3 à 80°N
//		list.add(new Item(3,200,0)); 
	}

	public void itemToItemMove(Move move) {
		ArrayList<Data> data = move.getMove();
		Data first = data.get(0);
		Data last = data.get(data.size()-1);
		
		System.out.println("Move orientations : ");
		System.out.println(first.getoX());
		System.out.println(last.getoX());
		
		
		// A compléter : recherche des objets correspondants aux objets;
	}
	
	public void itemToItemMove(Move move1,Move move2) {
	//	ArrayList<Data> data1 = move1.getMove();
	//	Data first = data1.get(0);
	//	Data last1 = data1.get(data1.size()-1);
		
		ArrayList<Data> data2 = move2.getMove();
			Data first = data2.get(0);
		Data last = data2.get(data2.size()-1);
		
		int orig = 0;
		double orig_dist = 360;
		int dest = 0;
		double dest_dist = 360;
		double temp;
		
		for (Item i : list){
			temp  = (Math.abs(first.getoX()-i.x) % 360)+ (Math.abs(first.getoY()-i.y) % 360);
			if(temp < SEUIL && temp < orig_dist){
				orig_dist = temp;
				orig = i.plug;
			}
			temp  = (Math.abs(last.getoX()-i.x) % 360)+ (Math.abs(last.getoY()-i.y) % 360);
			if(temp < SEUIL && temp < dest_dist){
				dest_dist = temp;
				dest = i.plug;
			}
		}
		
		if(orig !=0 && dest !=0 && orig != dest){
			gpio.turnOff(orig);
			gpio.turnOn(dest);
		}
		
	}
	
	
	
	
	
	
	

}
