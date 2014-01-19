package data;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gui.GraphPanel;


public class Stream {
	
	public final String port = "/dev/rfcomm0";
	private BufferedReader in;
	
	volatile private Float[] acc,ori;
	private GraphPanel graph;

	public void setAcc(Float[] values){
		acc = values;
	}
	
	public void setOri(Float[] values){
		ori = values;
	}
	
	public Stream(GraphPanel g){
		graph = g;
	}

	public String start(){
		
		InputStream portInputStream = null;

		try {
			CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(port);
			SerialPort serialPort = (SerialPort) commPortIdentifier.open("Test", 2000);
			portInputStream = new BufferedInputStream(serialPort.getInputStream());
	
		
			in = new BufferedReader(new InputStreamReader(portInputStream));
			in.readLine();
			
			return "";
			
		} catch (NoSuchPortException e) {
			e.printStackTrace();
			return "Impossible to communicate with the serial port";
		}  
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} 
		 		
	}
	
	public void newLine() throws IOException{
			String temp=null;
			String[] fig;
			Float[] val = new Float[6];
						
			while (in.ready()){
				temp=in.readLine();
			}
			
			if (temp != null){
				temp = temp.replace(',','.');
				fig=temp.split(";");
				
				for(int i=0;i<6;i++){
					val[i]=Float.valueOf(fig[i]);
				}
				
				setAcc (Arrays.copyOfRange(val, 0, 2));
				setOri (Arrays.copyOfRange(val, 3, 5));	
				
				graph.repaint();
				
			}
	}

	public Float[] getOri() {
		return ori;
	}
}