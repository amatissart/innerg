package gpioClient;

import gpioClient.devices.digital.GPIO;
import gpioClient.devices.digital.NativeGPIO;

public class GpioControl {
	
	public final static int nbPlugs = 4;
	
	private int[] pins = {2,3,4,17};
	private boolean[] states = {true,true,true,true};
	private NativeGPIO gpio;
	
	
	public GpioControl(){
		String host = "192.168.3.2";
		PiClient client = new PiHttpClient(host, 8042);

		client.setCredentials("webiopi", "raspberry");

		gpio = new NativeGPIO(client);

		for (int i=0;i<nbPlugs;i++){
			gpio.setFunction(pins[i], GPIO.OUT);
			gpio.digitalWrite(pins[i], states[i]);
		}
	}
	
	public void turnOn(int i){
		if (i>0 && i<= nbPlugs)
			gpio.digitalWrite(pins[i-1], false);
	}
	
	public void turnOff(int i){
		if (i>0 && i<= nbPlugs)
			gpio.digitalWrite(pins[i-1], true);
	}
	
	public void toggle(int i){
		if (i>0 && i<= nbPlugs){
			states[i-1] = !states[i-1];
			gpio.digitalWrite(pins[i-1],states[i-1]);
		}
	}
	
	

	public static void main(String[] args) throws InterruptedException {
		
		GpioControl gpio = new GpioControl();
		
		for (int i = 0; i <= 100; i++) {
			gpio.toggle(3);
			Thread.sleep(2000);
		}
	}

}
