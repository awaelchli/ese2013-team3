package ch.unibe.scg.team3.board;

import java.util.Random;

/**
 * 
 * @author viviane
 *
 */
public class BoardGenerator {
	
	
	
	public char[][] getBoard(int size){
		
		char[][] board=new char[size][size];
		
		Random r = new Random();
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size;j++){
				
				board[i][j]=(char)(r.nextInt(26) + 'a');
				
			}
			
		}
		
		return board;
		
	}
	
}
