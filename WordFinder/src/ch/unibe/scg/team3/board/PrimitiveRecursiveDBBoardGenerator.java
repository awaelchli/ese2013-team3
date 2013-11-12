package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.AbstractToken;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;

/**
 * @author viviane
 */

public class PrimitiveRecursiveDBBoardGenerator extends AbstractBoardGenerator {
	
	private int size;
	private WordlistHandler list;
	private int minWords;
	private Random r;
	char[][] matrix;

	public PrimitiveRecursiveDBBoardGenerator(int size, WordlistHandler wordList, int minWords) {
		super(size);
		this.size=size;
		this.list=wordList;
		this.minWords=minWords;
		this.r = new Random();
		//this.meter=new DefaultLetterMeter();
	}

	@Override
	public void generate() {
		
		matrix = new char[size][size];
		
		String firstWord = list.getRandomWordFromWordlist();
		placeWord(firstWord);
		
		int n=0;
		int placedCount = 1;
		boolean shortWords=false;
		
		while(placedCount < minWords){
			
			
			for(int i=0;i<size;i++){
				for(int j=0; j<size;j++){
					if(!(matrix[i][j]=='\u0000')){
						
						String letter = ""+matrix[i][j];
						String random = list.getRandomWordFromDatabaseByLetterAndLength(letter, shortWords);
						n++;
						
						char letters[]=random.toCharArray();
						LinkedList<Point> pCoord=new LinkedList<Point>();
						
						if(placeLetterAndContinue(letters, 0, pCoord, new Point(i,j))){
							
							System.out.println(random);
							for(int k=0; k<letters.length;k++){
								matrix[pCoord.get(k).getX()][pCoord.get(k).getY()]=letters[k];
							}
							placedCount++;
							if(placedCount==3){
								shortWords=true;
							}
						}
					}
				}
			}
		}
		
		System.out.println(n);
		
		// fill the rest with random letters
		for(int i=0;i<size;i++){
			for(int j=0; j<size;j++){
				if((matrix[i][j]=='\u0000')){
					String alphabet = MySQLiteHelper.ALPHABET;
					Random rnd = new Random();
					int rndIndex = rnd.nextInt(alphabet.length());
					char letter = alphabet.charAt(rndIndex);
						
					matrix[i][j]=letter;
				}
			}
		}
				
		for(int i=0;i<size;i++){
			for(int j=0; j<size;j++){
						
				board.setToken(new Token(matrix[i][j], meter.getValue(matrix[i][j]), new Point(i,j)));
				
			}
		}

	}

	//will place the words in the board, using existing letters if possible
	private boolean placeWord(String word) {
		
		char letters[]=word.toCharArray();
		int n=0;
		LinkedList<Point> pCoord=new LinkedList<Point>();
		boolean setWord=false;
		
		//place word on an existing letter if possible
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(matrix[i][j]==letters[n]){
					
					setWord = placeLetterAndContinue(letters, n, pCoord, new Point(i,j));	
					
				}
			}
		}
		
		//if not possible on existing letter, search for empty spot
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if((matrix[i][j]=='\u0000')){
					
					setWord = placeLetterAndContinue(letters, n, pCoord, new Point(i,j));
					
				}
			}
		}
		
		if(setWord){
			
			for(int k=0; k<letters.length;k++){
				matrix[pCoord.get(k).getX()][pCoord.get(k).getY()]=letters[k];
			}
			
			System.out.println(word);
			
			
		}
		
		return setWord;
	}

	private boolean addNextLetters(char[] letters, int index, LinkedList<Point> pCoord) {
		
		Point coord=pCoord.getLast();
		char nextLetter=letters[index];
		ArrayList<Point> adjacent=getAdjacent(coord);
		ArrayList<Point> empty= new ArrayList<Point>();
		boolean placed=false;
		
		while(adjacent.size()>0 && !placed){
			int rand=r.nextInt(adjacent.size());
			Point testNext= adjacent.remove(rand);
			
			if((matrix[testNext.getX()][testNext.getY()]==nextLetter) && !(pCoord.contains(testNext))){
				placed = placeLetterAndContinue(letters, index, pCoord, testNext);
			}
			if((matrix[testNext.getX()][testNext.getY()]=='\u0000') && !(pCoord.contains(testNext))){
				empty.add(testNext);
			}
		}
		
		while(!placed && empty.size()>0){
			int rand=r.nextInt(empty.size());
			Point testNext= empty.remove(rand);
			
			placed = placeLetterAndContinue(letters, index, pCoord, testNext);
			
		}
		
		return placed;
	}

	// places the token on the board and recursively invoke addNextLetters
	private boolean placeLetterAndContinue(char[] letters, int index, LinkedList<Point> pCoord, Point nextPoint) {
		
		boolean placed=false;
		
		pCoord.add(nextPoint);
		
		
		if(index+1==letters.length){
			placed=true;
		}else{
			placed=addNextLetters(letters, index+1, pCoord);
			if(!placed){
				pCoord.removeLast();
			}
		}
		return placed;
	}

	//returns Points adjacent to the parameter (only valid coordinates)
	private ArrayList<Point> getAdjacent(Point coord){
		
		ArrayList<Point> adjacent=new ArrayList<Point>();
		
		for(int i=-1; i<=1; i++){
			for(int j=-1 ; j <= 1; j++){
				if(!(i==0 && j==0)){
					if((coord.getX()+i>=0) && (coord.getX()+i<size) && (coord.getY()+j >=0) && (coord.getY()+j < size ))
						adjacent.add(new Point(coord.getX()+i, coord.getY()+j));	
				}
			}
		}
		
		return adjacent;
		
	}


}
