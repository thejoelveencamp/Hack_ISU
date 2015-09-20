package hackISU;

import java.util.Random;

public class LightsOut {
	
	private static final int WIDTH = 9;
	private static final int HEIGHT = 9;
	private ArduinoDriver ard;
	
	private int cursorRow;
	private int cursorCol;
	private int[][] gridColor = new int[WIDTH][HEIGHT];

	public LightsOut(ArduinoDriver arduino) {
		ard = arduino;
		ard.sendString("W");
	}
	
	public void startGame() {
		cursorRow = 0;
		cursorCol = 0;
		
		Random r = new Random();
		
		//Initiate all LED's to either green or red
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				gridColor[i][j] = r.nextInt(2)+1;
				sendMessage(i,j, gridColor[i][j]);
			}
		}
	}
	
	public void onClick(int cursorRow, int cursorCol) {
		//toggle cursor
		toggle(cursorRow, cursorCol);
		
		//corner cases
		if((cursorRow == 0) && (cursorCol == 0)) {
			toggle(1, 0);
			toggle(1, 1);
			toggle(0, 1);
		}
		else if((cursorRow == (HEIGHT - 1)) && (cursorCol == 0)) {
			toggle(HEIGHT-2, 0);
			toggle(HEIGHT-2, 1);
			toggle(HEIGHT-1, 1);
		}
		else if((cursorRow == 0) && (cursorCol == (WIDTH - 1))) {
			toggle(0, WIDTH-2);
			toggle(1, WIDTH-2);
			toggle(1, WIDTH-1);
		}
		else if((cursorRow == (HEIGHT - 1)) && (cursorCol == (WIDTH - 1))) {
			toggle(HEIGHT-1, WIDTH-2);
			toggle(HEIGHT-2, WIDTH-2);
			toggle(HEIGHT-2, WIDTH-1);
		}
		
		//edge cases
		else if(cursorRow == 0) {
			toggle(0, cursorCol-1);
			toggle(1, cursorCol-1);
			toggle(1, cursorCol);
			toggle(1, cursorCol+1);
			toggle(0, cursorCol+1);
		}
		else if(cursorRow == (HEIGHT - 1)) {
			toggle(HEIGHT-1, cursorCol-1);
			toggle(HEIGHT-2, cursorCol-1);
			toggle(HEIGHT-2, cursorCol);
			toggle(HEIGHT-2, cursorCol+1);
			toggle(HEIGHT-1, cursorCol+1);
		}
		else if(cursorCol == 0) {
			toggle(cursorRow-1, 0);
			toggle(cursorRow-1, 1);
			toggle(cursorRow, 1);
			toggle(cursorRow+1, 1);
			toggle(cursorRow+1, 0);
		}
		else if(cursorCol == (WIDTH - 1)) {
			toggle(cursorRow-1, WIDTH-1);
			toggle(cursorRow-1, WIDTH-2);
			toggle(cursorRow, WIDTH-2);
			toggle(cursorRow+1, WIDTH-2);
			toggle(cursorRow+1, WIDTH-1);
		}
		
		//middle case
		else {
			toggle(cursorRow-1, cursorCol-1);
			toggle(cursorRow, cursorCol-1);
			toggle(cursorRow+1, cursorCol-1);
			toggle(cursorRow+1, cursorCol);
			toggle(cursorRow+1, cursorCol+1);
			toggle(cursorRow, cursorCol+1);
			toggle(cursorRow-1, cursorCol+1);
			toggle(cursorRow-1, cursorCol);
		}
		
		
//		if((cursorRow+1) != HEIGHT) {
//			toggle(cursorRow+1, cursorCol);
//			if((cursorCol-1) >= 0)
//				toggle(cursorRow+1, cursorCol-1);
//			if((cursorCol+1) != WIDTH)
//				toggle(cursorRow+1, cursorCol+1);
//		}
//		if((cursorRow-1) >= 0) {
//			toggle(cursorRow-1, cursorCol);
//			if((cursorCol-1) >= 0)
//				toggle(cursorRow-1, cursorCol-1);
//			if((cursorCol+1) != WIDTH)
//				toggle(cursorRow-1, cursorCol+1);
//		}
//		if((cursorCol-1) >= 0) {
//			toggle(cursorRow, cursorCol-1);
//		}
//		if((cursorCol+1) != WIDTH) {
//			toggle(cursorRow, cursorCol+1);
//		}
		
		//check if game is won
		if(checkWin()) {
			//TODO: do something cool for win condition
		}
	}
	
	private void toggle(int row, int col) {
		gridColor[row][col] = (gridColor[row][col]%2)+1;
		sendMessage(row, col, gridColor[row][col]);
	}
	
	private void sendMessage(int r, int c, int l) {
		char r1 = (char) ('0' + r/10);
		char r2 = (char) ('0' + r%10);
		char c1 = (char) ('0' + c/10);
		char c2 = (char) ('0' + c%10);
		char color = (char) ('0' + l);
		
		if(ard != null) {
			ard.sendString("$" + r1 + r2 + c1 + c2 + color + "!");
			System.out.println("$" + r1 + r2 + c1 + c2 + color + "!");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.print("$");
			System.out.print(r1);
			System.out.print(r2);
			System.out.print(c1);
			System.out.print(c2);
			System.out.print(color);
			System.out.println("!");
		}
		
		return;
	}
	
	public void moveCursor(char dir) {
		switch(dir) {
			//left
			case 'a':
				if(cursorCol != 0) {
					ard.sendString("a");
					cursorCol--;
				}
				break;
			//up
			case 'w':
				if(cursorRow != (HEIGHT-1)) {
					ard.sendString("w");
					cursorRow++;
				}
				break;
			//right
			case 'd':
				if(cursorCol != (WIDTH-1)) {
					ard.sendString("d");
					cursorCol++;
				}
				break;
			//down
			case 's':
				if(cursorRow != 0) {
					ard.sendString("s");
					cursorRow--;
				}
				break;
			default:
		}
	}
	
	private boolean checkWin() {
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(gridColor[i][j] != 2)
					return false;
			}
		}
		return true;
	}
}
