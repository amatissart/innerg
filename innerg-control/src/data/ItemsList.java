package data;

import gpioClient.GpioControl;

import java.util.ArrayList;

public class ItemsList {
	
	private static final double SEUIL = 70;
	private ArrayList<Item> list;
	private GpioControl gpio;
	
	public ItemsList(GpioControl g){
		gpio = g;
		list = new ArrayList<Item>();
	//	list.add(new Item(2,45,-15)); 
		list.add(new Item(3,15,-10));
		list.add(new Item(4,70,-10));
		//		list.add(new Item(3,200,0));  Prise 3 à 200°N
	}

	public void itemToItemMove(Move move2) {
	//	ArrayList<Data> data1 = move1.getMove();
	//	Data first = data1.get(0);
	//	Data last1 = data1.get(data1.size()-1);
		
		ArrayList<Data> data2 = move2.getMove();
		Data last = data2.get(data2.size()-1);
		
		System.out.println("turnOn");
		System.out.println("x : "+last.getoX());
		System.out.println("y : "+last.getoY());
		
		int dest = 0;
		double dest_dist = 360;
		double temp;
		
		for (Item i : list){
			temp  = (Math.abs(last.getoX()-i.x)) % 360;
			temp = Math.min(temp, Math.abs(360-temp));
			temp += (Math.abs(last.getoY()-i.y) % 360);
			if(temp < SEUIL && temp < dest_dist){
				dest_dist = temp;
				dest = i.plug;
			}
		}
		
		if(dest !=0){
			gpio.turnOn(dest);
		}
		
	}

	public void turnOffMove(Move move) {
		System.out.println("turnoff");
		ArrayList<Data> data = move.getMove();
		Data last = data.get(0);
		
		int orig = 0;
		double dist = 360;
		double temp;
		
		for (Item i : list){
			temp  = (Math.abs(last.getoX()-i.x)) % 360;
			temp = Math.min(temp, Math.abs(360-temp));
			temp += (Math.abs(last.getoY()-i.y) % 360);
			
			if(temp < SEUIL && temp < dist){
				dist = temp;
				orig = i.plug;
			}
		}
		
		System.out.println("on eteint "+orig);
		
		if(orig !=0){
			gpio.turnOff(orig);
		}
		
	}
	
	
	
	
	
	
	

}
