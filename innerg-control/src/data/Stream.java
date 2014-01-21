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
import gui.Window;

public class Stream {
	
	public final String port = "/dev/rfcomm0";
	private BufferedReader in;
	private SerialPort serialPort;
	private InputStream portInputStream;
	
	
	volatile private Float[] acc,ori;
	private GraphPanel graph;
	private Window window;

	public void setAcc(Float[] values){
		acc = values;
	}
	
	public void setOri(Float[] values){
		ori = values;
	}
	
	public Stream(Window w){
		graph = w.getGraph();
		window = w;
	}

	public String start(){
		
		try {
			CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort) commPortIdentifier.open("Test", 2000);
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
				
				setAcc (Arrays.copyOfRange(val, 0, 3));
				setOri (Arrays.copyOfRange(val, 3, 6));	
				
				graph.repaint();
				window.setText("Acceleration  x: "+ acc[0]+" y: "+acc[1]+" z: "+acc[2],
							   "Orientation   x: "+ ori[0]+" y: "+ori[1]+" z: "+ori[2]	);
				
			}
	}

	public Float[] getOri() {
		return ori;
	}
	
	public Float[] getAcc() {
		return acc;
	}
	
	public void close(){
		try {
			in.close();
			portInputStream.close();
			serialPort.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}