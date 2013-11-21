package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.Token;

/** 
 *
 * Board generator using an iterative approach to to generate a board.
 * First a 2D char Array is filled, then a Board is generated based on the matrix. 
 * 
 * @author viviane
 */

public class PrimitiveDBBoardGenerator extends AbstractBoardGenerator {
	
	private int size;
	private WordlistHandler list;
	private int minWords;
	private Random r;
	char[][] boardMatrix;

	public PrimitiveDBBoardGenerator(int size, WordlistHandler wordList, int minWords) {
		super(size);
		this.size=size;
		this.list=wordList;
		this.minWords=minWords;
		this.r = new Random();
	}

	/**
	 * generates a board using an iterative algorithm
	 */
	@Override
	public void generate() {
		
		boardMatrix = new char[size][size];
		
		String firstWord = list.getRandomWordFromWordlist();
		Point start= new Point(0,0);
		placeWord(firstWord, start);
		
		int placedCount = 1;
		boolean shortWords=false;
		
		while(placedCount < minWords){
			
			for(int i=0;i<size;i++){
				for(int j=0; j<size;j++){
					if(!(boardMatrix[i][j]=='\u0000')){ // '\u0000' is the default value for char
						
						//get a random word starting with a letter which is already on the board
						String letter = "" + boardMatrix[i][j];
						String random = list.getRandomWordFromDatabaseByLetterAndLength(letter, shortWords);
						
						// if a word is placed augment the count
						if(placeWord(random, new Point(i,j))){
							placedCount++;
							System.out.println(random);
							
							if(placedCount==3){ //we only place 3 longer words on the board
								shortWords=true;
							}
						}
					}
				}	
			}
		}
		
		
		// fill the rest with random letters
		for(int i=0;i<size;i++){
			for(int j=0; j<size;j++){
				if((boardMatrix[i][j]=='\u0000')){
					String alphabet = MySQLiteHelper.ALPHABET;
					Random rnd = new Random();
					int rndIndex = rnd.nextInt(alphabet.length());
					char letter = alphabet.charAt(rndIndex);
				
					boardMatrix[i][j]=letter;
				}
			}
		}
		
		
		//fill the board with the values of the matrix
		for(int i=0;i<size;i++){
			for(int j=0; j<size;j++){
				
				board.setToken(new Token(boardMatrix[i][j], meter.getValue(boardMatrix[i][j]), new Point(i,j)));
				
			}
		}
	}


	/**
	 * Tries to place a word into the baordMatrix
	 * 
	 * @param word
	 *            word to be placed
	 * 
	 * @param start
	 *            Starting point of the word
	 */
	private boolean placeWord(String word, Point start) {
		
		char letters[]=word.toCharArray();
		LinkedList<Point> pCoord=new LinkedList<Point>();
		pCoord.add(start);
		boolean failed=false;
		
		//place word on an existing letter if possible, if not look for empty spot
		for(int i=1; (i < letters.length) && (!failed) ;i++){
			boolean letterPlaced=placeLetter(letters[i], pCoord);
			failed=!letterPlaced;
		}
		

		
		//put the word in the matrix, if a valid path has been found
		if(!failed){
			for(int i=0; i<letters.length;i++){
				boardMatrix[pCoord.get(i).getX()][pCoord.get(i).getY()]=letters[i];
			}
		}
		
		return !failed;
	}

	
	/**
	 * Tries to place a letter adjacent to the last point of pCoord
	 * 
	 * @param nextLetter
	 *            letter to be placed
	 * 
	 * @param pCoord
	 *            List of the Points already found for the word
	 */
	private boolean placeLetter(char nextLetter, LinkedList<Point> pCoord) {
		
		Point coord=pCoord.getLast();
		ArrayList<Point> adjacent=getAdjacent(coord);
		ArrayList<Point> empty= new ArrayList<Point>();
		boolean placed=false;
		
		
		while(adjacent.size()>0 && !placed){
			int rand=r.nextInt(adjacent.size());
			Point testNext= adjacent.remove(rand);
			
			//check if the adjacent Point already contains the letter
			if((boardMatrix[testNext.getX()][testNext.getY()]==nextLetter) && !(pCoord.contains(testNext))){
				pCoord.add(testNext);
				placed = true;
			}
			//if the adjacent point is empty keep it for later
			if((boardMatrix[testNext.getX()][testNext.getY()]=='\u0000') && !(pCoord.contains(testNext))){
				empty.add(testNext);
			}
		}
		
		//if the letter wasn't already in the adjacent points try putting it on an empty one
		if(!placed && empty.size()>0){
			int rand=r.nextInt(empty.size());
			Point testNext= empty.remove(rand);
			pCoord.add(testNext);
			placed=true;
			
		}
		
		return placed;
	}

	/**
	 * returns Points adjacent to the parameter (only valid coordinates)
	 * 
	 * @param coord
	 *            points from which adjacent points should be found
	 */
	private ArrayList<Point> getAdjacent(Point coord){
		
		ArrayList<Point> adjacent=new ArrayList<Point>();
		
		for(int i=-1; i<=1; i++){
			for(int j=-1 ; j <= 1; j++){
				if(!(i==0 && j==0)){
					
					//test if we stay on the board
					if((coord.getX()+i>=0) && (coord.getX()+i<size) && (coord.getY()+j >=0) && (coord.getY()+j < size ))
						adjacent.add(new Point(coord.getX()+i, coord.getY()+j));	
				}
			}
		}
		
		return adjacent;	
	}


}
