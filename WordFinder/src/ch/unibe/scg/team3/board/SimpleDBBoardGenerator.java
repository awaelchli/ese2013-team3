package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.NullToken;
import ch.unibe.scg.team3.token.Token;

/**
 * @author viviane
 */

public class SimpleDBBoardGenerator extends AbstractBoardGenerator {
	
	private int size;
	private WordlistHandler list;
	private int minWords;
	private Random r;
	private AbstractLetterMeter meter;

	public SimpleDBBoardGenerator(int size, WordlistHandler wordList, int minWords) {
		super(size);
		this.size=size;
		this.list=wordList;
		this.minWords=minWords;
		this.r = new Random();
		this.meter=new DefaultLetterMeter();
	}

	@Override
	public void generate() {
		
		
		
		int k=0;
		int n=0;
		
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
		
		StringLengthComparator comp=new StringLengthComparator();
		ArrayList<String> words= new ArrayList<String>();
		
		for(int m=0; m<3*minWords;m++){
			String word=list.getRandomWordFromWordlist();
			if(word.length()<8){
				words.add(word);
			}
			
		}
		
		
		Collections.sort(words, comp);
		
		for(int m=0; m<words.size();m++){
			String word=words.get(m);
			if(placeWord(word)){
				k++;
			}
		}
		
		while(k<minWords){
			
			String word=list.getRandomWordFromWordlist();
			
			n++;
			
			if(placeWord(word)){
				k++;
			}
			
		}
		
		
		System.out.println(n);
		
		
		// fill the rest with random letters

		for (int i = 0; i < this.board.getSize(); i++) {
		
			for (int j = 0; j < this.board.getSize(); j++) {

				if (board.getToken(i, j).getLetter() == NullToken.getInstance().getLetter()) {
							
					char letter = (char) (r.nextInt(26) + 'a');
					Token tok = new Token(letter, meter.getValue(letter));
					this.board.setToken(tok, i, j);
				}
			}
		}
	}

	//will place the words in the board, using existing letters if possible
	private boolean placeWord(String word) {
		
		char letters[]=word.toCharArray();
		int k=0;
		Path<IToken> p=new Path<IToken>();
		LinkedList<Point> pCoord=new LinkedList<Point>();
		boolean setWord=false;
		
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(this.board.getToken(i, j).getLetter()==letters[k]){
					
					setWord = placeLetterAndContinue(letters, k, p, pCoord, new Point(i,j));
					
					
				}
			}
		}
		
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(this.board.getToken(i, j).getLetter()==NullToken.getInstance().getLetter()){
					
					setWord = placeLetterAndContinue(letters, k, p, pCoord, new Point(i,j));

				}
			}
		}
		
		if(setWord){
			Iterator<IToken> itr=p.iterator();
			
			while(itr.hasNext()){
				IToken t=itr.next();
				board.setToken(t, t.getCoordinates().getX(), t.getCoordinates().getY());
				
			}
			
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
			if((boardToken.getLetter() == NullToken.getInstance().getLetter()) && !(pCoord.contains(testNext))){
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

	private boolean placeLetterAndContinue(char[] letters, int index, Path<IToken> p, LinkedList<Point> pCoord, Point nextPoint) {
		
		char nextLetter=letters[index];
		boolean placed=false;
		
		Token nextTok=new Token(nextLetter, meter.getValue(nextLetter));
		nextTok.setCoordinates(nextPoint);
		pCoord.add(nextPoint);
		boolean added=p.add(nextTok);
		
		if(added==false){
			p.add(nextTok);
		}
		
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
