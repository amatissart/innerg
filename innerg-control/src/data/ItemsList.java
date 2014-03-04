package data;

import gpioClient.GpioControl;

import java.util.ArrayList;

public class ItemsList {
	
	private ArrayList<Item> list;
	private GpioControl gpio;
	
	public ItemsList(GpioControl g){
		gpio = g;
		list = new ArrayList<Item>();
		list.add(new Item(1,100,0)); // Prise 1
		list.add(new Item(2,150,0)); // Prise 2
		list.add(new Item(3,200,0)); // Prise 3
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
	
	
	
	
	
	
	

}
