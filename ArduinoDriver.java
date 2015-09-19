package hackISU;

import java.io.OutputStream;

public class ArduinoDriver {
	private ArduinoMapper anArduinoMapper = null;
	private OutputStream anOutputStream = null;
	
	public void startArduino() {
		anArduinoMapper = new ArduinoMapper();
		anOutputStream = anArduinoMapper.initialize();
		
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(10000);} catch (InterruptedException ie) {}
			}
		};
			
		t.start();
		System.out.println("Started");
	}
		
	public void sendString (String aString) {
		if (anArduinoMapper != null) {  
			anArduinoMapper.send(aString, anOutputStream);
		}
		else {
			System.out.println("Arduino is stopped");
		}
	}
	
	public void stopArduino(){
		if (anArduinoMapper != null) {
			anArduinoMapper.close();
		}
	}
		
	

	
}
