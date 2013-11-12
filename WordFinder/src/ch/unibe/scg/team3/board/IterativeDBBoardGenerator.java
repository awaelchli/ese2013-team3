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

public class IterativeDBBoardGenerator extends AbstractBoardGenerator {
	
	private int size;
	private WordlistHandler list;
	private int minWords;
	private Random r;

	public IterativeDBBoardGenerator(int size, WordlistHandler wordList, int minWords) {
		super(size);
		this.size=size;
		this.list=wordList;
		this.minWords=minWords;
		this.r = new Random();
		//this.meter=new DefaultLetterMeter();
	}

	@Override
	public void generate() {
		
			
		String firstWord = list.getRandomWordFromWordlist();
		Token start= new Token(firstWord.charAt(0), meter.getValue(firstWord.charAt(0)), board.getToken(0, 0).getCoordinates());
		placeWord(firstWord, start);
		
		int n=0;
		int placedCount = 0;
		while(placedCount < minWords){
			
			for(IToken tok : board){
				if(!tok.isEmpty()){
					
					String letter = "" + tok.getLetter();
					String random = list.getRandomWordFromWordlistByLetter(letter);
					n++;
					
					
					
					if(placeWord(random, tok)){
						
						placedCount++;
					}
				}
			}
		}
		
		System.out.println(n);
		
		// fill the rest with random letters
		for(IToken tok : board){
			if(tok.isEmpty()){
				String alphabet = MySQLiteHelper.ALPHABET;
				Random rnd = new Random();
				int rndIndex = rnd.nextInt(alphabet.length());
				char letter = alphabet.charAt(rndIndex);
				
				board.setToken(new Token(letter, meter.getValue(letter), tok.getCoordinates()));
			}
		}
	}


	//will place the words in the board, using existing letters if possible
	private boolean placeWord(String word, IToken startingPoint) {
		
		char letters[]=word.toCharArray();
		Path<IToken> p=new Path<IToken>();
		p.add(startingPoint);
		LinkedList<Point> pCoord=new LinkedList<Point>();
		pCoord.add(startingPoint.getCoordinates());
		boolean failed=false;
		
		//place word on an existing letter if possible
		for(int i=1; (i < letters.length) && (!failed) ;i++){
			boolean letterPlaced=placeLetter(letters[i], p, pCoord);
			failed=!letterPlaced;
		}
		

		
		if(!failed){
			
			board.setPath(p);
			
			System.out.println("success: " + word);
			
			
		}else{
			System.out.println("fail: " + word);
		}
		
		return !failed;
	}

	private boolean placeLetter(char nextLetter, Path<IToken> p, LinkedList<Point> pCoord) {
		
		Point coord=p.getLast().getCoordinates();
		ArrayList<Point> adjacent=getAdjacent(coord);
		ArrayList<Point> empty= new ArrayList<Point>();
		boolean placed=false;
		
		while(adjacent.size()>0 && !placed){
			int rand=r.nextInt(adjacent.size());
			Point testNext= adjacent.remove(rand);
			
			
			IToken boardToken=this.board.getToken(testNext.getX(), testNext.getY());
			
			if((boardToken.getLetter()==nextLetter) && !(p.contains(boardToken))){
				pCoord.add(boardToken.getCoordinates());
				p.add(boardToken);
				placed = true;
			}
			if(boardToken.isEmpty()&& !(pCoord.contains(testNext))){
				empty.add(testNext);
			}
		}
		
		while(!placed && empty.size()>0){
			int rand=r.nextInt(empty.size());
			Point testNext= empty.remove(rand);
			
			AbstractToken nextTok=new Token(nextLetter, meter.getValue(nextLetter), testNext);
			pCoord.add(testNext);
			p.add(nextTok);
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
