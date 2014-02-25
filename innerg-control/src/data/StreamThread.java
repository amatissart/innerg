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
		long time;
		
		try {
			while(!isInterrupted()){
				time = System.currentTimeMillis();
				stream.newLine(proc);
				Thread.sleep(Math.max(0, 10-System.currentTimeMillis()+time));
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			stream.close();
		}
	}
}
