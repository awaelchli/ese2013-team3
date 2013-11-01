package ch.unibe.scg.team3.board;

import java.util.Random;

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
	private Wordlist list;
	private int listSize;
	private int minWords;
	private Random r;

	public SimpleDBBoardGenerator(int size, Wordlist list, int minWords) {
		super(size);
		this.size=size;
		this.list=list;
		this.listSize=list.getSize();
		this.minWords=minWords;
		this.r = new Random();
	}

	@Override
	public void generate() {
		
		
		int k=0;
		
		while(k<minWords){
			
			String word=list.getWord(r.nextInt(listSize));
			
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

	//will place the words in the board, searching randomly for possibilities
	private boolean placeWord(String word) {
		
		char letters[]=word.toCharArray();
		int k=0;
		
		for(int i=0; i < size;i++){
			for(int j=0; j<size; j++){
				if(this.board.getToken(i, j).getLetter()==letters[k]){
					
				}
			}
		}
		
		
		return true;
	}

}
