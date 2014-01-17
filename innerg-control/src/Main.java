import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;


public class Main {

	public static void main(String[] args){
		
		InputStream portInputStream = null;

		try {
			CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier("/dev/rfcomm0");
			SerialPort serialPort = (SerialPort) commPortIdentifier.open("Test", 2000);
			portInputStream = new BufferedInputStream(serialPort.getInputStream());
	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(portInputStream));
		
		String temp;
		String[] fig;
		Float[] val = new Float[6];
		
		while(true){
			try {
				temp = in.readLine().replace(',','.');
				fig=temp.split(";");
				
				for(int i=0;i<6;i++){
					val[i]=Float.valueOf(fig[i]);
					System.out.println(i+" "+val[i]);
				} 
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} 
		
	}
	
}
