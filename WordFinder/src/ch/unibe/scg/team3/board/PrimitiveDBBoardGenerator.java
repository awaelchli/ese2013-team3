package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.HashMap;
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

public class PrimitiveDBBoardGenerator extends AbstractBoardGenerator {
	
	private int size;
	private WordlistHandler list;
	private int minWords;
	private Random r;
	char[][] matrix;

	public PrimitiveDBBoardGenerator(int size, WordlistHandler wordList, int minWords) {
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
		Point start= new Point(0,0);
		placeWord(firstWord, start);
		
		int n=0;
		int placedCount = 1;
		boolean shortWords=false;
		
		//String[] strings=firstWord.split(".*");
		
		//System.out.println(strings);
		while(placedCount < minWords){
			
			for(int i=0;i<size;i++){
				for(int j=0; j<size;j++){
					if(!(matrix[i][j]=='\u0000')){
						
						String letter = "" + matrix[i][j];
						String random = list.getRandomWordFromDatabaseByLetterAndLength(letter, shortWords);
						n++;
					
					
					
						if(placeWord(random, new Point(i,j))){
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
	private boolean placeWord(String word, Point start) {
		
		char letters[]=word.toCharArray();
		LinkedList<Point> pCoord=new LinkedList<Point>();
		pCoord.add(start);
		boolean failed=false;
		
		//place word on an existing letter if possible
		for(int i=1; (i < letters.length) && (!failed) ;i++){
			boolean letterPlaced=placeLetter(letters[i], pCoord);
			failed=!letterPlaced;
		}
		

		
		if(!failed){
			
			for(int i=0; i<letters.length;i++){
				matrix[pCoord.get(i).getX()][pCoord.get(i).getY()]=letters[i];
			}
			
			System.out.println("success: " + word);
			
			
		}else{
			//System.out.println("fail: " + word);
		}
		
		return !failed;
	}

	private boolean placeLetter(char nextLetter, LinkedList<Point> pCoord) {
		
		Point coord=pCoord.getLast();
		ArrayList<Point> adjacent=getAdjacent(coord);
		ArrayList<Point> empty= new ArrayList<Point>();
		boolean placed=false;
		
		while(adjacent.size()>0 && !placed){
			int rand=r.nextInt(adjacent.size());
			Point testNext= adjacent.remove(rand);
			
			
			
			if((matrix[testNext.getX()][testNext.getY()]==nextLetter) && !(pCoord.contains(testNext))){
				pCoord.add(testNext);
				placed = true;
			}
			if((matrix[testNext.getX()][testNext.getY()]=='\u0000') && !(pCoord.contains(testNext))){
				empty.add(testNext);
			}
		}
		
		while(!placed && empty.size()>0){
			int rand=r.nextInt(empty.size());
			Point testNext= empty.remove(rand);
			pCoord.add(testNext);
			placed=true;
			
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
