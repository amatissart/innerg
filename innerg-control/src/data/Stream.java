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

public class Stream {
	
	public final String port = "/dev/rfcomm0";  // Port série correspondant à la communication Bluetooth
	private BufferedReader in;
	private SerialPort serialPort;
	private InputStream portInputStream;
	
	volatile private Float[] acc,ori;    // Tableaux contenant les données temps réel : Acceleration et Orientation

	public void setAcc(Float[] accValues){
		acc = accValues;
	}
	
	public void setOri(Float[] oriValues){
		ori = oriValues;
	}
	
	public Stream(){
		String result = this.start();
		
		if(!result.isEmpty())
		    System.err.println(result);
	}

	public String start(){
		
		try {
			String ligne;
			boolean serialPortOk = false;
			String[] cmd = {"/bin/sh", "-c"," sdptool browse local "};

			Process p =	Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());

			in = new BufferedReader(isr);
			while((ligne = in.readLine()) != null){
				if(ligne.contains("Serial Port")){
					serialPortOk = true;
					break;
				}
			}
					
			in.close();
			
			if(serialPortOk){
				CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(port);
				serialPort = (SerialPort) commPortIdentifier.open("Test", 2000);
				portInputStream = new BufferedInputStream(serialPort.getInputStream());
		
				in = new BufferedReader(new InputStreamReader(portInputStream));
				in.readLine();
				
				return "";
			}
			else return "No serial port !";
			
		} catch (NoSuchPortException e) {
			e.printStackTrace();
			return "Impossible to communicate with the serial port";
		}  
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} 
		 		
	}
	
	public void newLine(ProcessingInterface proc) throws IOException{
			String temp=null;
			String[] fig;
			Float[] val = new Float[6];
						
			try {
				while (in.ready()){
					temp=in.readLine();
					
					if (temp != null && !temp.isEmpty()){
						fig=temp.split(";"); // Les 6 valeurs reçues sur une ligne sont séparées par un point-virgule
						
						for(int i=0;i<6;i++){
							val[i]=Float.valueOf(fig[i]);
						}
						
						setAcc (Arrays.copyOfRange(val, 0, 3));
						setOri (Arrays.copyOfRange(val, 3, 6));	
						
						proc.update(acc,ori);  // C'est ici que le traitement est lancé, lorsque les valeurs sont reçues		
					}
				}
			} catch (Exception e) {
				System.err.println(temp);
				e.printStackTrace();
				System.exit(-1);
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
