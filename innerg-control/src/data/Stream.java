package data;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gui.Window;


public class Stream {
	
	public final String port = "/dev/rfcomm0";
	private Window w;
	private BufferedReader in;
	
	public Stream(Window w){
		this.w = w;
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
			return "Impossible to communicate with the serial port";
		}  
		catch (Exception e) {
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
					System.out.println(i+" "+val[i]);
				}
				w.newData(val);	
			}
	}
}