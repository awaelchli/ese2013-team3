package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.NullToken;
import ch.unibe.scg.team3.token.Token;
import ch.unibe.scg.team3.wordlist.Wordlist;

/**
 * 
 * @author viviane
 * 
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
		
		while(k<minWords){
			
			String word=list.getRandomWordFromWordlist();
			
			if(placeWord(word)){
				k++;
			}
			
		}
		
		// fill the rest with random letters

		

		for (int i = 0; i < this.board.getSize(); i++) {
		
			for (int j = 0; j < this.board.getSize(); j++) {

				if (board.getToken(i, j).getLetter() == NullToken.getInstance().getLetter()) {
							
					char letter = (char) (r.nextInt(26) + 'A');
					Token tok = new Token(letter, 1);
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
		boolean setWord=false;
		
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(this.board.getToken(i, j).getLetter()==letters[k]){
					Token t=new Token(letters[k],meter.getValue(letters[k]));
					t.setCoordinates(new Point(i,j));
					p.add(t);
					setWord=addNextLetters(letters, k+1, p);
					if(!setWord){
						p.removeLast();
					}
				}
			}
		}
		
		for(int i=0; (i < size) && (!setWord) ;i++){
			for(int j=0; (j<size) && (!setWord); j++){
				if(this.board.getToken(i, j).getLetter()==NullToken.getInstance().getLetter()){
					Token t=new Token(letters[k],meter.getValue(letters[k]));
					t.setCoordinates(new Point(i,j));
					p.add(t);
					setWord=addNextLetters(letters, k+1, p);
					if(!setWord){
						p.removeLast();
					}
				}
			}
		}
		
		if(setWord){
			Iterator<IToken> itr=p.iterator();
			
			while(itr.hasNext()){
				IToken t=itr.next();
				board.setToken(t, t.getCoordinates().getX(), t.getCoordinates().getY());
				
			}
			
		}
		
		return setWord;
	}

	private boolean addNextLetters(char[] letters, int index, Path<IToken> p) {
		
		Point coord=p.getLast().getCoordinates();
		char nextLetter=letters[index];
		ArrayList<Point> adjacent=getAdjacent(coord);
		ArrayList<Point> empty= new ArrayList<Point>();
		boolean placed=false;
		
		while(adjacent.size()>0 && !placed){
			int rand=r.nextInt(adjacent.size());
			Point testNext= adjacent.remove(rand);
			IToken boardToken=this.board.getToken(testNext.getX(), testNext.getY());
			
			if(boardToken.getLetter()==nextLetter){
				p.add(boardToken);
				if(index+1==letters.length){
					placed=true;
				}else{
					placed=addNextLetters(letters, index+1, p);
					if(!placed){
						p.removeLast();
					}
				}
			}
			if(boardToken.getLetter() == NullToken.getInstance().getLetter()){
				empty.add(testNext);
			}
		}
		
		while(!placed && empty.size()>0){
			int rand=r.nextInt(empty.size());
			Point testNext= empty.remove(rand);
			IToken boardToken=this.board.getToken(testNext.getX(), testNext.getY());
			
			Token nextTok=new Token(nextLetter, meter.getValue(nextLetter));
			nextTok.setCoordinates(coord);
			
			p.add(nextTok);
			if(index+1==letters.length){
				placed=true;
			}else{
				placed=addNextLetters(letters, index+1, p);
				if(!placed){
					p.removeLast();
				}
			}
			
		}
		
		return placed;
	}

	private ArrayList<Point> getAdjacent(Point coord){
		
		ArrayList<Point> adjacent=new ArrayList<Point>();
		
		for(int i=-1; i<=1; i++){
			for(int j=-1 ; j <= 1; j++){
				if(!(i==0 && j==0)){
					if((coord.getX()+i>=0) && (coord.getX()+i<6) && (coord.getY()+j >=0) && (coord.getY()+j < 6 ))
						adjacent.add(new Point(coord.getX()+i, coord.getY()+j));	
				}
			}
		}
		
		return adjacent;
		
	}


}
