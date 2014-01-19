package data;

import java.io.IOException;

public class StreamThread extends Thread {
	
	private Stream stream;
	
	public StreamThread(Stream s){
		stream = s;
	}
	
	public void run() {
		try {
			while(!isInterrupted()){
				stream.newLine();
				Thread.sleep(20);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
