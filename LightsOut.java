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
	}
	
	public void startGame() {
		cursorRow = 0;
		cursorCol = 0;
		
		Random r = new Random();
		
		//Initiate all LED's to either green or red
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				gridColor[i][j] = r.nextInt(2)+1;
				//sendMessage(i,j, gridColor[i][j]);
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
		
		//check if game is won, turn last 3 columns green
		if(checkWin()) {
			for(int i = WIDTH+2; i > (WIDTH-1); i--) {
				for(int j = 0; j < HEIGHT; j++) {
					sendMessage(j,i,2);
				}
			}
		}
	}
	
	private void toggle(int row, int col) {
		gridColor[row][col] = (gridColor[row][col]%2)+1;
		sendMessage(row, col, gridColor[row][col]);
	}
	
	private void sendMessage(int r, int c, int l) {
		if(ard != null) {
			ard.sendString("$" + ((char) ('0'+r)) + ((char) ('0'+c)) + ((char) ('0'+l)));
		}
		else {
			System.out.print("$");
			System.out.print((char) ('0' + r));
			System.out.print((char) ('0' + c));
			System.out.print((char) ('0' + l));
			System.out.println("!");
		}
		
		return;
	}
	
	public void moveCursor(int dir) {
		switch(dir) {
			//left
			case 0:
				if(cursorCol != 0)
					cursorCol--;
				break;
			//up
			case 1:
				if(cursorRow != (HEIGHT-1))
					cursorRow++;
				break;
			//right
			case 2:
				if(cursorCol != (WIDTH-1))
					cursorCol++;
				break;
			//down
			case 3:
				if(cursorRow != 0)
					cursorRow--;
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
