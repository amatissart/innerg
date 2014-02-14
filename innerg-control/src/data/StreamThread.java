package data;

import java.io.IOException;

public class StreamThread extends Thread {
	
	private Stream stream;
	private ProcessingInterface proc;
	
	public StreamThread(Stream s,ProcessingInterface p){
		stream = s;
		proc = p;
	}
	
	public void run() {
		try {
			while(!isInterrupted()){
				stream.newLine(proc);
				Thread.sleep(20);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			stream.close();
		}
	}
}
