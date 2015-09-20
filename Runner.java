package hackISU;

import java.util.Scanner;

import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

public class Runner {
	
	public static int game = 0;
	public static boolean gameSelected = false;
	public static ArduinoDriver ard = new ArduinoDriver();
	
	
    public static void changeGame(int i)
    {
    	System.out.print("Game:" + game);
    	if (i == 0)
    	{
    		if (game == 0)
    			game = 2;
    		else
    			game = game -1;
    	}
    	else
    	{
    		if(game == 2)
    			game = 0;
    		else
    			game = game +1;
    	}
    }
    
	public static void selectGame()
	{
		gameSelected = true;
	}
	
	
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
	    
	    xc.addXboxControllerListener(new XboxControllerAdapter()
	    {
			
		      public void dpad(int d, boolean pressed)
		      {

		    	  if (d == 0)
		    	  {
		    		 if (pressed)
		    		 changeGame(1);
		    	    // 
		    	  }
		    	  else if (d == 4)
		    	  {
		    		if (pressed)
		    	    changeGame(0);
		    	  }
		      }
		      
		      public void buttonA(boolean presses)
		      {
		    	  selectGame();
		      }
			
	    });
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
		while(gameSelected == false)
		{
			
		}
		
		if (game == 0)
		{
		LightsOut lightsOut = new LightsOut(xc, ard);
		lightsOut.startGame();
		System.out.println("Lights out");
		}
		else if (game == 1)
		{
			
			
		}
		else
		{
			
			
		}
//		
//		
		
		
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
