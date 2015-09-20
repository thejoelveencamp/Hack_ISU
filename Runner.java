package hackISU;

import java.util.Scanner;

import ch.aplu.xboxcontroller.XboxController;

public class Runner {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("===========================");
		System.out.println("=========WELCOME===========");
		System.out.println("===========================");
		System.out.println("Type (s) start: (q) to quit");
		System.out.println("===========================");
		
		XboxController xc = new XboxController();
	    while (!xc.isConnected())
	    {
	    	System.out.println("controller not connected");
	    }
	    
		
		ArduinoDriver ard = new ArduinoDriver();
		ard.startArduino();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		System.out.println("Ready");
//		ard.sendString("$00001!");
//		
//		
		LightsOut lightsOut = new LightsOut(xc, ard);
		lightsOut.startGame();
//		
//		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ard.stopArduino();
		
		
//		Scanner s = new Scanner(System.in);
//	
//		while(!s.next().equals("q")) {		
//			switch (s.next())
//			{
//			case "s":
//				//ImaSnake snake = new ImaSnake();
//				LightsOut lightsOut = new LightsOut();
//				lightsOut.startGame();
//				break;
//				
//				default:
//			}
//		}
	}

}
