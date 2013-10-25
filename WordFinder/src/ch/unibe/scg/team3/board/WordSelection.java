package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.token.IToken;


/**
 * The instance of this class represents a selection of a word on a board {@see Board}.
 * 
 * @author adrian
 */
public class WordSelection {

	private List<IToken> selection;
	
	public WordSelection(){
		this.selection = new ArrayList<IToken>();
	}
	
	/**
	 * @param tok A token not equal to null
	 */
	public void addToken(IToken tok){
		assert tok != null;
		this.selection.add(tok);
	}
	
	/**
	 * @return the length of the selection is a positive integer
	 */
	public int length(){
		assert selection.size() >= 0;
		return selection.size();
	}
	
	/**
	 * @return The sum of the values from all tokens in this selection, which is positive.
	 */
	public int getScore(){
		
		int sum = 0;
		
		for(IToken tok : this.selection){
			sum += tok.getValue();
		}
		
		assert sum >= 0;
		return sum;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(IToken tok : this.selection){
			sb.append(tok.toString());
		}
		
		return sb.toString();
	}	
}
