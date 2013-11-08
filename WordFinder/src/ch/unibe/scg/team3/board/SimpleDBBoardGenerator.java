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

public class SimpleDBBoardGenerator extends AbstractBoardGenerator {
	
	private int size;
	private WordlistHandler list;
	private int minWords;
	private Random r;

	public SimpleDBBoardGenerator(int size, WordlistHandler wordList, int minWords) {
		super(size);
		this.size=size;
		this.list=wordList;
		this.minWords=minWords;
		this.r = new Random();
		//this.meter=new DefaultLetterMeter();
	}

	@Override
	public void generate() {
		
		
//		
//		int k=0;
//		int n=0;
//		
////		while(k<minWords){
////			
////			String word=list.getRandomWordFromWordlist();
////			
////			n++;
////			
////			if(placeWord(word)){
////				k++;
////			}
////			
////		}
//		
//		StringLengthComparator comp=new StringLengthComparator();
//		ArrayList<String> words= new ArrayList<String>();
//		ArrayList<Character> lettersOnBoard= new ArrayList<Character>();
//		
//		for(int m=0; m<9*minWords;m++){
//			String word=list.getRandomWordFromWordlist();
//			if(word.length()<8){
//				words.add(word);
//			}
//			
//		}
//		
//		
//		Collections.sort(words, comp);
//		
//		for(int m=0; m<words.size();m++){
//			String word=words.get(m);
//			if(placeWord(word)){
//				k++;
//				
//			}
//		}
//		
//		while(k<minWords){
//			
//			String word=list.getRandomWordFromWordlist();
//			
//			n++;
//			
//			if(placeWord(word)){
//				k++;
//			}
//			
//		}
//		
//		
//		System.out.println(n);
//		
		String firstWord = list.getRandomWordFromWordlist();
		placeWord(firstWord);
		
		int n=0;
		int placedCount = 0;
		while(placedCount < minWords){
			
			for(IToken tok : board){
				if(!tok.isEmpty()){
					
					String letter = "" + tok.getLetter();
					String random = list.getRandomWordFromWordlistByLetter(letter);
					n++;
					
					char letters[]=random.toCharArray();
					Path<IToken> p=new Path<IToken>();
					LinkedList<Point> pCoord=new LinkedList<Point>();
					
					if(placeLetterAndContinue(letters, 0, p, pCoord, tok.getCoordinates())){
						
						System.out.println(random);
						board.setPath(p);
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

//		for (int i = 0; i < this.board.getSize(); i++) {
//		
//			for (int j = 0; j < this.board.getSize(); j++) {
//
//				if (board.getToken(i, j).isEmpty()) {
//							
//					char letter = (char) (r.nextInt(26) + 'a');
//					AbstractToken tok = new Token(letter, meter.getValue(letter), i, j);
//					this.board.setToken(tok);
//				}
//			}
//		}
	}

	//will place the words in the board, using existing letters if possible
	private boolean placeWord(String word) {
		
		char letters[]=word.toCharArray();
		int k=0;
		Path<IToken> p=new Path<IToken>();
		LinkedList<Point> pCoord=new LinkedList<Point>();
		boolean setWord=false;
		
		//place word on an existing letter if possible
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(this.board.getToken(i, j).getLetter()==letters[k]){
					
					setWord = placeLetterAndContinue(letters, k, p, pCoord, new Point(i,j));	
					
				}
			}
		}
		
		//if not possible on existing letter, search for empty spot
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(this.board.getToken(i, j).isEmpty()){
					
					setWord = placeLetterAndContinue(letters, k, p, pCoord, new Point(i,j));
					
				}
			}
		}
		
		if(setWord){
			
			board.setPath(p);
			
			System.out.println(word);
			
			
		}
		
		return setWord;
	}

	private boolean addNextLetters(char[] letters, int index, Path<IToken> p, LinkedList<Point> pCoord) {
		
		Point coord=p.getLast().getCoordinates();
		char nextLetter=letters[index];
		ArrayList<Point> adjacent=getAdjacent(coord);
		ArrayList<Point> empty= new ArrayList<Point>();
		boolean placed=false;
		
		while(adjacent.size()>0 && !placed){
			int rand=r.nextInt(adjacent.size());
			Point testNext= adjacent.remove(rand);
			IToken boardToken=this.board.getToken(testNext.getX(), testNext.getY());
			
			if((boardToken.getLetter()==nextLetter) && !(p.contains(boardToken))){
				placed = placeLetterAndContinue(letters, index, p, pCoord, testNext);
			}
			if(boardToken.isEmpty()&& !(pCoord.contains(testNext))){
				empty.add(testNext);
			}
		}
		
		while(!placed && empty.size()>0){
			int rand=r.nextInt(empty.size());
			Point testNext= empty.remove(rand);
			
			placed = placeLetterAndContinue(letters, index, p, pCoord, testNext);
			
		}
		
		return placed;
	}

	// places the token on the board and recursively invoke addNextLetters
	private boolean placeLetterAndContinue(char[] letters, int index, Path<IToken> p, LinkedList<Point> pCoord, Point nextPoint) {
		
		char nextLetter=letters[index];
		boolean placed=false;
		
		AbstractToken nextTok=new Token(nextLetter, meter.getValue(nextLetter), nextPoint);
		pCoord.add(nextPoint);
		p.add(nextTok);
		
		
		if(index+1==letters.length){
			placed=true;
		}else{
			placed=addNextLetters(letters, index+1, p, pCoord);
			if(!placed){
				p.removeLast();
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
